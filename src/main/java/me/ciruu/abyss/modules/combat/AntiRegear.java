package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;

public class AntiRegear
extends Module {
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting raytrace = new Setting("Raytrace", "", this, false);
    public final List list = new ArrayList();
    private EntityPlayer currentTarget;
    private BlockPos blockPos1;
    private BlockPos blockPos2;
    @EventHandler
    private Listener listen1 = new Listener<EventPlayerUpdateWalking>(this::getItem, new Predicate[0]);

    public AntiRegear() {
        super("AntiRegear", "Prevent other players to place shulkers and regear near you", Class11.COMBAT, "");
        this.addSetting(this.range);
        this.addSetting(this.raytrace);
    }

    public void getTarget() {
        super.getEnable();
        this.currentTarget = this.findTarget();
        if (this.currentTarget == null) {
            Class547.printChatMessage("Can't find target!");
            return;
        }
        this.blockPos1 = Class30.Method209(this.currentTarget);
        this.list.clear();
        this.blockPos2 = Class30.Method209((EntityPlayer)Globals.mc.player);
        Globals.mc.world.loadedTileEntityList.forEach(this::getShulkerBox);
        if (!this.list.isEmpty()) {
            Class602.Method3024((BlockPos)this.list.get(0));
        }
    }

    private EntityPlayer findTarget() {
        if (Globals.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer entityPlayer = null;
        for (EntityPlayer entityPlayer2 : Globals.mc.world.playerEntities) {
            if (entityPlayer2 == Globals.mc.player || Manager.Field223.Method711((Entity)entityPlayer2) || !Class354.Method1908((Entity)entityPlayer2) || entityPlayer2.getHealth() <= 0.0f || entityPlayer != null && Minecraft.getMinecraft().player.getDistance((Entity)entityPlayer2) > Minecraft.getMinecraft().player.getDistance((Entity)entityPlayer)) continue;
            entityPlayer = entityPlayer2;
        }
        return entityPlayer;
    }

    private void getItem(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        boolean bl;
        if (this.list.isEmpty()) {
            Class547.printChatMessage(ChatFormatting.RED + "Can't find a shulker!");
            return;
        }
        boolean bl2 = bl = Globals.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
        if (!bl) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = Globals.mc.player.inventory.getStackInSlot(i);
                if (itemStack.isEmpty() || itemStack.getItem() != Items.DIAMOND_PICKAXE) continue;
                bl = true;
                Globals.mc.player.inventory.currentItem = i;
                Globals.mc.playerController.updateController();
                break;
            }
        }
        if (!bl) {
            Class547.printChatMessage(ChatFormatting.RED + "No pickaxe!");
            return;
        }
        this.blockPos2 = Class30.Method209((EntityPlayer)Globals.mc.player);
        if (Class602.Method3025().getDistance(this.blockPos2.getX(), this.blockPos2.getY(), this.blockPos2.getZ()) > (double)((Float)this.range.getValue()).floatValue()) {
            Class547.printChatMessage(ChatFormatting.RED + "Shulker out range!");
            this.list.clear();
        }
        Class602.Method3026(((Float)this.range.getValue()).floatValue(), (Boolean)this.raytrace.getValue());
        if (Class602.Method3027()) {
            Class547.printChatMessage(ChatFormatting.GREEN + "Done!");
            this.getDisabledMethod();
        }
    }

    private void getShulkerBox(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityShulkerBox && this.blockPos2.getDistance(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ()) < (double)((Float)this.range.getValue()).floatValue()) {
            this.list.add(new BlockPos(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ()));
        }
    }


    private void getDisabledMethod() {
        this.list.clear();
        Class547.printChatMessage(ChatFormatting.GREEN + "Shulker boxes removed, regear prevented!");
        this.currentTarget = null;
        boolean j = this.isEnabled();
    }

}
