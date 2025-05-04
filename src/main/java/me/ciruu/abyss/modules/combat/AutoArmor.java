package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import me.ciruu.abyss.Class154;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class207;
import me.ciruu.abyss.Class219;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class350;
import me.ciruu.abyss.Class352;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.misc.XCarry;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

public class AutoArmor
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting delay = new Setting("Delay(ms)", "", (Module)this, (Object)50, 0, 500);
    private final Setting automend = new Setting("AutoMend", "", this, true);
    private final Setting enemydistance = new Setting("EnemyDistance", "", this, 1, 1, 20, this.mainwindow, this.automend::getValue);
    private final Setting mendkey = new Setting("MendKey", "", (Module)this, (Object)new Class207(0), this.mainwindow, this.automend::getValue);
    public final Setting armor = new Setting("Armor%", "", (Module)this, (Object)100, 1, 100);
    private final Setting curseOfBinding = new Setting("CurseOfBinding", "", this, false);
    private final Setting actions = new Setting("Actions", "", (Module)this, (Object)3, 1, 12);
    private final Setting elytraswap = new Setting("ElytraSwap", "", this, new Class207(0));
    private final Setting shiftclick = new Setting("ShiftClick", "", this, false);
    private final Class22 clazz1 = new Class22();
    private final Class22 clazz2 = new Class22();
    private final Queue qu1 = new ConcurrentLinkedQueue();
    private final List list = new ArrayList();
    private boolean bool1 = false;
    private boolean bool2 = false;
    private int int1 = -1;
    @EventHandler
    private Listener listen1 = new Listener<Class350>(this::getScreenMethod, new Predicate[0]);
    @EventHandler
    private Listener listen2 = new Listener<EventPlayerUpdateWalking>(this::getArmorCleanMethod, new Predicate[0]);
    public boolean bl;

    public AutoArmor() {
        super("AutoArmor", "Automatically puts on armor.", Class11.COMBAT, "");
        this.addSetting(this.delay);
        this.addSetting(this.automend);
        this.addSetting(this.enemydistance);
        this.addSetting(this.mendkey);
        this.addSetting(this.armor);
        this.addSetting(this.curseOfBinding);
        this.addSetting(this.elytraswap);
    }

    public void getDisableMethod() {
        super.getEnable();
        this.clazz2.getNanoTime();
        this.clazz1.getNanoTime();
        this.list.clear();
        this.qu1.clear();
    }

    public void getDisabledBoolMethod() {
        super.getDisable();
        this.qu1.clear();
        this.list.clear();
        this.bool1 = false;
    }

    public String getChatMethod() {
        return ChatFormatting.WHITE + (this.bool1 ? "Elytra" : "Armor");
    }

    private void getAutoArmorMethod(int n) {
        if (this.qu1.isEmpty()) {
            int n2 = -1;
            Iterator iterator = Class155.Method1807(Manager.moduleManager.isModuleEnabled(XCarry.class)).iterator();
            while (iterator.hasNext()) {
                int n3 = (Integer)iterator.next();
                if (this.list.contains(n2)) continue;
                n2 = n3;
                this.list.add(n3);
            }
            if (n2 != -1) {
                if (n2 < 5 && n2 > 0 || !((Boolean)this.shiftclick.getValue()).booleanValue()) {
                    this.qu1.add(new Class154(n));
                    this.qu1.add(new Class154(n2));
                } else {
                    this.qu1.add(new Class154(n, true));
                }
                this.qu1.add(new Class154());
            }
        }
    }

    private void getItemMethod(int n, int n2) {
        if (this.qu1.isEmpty()) {
            this.list.remove((Object)n2);
            if (n2 < 5 && n2 > 0 || !((Boolean)this.shiftclick.getValue()).booleanValue()) {
                this.qu1.add(new Class154(n2));
                this.qu1.add(new Class154(n));
            } else {
                this.qu1.add(new Class154(n2, true));
            }
            this.qu1.add(new Class154());
        }
    }

    private boolean getEnemyMethod() {
        try {
            EntityPlayer entityPlayer = Class30.Method1810(((Integer)this.enemydistance.getValue()).intValue());
            if (entityPlayer == null) {
                return true;
            }
            bl = Globals.mc.player.getDistanceSq((Entity)entityPlayer) >= Class29.getDouble6(((Integer)this.enemydistance.getValue()).intValue());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return bl;
    }

    public static int getItemMethod() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Globals.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || itemStack.getItem() != Items.EXPERIENCE_BOTTLE) continue;
            return i;
        }
        return -1;
    }

    private void getArmorCleanMethod(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (Globals.mc.player == null || Globals.mc.world == null || eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        if (Globals.mc.currentScreen instanceof GuiContainer && !(Globals.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        boolean bl = Keyboard.isKeyDown((int)((Class207)this.mendkey.getValue()).Method592());
        this.int1 = Globals.mc.player.inventory.currentItem;
        if (this.bool2 && !bl && this.int1 != -1) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.int1));
            this.bool2 = false;
        }
        if (this.qu1.isEmpty()) {
            int n;
            ItemStack itemStack;
            ItemStack itemStack2;
            int n2;
            int n3;
            ItemStack itemStack3;
            if (((Boolean) this.automend.getValue()).booleanValue() && (Class155.Method1812(ItemExpBottle.class) && Globals.mc.gameSettings.keyBindUseItem.isKeyDown() || bl) && this.getEnemyMethod()) {
                int n4;
                int n5;
                int n6;
                int n7;
                itemStack3 = Globals.mc.player.inventoryContainer.getSlot(5).getStack();
                n3 = 0;
                if (!itemStack3.isEmpty && (n7 = Class352.Method1813(itemStack3)) >= (Integer) this.armor.getValue()) {
                    n3 = 1;
                }
                ItemStack itemStack4 = Globals.mc.player.inventoryContainer.getSlot(6).getStack();
                n2 = 0;
                if (!itemStack4.isEmpty && (n6 = Class352.Method1813(itemStack4)) >= (Integer) this.armor.getValue()) {
                    n2 = 1;
                }
                ItemStack itemStack5 = Globals.mc.player.inventoryContainer.getSlot(7).getStack();
                boolean bl2 = false;
                if (!itemStack5.isEmpty && (n5 = Class352.Method1813(itemStack5)) >= (Integer) this.armor.getValue()) {
                    bl2 = true;
                }
                ItemStack itemStack6 = Globals.mc.player.inventoryContainer.getSlot(8).getStack();
                boolean bl3 = false;
                if (!itemStack6.isEmpty && (n4 = Class352.Method1813(itemStack6)) >= (Integer) this.armor.getValue()) {
                    bl3 = true;
                }
                int n8 = n4 = !(n3 == 0 && !itemStack3.isEmpty || n2 == 0 && !itemStack4.isEmpty || !bl2 && !itemStack5.isEmpty || !bl3 && !itemStack6.isEmpty) ? 1 : 0;
                if (bl && !this.bool2 && n4 == 0) {
                    boolean bl4 = !Class155.Method1812(ItemExpBottle.class);
                    int n9 = AutoArmor.getItemMethod();
                    if (bl4 && n9 != -1) {
                        this.bool2 = true;
                        Class155.Method522(n9, true);
                    }
                }
                if ((this.bool2 || Class155.Method1812(ItemExpBottle.class)) && n4 == 0 && bl) {
                    Globals.mc.player.connection.sendPacket((Packet) new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    eventPlayerUpdateWalking.setPitch(90.0f);
                    eventPlayerUpdateWalking.cancel();
                }
                if (n4 == 0) {
                    if (n3 != 0) {
                        this.getAutoArmorMethod(5);
                    }
                    if (n2 != 0) {
                        this.getAutoArmorMethod(6);
                    }
                    if (bl2) {
                        this.getAutoArmorMethod(7);
                    }
                    if (bl3) {
                        this.getAutoArmorMethod(8);
                    }
                    return;
                }
            }
            if ((itemStack3 = Globals.mc.player.inventoryContainer.getSlot(5).getStack()).getItem() == Items.AIR && (n3 = Class155.Method1814(EntityEquipmentSlot.HEAD, (Boolean) this.curseOfBinding.getValue(), Manager.moduleManager.isModuleEnabled(XCarry.class))) != -1) {
                this.getItemMethod(5, n3);
            }
            Object object_;
            Item object = null;
            if ((object_ = Globals.mc.player.inventoryContainer.getSlot(6).getStack()) == Items.AIR) {
                if (this.qu1.isEmpty()) {
                    int n10;
                    if (this.bool1 && this.clazz2.getLongNanoTime(500L)) {
                        int n11 = Class155.Method1815(Items.ELYTRA, false, Manager.moduleManager.isModuleEnabled(XCarry.class));
                        if (n11 != -1) {
                            if (n11 < 5 && n11 > 1 || !((Boolean) this.shiftclick.getValue()).booleanValue()) {
                                this.qu1.add(new Class154(n11));
                                this.qu1.add(new Class154(6));
                            } else {
                                this.qu1.add(new Class154(n11, true));
                            }
                            this.qu1.add(new Class154());
                            this.clazz2.getNanoTime();
                        }
                    } else if (!this.bool1 && (n10 = Class155.Method1814(EntityEquipmentSlot.CHEST, (Boolean) this.curseOfBinding.getValue(), Manager.moduleManager.isModuleEnabled(XCarry.class))) != -1) {
                        this.getItemMethod(6, n10);
                    }
                }
            } else if (this.bool1 && object != Items.ELYTRA && this.clazz2.getLongNanoTime(500L)) {
                if (this.qu1.isEmpty()) {
                    int n12 = Class155.Method1815(Items.ELYTRA, false, Manager.moduleManager.isModuleEnabled(XCarry.class));
                    if (n12 != -1) {
                        this.qu1.add(new Class154(n12));
                        this.qu1.add(new Class154(6));
                        this.qu1.add(new Class154(n12));
                        this.qu1.add(new Class154());
                    }
                    this.clazz2.getNanoTime();
                }
                object = null;
            } else if (!this.bool1 && object == Items.ELYTRA && this.clazz2.getLongNanoTime(500L) && this.qu1.isEmpty()) {
                int n13 = Class155.Method1815((Item) Items.DIAMOND_CHESTPLATE, false, Manager.moduleManager.isModuleEnabled(XCarry.class));
                if (n13 == -1 && (n13 = Class155.Method1815((Item) Items.IRON_CHESTPLATE, false, Manager.moduleManager.isModuleEnabled(XCarry.class))) == -1 && (n13 = Class155.Method1815((Item) Items.GOLDEN_CHESTPLATE, false, Manager.moduleManager.isModuleEnabled(XCarry.class))) == -1 && (n13 = Class155.Method1815((Item) Items.CHAINMAIL_CHESTPLATE, false, Manager.moduleManager.isModuleEnabled(XCarry.class))) == -1) {
                    n13 = Class155.Method1815((Item) Items.LEATHER_CHESTPLATE, false, Manager.moduleManager.isModuleEnabled(XCarry.class));
                }
                if (n13 != -1) {
                    this.qu1.add(new Class154(n13));
                    this.qu1.add(new Class154(6));
                    this.qu1.add(new Class154(n13));
                    this.qu1.add(new Class154());
                }
                this.clazz2.getNanoTime();
            }
            if ((itemStack2 = Globals.mc.player.inventoryContainer.getSlot(7).getStack()).getItem() == Items.AIR && (n2 = Class155.Method1814(EntityEquipmentSlot.LEGS, (Boolean) this.curseOfBinding.getValue(), Manager.moduleManager.isModuleEnabled(XCarry.class))) != -1) {
                this.getItemMethod(7, n2);
            }
            if ((itemStack = Globals.mc.player.inventoryContainer.getSlot(8).getStack()).getItem() == Items.AIR && (n = Class155.Method1814(EntityEquipmentSlot.FEET, (Boolean) this.curseOfBinding.getValue(), Manager.moduleManager.isModuleEnabled(XCarry.class))) != -1) {
                this.getItemMethod(8, n);
            }
        }
        if (this.clazz1.getLongNanoTime(((Integer)this.delay.getValue()).intValue())) {
            if (!this.qu1.isEmpty()) {
                for (int i = 0; i < (Integer)this.actions.getValue(); ++i) {
                    Class154 object = (Class154) this.qu1.poll();
                    if (object == null) continue;
                    ((Class154)object).Method438();
                }
            }
            this.clazz1.getNanoTime();
        }
    }

    private void getScreenMethod(Class350 class350) {
        if (!(Globals.mc.currentScreen instanceof Class219) && ((Class207)this.elytraswap.getValue()).Method592() == class350.Method1535()) {
            this.bool1 = !this.bool1;
        }
    }
}
