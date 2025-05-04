package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WebESP
extends Module {
    private final Setting radius = new Setting("Radious", "", (Module)this, (Object)8, 0, 30);
    private final Setting webcolor = new Setting("Web color", "", this, new Color(255, 0, 0, 125));
    @EventHandler
    private Listener Field301 = new Listener<Class66>(this::Method338, new Predicate[0]);

    public WebESP() {
        super("WebESP", "", Class11.RENDER, "");
        this.addSetting(this.radius);
        this.addSetting(this.webcolor);
    }

    private void Method338(Class66 class66) {
        Class31.Method210(Class30.Method209((EntityPlayer)Globals.mc.player), ((Integer)this.radius.getValue()).intValue(), (Integer)this.radius.getValue(), false, true, 0).stream().filter(null).forEach(null);
    }

    private void Method341(BlockPos blockPos) {
        Class50.Method137(blockPos, (Color)this.webcolor.getValue(), false, (Color)this.webcolor.getValue(), 1.0f, true, true, ((Color)this.webcolor.getValue()).getAlpha(), false);
    }

    private static boolean isBlockWeb(BlockPos blockPos) {
        return Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.WEB;
    }
}
