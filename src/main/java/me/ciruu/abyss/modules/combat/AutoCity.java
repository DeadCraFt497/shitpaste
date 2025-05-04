package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.misc.SpeedMine;
import me.ciruu.abyss.modules.render.CityESP;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/*
 * Illegal identifiers - recommend switching to table mode
 */
public class AutoCity
extends Module {
    private final Entity entity = (Entity)null;
    private final List<BlockPos> blockPositions;
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting rotate = new Setting("Rotate", "", this, false);
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting raytrace = new Setting("Raytrace", "", this, false);
    private final Setting burrow = new Setting("Burrow", "", this, false);
    private final Setting autospeedmine = new Setting("AutoSpeedMine", "", (Module)this, (Object)false, this.mainwindow, AutoCity::getMine);
    private final Setting mode_1_13 = new Setting("1.13Mode", "", this, false);
    private boolean bool1 = false;
    @EventHandler
    private Listener listen1 = new Listener<EventPlayerUpdateWalking>(this::getSpeed, new Predicate[0]);

    public AutoCity() {
        super("AutoCity", "City near players.", Class11.COMBAT, "");
        this.addSetting(this.rotate);
        this.addSetting(this.range);
        this.addSetting(this.raytrace);
        this.addSetting(this.burrow);
        this.addSetting(this.autospeedmine);
        this.addSetting(this.mode_1_13);
        blockPositions = Collections.emptyList();
    }

    public List<BlockPos> getBlockPos() {
        return this.blockPositions;
    }

    public void getManager() {
        super.getEnable();
        this.bool1 = false;

        ArrayList<Class307> arrayList = ((CityESP) Manager.moduleManager.getModuleByClass(CityESP.class)).Method1102();

        if (arrayList.isEmpty() &&
                !((Boolean) this.burrow.getValue()).booleanValue() &&
                !((Boolean) this.mode_1_13.getValue()).booleanValue()) {
            Class547.printChatMessage(ChatFormatting.RED + "There is no one to city!");
            return;
        }

        EntityPlayer entityPlayer = null;
        BlockPos blockPos2 = null;
        double closestDist = 100.0;

        if (!arrayList.isEmpty()) {
            for (Class307 class307 : arrayList) {
                for (BlockPos blockPos3 : getBlockPos()) {
                    if (blockPos2 == null) {
                        entityPlayer = (EntityPlayer) class307.Method2970();
                        blockPos2 = blockPos3;
                        continue;
                    }
                    double dist = blockPos3.getDistance(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
                    if (dist < closestDist) {
                        closestDist = dist;
                        blockPos2 = blockPos3;
                        entityPlayer = (EntityPlayer) class307.Method2970();
                    }
                }
            }
        }

        if (((Boolean) this.mode_1_13.getValue()).booleanValue()) {
            for (EntityPlayer entityPlayer2 : Globals.mc.world.playerEntities) {
                if (Globals.mc.player.getDistance(entityPlayer2) > ((Float) this.range.getValue()).floatValue() ||
                        entityPlayer2 == Globals.mc.player ||
                        Manager.Field223.Method711(entityPlayer2)) {
                    continue;
                }

                BlockPos possible = this.getBlockState(entityPlayer2);
                if (possible != null) {
                    blockPos2 = possible;
                    break;
                }
            }
        }

        BlockPos burrowBlock = this.getPlayerEntitys();
        if (((Boolean) this.burrow.getValue()).booleanValue() && burrowBlock != null) {
            blockPos2 = burrowBlock;
        }

        if (blockPos2 == null) {
            Class547.printChatMessage(ChatFormatting.RED + "Couldn't find any blocks to mine!");
            return;
        }

        Class602.Method3024(blockPos2);

        if (entityPlayer != null) {
            Class547.printChatMessage(ChatFormatting.WHITE + "Attempting to mine a block by your target: " + ChatFormatting.RED + entityPlayer.getName());
        }
    }


    private BlockPos getPlayerEntitys() {
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
            if (Globals.mc.player.getDistance((Entity)entityPlayer) > ((Float)this.range.getValue()).floatValue() || entityPlayer == Globals.mc.player || Manager.Field223.Method711((Entity)entityPlayer) || Globals.mc.world.getBlockState(Class30.Method209(entityPlayer)).getBlock() != Blocks.OBSIDIAN && Globals.mc.world.getBlockState(Class30.Method209(entityPlayer)).getBlock() != Blocks.ENDER_CHEST) continue;
            return Class30.Method209(entityPlayer);
        }
        return null;
    }

    public BlockPos getBlockState(EntityPlayer entityPlayer) {
        BlockPos[] blockPosArray;
        Vec3d vec3d = Class29.getVector((Entity)entityPlayer, Globals.mc.getRenderPartialTicks());
        BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.south();
        BlockPos blockPos4 = blockPos.east();
        BlockPos blockPos5 = blockPos.west();
        for (BlockPos blockPos6 : blockPosArray = new BlockPos[]{blockPos2, blockPos3, blockPos4, blockPos5}) {
            Block block = Globals.mc.world.getBlockState(blockPos6).getBlock();
            if (block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST || Globals.mc.player.getDistance((double)blockPos6.getX(), (double)blockPos6.getY(), (double)blockPos6.getZ()) > (double)((Float)this.range.getValue()).floatValue()) continue;
            return blockPos6;
        }
        return null;
    }

    private void getSpeed(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        List object = Collections.singletonList(new Object());
        boolean bl;
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        if (((Boolean)this.autospeedmine.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(SpeedMine.class) && !this.bool1) {
            Class602.Method3026(((Float)this.range.getValue()).floatValue(), (Boolean)this.raytrace.getValue());
            Class602.Method3026(((Float)this.range.getValue()).floatValue(), (Boolean)this.raytrace.getValue());
            return;
        }
        boolean bl2 = bl = Globals.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
        if (!bl) {
            for (int i = 0; i < 9; ++i) {
                 object = Collections.singletonList(Globals.mc.player.inventory.getStackInSlot(i));
                if (object.isEmpty() || object != Items.DIAMOND_PICKAXE) continue;
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
        BlockPos blockPos = Class602.Method3025();
        if (blockPos == null) {
            Class547.printChatMessage(ChatFormatting.GREEN + "Done!");
            return;
        }
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            eventPlayerUpdateWalking.cancel();
            object = Collections.singletonList(Class354.Method1505((double) blockPos.getX() + 0.5, (double) blockPos.getY() - 0.5, (double) blockPos.getZ() + 0.5, (EntityPlayer) Globals.mc.player));
            eventPlayerUpdateWalking.setPitch((double) object.get(1));
            eventPlayerUpdateWalking.setYaw((double) object.get(0));
        }
        Class602.Method3026(((Float)this.range.getValue()).floatValue(), (Boolean)this.raytrace.getValue());
    }

    private static boolean getMine() {
        return Manager.moduleManager.getModuleByClass(SpeedMine.class) == null || Manager.moduleManager.isModuleEnabled(SpeedMine.class);
    }
}
