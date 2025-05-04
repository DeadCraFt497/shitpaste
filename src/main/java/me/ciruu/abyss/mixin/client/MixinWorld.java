package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.events.render.EventRenderSkyColor;
import me.ciruu.abyss.modules.exploit.LiquidInteract;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={World.class})
public class MixinWorld {
    @Inject(method={"getSkyColor"}, at={@At(value="HEAD")}, cancellable=true)
    public void getSkyColor(Entity entity, float f, CallbackInfoReturnable callbackInfoReturnable) {
        EventRenderSkyColor eventRenderSkyColor = new EventRenderSkyColor();
        AbyssMod.EVENT_BUS.post(eventRenderSkyColor);
        if (eventRenderSkyColor.isCancelled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(eventRenderSkyColor.Method2003());
        }
    }

    @Inject(method={"rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;ZZZ)Lnet/minecraft/util/math/RayTraceResult;"}, at={@At(value="HEAD")}, cancellable=true)
    public void inject(Vec3d vec3d, Vec3d vec3d2, boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable callbackInfoReturnable) {
        if (Globals.mc.world == null || Globals.mc.player == null) {
            return;
        }
        if (Manager.moduleManager.isModuleEnabled(LiquidInteract.class)) {
            callbackInfoReturnable.cancel();
            if (!(Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z))) {
                if (!(Double.isNaN(vec3d2.x) || Double.isNaN(vec3d2.y) || Double.isNaN(vec3d2.z))) {
                    RayTraceResult rayTraceResult;
                    int n = MathHelper.floor((double)vec3d2.x);
                    int n2 = MathHelper.floor((double)vec3d2.y);
                    int n3 = MathHelper.floor((double)vec3d2.z);
                    int n4 = MathHelper.floor((double)vec3d.x);
                    int n5 = MathHelper.floor((double)vec3d.y);
                    int n6 = MathHelper.floor((double)vec3d.z);
                    BlockPos blockPos = new BlockPos(n4, n5, n6);
                    IBlockState iBlockState = Globals.mc.world.getBlockState(blockPos);
                    Block block = iBlockState.getBlock();
                    if ((!bl2 || iBlockState.getCollisionBoundingBox((IBlockAccess)Globals.mc.world, blockPos) != Block.NULL_AABB) && (this.isLiquid(blockPos) ? this.checkPlace(blockPos, iBlockState, bl) : block.canCollideCheck(iBlockState, bl)) && (rayTraceResult = iBlockState.collisionRayTrace((World)Globals.mc.world, blockPos, vec3d, vec3d2)) != null) {
                        callbackInfoReturnable.setReturnValue(rayTraceResult);
                        return;
                    }
                    rayTraceResult = null;
                    int n7 = 200;
                    while (n7-- >= 0) {
                        EnumFacing enumFacing;
                        if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
                            callbackInfoReturnable.setReturnValue(null);
                            return;
                        }
                        if (n4 == n && n5 == n2 && n6 == n3) {
                            callbackInfoReturnable.setReturnValue(bl3 ? rayTraceResult : null);
                            return;
                        }
                        boolean bl4 = true;
                        boolean bl5 = true;
                        boolean bl6 = true;
                        double d = 999.0;
                        double d2 = 999.0;
                        double d3 = 999.0;
                        if (n > n4) {
                            d = (double)n4 + 1.0;
                        } else if (n < n4) {
                            d = (double)n4 + 0.0;
                        } else {
                            bl4 = false;
                        }
                        if (n2 > n5) {
                            d2 = (double)n5 + 1.0;
                        } else if (n2 < n5) {
                            d2 = (double)n5 + 0.0;
                        } else {
                            bl5 = false;
                        }
                        if (n3 > n6) {
                            d3 = (double)n6 + 1.0;
                        } else if (n3 < n6) {
                            d3 = (double)n6 + 0.0;
                        } else {
                            bl6 = false;
                        }
                        double d4 = 999.0;
                        double d5 = 999.0;
                        double d6 = 999.0;
                        double d7 = vec3d2.x - vec3d.x;
                        double d8 = vec3d2.y - vec3d.y;
                        double d9 = vec3d2.z - vec3d.z;
                        if (bl4) {
                            d4 = (d - vec3d.x) / d7;
                        }
                        if (bl5) {
                            d5 = (d2 - vec3d.y) / d8;
                        }
                        if (bl6) {
                            d6 = (d3 - vec3d.z) / d9;
                        }
                        if (d4 == -0.0) {
                            d4 = -1.0E-4;
                        }
                        if (d5 == -0.0) {
                            d5 = -1.0E-4;
                        }
                        if (d6 == -0.0) {
                            d6 = -1.0E-4;
                        }
                        if (d4 < d5 && d4 < d6) {
                            enumFacing = n > n4 ? EnumFacing.WEST : EnumFacing.EAST;
                            vec3d = new Vec3d(d, vec3d.y + d8 * d4, vec3d.z + d9 * d4);
                        } else if (d5 < d6) {
                            enumFacing = n2 > n5 ? EnumFacing.DOWN : EnumFacing.UP;
                            vec3d = new Vec3d(vec3d.x + d7 * d5, d2, vec3d.z + d9 * d5);
                        } else {
                            enumFacing = n3 > n6 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                            vec3d = new Vec3d(vec3d.x + d7 * d6, vec3d.y + d8 * d6, d3);
                        }
                        n4 = MathHelper.floor((double)vec3d.x) - (enumFacing == EnumFacing.EAST ? 1 : 0);
                        n5 = MathHelper.floor((double)vec3d.y) - (enumFacing == EnumFacing.UP ? 1 : 0);
                        n6 = MathHelper.floor((double)vec3d.z) - (enumFacing == EnumFacing.SOUTH ? 1 : 0);
                        blockPos = new BlockPos(n4, n5, n6);
                        IBlockState iBlockState2 = Globals.mc.world.getBlockState(blockPos);
                        Block block2 = iBlockState2.getBlock();
                        if (bl2 && iBlockState2.getMaterial() != Material.PORTAL && iBlockState2.getCollisionBoundingBox((IBlockAccess)Globals.mc.world, blockPos) == Block.NULL_AABB) continue;
                        if (this.isLiquid(blockPos) ? this.checkPlace(blockPos, iBlockState2, bl) : block2.canCollideCheck(iBlockState2, bl)) {
                            RayTraceResult rayTraceResult2 = iBlockState2.collisionRayTrace((World)Globals.mc.world, blockPos, vec3d, vec3d2);
                            if (rayTraceResult2 == null) continue;
                            callbackInfoReturnable.setReturnValue(rayTraceResult2);
                            return;
                        }
                        rayTraceResult = new RayTraceResult(RayTraceResult.Type.MISS, vec3d, enumFacing, blockPos);
                    }
                    callbackInfoReturnable.setReturnValue(bl3 ? rayTraceResult : null);
                    return;
                }
                callbackInfoReturnable.setReturnValue(null);
                return;
            }
            callbackInfoReturnable.setReturnValue(null);
            return;
        }
    }

    private boolean checkPlace(BlockPos blockPos, IBlockState iBlockState, boolean bl) {
        EnumFacing[] enumFacingArray;
        for (EnumFacing enumFacing : enumFacingArray = EnumFacing.values()) {
            BlockPos blockPos2 = blockPos.offset(enumFacing);
            if (Globals.mc.world.getBlockState(blockPos2).getBlock() != Blocks.AIR) continue;
            return true;
        }
        return Globals.mc.world.getBlockState(blockPos).getBlock().canCollideCheck(iBlockState, bl);
    }

    private boolean isLiquid(BlockPos blockPos) {
        return Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER || Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.LAVA || Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.FLOWING_LAVA || Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.FLOWING_WATER;
    }
}
