package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.render.EventRenderToolTip;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class Tooltips
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting shulkerpreview = new Setting("ShulkerPreview", "", this, true);
    private final Setting echestpreview = new Setting("EchestPreview", "", this, true);
    private final Setting hidename = new Setting("HideName", "", (Module)this, (Object)false, this.mainwindow, this::Method79);
    private final Setting maps = new Setting("Maps", "", this, true);
    private final Setting colorwindow = new Setting("Main", "", this, new Class25("Color Settings"));
    private final Setting background = new Setting("Background", "", (Module)this, (Object)new Color(-15724528), this.colorwindow, Tooltips::Method80);
    private final Setting itembackground = new Setting("ItemBackground", "", (Module)this, (Object)new Color(-14671840), this.colorwindow, Tooltips::Method81);
    private final Setting outline = new Setting("Outline", "", (Module)this, (Object)new Color(-1727000560), this.colorwindow, Tooltips::Method82);
    private final Setting rainbowoutline = new Setting("RainbowOutline", "", (Module)this, (Object)false, this.colorwindow, Tooltips::Method83);
    private final Setting rainbowspeed = new Setting("RainbowSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorwindow, this.rainbowoutline::getValue);
    private final Setting rainbowsaturation = new Setting("RainbowSaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorwindow, this.rainbowoutline::getValue);
    private final Setting rainbowbrightness = new Setting("RainbowBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorwindow, this.rainbowoutline::getValue);
    private final Setting gradient = new Setting("Gradient", "", (Module)this, (Object)false, this.colorwindow, this.rainbowoutline::getValue);
    private final Setting offset = new Setting("Offset", "", this, 2, 1, 6, this.colorwindow, this::Method84);
    private final ArrayList Field126 = new ArrayList();
    private int Field127 = -1;
    private static final ResourceLocation resource = new ResourceLocation("textures/map/map_background.png");
    @EventHandler
    private Listener Field129 = new Listener<EventRenderToolTip>(this::Method85, new Predicate[0]);
    @EventHandler
    private Listener Field130 = new Listener<EventNetworkPrePacketEvent>(this::Method86, new Predicate[0]);

    public Tooltips() {
        super("Tooltips", "Render maps or shulker content in the hud.", Class11.RENDER, "");
        this.addSetting(this.shulkerpreview);
        this.addSetting(this.echestpreview);
        this.addSetting(this.hidename);
        this.addSetting(this.maps);
        this.addSetting(this.colorwindow);
        this.addSetting(this.background);
        this.addSetting(this.itembackground);
        this.addSetting(this.outline);
        this.addSetting(this.rainbowoutline);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
        this.addSetting(this.gradient);
        this.addSetting(this.offset);
    }

    private void Method88(EventRenderToolTip eventRenderToolTip) {
        if (eventRenderToolTip.getStack() == null) {
            return;
        }
        if (Item.getIdFromItem((Item)eventRenderToolTip.getStack().getItem()) == 130 && ((Boolean)this.echestpreview.getValue()).booleanValue()) {
            int n = eventRenderToolTip.getInt1();
            int n2 = eventRenderToolTip.getInt2();
            GlStateManager.translate((float)(n + 10), (float)(n2 - 5), (float)0.0f);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            if (((Boolean)this.rainbowoutline.getValue()).booleanValue() && ((Boolean)this.gradient.getValue()).booleanValue()) {
                Class50.Method92(-2.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 3, 146.0f, 50.0f, ((Color)this.background.getValue()).getRGB());
                Class50.Method92(0.0f, 0.0f, 144.0f, 48.0f, ((Color)this.itembackground.getValue()).getRGB());
                this.Method94(-2.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 3, 148.0f, 62.5f, this.Method93(0), this.Method93(6000 * (Integer)this.offset.getValue()));
            } else {
                Class50.Method92(-3.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 4, 147.0f, 51.0f, this.Method95());
                Class50.Method92(-2.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 3, 146.0f, 50.0f, ((Color)this.background.getValue()).getRGB());
                Class50.Method92(0.0f, 0.0f, 144.0f, 48.0f, ((Color)this.itembackground.getValue()).getRGB());
            }
            if (!((Boolean)this.hidename.getValue()).booleanValue()) {
                Class36.Method63("EnderChest", 0.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 1, -1);
            }
            GlStateManager.enableDepth();
            Globals.mc.getRenderItem().zLevel = 150.0f;
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < this.Field126.size(); ++i) {
                ItemStack itemStack = (ItemStack)this.Field126.get(i);
                if (itemStack == null) continue;
                int n3 = i % 9 * 16;
                int n4 = i / 9 * 16;
                Globals.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n3, n4);
                Globals.mc.getRenderItem().renderItemOverlayIntoGUI(Globals.mc.fontRenderer, itemStack, n3, n4, null);
            }
            eventRenderToolTip.cancel();
            RenderHelper.disableStandardItemLighting();
            Globals.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.translate((float)(-(n + 10)), (float)(-(n2 - 5)), (float)0.0f);
        } else if (eventRenderToolTip.getStack().getItem() instanceof ItemShulkerBox) {
            this.Method96(eventRenderToolTip);
        }
    }

    public void Method96(EventRenderToolTip eventRenderToolTip) {
        NBTTagCompound nBTTagCompound;
        ItemStack itemStack = eventRenderToolTip.getStack();
        NBTTagCompound nBTTagCompound2 = itemStack.getTagCompound();
        if (nBTTagCompound2 != null && nBTTagCompound2.hasKey("BlockEntityTag", 10) && (nBTTagCompound = nBTTagCompound2.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            eventRenderToolTip.cancel();
            NonNullList nonNullList = NonNullList.withSize((int)27, (Object)ItemStack.EMPTY);
            ItemStackHelper.loadAllItems((NBTTagCompound)nBTTagCompound, (NonNullList)nonNullList);
            int n = eventRenderToolTip.getInt1();
            int n2 = eventRenderToolTip.getInt2();
            GlStateManager.translate((float)(n + 10), (float)(n2 - 5), (float)0.0f);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            if (((Boolean)this.rainbowoutline.getValue()).booleanValue() && ((Boolean)this.gradient.getValue()).booleanValue()) {
                Class50.Method92(-2.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 3, 146.0f, 50.0f, ((Color)this.background.getValue()).getRGB());
                Class50.Method92(0.0f, 0.0f, 144.0f, 48.0f, ((Color)this.itembackground.getValue()).getRGB());
                this.Method94(-2.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 3, 148.0f, 62.5f, this.Method93(0), this.Method93(6000 * (Integer)this.offset.getValue()));
            } else {
                Class50.Method92(-3.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 4, 147.0f, 51.0f, this.Method95());
                Class50.Method92(-2.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 3, 146.0f, 50.0f, ((Color)this.background.getValue()).getRGB());
                Class50.Method92(0.0f, 0.0f, 144.0f, 48.0f, ((Color)this.itembackground.getValue()).getRGB());
            }
            if (!((Boolean)this.hidename.getValue()).booleanValue()) {
                Class36.Method63(itemStack.getDisplayName(), 0.0f, -Globals.mc.fontRenderer.FONT_HEIGHT - 1, -1);
            }
            GlStateManager.enableDepth();
            Globals.mc.getRenderItem().zLevel = 150.0f;
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < nonNullList.size(); ++i) {
                ItemStack itemStack2 = (ItemStack)nonNullList.get(i);
                int n3 = i % 9 * 16;
                int n4 = i / 9 * 16;
                Globals.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack2, n3, n4);
                Globals.mc.getRenderItem().renderItemOverlayIntoGUI(Globals.mc.fontRenderer, itemStack2, n3, n4, null);
            }
            RenderHelper.disableStandardItemLighting();
            Globals.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.translate((float)(-(n + 10)), (float)(-(n2 - 5)), (float)0.0f);
        }
    }

    private int Method95() {
        return (Boolean)this.rainbowoutline.getValue() != false ? this.Method93(0) : ((Color)this.outline.getValue()).getRGB();
    }

    private int Method93(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rainbowspeed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.rainbowsaturation.getValue()).floatValue(), ((Float)this.rainbowbrightness.getValue()).floatValue()).getRGB();
    }

    private void Method97(EventRenderToolTip eventRenderToolTip) {
        MapData mapData;
        if (!eventRenderToolTip.getStack().isEmpty() && eventRenderToolTip.getStack().getItem() instanceof ItemMap && (mapData = ((ItemMap)eventRenderToolTip.getStack().getItem()).getMapData(eventRenderToolTip.getStack(), (World)Globals.mc.world)) != null) {
            eventRenderToolTip.cancel();
            GlStateManager.pushMatrix();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
            int n = eventRenderToolTip.getInt1() + 6;
            int n2 = eventRenderToolTip.getInt2() + 6;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            GlStateManager.translate((double)n, (double)n2, (double)0.0);
            GlStateManager.scale((float)1.0f, (float)1.0f, (float)0.0f);
            Globals.mc.getTextureManager().bindTexture(resource);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthRange((double)0.0, (double)0.01);
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferBuilder.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
            bufferBuilder.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
            bufferBuilder.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
            bufferBuilder.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
            tessellator.draw();
            GL11.glDepthRange((double)0.0, (double)1.0);
            RenderHelper.enableStandardItemLighting();
            GlStateManager.disableDepth();
            GL11.glDepthRange((double)0.0, (double)0.01);
            Globals.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
            GL11.glDepthRange((double)0.0, (double)1.0);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
    }

    private void Method94(float f, float f2, float f3, float f4, int n, int n2) {
        Tooltips.Method98(f, f2, f + f3, f2, 2.0f, n, n2);
        Tooltips.Method98(f + f3, f2, f + f3, f2 + f4, 2.0f, n2, n);
        Tooltips.Method98(f + f3, f2 + f4, f, f2 + f4, 2.0f, n, n2);
        Tooltips.Method98(f, f2 + f4, f, f2, 2.0f, n2, n);
    }

    public static void Method98(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        float f6 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(n & 0xFF) / 255.0f;
        float f10 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f11 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f12 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f13 = (float)(n2 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        GL11.glLineWidth((float)f5);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)f, (double)f2, 0.0).color(f7, f8, f9, f6).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).color(f11, f12, f13, f10).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GL11.glDisable((int)2848);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    private void Method86(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        SPacketSetSlot sPacketSetSlot;
        if (!((Boolean)this.echestpreview.getValue()).booleanValue()) {
            return;
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketWindowItems) {
            SPacketWindowItems sPacketWindowItems = (SPacketWindowItems)eventNetworkPrePacketEvent.getPacket();
            if (sPacketWindowItems.getWindowId() == this.Field127) {
                this.Field126.clear();
                for (int i = 0; i < sPacketWindowItems.getItemStacks().size(); ++i) {
                    ItemStack itemStack = (ItemStack)sPacketWindowItems.getItemStacks().get(i);
                    if (itemStack == null) continue;
                    if (i <= 26) {
                        this.Field126.add(itemStack);
                        continue;
                    }
                    break;
                }
            }
        } else if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketOpenWindow) {
            SPacketOpenWindow sPacketOpenWindow = (SPacketOpenWindow)eventNetworkPrePacketEvent.getPacket();
            if (sPacketOpenWindow.getWindowTitle().getFormattedText().startsWith("Ender")) {
                this.Field127 = sPacketOpenWindow.getWindowId();
                return;
            }
        } else if (!(eventNetworkPrePacketEvent.getPacket() instanceof SPacketSetSlot) || (sPacketSetSlot = (SPacketSetSlot)eventNetworkPrePacketEvent.getPacket()).getWindowId() == this.Field127) {
            // empty if block
        }
    }

    private void Method85(EventRenderToolTip eventRenderToolTip) {
        if (((Boolean)this.maps.getValue()).booleanValue()) {
            this.Method97(eventRenderToolTip);
        }
        if (((Boolean)this.shulkerpreview.getValue()).booleanValue()) {
            this.Method88(eventRenderToolTip);
        }
    }

    private boolean Method84() {
        return (Boolean)this.rainbowoutline.getValue() != false && (Boolean)this.gradient.getValue() != false;
    }

    private static boolean Method83() {
        return true;
    }

    private static boolean Method82() {
        return true;
    }

    private static boolean Method81() {
        return true;
    }

    private static boolean Method80() {
        return true;
    }

    private boolean Method79() {
        return (Boolean)this.shulkerpreview.getValue() != false || (Boolean)this.echestpreview.getValue() != false;
    }
}
