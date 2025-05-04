package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.Serializable;
import java.util.function.Predicate;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class352;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class456;
import me.ciruu.abyss.enums.Class512;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class575;
import me.ciruu.abyss.enums.Class578;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.movement.Scaffold;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

public class KillAura
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting sswitch = new Setting("Switch", "", this, (Object) Class578.Auto);
    private final Setting range = new Setting("Range", "", (Module) this, (Object) Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(6.0f));
    private final Setting target = new Setting("Target", "", this, (Object) Class456.Closest);
    private final Setting health = new Setting("Health", "", this, Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(36.0f), this.mainwindow, this::Method2880);
    private final Setting delay = new Setting("Delay", "", this, true);
    private final Setting ticks = new Setting("Ticks", "", this, 0, 0, 20, this.mainwindow, this::Method2881);
    private final Setting rotate = new Setting("Rotate", "", this, false);
    private final Setting packetrotate = new Setting("PacketRot", "", (Module) this, (Object) true, this.mainwindow, this.rotate::getValue);
    private final Setting armorbreak = new Setting("ArmorBreak", "", this, false);
    private final Setting stopeating = new Setting("StopEating", "", this, false);
    private final Setting onlysword = new Setting("OnlySword", "", this, true);
    private final Setting raytrace = new Setting("Raytrace", "", (Module) this, (Object) Float.valueOf(3.0f), Float.valueOf(0.1f), Float.valueOf(6.0f));
    private final Setting players = new Setting("Players", "", this, true);
    private final Setting mobs = new Setting("Mobs", "", this, false);
    private final Setting animals = new Setting("Animals", "", this, false);
    private final Setting entities = new Setting("Entities", "", this, false);
    private final Setting projectiles = new Setting("Projectiles", "", this, false);
    private final Setting tpssync = new Setting("TpsSync", "", this, true);
    public final Setting toggleOnAutoCrystal = new Setting("ToggleOnAC", "", this, true);
    private final Setting autocriticals = new Setting("AutoCriticals", "", this, true);
    private final Setting switchback = new Setting("SwitchBack", "", (Module) this, (Object) true, this.mainwindow, this::Method2882);
    private final Setting metadata = new Setting("Metadata", "", this, (Object) Class512.Target);
    private final Class22 Field2322 = new Class22();
    public static Entity Field1388;
    private int Field2323 = -1;
    private int Field2324 = 0;
    private boolean Field2325 = false;
    @EventHandler
    private Listener Field2326 = new Listener<Class26>(this::Method2883, new Predicate[0]);
    @EventHandler
    private Listener Field2327 = new Listener<EventPlayerUpdateWalking>(this::Method2884, new Predicate[0]);

    public KillAura() {
        super("KillAura", "Attack nearby entities.", Class11.COMBAT, "");
        this.addSetting(this.sswitch);
        this.addSetting(this.range);
        this.addSetting(this.target);
        this.addSetting(this.health);
        this.addSetting(this.delay);
        this.addSetting(this.ticks);
        this.addSetting(this.rotate);
        this.addSetting(this.packetrotate);
        this.addSetting(this.armorbreak);
        this.addSetting(this.stopeating);
        this.addSetting(this.onlysword);
        this.addSetting(this.raytrace);
        this.addSetting(this.players);
        this.addSetting(this.mobs);
        this.addSetting(this.animals);
        this.addSetting(this.entities);
        this.addSetting(this.projectiles);
        this.addSetting(this.tpssync);
        this.addSetting(this.toggleOnAutoCrystal);
        this.addSetting(this.autocriticals);
        this.addSetting(this.switchback);
        this.addSetting(this.metadata);
    }

    public void Method2886(boolean bl) {
        if (true) {
            bl = false;
            this.Method2889();
        }
    }

    public void Method2890() {
        super.getEnable();
        if (((Boolean) this.toggleOnAutoCrystal.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(AutoCrystal.class)) {
            ((AutoCrystal) Manager.moduleManager.getModuleByClass(AutoCrystal.class)).getTarget();
        }
        this.Field2325 = false;
        if (((Boolean) this.autocriticals.getValue()).booleanValue()) {
            if (!Manager.moduleManager.isModuleEnabled(Criticals.class)) {
                ((Criticals) Manager.moduleManager.getModuleByClass(Criticals.class)).getSettings();
            } else {
                this.Field2325 = true;
            }
        }
        if (this.sswitch.getValue() == Class578.Auto && !(Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
            this.Field2323 = Globals.mc.player.inventory.currentItem;
            int n = this.Method2892(Items.DIAMOND_SWORD);
            int n2 = this.Method2892(Items.DIAMOND_AXE);
            if (n != -1) {
                Globals.mc.player.inventory.currentItem = n;
                return;
            }
            if (n2 != -1) {
                Globals.mc.player.inventory.currentItem = n2;
            }
        }
        this.Field2324 = 0;
    }

    private int Method2892(Item item) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Globals.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || itemStack.getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public void Method2889() {
        super.getDisable();
        if (((Boolean) this.autocriticals.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(Criticals.class) && !this.Field2325) {
            ((Criticals) Manager.moduleManager.getModuleByClass(Criticals.class)).enable();
        }
        if (this.sswitch.getValue() == Class578.Auto && ((Boolean) this.switchback.getValue()).booleanValue() && this.Field2323 != -1) {
            Globals.mc.player.inventory.currentItem = this.Field2323;
        }
    }

    public String Method2893() {
        if (Field1388 instanceof EntityPlayer && this.metadata.getValue() == Class512.Target) {
            return ChatFormatting.WHITE + Field1388.getName();
        }
        return ChatFormatting.WHITE + "R:" + this.range.getValue() + " | Delay:" + ((Boolean) this.delay.getValue() != false ? "Auto" : (Serializable) this.ticks.getValue());
    }

    private void Method2894() {
        int n = (Integer)null;
        if (((Boolean) this.onlysword.getValue()).booleanValue() && !Class30.Method2758((EntityPlayer) Globals.mc.player) || Manager.moduleManager.isModuleEnabled(Scaffold.class)) {
            Field1388 = null;
            return;
        }
        int n2 = (Boolean) this.delay.getValue() == false ? 0 : (n = (int) ((float) Class352.Method1878((EntityPlayer) Globals.mc.player) * ((Boolean) this.tpssync.getValue() != false ? Manager.Field1645.Method2895() : 1.0f)));
        if (!this.Field2322.getLongNanoTime(n) || ((Boolean) this.stopeating.getValue()).booleanValue() && Globals.mc.player.isHandActive() && (!Globals.mc.player.getHeldItemOffhand().getItem().equals(Items.SHIELD) || Globals.mc.player.getActiveHand() != EnumHand.OFF_HAND)) {
            return;
        }
        if (this.Field2324 < (Integer) this.ticks.getValue() && !((Boolean) this.delay.getValue()).booleanValue()) {
            return;
        }
        if (this.target.getValue() != Class456.Focus || Field1388 == null || !(Globals.mc.player.getDistanceSq(Field1388) < Class29.getDouble6(((Float) this.range.getValue()).floatValue())) || !Globals.mc.player.canEntityBeSeen(Field1388) && !Class30.Method2757(Field1388) && !(Globals.mc.player.getDistanceSq(Field1388) < Class29.getDouble6(((Float) this.raytrace.getValue()).floatValue()))) {
            Field1388 = this.Method2896();
        }
        if (Field1388 == null) {
            return;
        }
        if (((Boolean) this.rotate.getValue()).booleanValue() && !((Boolean) this.packetrotate.getValue()).booleanValue()) {
            Manager.Field456.Method2897(Field1388);
        }
        if (Manager.moduleManager.isModuleEnabled(Criticals.class) && (((Criticals) Manager.moduleManager.getModuleByClass(Criticals.class)).mode.getValue() == Class575.Jump || ((Criticals) Manager.moduleManager.getModuleByClass(Criticals.class)).mode.getValue() == Class575.MiniJump)) {
            if (Globals.mc.player.onGround) {
                Globals.mc.player.jump();
                if (((Criticals) Manager.moduleManager.getModuleByClass(Criticals.class)).mode.getValue() == Class575.MiniJump) {
                    Globals.mc.player.motionY /= 2.0;
                }
                return;
            }
            if (Globals.mc.player.motionY > -0.2) {
                return;
            }
        }
        if (((Boolean) this.armorbreak.getValue()).booleanValue()) {
            Globals.mc.playerController.windowClick(Globals.mc.player.inventoryContainer.windowId, 9, Globals.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer) Globals.mc.player);
            Class30.Method1524(Field1388, false, true);
            Globals.mc.playerController.windowClick(Globals.mc.player.inventoryContainer.windowId, 9, Globals.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer) Globals.mc.player);
            Class30.Method1524(Field1388, false, true);
        } else {
            Class30.Method1524(Field1388, false, true);
        }
        this.Field2324 = 0;
        this.Field2322.getNanoTime();
    }

    private Entity Method2896() {
        Entity entity = null;
        double d = ((Float) this.range.getValue()).floatValue();
        double d2 = 36.0;
        for (Entity entity2 : Globals.mc.world.loadedEntityList) {
            if (!((Boolean) this.players.getValue() != false && entity2 instanceof EntityPlayer || (Boolean) this.animals.getValue() != false && Class30.Method231(entity2) || (Boolean) this.mobs.getValue() != false && Class30.Method2722(entity2) || (Boolean) this.entities.getValue() != false && Class30.Method2726(entity2)) && (!((Boolean) this.projectiles.getValue()).booleanValue() || !Class30.Method2725(entity2)) || entity2 instanceof EntityLivingBase && Class30.Method749(entity2, d) || !Globals.mc.player.canEntityBeSeen(entity2) && Globals.mc.player.getDistanceSq(entity2) > Class29.getDouble6(((Float) this.raytrace.getValue()).floatValue()))
                continue;
            if (entity == null) {
                entity = entity2;
                d = Globals.mc.player.getDistanceSq(entity2);
                d2 = Class30.Method1454(entity2);
                continue;
            }
            if (entity2 instanceof EntityPlayer && Class352.Method1472((EntityPlayer) entity2, 18)) {
                entity = entity2;
                break;
            }
            if (this.target.getValue() == Class456.Smart && Class30.Method1454(entity2) < ((Float) this.health.getValue()).floatValue()) {
                entity = entity2;
                break;
            }
            if (this.target.getValue() != Class456.Health && Globals.mc.player.getDistanceSq(entity2) < d) {
                entity = entity2;
                d = Globals.mc.player.getDistanceSq(entity2);
                d2 = Class30.Method1454(entity2);
            }
            if (this.target.getValue() != Class456.Health || !((double) Class30.Method1454(entity2) < d2)) continue;
            entity = entity2;
            d = Globals.mc.player.getDistanceSq(entity2);
            d2 = Class30.Method1454(entity2);
        }
        return entity;
    }

    private void Method2884(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (eventPlayerUpdateWalking.getClass53() == Class53.PRE && ((Boolean) this.rotate.getValue()).booleanValue()) {
            if (Field1388 != null) {
                if (((Boolean) this.packetrotate.getValue()).booleanValue()) {
                    float[] fArray = Class29.getFloatVector(Globals.mc.player.getPositionEyes(Globals.mc.getRenderPartialTicks()), Field1388.getPositionEyes(Globals.mc.getRenderPartialTicks()));
                    eventPlayerUpdateWalking.setYaw(fArray[0]);
                    eventPlayerUpdateWalking.setPitch(fArray[1]);
                    eventPlayerUpdateWalking.cancel();
                    Class202.Method934(fArray[1], fArray[0]);
                } else {
                    Manager.Field456.Method2897(Field1388);
                }
            }
            this.Method2894();
            ++this.Field2324;
        }
    }

    private void Method2883(Class26 class26) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (!((Boolean) this.rotate.getValue()).booleanValue()) {
            this.Method2894();
            ++this.Field2324;
        }
    }

    private boolean Method2882() {
        return this.sswitch.getValue() == Class578.Auto;
    }

    private boolean Method2881() {
        return (Boolean) this.delay.getValue() == false;
    }

    private boolean Method2880() {
        return this.target.getValue() == Class456.Smart;
    }
}
