package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class PearlTracker
extends Module {
    public final Setting tracer = new Setting("Tracer", "", this, true);
    public final Setting box = new Setting("Box", "", this, true);
    public final Setting tail = new Setting("Tail", "", this, true);
    public final Setting width = new Setting("Width", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    private final Setting color = new Setting("Color", "", this, new Color(142, 0, 243, 125));
    private final HashMap Field1565 = new HashMap();
    @EventHandler
    private Listener Field1566 = new Listener<Class26>(this::Method1969, new Predicate[0]);
    @EventHandler
    private Listener Field1567 = new Listener<Class66>(this::Method1970, -10000, new Predicate[0]);

    public PearlTracker() {
        super("PearlTracker", "Tracks ender pearls.", Class11.RENDER, "");
        this.addSetting(this.tracer);
        this.addSetting(this.box);
        this.addSetting(this.tail);
        this.addSetting(this.width);
        this.addSetting(this.color);
    }

    private Color Method1972() {
        return (Color)this.color.getValue();
    }

    public static void Method1973(List list, int n, double d) {
        PearlTracker.Method1974();
        GL11.glEnable((int)32925);
        GL11.glLineWidth((float)((float)d));
        GlStateManager.color((float)((float)(n >> 16 & 0xFF) / 255.0f), (float)((float)(n >> 8 & 0xFF) / 255.0f), (float)((float)(n & 0xFF) / 255.0f), (float)((float)(n >> 24 & 0xFF) / 255.0f));
        GL11.glBegin((int)3);
        RenderManager renderManager = Globals.mc.getRenderManager();
        for (Object vec3d1 : list) {
            Vec3d vec3d = (Vec3d) vec3d1;
            vec3d1 = vec3d;
            GL11.glVertex3d((double)(vec3d.x - renderManager.viewerPosX), (double)(vec3d.y - renderManager.viewerPosY), (double)(vec3d.z - renderManager.viewerPosZ));
        }
        GL11.glEnd();
        PearlTracker.Method1975();
        GL11.glDisable((int)32925);
    }

    public static void Method1974() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)34383);
    }

    public static void Method1975() {
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GL11.glHint((int)3154, (int)4352);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)34383);
        GlStateManager.shadeModel((int)7424);
    }

    private void Method1970(Class66 class66) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }

        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderPearl)) continue;

            EntityEnderPearl entityEnderPearl = (EntityEnderPearl) entity;
            Vec3d vec3d = null;

            // Handle tracer logic if enabled
            if (((Boolean)this.tracer.getValue()).booleanValue() &&
                    (vec3d = Class29.getVector(entityEnderPearl, class66.Method1789()).subtract(
                            Globals.mc.getRenderManager().renderPosX,
                            Globals.mc.getRenderManager().renderPosY,
                            Globals.mc.getRenderManager().renderPosZ)) != null) {

                boolean viewBobbingState = Globals.mc.gameSettings.viewBobbing;
                Globals.mc.gameSettings.viewBobbing = false;

                // Call setupCameraTransform using reflection
                invokeSetupCameraTransformReflection(class66.Method1789());

                // Apply rotation for the Ender Pearl
                vec3d = new Vec3d(0.0, 0.0, 1.0)
                        .rotatePitch(-((float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)))
                        .rotateYaw(-((float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw)));

                // Render the tracer line
                Class50.Method915((float) vec3d.x, (float) vec3d.y + Globals.mc.player.getEyeHeight(),
                        (float) vec3d.z, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z,
                        ((Float) this.width.getValue()).floatValue(), this.Method1972().getRGB());

                // Restore view bobbing state
                Globals.mc.gameSettings.viewBobbing = viewBobbingState;
            }

            // Draw the bounding box if enabled
            if (((Boolean) this.box.getValue()).booleanValue()) {
                AxisAlignedBB axisAlignedBB = entityEnderPearl.getEntityBoundingBox();
                if (axisAlignedBB != null) {
                    AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(
                            axisAlignedBB.minX - Globals.mc.getRenderManager().viewerPosX,
                            axisAlignedBB.minY - Globals.mc.getRenderManager().viewerPosY,
                            axisAlignedBB.minZ - Globals.mc.getRenderManager().viewerPosZ,
                            axisAlignedBB.maxX - Globals.mc.getRenderManager().viewerPosX,
                            axisAlignedBB.maxY - Globals.mc.getRenderManager().viewerPosY,
                            axisAlignedBB.maxZ - Globals.mc.getRenderManager().viewerPosZ
                    );
                    Class50.Method879(axisAlignedBB2, this.Method1972().getRGB());
                    Class50.Method851(axisAlignedBB2, ((Float) this.width.getValue()).floatValue(),
                            new Color(this.Method1972().getRed(), this.Method1972().getGreen(),
                                    this.Method1972().getBlue(), 255).getRGB());
                }
            }

            // Process tail rendering if enabled
            if (((Boolean) this.tail.getValue()).booleanValue()) {
                this.Field1565.values().forEach(this::Method1976);
            }
        }
    }

    // Local method to invoke setupCameraTransform via reflection
    private void invokeSetupCameraTransformReflection(float partialTicks) {
        try {
            // Use reflection to get the private setupCameraTransform method
            Method setupCameraTransformMethod = EntityRenderer.class.getDeclaredMethod("setupCameraTransform", float.class, int.class);
            setupCameraTransformMethod.setAccessible(true); // Make it accessible
            setupCameraTransformMethod.invoke(Globals.mc.entityRenderer, partialTicks, 0);
        } catch (Exception e) {
            e.printStackTrace(); // Handle any reflection-related exceptions
        }
    }



    private void Method1976(Object o) {
    }

    private void Method1976(ArrayList arrayList) {
        PearlTracker.Method1973(arrayList, this.Method1972().getRGB(), ((Float)this.width.getValue()).floatValue());
    }

    private void Method1969(Class26 class26) {
        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderPearl)) continue;
            EntityEnderPearl entityEnderPearl = (EntityEnderPearl)entity;
            if (!((Boolean)this.tail.getValue()).booleanValue()) continue;
            if (!this.Field1565.containsKey(entityEnderPearl)) {
                this.Field1565.put(entityEnderPearl, new ArrayList());
                ((ArrayList)this.Field1565.get(entityEnderPearl)).add(entityEnderPearl.getPositionVector());
                continue;
            }
            ((ArrayList)this.Field1565.get(entityEnderPearl)).add(entityEnderPearl.getPositionVector());
        }
        ArrayList arrayList = new ArrayList();
        this.Field1565.keySet().forEach(arg_0 -> PearlTracker.Method1977((List) arrayList, (EntityEnderPearl) arg_0));
        arrayList.forEach(this.Field1565::remove);
    }

    private static void Method1977(List list, EntityEnderPearl entityEnderPearl) {
        if (!Globals.mc.world.loadedEntityList.contains(entityEnderPearl)) {
            list.add(entityEnderPearl);
        }
    }
}
