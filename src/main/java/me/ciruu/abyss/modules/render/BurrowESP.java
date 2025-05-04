package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class BurrowESP
extends Module {
    private final Setting radius = new Setting("Radius", "", (Module)this, (Object)8, 0, 30);
    private final Setting color = new Setting("Color", "", this, new Color(255, 0, 0, 125));
    private final Setting ignoreself = new Setting("Ignore Self", "", this, true);
    private final Setting onlyobsidian = new Setting("Only Obsidian", "", this, true);
    @EventHandler
    private Listener Field1526 = new Listener<Class66>(this::Method1932, new Predicate[0]);

    public BurrowESP() {
        super("BurrowESP", "Highlights the blocks that people are inside.", Class11.RENDER, "");
        this.addSetting(this.radius);
        this.addSetting(this.color);
        this.addSetting(this.ignoreself);
        this.addSetting(this.onlyobsidian);
    }

    private void Method1934() {
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
            if (Globals.mc.player.getDistance((Entity)entityPlayer) > (float)((Integer)this.radius.getValue()).intValue() || ((Boolean)this.ignoreself.getValue()).booleanValue() && entityPlayer == Globals.mc.player) continue;
            if (((Boolean)this.onlyobsidian.getValue()).booleanValue() && Globals.mc.world.getBlockState(Class30.Method209(entityPlayer)).getBlock() == Blocks.OBSIDIAN) {
                Class50.Method137(Class30.Method209(entityPlayer), (Color)this.color.getValue(), false, (Color)this.color.getValue(), 1.0f, true, true, ((Color)this.color.getValue()).getAlpha(), false);
                continue;
            }
            if (((Boolean)this.onlyobsidian.getValue()).booleanValue() || Globals.mc.world.getBlockState(Class30.Method209(entityPlayer)).getBlock().isReplaceable((IBlockAccess)Globals.mc.world, Class30.Method209(entityPlayer))) continue;
            Class50.Method137(Class30.Method209(entityPlayer), (Color)this.color.getValue(), false, (Color)this.color.getValue(), 1.0f, true, true, ((Color)this.color.getValue()).getAlpha(), false);
        }
    }

    private void Method1932(Class66 class66) {
        this.Method1934();
    }
}
