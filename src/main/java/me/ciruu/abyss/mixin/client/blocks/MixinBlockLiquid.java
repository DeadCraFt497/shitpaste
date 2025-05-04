package me.ciruu.abyss.mixin.client.blocks;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.liquid.EventLiquidCollisionBB;
import me.ciruu.abyss.events.render.EventRenderBlockLayer;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockLiquid.class})
public class MixinBlockLiquid {
    @Inject(method={"getRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void getRenderLayer(CallbackInfoReturnable callbackInfoReturnable) {
        EventRenderBlockLayer eventRenderBlockLayer = new EventRenderBlockLayer(null);
        AbyssMod.EVENT_BUS.post(eventRenderBlockLayer);
        if (eventRenderBlockLayer.isCancelled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(eventRenderBlockLayer.getBlockRenderLayer());
        }
    }

    @Inject(method={"getCollisionBoundingBox"}, at={@At(value="HEAD")}, cancellable=true)
    public void getCollisionBoundingBox(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, CallbackInfoReturnable callbackInfoReturnable) {
        EventLiquidCollisionBB eventLiquidCollisionBB = new EventLiquidCollisionBB(blockPos);
        AbyssMod.EVENT_BUS.post(eventLiquidCollisionBB);
        if (eventLiquidCollisionBB.isCancelled()) {
            callbackInfoReturnable.setReturnValue(eventLiquidCollisionBB.getAxis());
            callbackInfoReturnable.cancel();
        }
    }
}
