package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Radar
extends Module {
    private final Setting x = new Setting("X", "X", (Module)this, (Object)10, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)150, 0, 1000);
    private final Setting width = new Setting("Width", "", (Module)this, (Object)60, 1, 300);
    private final Setting height = new Setting("Height", "", (Module)this, (Object)60, 1, 300);
    private final Setting radius = new Setting("Radius", "", (Module)this, (Object)2, 1, 5);
    private final Setting zoom = new Setting("Zoom", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(4.0f));
    private final Setting playersCount = new Setting("PlayersCount", "Count players inside & out of the radar", this, true);
    private final Setting friendColor = new Setting("FriendColor", "", this, Color.CYAN);
    private final Setting enemyColor = new Setting("EnemyColor", "", this, Color.RED);
    private final Setting bgColor = new Setting("BgColor", "", this, new Color(0, 0, 0, 125));
    @EventHandler
    private Listener Field3176 = new Listener<Class35>(this::Method3850, new Predicate[0]);

    public Radar() {
        super("Radar", "Displays nearby entities in a radar.", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.width);
        this.addSetting(this.height);
        this.addSetting(this.radius);
        this.addSetting(this.zoom);
        this.addSetting(this.playersCount);
        this.addSetting(this.friendColor);
        this.addSetting(this.enemyColor);
        this.addSetting(this.bgColor);
    }

    private Color Method3852(EntityPlayer entityPlayer) {
        return Manager.Field223.Method711((Entity)entityPlayer) ? (Color)this.friendColor.getValue() : (Color)this.enemyColor.getValue();
    }

    private double[] Method3853(double d, float f, float f2) {
        double d2 = Math.sin(d);
        double d3 = Math.cos(d);
        return new double[]{(double)f * d3 - (double)f2 * d2, (double)f * d2 + (double)f2 * d3};
    }

    private void Method3850(Class35 class35) {
        if (((Boolean)this.playersCount.getValue()).booleanValue()) {
            Class36.Method63("Players:" + (Globals.mc.world.playerEntities.size() - 1), (Integer)this.x.getValue() + 5, (Integer)this.y.getValue() - 10, Color.WHITE.getRGB());
        }
        Class50.Method92(((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), (Integer)this.x.getValue() + (Integer)this.width.getValue(), (Integer)this.y.getValue() + (Integer)this.height.getValue(), ((Color)this.bgColor.getValue()).getRGB());
        Class50.Method861((Integer)this.x.getValue() + (Integer)this.width.getValue(), (Integer)this.y.getValue() + (Integer)this.height.getValue(), ((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), 1.0f, new Color(((Color)this.bgColor.getValue()).getRed(), ((Color)this.bgColor.getValue()).getGreen(), ((Color)this.bgColor.getValue()).getBlue(), 255).getRGB());
        Class50.Method802((float)((Integer)this.x.getValue()).intValue() + (float)((Integer)this.width.getValue()).intValue() / 2.0f, ((Integer)this.y.getValue()).intValue(), (float)((Integer)this.x.getValue()).intValue() + (float)((Integer)this.width.getValue()).intValue() / 2.0f, (Integer)this.y.getValue() + (Integer)this.height.getValue(), 1.0f, new Color(((Color)this.bgColor.getValue()).getRed(), ((Color)this.bgColor.getValue()).getGreen(), ((Color)this.bgColor.getValue()).getBlue(), 255).getRGB());
        Class50.Method802(((Integer)this.x.getValue()).intValue(), (float)((Integer)this.y.getValue()).intValue() + (float)((Integer)this.height.getValue()).intValue() / 2.0f, (Integer)this.x.getValue() + (Integer)this.width.getValue(), (float)((Integer)this.y.getValue()).intValue() + (float)((Integer)this.height.getValue()).intValue() / 2.0f, 1.0f, new Color(((Color)this.bgColor.getValue()).getRed(), ((Color)this.bgColor.getValue()).getGreen(), ((Color)this.bgColor.getValue()).getBlue(), 255).getRGB());
        float f = (float)((Integer)this.x.getValue()).intValue() + (float)((Integer)this.width.getValue()).intValue() / 2.0f;
        float f2 = (float)((Integer)this.y.getValue()).intValue() + (float)((Integer)this.height.getValue()).intValue() / 2.0f;
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
            if (entityPlayer == Globals.mc.player) continue;
            float f3 = (float)(Globals.mc.player.posX - entityPlayer.posX) * ((Float)this.zoom.getValue()).floatValue();
            float f4 = (float)(Globals.mc.player.posZ - entityPlayer.posZ) * ((Float)this.zoom.getValue()).floatValue();
            if (Math.abs(f3) + (float)((Integer)this.radius.getValue()).intValue() > (float)((Integer)this.width.getValue()).intValue() / 2.0f || Math.abs(f4) + (float)((Integer)this.radius.getValue()).intValue() > (float)((Integer)this.height.getValue()).intValue() / 2.0f) continue;
            double d = Globals.mc.player.rotationYaw % 360.0f;
            double d2 = d < 0.0 ? 360.0 - -d : d;
            float f5 = (float)((double)f + this.Method3853(-Math.toRadians(d2), f3, f4)[0]);
            float f6 = (float)((double)f2 + this.Method3853(-Math.toRadians(d2), f3, f4)[1]);
            Class50.Method891(f5, f6, ((Integer)this.radius.getValue()).intValue(), this.Method3852(entityPlayer));
        }
    }
}
