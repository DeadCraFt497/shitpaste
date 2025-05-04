package me.ciruu.abyss.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import me.ciruu.abyss.Class482;
import me.ciruu.abyss.Class547;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.render.EventRenderBlockLayer;
import me.ciruu.abyss.events.render.EventRenderColorMultiplier;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeModContainer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class WallHack
extends Module {
    public final Setting opacity = new Setting("Opacity", "Opacity level", (Module)this, (Object)Float.valueOf(128.0f), Float.valueOf(0.0f), Float.valueOf(255.0f));
    public final Setting handblock = new Setting("HandBlock", "Only display hand block", this, false);
    public final Setting threadreload = new Setting("ThreadReload", "Reloads softly", this, true);
    private Class482 blocks = new Class482(new ArrayList<String>(Arrays.asList("minecraft:gold_ore", "minecraft:iron_ore", "minecraft:coal_ore", "minecraft:lapis_ore", "minecraft:diamond_ore", "minecraft:redstone_ore", "minecraft:lit_redstone_ore", "minecraft:tnt", "minecraft:emerald_ore", "minecraft:furnace", "minecraft:lit_furnace", "minecraft:diamond_block", "minecraft:iron_block", "minecraft:gold_block", "minecraft:quartz_ore", "minecraft:beacon", "minecraft:mob_spawner")));
    private Block Field2531;
    @EventHandler
    private Listener Field2532 = new Listener<EventRenderBlockLayer>(this::Method3032, new Predicate[0]);
    @EventHandler
    private Listener Field2533 = new Listener<EventRenderColorMultiplier>(this::Method3033, new Predicate[0]);

    public WallHack() {
        super("WallHack", "Allows you to see through walls.", Class11.RENDER, "");
        this.addSetting(this.opacity);
        this.addSetting(this.threadreload);
    }

    public void Method3035(boolean bl) {
        if(isEnabled()) {
            bl = false;
        }
    }

    public void Method3039() {
        ItemStack itemStack;
        super.getEnable();
        Globals.mc.renderChunksMany = false;
        this.Method3040();
        ForgeModContainer.forgeLightPipelineEnabled = false;
        if (((Boolean)this.handblock.getValue()).booleanValue() && (itemStack = Globals.mc.player.getHeldItemMainhand()).getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
            this.Field2531 = itemBlock.getBlock();
            Class547.printChatMessage("Displaying:" + itemStack.getDisplayName());
        }
    }

    public void Method3038() {
        super.getDisable();
        Globals.mc.renderChunksMany = false;
        this.Method3040();
        ForgeModContainer.forgeLightPipelineEnabled = true;
    }

    public String Method3041() {
        return ChatFormatting.WHITE + String.valueOf(this.opacity.getValue());
    }

    private void Method3040() {
        if (Globals.mc.world == null || Globals.mc.renderGlobal == null) {
            return;
        }
        if (((Boolean)this.threadreload.getValue()).booleanValue()) {
            Globals.mc.addScheduledTask(WallHack::Method3042);
        } else {
            Globals.mc.renderGlobal.loadRenderers();
        }
    }

    private boolean Method3043(Block block) {
        if (((Boolean)this.handblock.getValue()).booleanValue() && this.Field2531 != null) {
            return block == this.Field2531;
        }
        return this.blocks.containsBlock(block);
    }

    public void Method2463(Block block, IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable callbackInfoReturnable) {
        if (this.Method3043(block)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    public void Method2464(Block block, CallbackInfoReturnable callbackInfoReturnable) {
        if (this.Method3043(block)) {
            callbackInfoReturnable.setReturnValue(1);
        }
    }

    private void Method3033(EventRenderColorMultiplier eventRenderColorMultiplier) {
        eventRenderColorMultiplier.cancel();
        eventRenderColorMultiplier.setAlpha(((Float)this.opacity.getValue()).floatValue() / 255.0f);
    }

    private void Method3032(EventRenderBlockLayer eventRenderBlockLayer) {
        if (!this.Method3043(eventRenderBlockLayer.getBlock())) {
            eventRenderBlockLayer.cancel();
            eventRenderBlockLayer.setBlockRenderLayer(BlockRenderLayer.TRANSLUCENT);
        }
    }

    private static void Method3042() {
        int n = (int)Globals.mc.player.posX;
        int n2 = (int)Globals.mc.player.posY;
        int n3 = (int)Globals.mc.player.posZ;
        int n4 = Globals.mc.gameSettings.renderDistanceChunks * 16;
        Globals.mc.renderGlobal.markBlockRangeForRenderUpdate(n - n4, n2 - n4, n3 - n4, n + n4, n2 + n4, n3 + n4);
    }
}
