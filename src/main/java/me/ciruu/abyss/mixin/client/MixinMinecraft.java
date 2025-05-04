package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Class499;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class500;
import me.ciruu.abyss.events.minecraft.EventMinecraftCrashReport;
import me.ciruu.abyss.managers.ModuleManager;
import me.ciruu.abyss.modules.client.ScreenShaders;
import me.ciruu.abyss.modules.combat.MultiTask;
import me.ciruu.abyss.modules.misc.FastPlace;
import me.ciruu.abyss.util.SplashProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.crash.CrashReport;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Mixin(value={Minecraft.class}, priority=2000)
public class MixinMinecraft {
    @Shadow
    public GuiScreen currentScreen;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public Framebuffer framebuffer;
    @Shadow
    public TextureManager renderEngine;
    @Shadow
    public int rightClickDelayTimer;

    @Inject(method = {"displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"}, at = {@At(value = "HEAD")})
    public void displayCrashReport(CrashReport crashReport, CallbackInfo callbackInfo) {
        System.out.println("Detected crash:");
        System.out.println(crashReport.getCompleteReport());
        EventMinecraftCrashReport eventMinecraftCrashReport = new EventMinecraftCrashReport(crashReport);
        AbyssMod.EVENT_BUS.post(eventMinecraftCrashReport);
    }

    @Inject(method = {"getLimitFramerate"}, at = {@At(value = "HEAD")}, cancellable = true)
    private void getFrameRateAbyss(CallbackInfoReturnable callbackInfoReturnable) {
        try {
            if (Manager.moduleManager != null && Manager.moduleManager.isModuleEnabled(ScreenShaders.class)) {
                ScreenShaders screenShaders = (ScreenShaders) Manager.moduleManager.getModuleByClass(ScreenShaders.class);
                if (Minecraft.getMinecraft().world == null && this.currentScreen != null) {
                    callbackInfoReturnable.setReturnValue(screenShaders.fps.getValue());
                } else {
                    callbackInfoReturnable.setReturnValue(this.gameSettings.limitFramerate);
                }
            }
        } catch (NullPointerException e) {
            System.err.println("NullPointerException in getLimitFramerate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"drawSplashScreen"}, at = {@At(value = "HEAD")}, cancellable = true)
    public void drawSplashScreen(TextureManager textureManager, CallbackInfo callbackInfo) {
        SplashProgress.drawSplash(textureManager);
        SplashProgress.setProgress(1, "Starting Game...");
    }

    @Inject(method = {"init"}, at = {@At(value = "INVOKE", remap = false, target = "Lnet/minecraft/client/renderer/texture/TextureMap;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE)})
    private void onLoadingTextureMap(CallbackInfo callbackInfo) {
        try {
            SplashProgress.setProgress(2, "Loading Texture Map...");
        } catch (NullPointerException e) {
            System.err.println("Error in Texture Map Loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ModelManager;<init>(Lnet/minecraft/client/renderer/texture/TextureMap;)V", shift = At.Shift.BEFORE)})
    private void onLoadingModelManager(CallbackInfo callbackInfo) {
        try {
            SplashProgress.setProgress(3, "Loading Model Manager...");
        } catch (NullPointerException e) {
            System.err.println("Error in Model Manager Loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;<init>(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/renderer/block/model/ModelManager;Lnet/minecraft/client/renderer/color/ItemColors;)V", shift = At.Shift.BEFORE)})
    private void onLoadingItemRenderer(CallbackInfo callbackInfo) {
        try {
            SplashProgress.setProgress(4, "Loading Item Renderer...");
        } catch (NullPointerException e) {
            System.err.println("Error in Item Renderer Loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"init"}, at = {@At(value = "INVOKE", remap = false, target = "Lnet/minecraft/client/renderer/EntityRenderer;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/resources/IResourceManager;)V", shift = At.Shift.BEFORE)})
    private void onLoadingEntityRenderer(CallbackInfo callbackInfo) {
        try {
            SplashProgress.setProgress(5, "Loading Entity Renderer...");
        } catch (NullPointerException e) {
            System.err.println("Error in Entity Renderer Loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Redirect(method = {"rightClickMouse"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean hittingBlock(PlayerControllerMP playerControllerMP) {
        try {
            if (((MultiTask) Manager.moduleManager.getModuleByClass(MultiTask.class)).Method3250()) {
                return false;
            }
        } catch (NullPointerException e) {
            System.err.println("Error checking if hitting block: " + e.getMessage());
            e.printStackTrace();
        }
        return playerControllerMP.getIsHittingBlock();
    }

    @Redirect(method = {"sendClickBlockToController"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean handActive(EntityPlayerSP entityPlayerSP) {
        try {
            if (((MultiTask) Manager.moduleManager.getModuleByClass(MultiTask.class)).Method3250()) {
                return false;
            }
        } catch (NullPointerException e) {
            System.err.println("Error checking hand active: " + e.getMessage());
            e.printStackTrace();
        }
        return entityPlayerSP.isHandActive();
    }

    @Inject(method = {"clickMouse"}, at = {@At(value = "HEAD")})
    private void clickMouse(CallbackInfo callbackInfo) {
        try {
            Class499.Method2190(Class500.LEFT);
        } catch (NullPointerException e) {
            System.err.println("Error in clickMouse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"middleClickMouse"}, at = {@At(value = "HEAD")})
    private void middleClickMouse(CallbackInfo callbackInfo) {
        try {
            Class499.Method2190(Class500.MIDDLE);
        } catch (NullPointerException e) {
            System.err.println("Error in middleClickMouse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"rightClickMouse"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I", shift = At.Shift.AFTER)}, cancellable = true)
    private void rightClickMouse(CallbackInfo callbackInfo) {
        try {
            Class499.Method2190(Class500.RIGHT);
            if (Manager.moduleManager.isModuleEnabled(FastPlace.class) && !((Boolean) ((FastPlace) Manager.moduleManager.getModuleByClass(FastPlace.class)).onlyxp.getValue()).booleanValue()) {
                this.rightClickDelayTimer = ((FastPlace) Manager.moduleManager.getModuleByClass(FastPlace.class)).placedelay.getValue();
            }
        } catch (NullPointerException e) {
            System.err.println("Error in rightClickMouse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Inject(method = {"runTickMouse"}, at = {@At(value = "HEAD")})
    public void runTickMouse(CallbackInfo callbackInfo) {
        try {
            if (Mouse.getEventButton() != -1 && Mouse.isButtonDown(Mouse.getEventButton())) {
                ModuleManager.getString1(Mouse.getButtonName(Mouse.getEventButton()));
            }
        } catch (NullPointerException e) {
            System.err.println("Error in runTickMouse: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
