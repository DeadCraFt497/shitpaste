package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class583;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AutoWeb
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting placerange = new Setting("PlaceRange", "Place Range", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting placeticks = new Setting("PlaceTicks", "Place Delay", (Module)this, (Object)0, 0, 10);
    private final Setting rotate = new Setting("Rotate", "Rotate", this, false);
    private final Setting feet = new Setting("Feet", "", this, true);
    private final Setting head = new Setting("Head", "", this, false);
    private final Setting actarget = new Setting("ACTarget", "", this, true);
    private final Setting sswitch = new Setting("Switch", "", this, (Object)Class583.Silent);
    private final Setting switchback = new Setting("SwitchBack", "", (Module)this, (Object)true, this.mainwindow, this::Method3082);
    private final Setting raytrace = new Setting("Raytrace", "", this, false);
    private EntityPlayer Field2591 = null;
    private int Field2592 = 0;
    private int Field2593 = -1;
    private int Field2594 = -1;
    @EventHandler
    private final Listener Field2595 = new Listener<Class26>(this::Method3083, new Predicate[0]);

    public AutoWeb() {
        super("AutoWeb", "Automatically place cobwebs to nearby players.", Class11.COMBAT, "");
        this.addSetting(this.placerange);
        this.addSetting(this.placeticks);
        this.addSetting(this.rotate);
        this.addSetting(this.feet);
        this.addSetting(this.head);
        this.addSetting(this.actarget);
        this.addSetting(this.sswitch);
        this.addSetting(this.switchback);
    }

    public void Method3085() {
        super.getEnable();
        this.Field2592 = 0;
    }

    public void Method3086() {
        super.getDisable();
        if (this.Field2593 != -1 && ((Boolean)this.switchback.getValue()).booleanValue() && this.sswitch.getValue() == Class583.Auto) {
            Class155.Method522(this.Field2593, false);
        }
    }

    public String Method3087() {
        return this.Field2591 != null ? ChatFormatting.WHITE + this.Field2591.getName() : null;
    }

    public void Method3088(boolean bl) {
        if (bl == false) {
            this.Method3086();
        }
    }

    private void Method3091() {
        this.Field2591 = this.Method3092();
        if (this.Field2591 == null || this.Field2592 < (Integer)this.placeticks.getValue()) {
            return;
        }
        if (!this.Method3093()) {
            return;
        }
        List list = this.Method3094();
        int n = Class155.Method545(Blocks.WEB);
        if (this.sswitch.getValue() == Class583.Silent && n != -1) {
            this.Field2593 = Globals.mc.player.inventory.currentItem;
            Globals.mc.player.inventory.currentItem = n;
            Globals.mc.playerController.updateController();
        }
        this.Method3095(list);
        if (this.sswitch.getValue() == Class583.Silent && this.Field2593 != -1 && n != -1) {
            Globals.mc.player.inventory.currentItem = this.Field2593;
            Globals.mc.playerController.updateController();
        }
        this.Field2592 = 0;
    }

    private EntityPlayer Method3092() {
        return (Boolean)this.actarget.getValue() != false && Manager.moduleManager.isModuleEnabled(AutoCrystal.class) && ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).entity != null ? ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).entity : Class30.Method1810(((Float)this.placerange.getValue()).floatValue());
    }

    private boolean Method3093() {
        this.Field2593 = Globals.mc.player.inventory.currentItem;
        this.Field2594 = Class155.Method545(Blocks.WEB);
        if (this.Field2594 == -1) {
            return false;
        }
        if (this.sswitch.getValue() == Class583.Auto) {
            Class155.Method522(this.Field2594, false);
        }
        return this.sswitch.getValue() == Class583.Silent || Globals.mc.player.inventory.currentItem == this.Field2594;
    }

    private List Method3094() {
        ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
        Vec3d vec3d = this.Field2591.getPositionVector();
        if (((Boolean)this.feet.getValue()).booleanValue()) {
            arrayList.add(vec3d);
        }
        if (((Boolean)this.head.getValue()).booleanValue()) {
            arrayList.add(vec3d.add(0.0, 1.0, 0.0));
        }
        return arrayList;
    }

    private void Method3095(List<Vec3d> list) {
        list.sort(AutoWeb::Method3096);
        list.sort(Comparator.comparingDouble(AutoWeb::Method3097));
        for (Vec3d vec3d : list) {
            BlockPos blockPos = new BlockPos(vec3d);
            int n = Class31.Method533(blockPos, this.raytrace.getValue());
            if (n != 3 && n != 1) continue;
            Class31.Method536(blockPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, Globals.mc.player.isSneaking());
        }
    }


    private static double Method3097(Vec3d vec3d) {
        return vec3d.y;
    }

    private static int Method3096(Vec3d vec3d, Vec3d vec3d2) {
        return Double.compare(Globals.mc.player.getDistanceSq(vec3d2.x, vec3d2.y, vec3d2.z), Globals.mc.player.getDistanceSq(vec3d.x, vec3d.y, vec3d.z));
    }

    private void Method3083(Class26 class26) {
        this.Method3091();
        ++this.Field2592;
    }

    private boolean Method3082() {
        return this.sswitch.getValue() == Class583.Auto;
    }
}
