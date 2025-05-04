package me.ciruu.abyss.util;

import java.awt.Color;
import me.ciruu.abyss.Class36;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import me.ciruu.abyss.Globals;
import net.minecraft.util.ResourceLocation;

public class SplashProgress {

    private static final int MAX = 7;
    private static int Progress = 1;
    private static String Current = "";
    private static ResourceLocation splash = new ResourceLocation("abyss", "textures/abyss-screen.png");
    private static TextureManager texturemgr = null;

    public static void setProgress(int n, String string) {
        Progress = n;
        Current = string != null ? string : "";
        update(); // Don't use @SubscribeEvent here
    }

    private static void update() {
        Minecraft mc = Globals.mc;
        if (mc == null || mc.getTextureManager() == null || mc.getLanguageManager() == null) return;

        drawSplash(mc.getTextureManager());
    }

    public static void drawSplash(TextureManager textureManager) {
        if (texturemgr == null && textureManager != null) {
            texturemgr = textureManager;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.getTextureManager() == null) return;

        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scale = scaledResolution.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(
                scaledResolution.getScaledWidth() * scale,
                scaledResolution.getScaledHeight() * scale,
                true
        );
        framebuffer.bindFramebuffer(false);

        GlStateManager.matrixMode(5889); // GL_PROJECTION
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888); // GL_MODELVIEW
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        if (splash == null) {
            splash = new ResourceLocation("abyss", "textures/abyss-screen.png");
        }

        textureManager.bindTexture(splash);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, 1920, 1080,
                scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 1920.0f, 1080.0f);

        drawProgress();

        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scale, scaledResolution.getScaledHeight() * scale);

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        mc.updateDisplay();
    }

    private static void drawProgress() {
        Minecraft mc = Globals.mc;
        if (mc == null || mc.gameSettings == null || mc.getTextureManager() == null) return;

        ScaledResolution sr = new ScaledResolution(mc);
        double progressRatio = (double) Progress / MAX * sr.getScaledWidth();

        // Background
        Gui.drawRect(0, sr.getScaledHeight() - 35, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 50).getRGB());

        // Progress text
        Class36.Field465.Method556(Current, 20.0f, sr.getScaledHeight() - 25, -1);
        String status = Progress + "/" + MAX;
        Class36.Field465.Method556(status,
                sr.getScaledWidth() - 20 - Class36.Field465.Method563(status),
                sr.getScaledHeight() - 25,
                -505290241
        );

        // Progress bar
        Gui.drawRect(0, sr.getScaledHeight() - 2, (int) progressRatio, sr.getScaledHeight(), new Color(3, 169, 244).getRGB());
        Gui.drawRect((int) progressRatio, sr.getScaledHeight() - 2, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 10).getRGB());
    }

    // This method was broken and unnecessary for its purpose
    public static void ScreenForcer() {
        if (texturemgr == null) return;

        try {
            texturemgr.getTexture(splash); // Preload/ensure texture is there
        } catch (Exception e) {
            e.printStackTrace(); // At least log the issue
        }
    }
}
