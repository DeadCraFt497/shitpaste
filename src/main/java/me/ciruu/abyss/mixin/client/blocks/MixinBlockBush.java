package me.ciruu.abyss.mixin.client.blocks;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.render.EventRenderBlockLayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockBush.class})
public class MixinBlockBush {
    @Inject(method={"getRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void getRenderLayer(CallbackInfoReturnable callbackInfoReturnable) {
        EventRenderBlockLayer eventRenderBlockLayer = new EventRenderBlockLayer(null);
        AbyssMod.EVENT_BUS.post(eventRenderBlockLayer);
        if (eventRenderBlockLayer.isCancelled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(eventRenderBlockLayer.getBlockRenderLayer());
        }
    }
}
