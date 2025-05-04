package me.ciruu.abyss.mixin.client.blocks;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.events.render.EventRenderBlockLayer;
import me.ciruu.abyss.modules.exploit.Phase;
import me.ciruu.abyss.modules.render.WallHack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
public class MixinBlock {
    @Inject(method = {"shouldSideBeRendered"}, at = {@At("HEAD")}, cancellable = true)
    public void shouldSideBeRendered(IBlockState paramIBlockState, IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, EnumFacing paramEnumFacing, CallbackInfoReturnable paramCallbackInfoReturnable) {
        if (Manager.moduleManager.getModuleByClass(WallHack.class) != null && Manager.moduleManager.isModuleEnabled(WallHack.class))
            ((WallHack)Manager.moduleManager.getModuleByClass(WallHack.class)).Method2463(null, paramIBlockState, paramIBlockAccess, paramBlockPos, paramEnumFacing, paramCallbackInfoReturnable);
    }

    @Inject(method = {"getRenderLayer"}, at = {@At("HEAD")}, cancellable = true)
    public void getRenderLayer(CallbackInfoReturnable paramCallbackInfoReturnable) {
        EventRenderBlockLayer eventRenderBlockLayer = new EventRenderBlockLayer(null);
        AbyssMod.EVENT_BUS.post(eventRenderBlockLayer);
        if (eventRenderBlockLayer.isCancelled()) {
            paramCallbackInfoReturnable.cancel();
            paramCallbackInfoReturnable.setReturnValue(eventRenderBlockLayer.getBlockRenderLayer());
        }
    }

    @Inject(method = {"getLightValue"}, at = {@At("HEAD")}, cancellable = true)
    public void getLightValue(CallbackInfoReturnable paramCallbackInfoReturnable) {
        if (Manager.moduleManager.getModuleByClass(WallHack.class) != null && Manager.moduleManager.isModuleEnabled(WallHack.class))
            ((WallHack)Manager.moduleManager.getModuleByClass(WallHack.class)).Method2464(null, paramCallbackInfoReturnable);
    }

    @Inject(method = {"getCollisionBoundingBox"}, at = {@At("RETURN")}, cancellable = true)
    public void shouldSideBeRendered(IBlockState paramIBlockState, IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, CallbackInfoReturnable paramCallbackInfoReturnable) {
        if (Manager.moduleManager.getModuleByClass(Phase.class) != null && Manager.moduleManager.isModuleEnabled(Phase.class) && ((Phase)Manager.moduleManager.getModuleByClass(Phase.class)).Method2465())
            paramCallbackInfoReturnable.setReturnValue(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
    }

    static {

    }
}
