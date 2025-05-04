package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class198;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class478;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class CrystalESP
extends Module {
    public final Setting linewidth = new Setting("LineWidth", "Line width", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(5.0f));
    public final Setting crystalmodel = new Setting("CrystalModel", "", this, true);
    public final Setting crystalcolor = new Setting("Crystal color", "Crystal color", this, new Color(131, 0, 255, 255));
    public final Setting rainbow = new Setting("Rainbow", "", this, false);
    public final Setting rainbowspeed = new Setting("RainbowSpeed", "", (Module)this, (Object)Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(10.0f));
    public final Setting rainbowsaturation = new Setting("RainbowSaturation", "", (Module)this, (Object)Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public final Setting rainbowbrightness = new Setting("RainbowBrightness", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    private final Setting renderID = new Setting("RenderID", "", this, false);
    private final Setting modeID = new Setting("ModeId", "", this, (Object)Class478.Both);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(10.0f));
    public static boolean Field1682 = false;
    @EventHandler
    private Listener Field1683 = new Listener<Class66>(this::Method2042, new Predicate[0]);

    public CrystalESP() {
        super("CrystalESP", "Highlights ender crystal entities.", Class11.RENDER, "");
        this.addSetting(this.linewidth);
        this.addSetting(this.crystalmodel);
        this.addSetting(this.crystalcolor);
        this.addSetting(this.rainbow);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
        this.addSetting(this.renderID);
        this.addSetting(this.modeID);
        this.addSetting(this.scale);
    }

    public void Method2044() {
        super.getEnable();
        Field1682 = true;
    }

    public void Method2045() {
        super.getDisable();
        Field1682 = false;
    }

    private void Method2046(Class66 class66, EntityEnderCrystal entityEnderCrystal) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        this.Method2054(((Color)this.crystalcolor.getValue()).getRGB());
        GL11.glLineWidth((float)((Float)this.linewidth.getValue()).floatValue());
        Globals.mc.getRenderManager().renderEntityStatic((Entity)entityEnderCrystal, class66.Method1789(), false);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private void Method2061() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        if (this.modeID.getValue() == Class478.Crystals) {
            Globals.mc.world.loadedEntityList.stream().filter(CrystalESP::Method2066).forEach(this::Method2068);
        } else if (this.modeID.getValue() == Class478.Players) {
            Globals.mc.world.loadedEntityList.stream().filter(CrystalESP::Method2070).forEach(this::Method2068);
        } else if (this.modeID.getValue() == Class478.Both) {
            Globals.mc.world.loadedEntityList.stream().filter(CrystalESP::Method2071).forEach(this::Method2068);
        } else if (this.modeID.getValue() == Class478.All) {
            Globals.mc.world.loadedEntityList.forEach(this::Method2068);
        }
        GlStateManager.disableTexture2D();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private void Method2068(Entity entity) {
        GlStateManager.pushMatrix();
        Vec3d vec3d = Class30.Method2079(entity, Globals.mc.getRenderPartialTicks());
        float f = entity.height + 0.5f - (entity.isSneaking() ? 0.25f : 0.0f);
        double d = vec3d.x;
        double d2 = vec3d.y + (double)f;
        double d3 = vec3d.z;
        float f2 = Globals.mc.getRenderManager().playerViewY;
        float f3 = Globals.mc.getRenderManager().playerViewX;
        boolean bl = Globals.mc.getRenderManager().options.thirdPersonView == 2;
        GlStateManager.translate((double)d, (double)d2, (double)d3);
        GlStateManager.rotate((float)(-f2), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)((float)(bl ? -1 : 1) * f3), (float)1.0f, (float)0.0f, (float)0.0f);
        float f4 = Globals.mc.player.getDistance(entity);
        float f5 = f4 / 8.0f * (float)Math.pow(1.258925437927246, ((Float)this.scale.getValue()).floatValue());
        GlStateManager.scale((float)f5, (float)f5, (float)f5);
        Class198 class198 = Class36.Method550();
        GlStateManager.scale((float)-0.025f, (float)-0.025f, (float)0.025f);
        String string = String.valueOf(entity.entityId);
        int n = class198.getStringWidth(string) / 2;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.disableDepth();
        GL11.glTranslatef((float)0.0f, (float)-20.0f, (float)0.0f);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)(-n - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        bufferBuilder.pos((double)(-n - 1), 19.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        bufferBuilder.pos((double)(n + 1), 19.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        bufferBuilder.pos((double)(n + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
        tessellator.draw();
        bufferBuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)(-n - 1), 8.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        bufferBuilder.pos((double)(-n - 1), 19.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        bufferBuilder.pos((double)(n + 1), 19.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        bufferBuilder.pos((double)(n + 1), 8.0, 0.0).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        class198.drawString(string, -n, 10, 0xFFAA00);
        GlStateManager.glNormal3f((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)20.0f, (float)0.0f);
        GlStateManager.scale((float)-40.0f, (float)-40.0f, (float)40.0f);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private boolean Method2100(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    private void Method2054(int n) {
        GL11.glColor4f((float)((float)(n >> 16 & 0xFF) / 255.0f), (float)((float)(n >> 8 & 0xFF) / 255.0f), (float)((float)(n & 0xFF) / 255.0f), (float)((float)(n >> 24 & 0xFF) / 255.0f));
    }

    private static boolean Method2071(Entity entity) {
        return entity instanceof EntityPlayer || entity instanceof EntityEnderCrystal;
    }

    private static boolean Method2070(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    private static boolean Method2066(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    private void Method2042(Class66 class66) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }
        if (((Boolean)this.renderID.getValue()).booleanValue()) {
            this.Method2061();
        }
    }
}
