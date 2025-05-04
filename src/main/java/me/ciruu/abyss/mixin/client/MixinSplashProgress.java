package me.ciruu.abyss.mixin.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.fml.client.SplashProgress;
import org.lwjgl.LWJGLException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SplashProgress.class, remap = false)
public class MixinSplashProgress {

    private static Method disableSplashMethod;

    @Inject(method = "drawVanillaScreen", at = @At("HEAD"), cancellable = true)
    private static void drawVanillaScreen(TextureManager textureManager, CallbackInfo ci) throws LWJGLException {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && textureManager != null) {
        }
        ci.cancel();
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private static void onStart(CallbackInfo ci) {
        try {
            if (disableSplashMethod == null) {
                disableSplashMethod = SplashProgress.class.getDeclaredMethod("disableSplash");
                disableSplashMethod.setAccessible(true);
            }
            disableSplashMethod.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(); // Log instead of crash
        }
        ci.cancel(); // Prevent original splash screen behavior
    }
}
