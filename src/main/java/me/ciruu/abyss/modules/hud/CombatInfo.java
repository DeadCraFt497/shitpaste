package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.Comparator;
import java.util.function.Predicate;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.combat.AutoCrystal;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CombatInfo
extends Module {
    private final Setting x = new Setting("X", "X", (Module)this, (Object)1, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)200, 0, 1000);
    private final Setting acTarget = new Setting("ACTarget", "", this, true);
    @EventHandler
    private Listener Field1978 = new Listener<Class35>(this::Method2383, new Predicate[0]);

    public CombatInfo() {
        super("CombatInfo", "", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.acTarget);
    }

    private void Method2383(Class35 class35) {
        int n = Globals.mc.player.inventory.mainInventory.stream().filter(CombatInfo::Method2385).mapToInt(ItemStack::getCount).sum();
        if (Globals.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++n;
        }
        EntityPlayer entityPlayer = Globals.mc.world.playerEntities.stream().filter(CombatInfo::Method2386).filter(CombatInfo::Method2387).min(Comparator.comparing(CombatInfo::Method2388)).orElse(null);
        float f = ((Integer)this.y.getValue()).intValue();
        if (((Boolean)this.acTarget.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(AutoCrystal.class) && ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).entity != null) {
            entityPlayer = ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).entity;
        }
        Class36.Method63("HTR", ((Integer)this.x.getValue()).intValue(), f, entityPlayer != null && Globals.mc.player.getDistance((Entity)entityPlayer) <= ((Float)((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).breakrange.getValue()).floatValue() ? Color.GREEN.getRGB() : Color.RED.getRGB());
        Class36.Method63("PLR", ((Integer)this.x.getValue()).intValue(), f += 10.0f, entityPlayer != null && Globals.mc.player.getDistance((Entity)entityPlayer) <= ((Float)((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).placerange.getValue()).floatValue() ? Color.GREEN.getRGB() : Color.RED.getRGB());
        Class36.Method63(String.valueOf(n), ((Integer)this.x.getValue()).intValue(), f += 10.0f, n > 0 ? Color.GREEN.getRGB() : Color.RED.getRGB());
        Class36.Method63(String.valueOf(Manager.Field1645.Method2389()), ((Integer)this.x.getValue()).intValue(), f += 10.0f, Manager.Field1645.Method2389() <= 100 ? Color.GREEN.getRGB() : Color.RED.getRGB());
        Class36.Method63("LBY", ((Integer)this.x.getValue()).intValue(), f += 10.0f, entityPlayer != null && Class30.Method543((Entity)entityPlayer) ? Color.GREEN.getRGB() : Color.RED.getRGB());
        f += 10.0f;
    }

    private static Float Method2388(EntityPlayer entityPlayer) {
        return Float.valueOf(Globals.mc.player.getDistance((Entity)entityPlayer));
    }

    private static boolean Method2387(EntityPlayer entityPlayer) {
        return !Manager.Field223.Method233(entityPlayer.getName());
    }

    private static boolean Method2386(EntityPlayer entityPlayer) {
        return !entityPlayer.getName().equals(Globals.mc.player.getName());
    }

    private static boolean Method2385(ItemStack itemStack) {
        return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
    }
}
