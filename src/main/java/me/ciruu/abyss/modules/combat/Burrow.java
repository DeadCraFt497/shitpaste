package me.ciruu.abyss.modules.combat;

import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class421;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Burrow
extends Module {
    private final Setting rotate = new Setting("Rotate", "Rotate", this, false);
    private final Setting offset = new Setting("Offset", "", (Module)this, (Object)Float.valueOf(2.0f), Float.valueOf(-20.0f), Float.valueOf(20.0f));
    private final Setting itemmode = new Setting("ItemMode", "", this, (Object)Class421.Obby);
    private final Setting sneak = new Setting("Sneak", "", this, true);
    private BlockPos Field2061;
    private int Field2062 = -1;
    @EventHandler
    private final Listener Field2063 = new Listener<Class26>(this::Method2490, new Predicate[0]);

    public Burrow() {
        super("Burrow", "Glitch a player into a block at its feet.", Class11.COMBAT, "");
        this.addSetting(this.rotate);
        this.addSetting(this.offset);
        this.addSetting(this.itemmode);
        this.addSetting(this.sneak);
    }

    public void Method2492() {
        super.getEnable();
        this.Field2061 = new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ);
        if (this.Method2493() || this.Method2494(this.Field2061)) {
            this.Method2493();
            return;
        }
        this.Field2062 = Globals.mc.player.inventory.currentItem;
    }

    private boolean Method2494(BlockPos blockPos) {
        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (entity.equals((Object)Globals.mc.player) || entity instanceof EntityItem || !new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    private boolean Method2493() {
        BlockPos blockPos = new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ);
        if (Globals.mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST && Globals.mc.player.posY - (double)blockPos.getY() == 0.875) {
            this.Field2061 = this.Field2061.up();
            return false;
        }
        return !Globals.mc.world.getBlockState(new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ)).getMaterial().isReplaceable();
    }

    private boolean Method2495() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Globals.mc.player.inventory.getStackInSlot(i);
            if (!this.Method2496(itemStack)) continue;
            Class155.Method522(i, false);
            return true;
        }
        Class547.printChatMessage("Can't find blocks in hotbar!");
        return false;
    }

    private boolean Method2496(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemBlock) || itemStack.isEmpty()) {
            return false;
        }
        String string = null;
        if (this.itemmode.getValue() == Class421.BlackList || this.itemmode.getValue() == Class421.Whitelist) {
            try {
                string = itemStack.item.getRegistryName().getPath();
                if (string == null) {
                    return this.itemmode.getValue() == Class421.All;
                }
            }
            catch (Exception exception) {
                return false;
            }
        }
        switch ((Class421)((Object)this.itemmode.getValue())) {
            case All: {
                return true;
            }
            case Obby: {
                return Item.getItemFromBlock((Block)Blocks.OBSIDIAN).equals(itemStack.getItem());
            }
            case Echest: {
                return Item.getItemFromBlock((Block)Blocks.ENDER_CHEST).equals(itemStack.getItem());
            }
            case BlackList: {
                return !Manager.Field1255.Method1595(string, true);
            }
            case Whitelist: {
                return Manager.Field1255.Method1595(string, false);
            }
        }
        return true;
    }

    private void Method2490(Class26 class26) {
        if (!this.Method2495()) {
            return;
        }
        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.41999998688698, Globals.mc.player.posZ, true));
        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.7531999805211997, Globals.mc.player.posZ, true));
        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 1.00133597911214, Globals.mc.player.posZ, true));
        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 1.16610926093821, Globals.mc.player.posZ, true));
        boolean bl = Globals.mc.player.isSneaking();
        if (((Boolean)this.sneak.getValue()).booleanValue() && !bl) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        boolean bl2 = Class31.Method536(this.Field2061, EnumHand.MAIN_HAND, (Boolean)this.rotate.getValue(), true, Globals.mc.player.isSneaking() || (Boolean)this.sneak.getValue() != false);
        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + (double)((Float)this.offset.getValue()).floatValue(), Globals.mc.player.posZ, false));
        if (((Boolean)this.sneak.getValue()).booleanValue() && !bl || bl2) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        Class155.Method522(this.Field2062, false);
    }
}
