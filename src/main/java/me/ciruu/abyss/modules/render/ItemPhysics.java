package me.ciruu.abyss.modules.render;

import java.util.Random;
import java.util.function.Predicate;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemPhysics
extends Module {
    public static final Setting rotatespeed = new Setting("RotateSpeed", "", null, (Object)Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f));
    public static Minecraft mc = Minecraft.getMinecraft();
    public static long Field1898;
    private static double Field1899;
    private static Random Field1900;
    @EventHandler
    private Listener Field1901 = new Listener<Class66>(ItemPhysics::Method2277, new Predicate[0]);

    public ItemPhysics() {
        super("ItemPhysics", "Change the item physics.", Class11.RENDER, "");
    }

    private static ResourceLocation Method2278() {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static void Method2279(Entity entity, double d, double d2, double d3) {
        EntityItem entityItem;
        ItemStack itemStack;
        Field1899 = (double)(System.nanoTime() - Field1898) / 2500000.0 * (double)((Float)rotatespeed.getValue()).floatValue();
        if (!ItemPhysics.mc.inGameHasFocus) {
            Field1899 = 0.0;
        }
        int n = (itemStack = (entityItem = (EntityItem)entity).getItem()) != null && itemStack.getItem() != null ? Item.getIdFromItem((Item)itemStack.getItem()) + itemStack.getMetadata() : 187;
        Field1900.setSeed(n);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ItemPhysics.Method2278());
        Minecraft.getMinecraft().getTextureManager().getTexture(ItemPhysics.Method2278()).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc((int)516, (float)0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.pushMatrix();
        IBakedModel iBakedModel = mc.getRenderItem().getItemModelMesher().getItemModel(itemStack);
        boolean bl = iBakedModel.isGui3d();
        boolean bl2 = iBakedModel.isGui3d();
        int n2 = ItemPhysics.Method2280(itemStack);
        GlStateManager.translate((float)((float)d), (float)((float)d2), (float)((float)d3));
        if (iBakedModel.isGui3d()) {
            GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
        }
        GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)entityItem.rotationYaw, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.translate((double)0.0, (double)0.0, (double)(bl2 ? -0.08 : -0.04));
        if (bl2 || ItemPhysics.mc.getRenderManager().options != null) {
            double d4;
            if (bl2) {
                if (!entityItem.onGround) {
                    d4 = Field1899 * 2.0;
                    entityItem.rotationPitch = (float)((double)entityItem.rotationPitch + d4);
                }
            } else if (!(Double.isNaN(entityItem.posX) || Double.isNaN(entityItem.posY) || Double.isNaN(entityItem.posZ) || entityItem.world == null)) {
                if (entityItem.onGround) {
                    entityItem.rotationPitch = 0.0f;
                } else {
                    d4 = Field1899 * 2.0;
                    entityItem.rotationPitch = (float)((double)entityItem.rotationPitch + d4);
                }
            }
            GlStateManager.rotate((float)entityItem.rotationPitch, (float)1.0f, (float)0.0f, (float)0.0f);
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        for (int i = 0; i < n2; ++i) {
            GlStateManager.pushMatrix();
            if (bl) {
                if (i > 0) {
                    float f = (Field1900.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float f2 = (Field1900.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float f3 = (Field1900.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    GlStateManager.translate((float)f, (float)f2, (float)f3);
                }
                mc.getRenderItem().renderItem(itemStack, iBakedModel);
                GlStateManager.popMatrix();
                continue;
            }
            mc.getRenderItem().renderItem(itemStack, iBakedModel);
            GlStateManager.popMatrix();
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.05375f);
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(ItemPhysics.Method2278());
        Minecraft.getMinecraft().getTextureManager().getTexture(ItemPhysics.Method2278()).restoreLastBlurMipmap();
    }

    private static int Method2280(ItemStack itemStack) {
        int n = 1;
        if (itemStack.stackSize > 48) {
            n = 5;
        } else if (itemStack.stackSize > 32) {
            n = 4;
        } else if (itemStack.stackSize > 16) {
            n = 3;
        } else if (itemStack.stackSize > 1) {
            n = 2;
        }
        return n;
    }

    private static void Method2277(Class66 class66) {
        Field1898 = System.nanoTime();
    }

    static {
        Field1900 = new Random();
    }
}
