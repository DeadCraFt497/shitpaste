package me.ciruu.abyss;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.LWJGLException;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SplashProgressMain extends SplashProgress {

    private static Method method;

    private static void start(CallbackInfo callbackInfo) {
        /*
        try {
            method = SplashProgress.class.getDeclaredMethod("disableSplash", new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        method.setAccessible(true);

        try {
            method.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        callbackInfo.cancel();
    */}
}