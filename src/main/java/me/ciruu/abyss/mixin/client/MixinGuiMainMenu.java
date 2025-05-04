package me.ciruu.abyss.mixin.client;

import java.io.IOException;
import java.util.Random;
import me.ciruu.abyss.Class176;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class173;
import me.ciruu.abyss.enums.Class175;
import me.ciruu.abyss.modules.client.ScreenShaders;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = { GuiMainMenu.class })
public abstract class MixinGuiMainMenu extends GuiScreen {

    @Shadow
    protected abstract void renderSkybox(int var1, int var2, float var3);

    @Inject(method = "initGui", at = @At("RETURN"), cancellable = true)
    public void initGui(CallbackInfo callbackInfo) {
        if (Manager.Field375 == null) {
            System.err.println("Shader program is null, skipping shader render.");
            return;
        }
        if (Manager.moduleManager != null) {
            ScreenShaders screenShaders = (ScreenShaders) Manager.moduleManager.getModuleByClass(ScreenShaders.class);
        } else {
            System.err.println("ModuleManager is not initialized");
        }

        try {
            ScreenShaders screenShaders = (ScreenShaders) Manager.moduleManager.getModuleByClass(ScreenShaders.class);
            if (screenShaders != null) {
                if (screenShaders.mode.getValue() == Class173.Random) {
                    Random random = new Random();
                    Class175[] class175Array = Class175.values();
                    Manager.Field375 = new Class176(class175Array[random.nextInt(class175Array.length)].get());
                } else {
                    Object shaderValue = screenShaders.shader.getValue();
                    if (shaderValue instanceof Class175) {
                        Manager.Field375 = new Class176(((Class175) shaderValue).get());
                    }
                }
            }
        } catch (IOException | NullPointerException | IllegalStateException e) {
            System.err.println("Shader init failed: " + e.getMessage());
            e.printStackTrace();
        }

        Manager.Field377 = System.currentTimeMillis();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;renderSkybox(IIF)V"))
    private void voided(GuiMainMenu guiMainMenu, int n, int n2, float f) {
        if (!Manager.moduleManager.isModuleEnabled(ScreenShaders.class)) {
            this.renderSkybox(n, n2, f);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 0))
    private void noRect1(GuiMainMenu guiMainMenu, int n, int n2, int n3, int n4, int n5, int n6) {
        if (!Manager.moduleManager.isModuleEnabled(ScreenShaders.class)) {
            this.drawGradientRect(n, n2, n3, n4, n5, n6);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 1))
    private void noRect2(GuiMainMenu guiMainMenu, int n, int n2, int n3, int n4, int n5, int n6) {
        if (!Manager.moduleManager.isModuleEnabled(ScreenShaders.class)) {
            this.drawGradientRect(n, n2, n3, n4, n5, n6);
        }
    }

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void drawScreenShader(int n, int n2, float f, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(ScreenShaders.class) && Manager.Field375 != null) {
            try {
                GlStateManager.disableCull();
                float time = (System.currentTimeMillis() - Manager.Field377) / 1000.0f;
                Manager.Field375.Method461(this.width * 2, this.height * 2, n * 2, n2 * 2, time);

                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(-1.0f, -1.0f);
                GL11.glVertex2f(-1.0f, 1.0f);
                GL11.glVertex2f(1.0f, 1.0f);
                GL11.glVertex2f(1.0f, -1.0f);
                GL11.glEnd();

                GL20.glUseProgram(0);
            } catch (Exception e) {
                System.err.println("Shader draw failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
