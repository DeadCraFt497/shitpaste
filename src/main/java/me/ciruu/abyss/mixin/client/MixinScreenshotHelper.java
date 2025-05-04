package me.ciruu.abyss.mixin.client;

import java.io.File;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.events.client.EventClientScreenShot;
import me.ciruu.abyss.modules.misc.ImgurScreenshot;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ScreenShotHelper.class})
public class MixinScreenshotHelper {
    @Inject(method={"saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;"}, at={@At(value="HEAD")})
    private static void saveScreenshot(File file, String string, int n, int n2, Framebuffer framebuffer, CallbackInfoReturnable callbackInfoReturnable) {
        if (Manager.moduleManager.getModuleByClass(ImgurScreenshot.class) != null && Manager.moduleManager.isModuleEnabled(ImgurScreenshot.class)) {
            EventClientScreenShot eventClientScreenShot = new EventClientScreenShot(ScreenShotHelper.createScreenshot((int)n, (int)n2, (Framebuffer)framebuffer));
            eventClientScreenShot.start();
        }
    }
}
