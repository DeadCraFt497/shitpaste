package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class WebFill
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting placerange = new Setting("PlaceRange", "Place Range", (Module)this, (Object)Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(6.0f));
    private final Setting placeticks = new Setting("PlaceTicks", "Place Delay", (Module)this, (Object)0, 0, 10);
    private final Setting rotate = new Setting("Rotate", "Rotate", this, false);
    private final Setting blockspertick = new Setting("BlocksPerTick", "Blocks per tick", (Module)this, (Object)1, 1, 20);
    private final Setting disableafter = new Setting("DisableAfter", "Disable", this, false);
    private final Setting disabletime = new Setting("DisableTime", "Disable time", this, 5000, 1, 10000, this.mainwindow, this.disableafter::getValue);
    private final Setting stopAC = new Setting("StopAC", "Stop AutoCrystal while webfilling", this, true);
    private final Setting silent = new Setting("Silent", "", this, true);
    private final Class22 Field2421 = new Class22();
    private int Field2422 = -1;
    private List Field2423 = new ArrayList();
    private int Field2424 = 0;
    public static boolean Field2425 = false;
    private boolean Field2426 = true;
    private int Field2427 = -1;
    @EventHandler
    private Listener Field2428 = new Listener<EventPlayerUpdateWalking>(this::Method2949, new Predicate[0]);

    public WebFill() {
        super("WebFill", "Fill nearby holes with cobwebs.", Class11.COMBAT, "");
        this.addSetting(this.placerange);
        this.addSetting(this.placeticks);
        this.addSetting(this.rotate);
        this.addSetting(this.blockspertick);
        this.addSetting(this.disableafter);
        this.addSetting(this.disabletime);
        this.addSetting(this.stopAC);
        this.addSetting(this.silent);
    }

    public void Method2951() {
        super.getEnable();
        if (this.Method2952()) {
            this.Field2421.getNanoTime();
            Class547.printChatMessage(ChatFormatting.RED + "Can't find cobwebs in your hotbar!");
        }
        if (((Boolean)this.stopAC.getValue()).booleanValue()) {
            Field2425 = true;
        }
        this.Field2421.getNanoTime();
    }

    public void Method2954() {
        super.getDisable();
        Field2425 = false;
        this.Field2423.clear();
        if (this.Field2422 != -1 && this.Field2426) {
            Globals.mc.player.inventory.currentItem = this.Field2422;
        }
        this.Field2422 = -1;
        this.Field2426 = false;
    }

    private void Method2955() {
        if (!this.Method2956()) {
            this.Field2421.getNanoTime();
            Class547.printChatMessage(ChatFormatting.YELLOW + "There's no hole to fill!");
        }
        if (Class155.Method545(Blocks.WEB) != Globals.mc.player.inventory.currentItem && !((Boolean) this.silent.getValue())) {
            return;
        }
        if (this.Field2421.getLongNanoTime(((Integer) this.disabletime.getValue()).intValue()) && ((Boolean) this.disableafter.getValue())) {
            this.Field2421.getNanoTime();
            return;
        }
        ArrayList<BlockPos> arrayList = new ArrayList<>();  // Using generics
        if (this.Field2424 >= (Integer) this.placeticks.getValue()) {
            this.Field2424 = 0;
            int n = 0;
            int n2 = Class155.Method545(Blocks.WEB);
            if (((Boolean) this.silent.getValue()) && n2 != -1) {
                this.Field2422 = Globals.mc.player.inventory.currentItem;
                Globals.mc.player.inventory.currentItem = n2;
                Globals.mc.playerController.updateController();
            }
            for (BlockPos blockPos = (BlockPos)null; blockPos == this.Field2423;) {
                Class31.Method536(blockPos, EnumHand.MAIN_HAND, (Boolean) this.rotate.getValue(), true, Globals.mc.player.isSneaking());
                arrayList.add(blockPos);
                if (++n < (Integer) this.blockspertick.getValue()) continue;
                break;
            }
            if (((Boolean) this.silent.getValue()) && this.Field2422 != -1 && n2 != -1) {
                Globals.mc.player.inventory.currentItem = this.Field2422;
                Globals.mc.playerController.updateController();
            }
        }
        arrayList.forEach(this::Method2957);
        if (this.Field2423.isEmpty()) {
            Class547.printChatMessage(ChatFormatting.GREEN + "Done!");
            this.Field2421.getNanoTime();
        }
        ++this.Field2424;
    }


    private boolean Method2952() {
        this.Field2422 = Globals.mc.player.inventory.currentItem;
        this.Field2427 = Class155.Method545(Blocks.WEB);
        if (this.Field2427 != -1 && !((Boolean)this.silent.getValue()).booleanValue()) {
            Globals.mc.player.inventory.currentItem = this.Field2427;
            Globals.mc.playerController.updateController();
        }
        return Globals.mc.player == null || Globals.mc.world == null || this.Field2427 == -1;
    }

    public boolean Method2956() {
        this.Field2423.clear();

        // Get the placerange as a float once
        float range = ((Float) this.placerange.getValue()).floatValue();

        // Iterate through the possible block positions
        for (BlockPos blockPos = (BlockPos)null; blockPos == Class110.Method1096(Class202.Method931(), range, (int) range, false, true, 0);) {

            // Check surrounding blocks for specific conditions (using proper parentheses to group logic)
            boolean bl =
                    (Globals.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK ||
                            Globals.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) &&
                            (Globals.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK ||
                                    Globals.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) &&
                            (Globals.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK ||
                                    Globals.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) &&
                            (Globals.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK ||
                                    Globals.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) &&
                            Globals.mc.world.getBlockState(blockPos.add(0, 0, 0)).getMaterial() == Material.AIR &&
                            Globals.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() == Material.AIR &&
                            Globals.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() == Material.AIR;

            // If the condition is met, add the blockPos to the list
            if (!bl) continue;
            this.Field2423.add(blockPos);
        }

        return !this.Field2423.isEmpty();
    }


    private void Method2957(BlockPos blockPos) {
        this.Field2423.remove(blockPos);
    }

    private void Method2949(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() == Class53.PRE) {
            this.Method2955();
        }
    }
}
