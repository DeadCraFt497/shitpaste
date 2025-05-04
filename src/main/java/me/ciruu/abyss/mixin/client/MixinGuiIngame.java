package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.hud.CustomIngameGUI;
import me.ciruu.abyss.modules.hud.InfoList;
import me.ciruu.abyss.modules.render.NoRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiIngame.class})
public class MixinGuiIngame
extends Gui {
    @Inject(method={"renderPortal"}, at={@At(value="HEAD")}, cancellable=true)
    protected void renderPortalHook(float f, ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).portal.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderPumpkinOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    protected void renderPumpkinOverlayHook(ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((Boolean)((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).pumpkin.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderPotionEffects"}, at={@At(value="HEAD")}, cancellable=true)
    protected void renderPotionEffectsHook(ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(InfoList.class)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderHotbar"}, at={@At(value="HEAD")}, cancellable=true)
    protected void renderHotbar(ScaledResolution scaledResolution, float f, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.getModuleByClass(CustomIngameGUI.class) != null && Manager.moduleManager.isModuleEnabled(CustomIngameGUI.class) && ((Boolean)((CustomIngameGUI)Manager.moduleManager.getModuleByClass(CustomIngameGUI.class)).hotbar.getValue()).booleanValue()) {
            callbackInfo.cancel();
            ((CustomIngameGUI)Manager.moduleManager.getModuleByClass(CustomIngameGUI.class)).Method73(scaledResolution, f);
        }
    }

    @Inject(method={"renderExpBar"}, at={@At(value="HEAD")}, cancellable=true)
    protected void renderExpBar(ScaledResolution scaledResolution, int n, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.getModuleByClass(CustomIngameGUI.class) != null && Manager.moduleManager.isModuleEnabled(CustomIngameGUI.class) && ((Boolean)((CustomIngameGUI)Manager.moduleManager.getModuleByClass(CustomIngameGUI.class)).expBar.getValue()).booleanValue()) {
            callbackInfo.cancel();
            ((CustomIngameGUI)Manager.moduleManager.getModuleByClass(CustomIngameGUI.class)).Method74(scaledResolution, n);
        }
    }

    @Inject(method={"renderPlayerStats"}, at={@At(value="HEAD")}, cancellable=true)
    protected void renderPlayerStats(ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.getModuleByClass(CustomIngameGUI.class) != null && Manager.moduleManager.isModuleEnabled(CustomIngameGUI.class) && ((Boolean)((CustomIngameGUI)Manager.moduleManager.getModuleByClass(CustomIngameGUI.class)).Field103.getValue()).booleanValue()) {
            callbackInfo.cancel();
            ((CustomIngameGUI)Manager.moduleManager.getModuleByClass(CustomIngameGUI.class)).Method75(scaledResolution);
        }
    }
}
