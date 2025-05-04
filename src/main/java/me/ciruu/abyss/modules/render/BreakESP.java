package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.init.Blocks;

public class BreakESP
extends Module {
    private final Setting color = new Setting("Color", "", this, new Color(255, 0, 255, 125));
    private final Setting onlyobsidian = new Setting("OnlyObsidian", "", this, true);
    private final Setting ignoreself = new Setting("IgnoreSelf", "", this, true);
    private final Setting progress = new Setting("Progress", "", this, true);
    private final Setting percentage = new Setting("Percentage", "", this, true);
    private final Setting name = new Setting("Name", "", this, true);
    @EventHandler
    private Listener Field1329 = new Listener<Class66>(this::Method1745, new Predicate[0]);

    public BreakESP() {
        super("BreakESP", "Highlights the blocks that people break.", Class11.RENDER, "");
        this.addSetting(this.color);
        this.addSetting(this.onlyobsidian);
        this.addSetting(this.ignoreself);
        this.addSetting(this.progress);
        this.addSetting(this.percentage);
        this.addSetting(this.name);
    }

    private void Method1747() {
        Globals.mc.renderGlobal.damagedBlocks.forEach(this::Method1748);
    }

    private void Method1748(Integer n, DestroyBlockProgress destroyBlockProgress) {
        if (destroyBlockProgress == null) {
            return;
        }
        if (((Boolean)this.ignoreself.getValue()).booleanValue() && Globals.mc.world.getEntityByID(n.intValue()) == Globals.mc.player) {
            return;
        }
        if (((Boolean)this.onlyobsidian.getValue()).booleanValue() && Globals.mc.world.getBlockState(destroyBlockProgress.getPosition()).getBlock() != Blocks.OBSIDIAN) {
            return;
        }
        Class50.Method137(destroyBlockProgress.getPosition(), (Color)this.color.getValue(), false, (Color)this.color.getValue(), 1.0f, true, true, (Boolean)this.progress.getValue() != false ? destroyBlockProgress.getPartialBlockDamage() * 25 + 5 : ((Color)this.color.getValue()).getAlpha(), false);
        if (((Boolean)this.name.getValue()).booleanValue()) {
            Class50.Method848(destroyBlockProgress.getPosition(), Globals.mc.world.getEntityByID(n.intValue()) != null ? Globals.mc.world.getEntityByID(n.intValue()).getName() : "", (Boolean)this.percentage.getValue() != false ? 0.7f : 0.5f);
        }
        if (((Boolean)this.percentage.getValue()).booleanValue()) {
            Class50.Method848(destroyBlockProgress.getPosition(), destroyBlockProgress.getPartialBlockDamage() * 10 + "%", (Boolean)this.name.getValue() != false ? 0.3f : 0.5f);
        }
    }

    private void Method1745(Class66 class66) {
        this.Method1747();
    }
}
