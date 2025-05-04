package me.ciruu.abyss.mixin.client;

import java.nio.ByteOrder;
import java.nio.IntBuffer;
import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.render.EventRenderColorMultiplier;
import net.minecraft.client.renderer.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BufferBuilder.class})
public abstract class MixinBufferBuilder {
    @Shadow
    private boolean noColor;
    @Shadow
    private IntBuffer rawIntBuffer;

    @Shadow
    public abstract int getColorIndex(int var1);

    @Inject(method={"putColorMultiplier"}, at={@At(value="HEAD")}, cancellable=true)
    public void putColorMultiplier(float f, float f2, float f3, int n, CallbackInfo callbackInfo) {
        EventRenderColorMultiplier eventRenderColorMultiplier = new EventRenderColorMultiplier();
        AbyssMod.EVENT_BUS.post(eventRenderColorMultiplier);
        if (eventRenderColorMultiplier.isCancelled()) {
            callbackInfo.cancel();
            int n2 = this.getColorIndex(n);
            int n3 = -1;
            float f4 = eventRenderColorMultiplier.getAlpha();
            if (!this.noColor) {
                n3 = this.rawIntBuffer.get(n2);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    int n4 = (int)((float)(n3 & 0xFF) * f);
                    int n5 = (int)((float)(n3 >> 8 & 0xFF) * f2);
                    int n6 = (int)((float)(n3 >> 16 & 0xFF) * f3);
                    int n7 = (int)((float)(n3 >> 24 & 0xFF) * f4);
                    n3 = n7 << 24 | n6 << 16 | n5 << 8 | n4;
                } else {
                    int n8 = (int)((float)(n3 >> 24 & 0xFF) * f);
                    int n9 = (int)((float)(n3 >> 16 & 0xFF) * f2);
                    int n10 = (int)((float)(n3 >> 8 & 0xFF) * f3);
                    int n11 = (int)((float)(n3 & 0xFF) * f4);
                    n3 = n8 << 24 | n9 << 16 | n10 << 8 | n11;
                }
            }
            this.rawIntBuffer.put(n2, n3);
        }
    }
}
