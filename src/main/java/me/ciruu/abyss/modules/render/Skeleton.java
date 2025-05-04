package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Skeleton
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting linewidth = new Setting("LineWidth", "", (Module)this, (Object)Float.valueOf(1.5f), Float.valueOf(0.1f), Float.valueOf(5.0f));
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(142, 0, 255, 255), this.mainwindow, Skeleton::Method2429);
    private final Setting friend = new Setting("Friend", "", this, true);
    private final Setting friendcolor = new Setting("FriendColor", "", (Module)this, (Object)new Color(0, 255, 255, 255), this.mainwindow, this.friend::getValue);
    public final Setting invisibles = new Setting("Invisibles", "", this, false);
    private final Map Field1999 = new HashMap();
    @EventHandler
    private Listener Field2000 = new Listener<Class66>(this::Method2430, new Predicate[0]);

    public Skeleton() {
        super("Skeleton", "Renders a skeleton on players.", Class11.RENDER, "");
        this.addSetting(this.linewidth);
        this.addSetting(this.color);
        this.addSetting(this.friend);
        this.addSetting(this.friendcolor);
        this.addSetting(this.invisibles);
    }

    public void Method2381(EntityPlayer entityPlayer, ModelPlayer modelPlayer) {
        this.Field1999.put(entityPlayer, new float[][]{{modelPlayer.bipedHead.rotateAngleX, modelPlayer.bipedHead.rotateAngleY, modelPlayer.bipedHead.rotateAngleZ}, {modelPlayer.bipedRightArm.rotateAngleX, modelPlayer.bipedRightArm.rotateAngleY, modelPlayer.bipedRightArm.rotateAngleZ}, {modelPlayer.bipedLeftLeg.rotateAngleX, modelPlayer.bipedLeftLeg.rotateAngleY, modelPlayer.bipedLeftLeg.rotateAngleZ}, {modelPlayer.bipedRightLeg.rotateAngleX, modelPlayer.bipedRightLeg.rotateAngleY, modelPlayer.bipedRightLeg.rotateAngleZ}, {modelPlayer.bipedLeftLeg.rotateAngleX, modelPlayer.bipedLeftLeg.rotateAngleY, modelPlayer.bipedLeftLeg.rotateAngleZ}});
    }

    private void Method2432(EntityPlayer entityPlayer, float[][] fArray, Color color) {
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        Vec3d vec3d = Class30.Method2079((Entity)entityPlayer, Globals.mc.getRenderPartialTicks());
        double d = vec3d.x;
        double d2 = vec3d.y;
        double d3 = vec3d.z;
        GlStateManager.translate((double)d, (double)d2, (double)d3);
        GlStateManager.rotate((float)(-entityPlayer.renderYawOffset), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.translate((double)0.0, (double)0.0, (double)(entityPlayer.isSneaking() ? -0.235 : 0.0));
        float f = entityPlayer.isSneaking() ? 0.6f : 0.75f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)-0.125, (double)f, (double)0.0);
        if (fArray[3][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[3][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[3][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[3][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[3][2] != 0.0f) {
            GlStateManager.rotate((float)(fArray[3][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)(-f), (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.125, (double)f, (double)0.0);
        if (fArray[4][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[4][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[4][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[4][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[4][2] != 0.0f) {
            GlStateManager.rotate((float)(fArray[4][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)(-f), (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.translate((double)0.0, (double)0.0, (double)(entityPlayer.isSneaking() ? 0.25 : 0.0));
        GlStateManager.pushMatrix();
        double d4 = 0.0;
        if (entityPlayer.isSneaking()) {
            d4 = -0.05;
        }
        GlStateManager.translate((double)0.0, (double)d4, (double)(entityPlayer.isSneaking() ? -0.01725 : 0.0));
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)-0.375, (double)((double)f + 0.55), (double)0.0);
        if (fArray[1][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[1][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[1][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[1][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[1][2] != 0.0f) {
            GlStateManager.rotate((float)(-fArray[1][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)-0.5, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.375, (double)((double)f + 0.55), (double)0.0);
        if (fArray[2][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[2][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[2][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[2][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[2][2] != 0.0f) {
            GlStateManager.rotate((float)(-fArray[2][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)-0.5, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)((double)f + 0.55), (double)0.0);
        if (fArray[0][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[0][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.3, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.rotate((float)(entityPlayer.isSneaking() ? 25.0f : 0.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        if (entityPlayer.isSneaking()) {
            d4 = -0.16175;
        }
        GlStateManager.translate((double)0.0, (double)d4, (double)(entityPlayer.isSneaking() ? -0.48025 : 0.0));
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)f, (double)0.0);
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)-0.125, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.125, (double)0.0, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)f, (double)0.0);
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.55, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)((double)f + 0.55), (double)0.0);
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)-0.375, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.375, (double)0.0, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private void Method2433(EntityPlayer entityPlayer, float[][] fArray, Color color, Color color2) {
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        Vec3d vec3d = Class30.Method2079((Entity)entityPlayer, Globals.mc.getRenderPartialTicks());
        double d = vec3d.x;
        double d2 = vec3d.y;
        double d3 = vec3d.z;
        GlStateManager.translate((double)d, (double)d2, (double)d3);
        GlStateManager.rotate((float)(-entityPlayer.renderYawOffset), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.translate((double)0.0, (double)0.0, (double)(entityPlayer.isSneaking() ? -0.235 : 0.0));
        float f = entityPlayer.isSneaking() ? 0.6f : 0.75f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)-0.125, (double)f, (double)0.0);
        if (fArray[3][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[3][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[3][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[3][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[3][2] != 0.0f) {
            GlStateManager.rotate((float)(fArray[3][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)(-f), (double)0.0);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.125, (double)f, (double)0.0);
        if (fArray[4][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[4][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[4][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[4][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[4][2] != 0.0f) {
            GlStateManager.rotate((float)(fArray[4][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)(-f), (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.translate((double)0.0, (double)0.0, (double)(entityPlayer.isSneaking() ? 0.25 : 0.0));
        GlStateManager.pushMatrix();
        double d4 = 0.0;
        if (entityPlayer.isSneaking()) {
            d4 = -0.05;
        }
        GlStateManager.translate((double)0.0, (double)d4, (double)(entityPlayer.isSneaking() ? -0.01725 : 0.0));
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)-0.375, (double)((double)f + 0.55), (double)0.0);
        if (fArray[1][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[1][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[1][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[1][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[1][2] != 0.0f) {
            GlStateManager.rotate((float)(-fArray[1][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)-0.5, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.375, (double)((double)f + 0.55), (double)0.0);
        if (fArray[2][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[2][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (fArray[2][1] != 0.0f) {
            GlStateManager.rotate((float)(fArray[2][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        if (fArray[2][2] != 0.0f) {
            GlStateManager.rotate((float)(-fArray[2][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)-0.5, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)((double)f + 0.55), (double)0.0);
        if (fArray[0][0] != 0.0f) {
            GlStateManager.rotate((float)(fArray[0][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.3, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.rotate((float)(entityPlayer.isSneaking() ? 25.0f : 0.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        if (entityPlayer.isSneaking()) {
            d4 = -0.16175;
        }
        GlStateManager.translate((double)0.0, (double)d4, (double)(entityPlayer.isSneaking() ? -0.48025 : 0.0));
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)f, (double)0.0);
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)-0.125, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.125, (double)0.0, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)f, (double)0.0);
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.0, (double)0.55, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)0.0, (double)((double)f + 0.55), (double)0.0);
        GlStateManager.glBegin((int)3);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex3d((double)-0.375, (double)0.0, (double)0.0);
        GlStateManager.color((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex3d((double)0.375, (double)0.0, (double)0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private void Method2430(Class66 class66) {
        Class50.Method892(((Float)this.linewidth.getValue()).floatValue());
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
            if (entityPlayer.isInvisible() && !((Boolean)this.invisibles.getValue()).booleanValue() || entityPlayer == null || entityPlayer == Globals.mc.getRenderViewEntity() || !entityPlayer.isEntityAlive() || entityPlayer.isPlayerSleeping() || this.Field1999.get(entityPlayer) == null || !(Globals.mc.player.getDistanceSq((Entity)entityPlayer) < 2500.0)) continue;
            this.Method2432(entityPlayer, (float[][])this.Field1999.get(entityPlayer), Manager.Field223.Method711((Entity)entityPlayer) ? (Color)this.friendcolor.getValue() : (Color)this.color.getValue());
        }
        Class50.Method895();
    }

    private static boolean Method2429() {
        return true;
    }
}
