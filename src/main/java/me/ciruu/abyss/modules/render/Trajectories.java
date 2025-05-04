package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class318;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class319;
import me.ciruu.abyss.enums.Class458;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Trajectories
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class458.Both);
    private final Setting color = new Setting("Color", "", this, new Color(142, 0, 243, 125));
    private final Setting width = new Setting("Width", "Width", this, 1, 1, 10, this.mainwindow, this::Method1991);
    private final Queue Field1584 = new ConcurrentLinkedQueue();
    @EventHandler
    private Listener Field1585 = new Listener<Class66>(this::Method1992, -9000, new Predicate[0]);

    public Trajectories() {
        super("Trajectories", "Shows the trajectory throwable items.", Class11.RENDER, "");
        this.addSetting(this.mode);
        this.addSetting(this.color);
        this.addSetting(this.width);
    }

    private Class319 Method1994(EntityPlayerSP entityPlayerSP) {
        if (entityPlayerSP.getHeldItemMainhand() == null) {
            return Class319.NONE;
        }
        ItemStack itemStack = entityPlayerSP.getHeldItem(EnumHand.MAIN_HAND);
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: {
                if (!entityPlayerSP.isHandActive()) break;
                return Class319.ARROW;
            }
            case 346: {
                return Class319.FISHING_ROD;
            }
            case 438: 
            case 441: {
                return Class319.POTION;
            }
            case 384: {
                return Class319.EXPERIENCE;
            }
            case 332: 
            case 344: 
            case 368: {
                return Class319.NORMAL;
            }
        }
        return Class319.NONE;
    }

    private void Method1992(Class66 class66) {
        Vec3d vec3d;
        RayTraceResult vec3d2;
        Class319 class319 = this.Method1994(Globals.mc.player);
        if (class319 == Class319.NONE) {
            return;
        }
        Class318 class318 = new Class318(this, Globals.mc.player, class319);
        while (!class318.Method1191()) {
            class318.Method1185();
            this.Field1584.offer(new Vec3d(Class318.Method1193((Class318)class318).x - Globals.mc.getRenderManager().viewerPosX, Class318.Method1193((Class318)class318).y - Globals.mc.getRenderManager().viewerPosY, Class318.Method1193((Class318)class318).z - Globals.mc.getRenderManager().viewerPosZ));
        }
        boolean bl = Globals.mc.gameSettings.viewBobbing;
        Globals.mc.gameSettings.viewBobbing = false;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GL11.glLineWidth((float)((Integer)this.width.getValue()).intValue());
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.disableDepth();
        GL11.glEnable((int)34383);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        while (!this.Field1584.isEmpty()) {
            bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            vec3d2 = (RayTraceResult) this.Field1584.poll();

            if (this.Field1584.peek() != null) {
                vec3d = (Vec3d)this.Field1584.peek();
                bufferBuilder.pos(vec3d.x, vec3d.y, vec3d.z).color(((Color)this.color.getValue()).getRed(), ((Color)this.color.getValue()).getGreen(), ((Color)this.color.getValue()).getBlue(), ((Color)this.color.getValue()).getAlpha()).endVertex();
            }
            tessellator.draw();
        }
        GlStateManager.shadeModel((int)7424);
        GL11.glDisable((int)2848);
        GlStateManager.enableDepth();
        GL11.glDisable((int)34383);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        Globals.mc.gameSettings.viewBobbing = bl;
        if (Class318.Method1194(class318)) {
            AxisAlignedBB axisAlignedBB;
            vec3d2 = Class318.Method1195(class318);
            vec3d = null;
            if (vec3d2 == null) {
                return;
            }
            if (vec3d2.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = vec3d2.getBlockPos();
                IBlockState iBlockState = Globals.mc.world.getBlockState(blockPos);
                if (iBlockState.getMaterial() != Material.AIR && Globals.mc.world.getWorldBorder().contains(blockPos)) {
                    Vec3d vec3d3 = Class29.getVector((Entity)Globals.mc.player, Globals.mc.getRenderPartialTicks());
                    vec3d = iBlockState.getSelectedBoundingBox((World)Globals.mc.world, blockPos).grow((double)0.002f).offset(-vec3d3.x, -vec3d3.y, -vec3d3.z).getCenter();
                }
            } else if (vec3d2.typeOfHit == RayTraceResult.Type.ENTITY && vec3d2.entityHit != null && (axisAlignedBB = vec3d2.entityHit.getEntityBoundingBox()) != null) {
                vec3d = new AxisAlignedBB(axisAlignedBB.minX - Globals.mc.getRenderManager().viewerPosX, axisAlignedBB.minY - Globals.mc.getRenderManager().viewerPosY, axisAlignedBB.minZ - Globals.mc.getRenderManager().viewerPosZ, axisAlignedBB.maxX - Globals.mc.getRenderManager().viewerPosX, axisAlignedBB.maxY - Globals.mc.getRenderManager().viewerPosY, axisAlignedBB.maxZ - Globals.mc.getRenderManager().viewerPosZ).getCenter();
            }
            if (vec3d != null) {
                switch ((Class458)((Object)this.mode.getValue())) {
                    case Box: {
                        break;
                    }
                    case Line: {
                        break;
                    }
                    case Both: {
                        break;
                    }
                }
            }
        }
    }

    private boolean Method1991() {
        return this.mode.getValue() != Class458.Box;
    }
}
