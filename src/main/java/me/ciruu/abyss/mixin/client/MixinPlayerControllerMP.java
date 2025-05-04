package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.events.player.EventPlayerClickBlock;
import me.ciruu.abyss.events.player.EventPlayerDamageBlock;
import me.ciruu.abyss.events.player.EventPlayerDestroyBlock;
import me.ciruu.abyss.events.player.EventPlayerResetBlockRemoving;
import me.ciruu.abyss.events.player.EventPlayerStoppedUsingItem;
import me.ciruu.abyss.modules.exploit.Reach;
import me.ciruu.abyss.modules.misc.NoGlitchBlocks;
import me.ciruu.abyss.modules.movement.Anchor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerControllerMP.class})
public class MixinPlayerControllerMP {
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private GameType currentGameType;
    @Shadow
    private BlockPos currentBlock;

    @Inject(method={"onPlayerDamageBlock"}, at={@At(value="HEAD")}, cancellable=true)
    public void onPlayerDamageBlock(BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable callbackInfoReturnable) {
        EventPlayerDamageBlock eventPlayerDamageBlock = new EventPlayerDamageBlock(blockPos, enumFacing);
        AbyssMod.EVENT_BUS.post(eventPlayerDamageBlock);
        if (eventPlayerDamageBlock.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method={"resetBlockRemoving"}, at={@At(value="HEAD")}, cancellable=true)
    public void resetBlockRemoving(CallbackInfo callbackInfo) {
        EventPlayerResetBlockRemoving eventPlayerResetBlockRemoving = new EventPlayerResetBlockRemoving();
        AbyssMod.EVENT_BUS.post(eventPlayerResetBlockRemoving);
        if (eventPlayerResetBlockRemoving.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"clickBlock"}, at={@At(value="HEAD")}, cancellable=true)
    public void clickBlock(BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable callbackInfoReturnable) {
        EventPlayerClickBlock eventPlayerClickBlock = new EventPlayerClickBlock(blockPos, enumFacing);
        AbyssMod.EVENT_BUS.post(eventPlayerClickBlock);
        if (eventPlayerClickBlock.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method={"onPlayerDestroyBlock"}, at={@At(value="HEAD")}, cancellable=true)
    public void onPlayerDestroyBlock(BlockPos blockPos, CallbackInfoReturnable callbackInfoReturnable) {
        EventPlayerDestroyBlock eventPlayerDestroyBlock = new EventPlayerDestroyBlock(blockPos);
        AbyssMod.EVENT_BUS.post(eventPlayerDestroyBlock);
        if (eventPlayerDestroyBlock.isCancelled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(false);
        }
        if (Manager.moduleManager.getModuleByClass(NoGlitchBlocks.class) != null && Manager.moduleManager.isModuleEnabled(NoGlitchBlocks.class)) {
            ItemStack worldClient_;
            WorldClient worldClient;
            callbackInfoReturnable.cancel();
            if (this.currentGameType.hasLimitedInteractions()) {
                if (this.currentGameType == GameType.SPECTATOR) {
                    callbackInfoReturnable.setReturnValue(false);
                }
                if (!this.mc.player.isAllowEdit()) {
                    worldClient_ = this.mc.player.getHeldItemMainhand();
                    if (worldClient_.isEmpty()) {
                        callbackInfoReturnable.setReturnValue(false);
                    }
                    if (!worldClient_.canDestroy(this.mc.world.getBlockState(blockPos).getBlock())) {
                        callbackInfoReturnable.setReturnValue(false);
                    }
                }
            }
            if (this.currentGameType.isCreative() && !this.mc.player.getHeldItemMainhand().isEmpty() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                callbackInfoReturnable.setReturnValue(false);
            } else {
                worldClient = this.mc.world;
                IBlockState iBlockState = worldClient.getBlockState(blockPos);
                Block block = iBlockState.getBlock();
                if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !this.mc.player.canUseCommandBlock()) {
                    callbackInfoReturnable.setReturnValue(false);
                } else if (iBlockState.getMaterial() == Material.AIR) {
                    callbackInfoReturnable.setReturnValue(false);
                } else {
                    ItemStack itemStack;
                    boolean bl;
                    worldClient.playEvent(2001, blockPos, Block.getStateId((IBlockState)iBlockState));
                    block.onBlockHarvested((World)worldClient, blockPos, iBlockState, (EntityPlayer)this.mc.player);
                    boolean bl2 = false;
                    boolean bl3 = bl = Manager.moduleManager.getModuleByClass(NoGlitchBlocks.class) != null && Manager.moduleManager.isModuleEnabled(NoGlitchBlocks.class) && (Boolean)((NoGlitchBlocks)Manager.moduleManager.getModuleByClass(NoGlitchBlocks.class)).breakSetting.getValue() != false;
                    if (!bl && (bl2 = worldClient.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 11))) {
                        block.onPlayerDestroy((World)worldClient, blockPos, iBlockState);
                    }
                    this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
                    if (!this.currentGameType.isCreative() && !(itemStack = this.mc.player.getHeldItemMainhand()).isEmpty()) {
                        itemStack.onBlockDestroyed((World)worldClient, iBlockState, blockPos, (EntityPlayer)this.mc.player);
                        if (itemStack.isEmpty()) {
                            this.mc.player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                        }
                    }
                    callbackInfoReturnable.setReturnValue(bl2);
                }
            }
        }
    }

    @Inject(method={"getBlockReachDistance"}, at={@At(value="RETURN")}, cancellable=true)
    private void getReachDistanceHook(CallbackInfoReturnable callbackInfoReturnable) {
        if (Manager.moduleManager.isModuleEnabled(Anchor.class) && Anchor.Field474) {
            callbackInfoReturnable.setReturnValue(((Anchor)Manager.moduleManager.getModuleByClass(Anchor.class)).blockdistance.getValue());
        }
        if (Manager.moduleManager.isModuleEnabled(Reach.class)) {
            float f = ((Float)callbackInfoReturnable.getReturnValue()).floatValue();
            callbackInfoReturnable.setReturnValue((Boolean)((Reach)Manager.moduleManager.getModuleByClass(Reach.class)).override.getValue() != false ? (Float)((Reach)Manager.moduleManager.getModuleByClass(Reach.class)).reach.getValue() : Float.valueOf(f + ((Float)((Reach)Manager.moduleManager.getModuleByClass(Reach.class)).add.getValue()).floatValue()));
        }
    }

    @Inject(method={"onStoppedUsingItem"}, at={@At(value="HEAD")}, cancellable=true)
    public void onPlayerDestroyBlock(EntityPlayer entityPlayer, CallbackInfo callbackInfo) {
        EventPlayerStoppedUsingItem eventPlayerStoppedUsingItem = new EventPlayerStoppedUsingItem();
        AbyssMod.EVENT_BUS.post(eventPlayerStoppedUsingItem);
        if (eventPlayerStoppedUsingItem.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
