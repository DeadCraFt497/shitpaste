/** @Info DeadCraFt497 May 21st */

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
    
    public static Method method = new Method();
    private static void start(CallbackInfo callbackInfo) {
        callbackInfo.cancel();
        
    }
    static {
        CallbackInfo call = null;
        start(call.cancel());
    }
}
