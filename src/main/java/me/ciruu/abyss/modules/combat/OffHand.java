package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import me.ciruu.abyss.Class154;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class207;
import me.ciruu.abyss.Class219;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class350;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class598;
import me.ciruu.abyss.enums.Class96;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.combat.AutoCrystal;
import me.ciruu.abyss.modules.combat.HoleFiller;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketEntityAction;

public class OffHand
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public Setting eventmode = new Setting("EventMode", "Event mode", this, (Object)Class598.Update);
    public Setting mode = new Setting("Mode", "Mode", this, (Object)Class96.Totem);
    public Setting strict = new Setting("Strict", "", this, false);
    public Setting nosprint = new Setting("NoSprint", "", this, false);
    public Setting togglehealth = new Setting("ToggleHealth", "Toggle Health", (Module)this, (Object)Float.valueOf(13.0f), Float.valueOf(0.0f), Float.valueOf(36.0f));
    public Setting crystal = new Setting("Crystal", "Crystal", this, new Class207(0));
    public Setting gapple = new Setting("Gapple", "Gapple", this, new Class207(0));
    public Setting hotbarfirst = new Setting("HotbarFirst", "HotbarFirst", this, true);
    public Setting gapplehole = new Setting("GappleHole", "GappleHole", this, false);
    public Setting rightclickgap = new Setting("RightClickGap", "", this, false);
    public Setting crystalAC = new Setting("CrystalAC", "Will put a crystal in your offhand if CA is active", this, false);
    public Setting disableAC = new Setting("DisableAC", "Disable AutoCrystal if your health is below ToggleHealth value", this, false);
    public Setting gapchange = new Setting("GapChange", "Automatically change to gap in hotbar if you healt is below ToggleHealth value", this, false);
    private final Queue Field2484 = new ConcurrentLinkedQueue();
    public static boolean Field852 = false;
    private boolean Field2485 = false;
    private Class96 Field2486 = Class96.Totem;
    private boolean Field2487 = false;
    @EventHandler
    private Listener Field2488 = new Listener<Class26>(this::Method2987, new Predicate[0]);
    @EventHandler
    private Listener Field2489 = new Listener<EventPlayerUpdate>(this::Method2988, new Predicate[0]);
    @EventHandler
    private Listener Field2490 = new Listener<Class350>(this::Method2989, new Predicate[0]);

    public OffHand() {
        super("OffHand", "Automatically put certain items in your offHand.", Class11.COMBAT, "");
        this.addSetting(this.eventmode);
        this.addSetting(this.mode);
        this.addSetting(this.strict);
        this.addSetting(this.nosprint);
        this.addSetting(this.togglehealth);
        this.addSetting(this.crystal);
        this.addSetting(this.gapple);
        this.addSetting(this.hotbarfirst);
        this.addSetting(this.gapplehole);
        this.addSetting(this.rightclickgap);
        this.addSetting(this.crystalAC);
        this.addSetting(this.disableAC);
        this.addSetting(this.gapchange);
    }

    public String Method2991() {
        String string = ((ItemStack)Globals.mc.player.inventoryContainer.getInventory().get(45)).getDisplayName();
        if (Globals.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            int n = Globals.mc.player.inventory.mainInventory.stream()
                    .filter(OffHand::Method2992)
                    .mapToInt(ItemStack::getCount)
                    .sum() + 1;
            string = "Totem(" + n + ")";
        }
        return ChatFormatting.WHITE + string;
    }


    private void Method2993() {
        if (Class30.Method1454((Entity)Globals.mc.player) > ((Float)this.togglehealth.getValue()).floatValue() && ((Boolean)this.rightclickgap.getValue()).booleanValue()) {
            if (Globals.mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE && Globals.mc.gameSettings.keyBindUseItem.pressed && Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && this.mode.getValue() != Class96.Gapple) {
                this.Field2486 = (Class96)((Object)this.mode.getValue());
                this.mode.setValue((Object)Class96.Gapple);
                this.Field2487 = true;
                return;
            }
            if (Globals.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && !Globals.mc.gameSettings.keyBindUseItem.pressed && this.Field2487) {
                this.Field2487 = false;
                if (this.Field2486 != Class96.Gapple) {
                    this.mode.setValue((Object)this.Field2486);
                }
                return;
            }
        }
        if (((HoleFiller)Manager.moduleManager.getModuleByClass(HoleFiller.class)).isEnabled()) {
            isEnabled();
            HoleFiller cfr_ignored_0 = (HoleFiller)Manager.moduleManager.getModuleByClass(HoleFiller.class);
            if (HoleFiller.Field826) {
                HoleFiller cfr_ignored_1 = (HoleFiller)Manager.moduleManager.getModuleByClass(HoleFiller.class);
                if (!HoleFiller.Field827) {
                    this.Method2994(Class96.Obsidian);
                    return;
                }
            }
        }
        if (Class30.Method1454((Entity)Globals.mc.player) < ((Float)this.togglehealth.getValue()).floatValue()) {
            this.Method2994(Class96.Totem);
            if (((Boolean)this.disableAC.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(AutoCrystal.class)) {
                ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).isEnabled();
            }
            if (((Boolean)this.gapchange.getValue()).booleanValue()) {
                Class155.Method1491(ItemAppleGold.class, false);
            }
            Field852 = true;
            return;
        }
        Field852 = false;
        if (((HoleFiller)Manager.moduleManager.getModuleByClass(HoleFiller.class)).isEnabled()) {
            HoleFiller cfr_ignored_2 = (HoleFiller)Manager.moduleManager.getModuleByClass(HoleFiller.class);
            if (HoleFiller.Field826) {
                HoleFiller cfr_ignored_3 = (HoleFiller)Manager.moduleManager.getModuleByClass(HoleFiller.class);
                if (HoleFiller.Field827) {
                    this.Method2994(Class96.Obsidian);
                    return;
                }
            }
        }
        if (((Boolean)this.crystalAC.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(AutoCrystal.class) && !this.Field2487) {
            this.Method2994(Class96.Crystal);
            return;
        }
        if (((Boolean)this.gapplehole.getValue()).booleanValue() && Class30.Method543((Entity)Globals.mc.player)) {
            this.Method2994(Class96.Gapple);
            return;
        }
        this.Method2994((Class96)((Object)this.mode.getValue()));
    }

    private void Method2994(Class96 class96) {
        Item item;
        if (Globals.mc.currentScreen instanceof GuiContainer && !(Globals.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (((Boolean)this.nosprint.getValue()) && Globals.mc.player.isSprinting()) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            this.Field2485 = true;
        }

        int n = -1;
        if (class96 != Class96.Obsidian) {
            item = this.Method2995(class96);
            if (Globals.mc.player.getHeldItemOffhand().getItem() != item) {
                n = ((Boolean)this.hotbarfirst.getValue()) ? OffHand.Method2996(item) : OffHand.Method2997(item);
            }
        } else {
            item = (Item)Item.REGISTRY.getObjectById(49);  // Getting the item by ID 49 (Obsidian)
            n = ((Boolean)this.hotbarfirst.getValue()) ? OffHand.Method2996(item) : OffHand.Method2997(item);
        }

        boolean bl = Globals.mc.player.inventoryContainer.getSlot(45).getHasStack();
        if (n != -1 && Globals.mc.player.getHeldItemOffhand().getItem() != item) {
            if (class96 == Class96.Totem) {
                Globals.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Globals.mc.player);
                Globals.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Globals.mc.player);
                if (bl) {
                    Globals.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Globals.mc.player);
                }
                Globals.mc.playerController.updateController();
                return;
            }
            this.Method2998(n, n);
            if (((Boolean)this.strict.getValue())) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(0, -1, 0, ClickType.CLONE, new ItemStack(Blocks.STONE, 1), (short) -1));
                Globals.mc.player.connection.sendPacket((Packet)new CPacketConfirmTransaction(0, (short) -1, true));
                Globals.mc.player.connection.sendPacket((Packet)new CPacketConfirmTransaction(0, (short) -1, true));
            }
        }

        // Processing any remaining class154 instances
        for (int i = 0; i < 4; ++i) {
            Class154 class154 = (Class154)this.Field2484.poll();
            if (class154 == null) continue;
            class154.Method438();
        }

        // Resuming sprint if needed
        if (((Boolean)this.nosprint.getValue()) && this.Field2485) {
            this.Field2485 = false;
            Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }


    public Item Method2995(Class96 class96) {
        switch (class96) {
            case Crystal: {
                return Items.END_CRYSTAL;
            }
            case Gapple: {
                return Items.GOLDEN_APPLE;
            }
            case Totem: {
                return Items.TOTEM_OF_UNDYING;
            }
        }
        return Items.TOTEM_OF_UNDYING;
    }

    public static int Method2996(Item item) {
        if (Globals.mc.player == null) {
            return 0;
        }
        for (int i = Globals.mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            ItemStack itemStack;
            if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8 || (itemStack = (ItemStack)Globals.mc.player.inventoryContainer.getInventory().get(i)).isEmpty() || itemStack.getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public static int Method2997(Item item) {
        if (Globals.mc.player == null) {
            return 0;
        }
        for (int i = 0; i < Globals.mc.player.inventoryContainer.getInventory().size(); ++i) {
            ItemStack itemStack;
            if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8 || (itemStack = (ItemStack)Globals.mc.player.inventoryContainer.getInventory().get(i)).isEmpty() || itemStack.getItem() != item) continue;
            return i;
        }
        return -1;
    }

    private void Method2998(int n, int n2) {
        if (n != -1 && this.Field2484.isEmpty()) {
            this.Field2484.add(new Class154(n));
            this.Field2484.add(new Class154(45));
            this.Field2484.add(new Class154(n2));
            this.Field2484.add(new Class154());
        }
    }

    private void Method2989(Class350 class350) {
        if (Class30.Method1454((Entity)Globals.mc.player) < ((Float)this.togglehealth.getValue()).floatValue()) {
            return;
        }
        if (!(Globals.mc.currentScreen instanceof Class219)) {
            if (((Class207)this.crystal.getValue()).Method592() == class350.Method1535()) {
                if (this.mode.getValue() == Class96.Crystal) {
                    this.mode.setValue((Object)Class96.Totem);
                } else {
                    this.mode.setValue((Object)Class96.Crystal);
                }
            }
            if (((Class207)this.gapple.getValue()).Method592() == class350.Method1535()) {
                if (this.mode.getValue() == Class96.Gapple) {
                    this.mode.setValue((Object)Class96.Totem);
                } else {
                    this.mode.setValue((Object)Class96.Gapple);
                }
            }
        }
    }

    private void Method2988(EventPlayerUpdate eventPlayerUpdate) {
        if (this.eventmode.getValue() == Class598.Update) {
            this.Method2993();
        }
    }

    private void Method2987(Class26 class26) {
        if (this.eventmode.getValue() == Class598.Tick) {
            this.Method2993();
        }
    }

    private static boolean Method2992(ItemStack itemStack) {
        return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
    }
}
