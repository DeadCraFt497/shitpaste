package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import me.ciruu.abyss.Class307;
import me.ciruu.abyss.Class308;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class CityESP
extends Module {
    private final Setting radius = new Setting("Radius", "", (Module)this, (Object)8, 0, 30);
    private final Setting extended = new Setting("Extended", "", this, false);
    private final Setting color = new Setting("Color", "", this, new Color(0, 255, 0, 125));
    private final Setting selfcolor = new Setting("SelfColor", "", this, new Color(255, 0, 0, 125));
    private ArrayList Field877 = new ArrayList();
    private ICamera Field878 = new Frustum();
    private static final BlockPos[] Field879 = new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
    @EventHandler
    private Listener Field880 = new Listener<Class66>(this::Method1100, new Predicate[0]);

    public CityESP() {
        super("CityESP", "Highlights city blocks.", Class11.RENDER, "");
        this.addSetting(this.radius);
        this.addSetting(this.extended);
        this.addSetting(this.color);
        this.addSetting(this.selfcolor);
    }

    public ArrayList Method1102() {
        ArrayList<Class307> arrayList = new ArrayList<Class307>();
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities.stream().filter(this::Method1103).collect(Collectors.toList())) {
            ArrayList arrayList2 = new ArrayList();
            this.Method1104(entityPlayer, arrayList2);
            if (arrayList2.isEmpty()) continue;
            arrayList.add(new Class307(entityPlayer, arrayList2));
        }
        return arrayList;
    }

    private void Method1104(EntityPlayer entityPlayer, ArrayList arrayList) {
        for (int i = 0; i < 4; ++i) {
            BlockPos blockPos = this.Method1105((Entity)entityPlayer, Field879[i]);
            if (Globals.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) continue;
            boolean bl = false;
            switch (i) {
                case 0: {
                    bl = Class308.Method1106(blockPos.north(1).down(), true, false) || Class308.Method1106(blockPos.north(1).down().east(), true, false) && (Boolean)this.extended.getValue() != false || Class308.Method1106(blockPos.north(1).down().west(), true, false) && (Boolean)this.extended.getValue() != false;
                    break;
                }
                case 1: {
                    bl = Class308.Method1106(blockPos.east(1).down(), true, false) || Class308.Method1106(blockPos.east(1).down().north(), true, false) && (Boolean)this.extended.getValue() != false || Class308.Method1106(blockPos.east(1).down().south(), true, false) && (Boolean)this.extended.getValue() != false;
                    break;
                }
                case 2: {
                    bl = Class308.Method1106(blockPos.south(1).down(), true, false) || Class308.Method1106(blockPos.south(1).down().west(), true, false) && (Boolean)this.extended.getValue() != false || Class308.Method1106(blockPos.south(1).down().east(), true, false) && (Boolean)this.extended.getValue() != false;
                    break;
                }
                case 3: {
                    boolean bl2 = bl = Class308.Method1106(blockPos.west(1).down(), true, false) || Class308.Method1106(blockPos.west(1).down().north(), true, false) && (Boolean)this.extended.getValue() != false || Class308.Method1106(blockPos.west(1).down().south(), true, false) && (Boolean)this.extended.getValue() != false;
                }
            }
            if (!bl) continue;
            arrayList.add(blockPos);
        }
    }

    private BlockPos Method1105(Entity entity, @Nullable BlockPos blockPos) {
        Vec3d vec3d = entity.getPositionVector();
        if (blockPos == null) {
            return new BlockPos(vec3d.x, vec3d.y, vec3d.z);
        }
        return new BlockPos(vec3d.x, vec3d.y, vec3d.z).add((Vec3i)blockPos);
    }

    private void Method1100(Class66 class66) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }
        this.Method1102().forEach(this::Method1107);
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        this.Method1104((EntityPlayer)Globals.mc.player, arrayList);
        arrayList.forEach(this::Method1108);
    }

    private void Method1107(Object o) {
        Class307 class307 = (Class307)o;
        Method1107(class307);
    }

    private void Method1108(BlockPos blockPos) {
        Class50.Method137(blockPos, (Color)this.selfcolor.getValue(), false, (Color)this.selfcolor.getValue(), 1.0f, true, true, ((Color)this.selfcolor.getValue()).getAlpha(), false);
    }

    public void Method1107(Class307 class307) {
        ((ArrayList)class307.Method1109()).forEach(null);
    }

    private void Method1110(BlockPos blockPos) {
        Class50.Method137(blockPos, (Color)this.color.getValue(), false, (Color)this.color.getValue(), 1.0f, true, true, ((Color)this.color.getValue()).getAlpha(), false);
    }

    private boolean Method1103(EntityPlayer entityPlayer) {
        return !Manager.Field223.Method711((Entity)entityPlayer) && Globals.mc.player.getDistance((Entity)entityPlayer) <= (float)((Integer)this.radius.getValue()).intValue() && entityPlayer != Globals.mc.player;
    }
}
