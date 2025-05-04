package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.client.Client;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiChat.class})
public class MixinGuiChat {
    private boolean loaded = false;

    @Inject(method={"initGui"}, at={@At(value="HEAD")})
    public void initGuiPatch(CallbackInfo callbackInfo) {
        if (((Boolean)((Client)Manager.moduleManager.getModuleByClass(Client.class)).chatblur.getValue()).booleanValue()) {
            Globals.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            this.loaded = true;
        }
    }

    @Inject(method={"onGuiClosed"}, at={@At(value="TAIL")})
    public void onGuiClosedPatch(CallbackInfo callbackInfo) {
        if (this.loaded) {
            Globals.mc.entityRenderer.stopUseShader();
            this.loaded = false;
        }
    }
}
