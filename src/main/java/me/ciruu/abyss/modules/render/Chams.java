package me.ciruu.abyss.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class88;
import me.ciruu.abyss.events.render.EventRenderEntityModel;
import me.ciruu.abyss.events.render.EventRenderModelPlayer;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public static final Setting mode = new Setting("Mode", "", null, (Object) Class88.ESP);
    private static final Setting players = new Setting("Players", "", null, true);
    private static final Setting animals = new Setting("Animals", "", null, true);
    private static final Setting mobs = new Setting("Mobs", "", null, true);
    public final Setting crystals = new Setting("Crystals", "", (Module) this, (Object) true, this.mainwindow, Chams::Method212);
    public final Setting color = new Setting("Color", "", (Module) this, (Object) new Color(131, 0, 255, 80), this.mainwindow, Chams::Method213);
    public final Setting hand = new Setting("Hand", "", (Module) this, (Object) true, this.mainwindow, Chams::Method214);
    public final Setting lines = new Setting("Lines", "", (Module) this, (Object) false, this.mainwindow, Chams::Method215);
    public final Setting width = new Setting("Width", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(10.0f), this.mainwindow, Chams::Method216);
    public final Setting friendcolor = new Setting("FriendColor", "", (Module) this, (Object) true, this.mainwindow, Chams::Method217);
    public final Setting fcolor = new Setting("FColor", "", (Module) this, (Object) new Color(69, 178, 235, 80), this.mainwindow, this::Method218);
    public final Setting rainbow = new Setting("Rainbow", "", (Module) this, (Object) true, this.mainwindow, Chams::Method219);
    public final Setting rainbowspeed = new Setting("RainbowSpeed", "", this, Float.valueOf(7.0f), Float.valueOf(0.1f), Float.valueOf(10.0f), this.mainwindow, this::Method220);
    public final Setting rainbowsaturation = new Setting("RainbowSaturation", "", this, Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method221);
    public final Setting rainbowbrightness = new Setting("RainbowBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method222);
    public final Setting visible = new Setting("Visible", "", (Module) this, (Object) Color.RED, this.mainwindow, Chams::Method223);
    public final Setting walls = new Setting("Walls", "", (Module) this, (Object) Color.BLUE, this.mainwindow, Chams::Method224);
    @EventHandler
    private Listener Field219 = new Listener<EventRenderEntityModel>(this::Method225, new Predicate[0]);
    @EventHandler
    private Listener Field220 = new Listener<Class90>(this::Method226, new Predicate[0]);
    @EventHandler
    private Listener Field221 = new Listener<EventRenderModelPlayer>(this::Method227, new Predicate[0]);

    public Chams() {
        super("Chams", "Changes the color of entities.", Class11.RENDER, "");
        this.addSetting(mode);
        this.addSetting(players);
        this.addSetting(animals);
        this.addSetting(mobs);
        this.addSetting(this.crystals);
        this.addSetting(this.color);
        this.addSetting(this.hand);
        this.addSetting(this.lines);
        this.addSetting(this.width);
        this.addSetting(this.friendcolor);
        this.addSetting(this.fcolor);
        this.addSetting(this.rainbow);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
        this.addSetting(this.visible);
        this.addSetting(this.walls);
    }

    public String Method229() {
        return ChatFormatting.WHITE + ((Class88) ((Object) mode.getValue())).name();
    }

    public static boolean Method230(Entity entity) {
        return mode.getValue() == Class88.ESP ? false : (entity instanceof EntityPlayer ? (Boolean) players.getValue() : (Class30.Method231(entity) ? ((Boolean) animals.getValue()).booleanValue() : ((Boolean) mobs.getValue()).booleanValue()));
    }

    private Color Method232(int n, float f, float f2) {
        float f3 = System.currentTimeMillis() % (long) n;
        return Color.getHSBColor(f3 /= (float) n, f, f2);
    }

    private void Method227(EventRenderModelPlayer eventRenderModelPlayer) {
        if (mode.getValue() == Class88.ESP && ((Boolean) players.getValue()).booleanValue()) {
            Color color = (Boolean) this.friendcolor.getValue() != false && Manager.Field223.Method233(eventRenderModelPlayer.entity.getName()) ? new Color(0.27f, 0.7f, 0.92f) : ((Boolean) this.rainbow.getValue() != false ? this.Method232((int) (((Float) this.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float) this.rainbowsaturation.getValue()).floatValue(), ((Float) this.rainbowbrightness.getValue()).floatValue()) : (Color) this.color.getValue());
            switch (eventRenderModelPlayer.getEra()) {
                case PRE: {
                    GL11.glPushMatrix();
                    GL11.glEnable((int) 32823);
                    GL11.glPolygonOffset((float) 1.0f, (float) -1.0E7f);
                    GL11.glPushAttrib((int) 1048575);
                    if (!((Boolean) this.lines.getValue()).booleanValue()) {
                        GL11.glPolygonMode((int) 1028, (int) 6914);
                    } else {
                        GL11.glPolygonMode((int) 1028, (int) 6913);
                    }
                    GL11.glDisable((int) 3553);
                    GL11.glDisable((int) 2896);
                    GL11.glDisable((int) 2929);
                    GL11.glEnable((int) 2848);
                    GL11.glEnable((int) 3042);
                    GL11.glBlendFunc((int) 770, (int) 771);
                    GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f), (float) ((float) color.getBlue() / 255.0f), (float) ((float) ((Color) this.color.getValue()).getAlpha() / 255.0f / 2.0f));
                    if (!((Boolean) this.lines.getValue()).booleanValue()) break;
                    GL11.glLineWidth((float) ((Float) this.width.getValue()).floatValue());
                    break;
                }
                case POST: {
                    GL11.glPopAttrib();
                    GL11.glPolygonOffset((float) 1.0f, (float) 1.0E7f);
                    GL11.glDisable((int) 32823);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    public void Method226(Class90 class90) {
        Color color = (Boolean) this.friendcolor.getValue() != false && Manager.Field223.Method233(class90.Field227.getName()) ? new Color(0.27f, 0.7f, 0.92f) : (((color = (Boolean) this.rainbow.getValue() != false ? this.Method232((int) (((Float) this.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float) this.rainbowsaturation.getValue()).floatValue(), ((Float) this.rainbowbrightness.getValue()).floatValue()) : (Color) this.color.getValue())));
        if (class90.getEra() == Class53.PRE && class90.Field227 instanceof EntityOtherPlayerMP && ((Boolean) players.getValue()).booleanValue()) {
            GL11.glPushMatrix();
            GL11.glEnable((int) 32823);
            GL11.glPolygonOffset((float) 1.0f, (float) -100000.0f);
            GL11.glPushAttrib((int) 1048575);
            if (!((Boolean) this.lines.getValue()).booleanValue()) {
                GL11.glPolygonMode((int) 1028, (int) 6914);
            } else {
                GL11.glPolygonMode((int) 1028, (int) 6913);
            }
            GL11.glDisable((int) 3553);
            GL11.glDisable((int) 2896);
            GL11.glDisable((int) 2929);
            GL11.glEnable((int) 2848);
            GL11.glEnable((int) 3042);
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glColor4f((float) ((float) color.getBlue() / 255.0f), (float) ((float) color.getBlue() / 255.0f), (float) ((float) color.getBlue() / 255.0f), (float) ((float) ((Color) this.color.getValue()).getAlpha() / 255.0f / 2.0f));
            if (((Boolean) this.lines.getValue()).booleanValue()) {
                GL11.glLineWidth((float) ((Float) this.width.getValue()).floatValue());
            }
            class90.Field228.render((Entity) class90.Field227, class90.Field229, class90.Field230, class90.Field231, class90.Field232, class90.Field233, class90.Field234);
            GL11.glPopAttrib();
            GL11.glPolygonOffset((float) 1.0f, (float) 100000.0f);
            GL11.glDisable((int) 32823);
            GL11.glPopMatrix();
        }
    }

    private void Method225(EventRenderEntityModel eventRenderEntityModel) {
        if (mode.getValue() == Class88.Walls) {
            if (eventRenderEntityModel.entity instanceof EntityOtherPlayerMP && !((Boolean) players.getValue()).booleanValue()) {
                return;
            }
            if (Class30.Method231(eventRenderEntityModel.entity) && !((Boolean) animals.getValue()).booleanValue()) {
                return;
            }
            if (!Class30.Method231(eventRenderEntityModel.entity) && !((Boolean) mobs.getValue()).booleanValue()) {
                return;
            }
            GlStateManager.pushMatrix();
            GL11.glDisable((int) 2929);
            GL11.glColor4f((float) ((float) ((Color) this.walls.getValue()).getRed() / 255.0f), (float) ((float) ((Color) this.walls.getValue()).getGreen() / 255.0f), (float) ((float) ((Color) this.walls.getValue()).getBlue() / 255.0f), (float) ((float) ((Color) this.walls.getValue()).getAlpha() / 255.0f));
            GL11.glDisable((int) 3553);
            eventRenderEntityModel.modelBase.render(eventRenderEntityModel.entity, eventRenderEntityModel.fl_1, eventRenderEntityModel.fl_2, eventRenderEntityModel.fl_3, eventRenderEntityModel.fl_4, eventRenderEntityModel.fl_5, eventRenderEntityModel.fl_6);
            GL11.glEnable((int) 2929);
            GL11.glColor4f((float) ((float) ((Color) this.visible.getValue()).getRed() / 255.0f), (float) ((float) ((Color) this.visible.getValue()).getGreen() / 255.0f), (float) ((float) ((Color) this.visible.getValue()).getBlue() / 255.0f), (float) ((float) ((Color) this.visible.getValue()).getAlpha() / 255.0f));
            eventRenderEntityModel.modelBase.render(eventRenderEntityModel.entity, eventRenderEntityModel.fl_1, eventRenderEntityModel.fl_2, eventRenderEntityModel.fl_3, eventRenderEntityModel.fl_4, eventRenderEntityModel.fl_5, eventRenderEntityModel.fl_6);
            GL11.glEnable((int) 3553);
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
            GlStateManager.popMatrix();
            eventRenderEntityModel.cancel();
        } else if (mode.getValue() == Class88.ESP) {
            Color color = null;
            Color color2 = (Boolean) this.friendcolor.getValue() != false && Manager.Field223.Method233(eventRenderEntityModel.entity.getName()) ? (Color) this.fcolor.getValue() : (color = (Boolean) this.rainbow.getValue() != false ? this.Method232((int) (((Float) this.rainbowspeed.getValue()).floatValue() * 1000.0f), ((Float) this.rainbowsaturation.getValue()).floatValue(), ((Float) this.rainbowbrightness.getValue()).floatValue()) : (Color) this.color.getValue());
            if (eventRenderEntityModel.getEra() == Class53.PRE && !(eventRenderEntityModel.entity instanceof EntityOtherPlayerMP)) {
                if (Class30.Method231(eventRenderEntityModel.entity) && ((Boolean) animals.getValue()).booleanValue()) {
                    GL11.glPushMatrix();
                    GL11.glEnable((int) 32823);
                    GL11.glPolygonOffset((float) 1.0f, (float) -100000.0f);
                    GL11.glPushAttrib((int) 1048575);
                    if (!((Boolean) this.lines.getValue()).booleanValue()) {
                        GL11.glPolygonMode((int) 1028, (int) 6914);
                    } else {
                        GL11.glPolygonMode((int) 1028, (int) 6913);
                    }
                    GL11.glDisable((int) 3553);
                    GL11.glDisable((int) 2896);
                    GL11.glDisable((int) 2929);
                    GL11.glEnable((int) 2848);
                    GL11.glEnable((int) 3042);
                    GL11.glBlendFunc((int) 770, (int) 771);
                    GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f), (float) ((float) color.getBlue() / 255.0f), (float) ((float) ((Color) this.color.getValue()).getAlpha() / 255.0f));
                    if (((Boolean) this.lines.getValue()).booleanValue()) {
                        GL11.glLineWidth((float) ((Float) this.width.getValue()).floatValue());
                    }
                    eventRenderEntityModel.modelBase.render(eventRenderEntityModel.entity, eventRenderEntityModel.fl_1, eventRenderEntityModel.fl_2, eventRenderEntityModel.fl_3, eventRenderEntityModel.fl_4, eventRenderEntityModel.fl_5, eventRenderEntityModel.fl_6);
                    GL11.glPopAttrib();
                    GL11.glPolygonOffset((float) 1.0f, (float) 100000.0f);
                    GL11.glDisable((int) 32823);
                    GL11.glPopMatrix();
                    eventRenderEntityModel.cancel();
                } else if (!Class30.Method231(eventRenderEntityModel.entity) && ((Boolean) mobs.getValue()).booleanValue()) {
                    GL11.glPushMatrix();
                    GL11.glEnable((int) 32823);
                    GL11.glPolygonOffset((float) 1.0f, (float) -100000.0f);
                    GL11.glPushAttrib((int) 1048575);
                    if (!((Boolean) this.lines.getValue()).booleanValue()) {
                        GL11.glPolygonMode((int) 1028, (int) 6914);
                    } else {
                        GL11.glPolygonMode((int) 1028, (int) 6913);
                    }
                    GL11.glDisable((int) 3553);
                    GL11.glDisable((int) 2896);
                    GL11.glDisable((int) 2929);
                    GL11.glEnable((int) 2848);
                    GL11.glEnable((int) 3042);
                    GL11.glBlendFunc((int) 770, (int) 771);
                    GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f), (float) ((float) color.getBlue() / 255.0f), (float) ((float) ((Color) this.color.getValue()).getAlpha() / 255.0f));
                    if (((Boolean) this.lines.getValue()).booleanValue()) {
                        GL11.glLineWidth((float) ((Float) this.width.getValue()).floatValue());
                    }
                    eventRenderEntityModel.modelBase.render(eventRenderEntityModel.entity, eventRenderEntityModel.fl_1, eventRenderEntityModel.fl_2, eventRenderEntityModel.fl_3, eventRenderEntityModel.fl_4, eventRenderEntityModel.fl_5, eventRenderEntityModel.fl_6);
                    GL11.glPopAttrib();
                    GL11.glPolygonOffset((float) 1.0f, (float) 100000.0f);
                    GL11.glDisable((int) 32823);
                    GL11.glPopMatrix();
                    eventRenderEntityModel.cancel();
                }
            }
        }
    }


    private static boolean Method224() {
        return mode.getValue() == Class88.Walls;
    }

    private static boolean Method223() {
        return mode.getValue() == Class88.Walls;
    }

    private boolean Method222() {
        return mode.getValue() == Class88.ESP && (Boolean)this.rainbow.getValue() != false;
    }

    private boolean Method221() {
        return mode.getValue() == Class88.ESP && (Boolean)this.rainbow.getValue() != false;
    }

    private boolean Method220() {
        return mode.getValue() == Class88.ESP && (Boolean)this.rainbow.getValue() != false;
    }

    private static boolean Method219() {
        return mode.getValue() == Class88.ESP;
    }

    private boolean Method218() {
        return mode.getValue() == Class88.ESP && (Boolean)this.friendcolor.getValue() != false;
    }

    private static boolean Method217() {
        return mode.getValue() == Class88.ESP;
    }

    private static boolean Method216() {
        return mode.getValue() == Class88.ESP;
    }

    private static boolean Method215() {
        return mode.getValue() == Class88.ESP;
    }

    private static boolean Method214() {
        return mode.getValue() == Class88.ESP;
    }

    private static boolean Method213() {
        return mode.getValue() == Class88.ESP;
    }

    private static boolean Method212() {
        return mode.getValue() != Class88.Normal;
    }
}
