package me.ciruu.abyss.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.*;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class354;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.client.Alias;
import me.ciruu.abyss.modules.client.CustomFont;
import me.ciruu.abyss.modules.misc.TotemPopCount;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class NameTags
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting items = new Setting("Items", "", this, true);
    public final Setting enchantments = new Setting("Enchantments", "", (Module) this, (Object) true, this.mainwindow, this.items::getValue);
    public final Setting durability = new Setting("Durability", "", (Module) this, (Object) true, this.mainwindow, this.items::getValue);
    public final Setting invisibles = new Setting("Invisibles", "", this, false);
    public final Setting ping = new Setting("Ping", "", this, true);
    public final Setting totempops = new Setting("TotemPops", "", this, true);
    public final Setting renderdistance = new Setting("RenderDistance", "", this, false);
    public final Setting distance = new Setting("Distance", "", this, Float.valueOf(50.0f), Float.valueOf(1.0f), Float.valueOf(200.0f), this.mainwindow, this.renderdistance::getValue);
    public final Setting scaling = new Setting("Scaling", "Scaling", (Module) this, (Object) Float.valueOf(3.0f), Float.valueOf(1.0f), Float.valueOf(10.0f));
    public final Setting background = new Setting("Background", "", this, new Color(20, 20, 20, 130));
    public final Setting outline = new Setting("Outline", "", this, true);
    public final Setting thickness = new Setting("Thickness", "", this, Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(5.0f), this.mainwindow, this.outline::getValue);
    public final Setting color = new Setting("Color", "", (Module) this, (Object) Color.BLACK, this.mainwindow, this.outline::getValue);
    private final Setting rainbow = new Setting("Rainbow", "", (Module) this, (Object) true, this.mainwindow, this.outline::getValue);
    private final Setting rainbowspeed = new Setting("RainbowSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.mainwindow, this::Method1879);
    private final Setting rainbowsaturation = new Setting("RainbowSaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method1880);
    private final Setting rainbowbrightness = new Setting("RainbowBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method1881);
    private final Setting gradient = new Setting("Gradient", "", (Module) this, (Object) true, this.mainwindow, this::Method1882);
    private final Setting delta = new Setting("Delta", "", this, Float.valueOf(2.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.mainwindow, this::Method1883);
    @EventHandler
    private Listener Field1496 = new Listener<Class66>(this::Method1884, new Predicate[0]);

    public NameTags() {
        super("NameTags", "Draws nametags with armor and more info over players.", Class11.RENDER, "");
        this.addSetting(this.items);
        this.addSetting(this.enchantments);
        this.addSetting(this.durability);
        this.addSetting(this.invisibles);
        this.addSetting(this.ping);
        this.addSetting(this.totempops);
        this.addSetting(this.renderdistance);
        this.addSetting(this.distance);
        this.addSetting(this.scaling);
        this.addSetting(this.background);
        this.addSetting(this.outline);
        this.addSetting(this.thickness);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
        this.addSetting(this.gradient);
        this.addSetting(this.delta);
    }

    private String Method1886(Enchantment enchantment, int n) {
        int n2;
        if (enchantment.getTranslatedName(n).contains("Vanish")) {
            return ChatFormatting.RED + "Van";
        }
        if (enchantment.getTranslatedName(n).contains("Bind")) {
            return ChatFormatting.RED + "Bind";
        }
        String string = enchantment.getTranslatedName(n);
        int n3 = n2 = n > 1 ? 2 : 3;
        if (string.length() > n2) {
            string = string.substring(0, n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = string;
        boolean bl = false;
        String string3 = stringBuilder.insert(0, string2.substring(0, 1).toUpperCase()).append(string.substring(1)).toString();
        if (n > 1) {
            string3 = new StringBuilder().insert(0, string3).append(n).toString();
        }
        return string3;
    }

    private void Method1887(ItemStack itemStack, int n, int n2) {
        Iterator iterator;
        if (!((Boolean) this.enchantments.getValue()).booleanValue()) {
            return;
        }
        GlStateManager.scale((float) 0.5f, (float) 0.5f, (float) 0.5f);
        int n3 = -1;
        Iterator iterator2 = iterator = EnchantmentHelper.getEnchantments((ItemStack) itemStack).keySet().iterator();
        while (iterator2.hasNext()) {
            Enchantment enchantment = (Enchantment) iterator.next();
            if (enchantment == null) {
                iterator2 = iterator;
                continue;
            }
            this.Method1888(this.Method1886(enchantment, EnchantmentHelper.getEnchantmentLevel((Enchantment) enchantment, (ItemStack) itemStack)), n * 2, n2, -1);
            n2 += 8;
            iterator2 = iterator;
        }
        if (itemStack.getItem().equals(Items.GOLDEN_APPLE) && itemStack.hasEffect()) {
            this.Method1888(ChatFormatting.DARK_RED + "Gap", n * 2, n2, -1);
        }
        GlStateManager.scale((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    private void Method1889(ItemStack itemStack, int n, int n2) {
        float f = ((float) itemStack.getMaxDamage() - (float) itemStack.getItemDamage()) / (float) itemStack.getMaxDamage() * 100.0f;
        int n3 = 0x1FFF00;
        if (f > 30.0f && f < 70.0f) {
            n3 = 0xFFFF00;
        } else if (f <= 30.0f) {
            n3 = 0xFF0000;
        }
        GlStateManager.scale((float) 0.5f, (float) 0.5f, (float) 0.5f);
        GlStateManager.disableDepth();
        this.Method1888(new StringBuilder().insert(0, String.valueOf((int) f)).append('%').toString(), n * 2, n2, n3);
        GlStateManager.enableDepth();
        GlStateManager.scale((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    private void Method1890(ItemStack itemStack, int n, int n2, int n3) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean) true);
        GlStateManager.clear((int) 256);
        RenderHelper.enableStandardItemLighting();
        Globals.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        int n4 = n3 > 4 ? (n3 - 4) * 8 / 2 : 0;
        Globals.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + n4);
        Globals.mc.getRenderItem().renderItemOverlays(Globals.mc.fontRenderer, itemStack, n, n2 + n4);
        Globals.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        float f = 0.5f;
        float f2 = 0.5f;
        GlStateManager.scale((float) 0.5f, (float) 0.5f, (float) 0.5f);
        GlStateManager.disableDepth();
        if (itemStack.getMaxDamage() > 1 && ((Boolean) this.durability.getValue()).booleanValue()) {
            this.Method1889(itemStack, n * 2, n2 - 100);
        }
        GlStateManager.enableDepth();
        float f3 = 2.0f;
        int n5 = 2;
        GlStateManager.scale((float) 2.0f, (float) 2.0f, (float) 2.0f);
        GlStateManager.popMatrix();
    }

    private int Method1891(float f, float f2) {
        Color color = new Color(72, 255, 94);
        Color color2 = new Color(255, 250, 57);
        Color color3 = new Color(255, 35, 40);
        float f3 = f / 2.0f;
        if (f2 <= f3) {
            return this.Method1892(color2, color3, f2 / f3).getRGB();
        }
        if (f2 <= f3 * 2.0f) {
            return this.Method1892(color, color2, (f2 - f3) / f3).getRGB();
        }
        return color.getRGB();
    }

    private String Method1893(EntityPlayer entityPlayer) {
        int n;
        String string;
        String string2 = string = Manager.moduleManager.getModuleByClass(Alias.class) != null && Manager.moduleManager.isModuleEnabled(Alias.class) && (Boolean) ((Alias) Manager.moduleManager.getModuleByClass(Alias.class)).onlynametags.getValue() != false && Manager.Field489.Method200(entityPlayer.getName()) != null ? Manager.Field489.Method200(entityPlayer.getName()) : entityPlayer.getName();
        string = Manager.Field223.Method711((Entity) entityPlayer) ? ChatFormatting.AQUA + string + ChatFormatting.RESET : (entityPlayer.isSneaking() ? ChatFormatting.GOLD + string + ChatFormatting.RESET : ChatFormatting.WHITE + string + ChatFormatting.RESET);
        if (((Boolean) this.ping.getValue()).booleanValue()) {
            n = -1;
            try {
                n = (int) Class29.getFloat2(Globals.mc.player.connection.getPlayerInfo(entityPlayer.getUniqueID()).getResponseTime(), 0.0f, 10000.0f);
            } catch (NullPointerException nullPointerException) {
                // empty catch block
            }
            if (n > 200) {
                string = string + ChatFormatting.RED;
            } else if (n <= 200 && n >= 100) {
                string = string + ChatFormatting.YELLOW;
            } else if (n < 100) {
                string = string + ChatFormatting.GREEN;
            }
            string = string + "" + n + "ms" + ChatFormatting.RESET + "";
        } else {
            string = string + "";
        }
        string = string + Math.floor(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount()) + "";
        if (((Boolean) this.totempops.getValue()).booleanValue() && Manager.moduleManager.isModuleEnabled(TotemPopCount.class)) {
            n = 0;
            if (TotemPopCount.Field1498.containsKey(entityPlayer.getName())) {
                n = (Integer) TotemPopCount.Field1498.get(entityPlayer.getName());
            }
            string = string + ChatFormatting.WHITE + n;
        }
        return string;
    }

    private double Method1895(double d, double d2, float f) {
        return d2 + (d - d2) * (double) Globals.mc.getRenderPartialTicks();
    }

    private Vec3d Method1896(Entity entity, float f) {
        return new Vec3d(this.Method1895(entity.posX, entity.lastTickPosX, f) - Globals.mc.getRenderManager().renderPosX, this.Method1895(entity.posY, entity.lastTickPosY, f) - Globals.mc.getRenderManager().renderPosY, this.Method1895(entity.posZ, entity.lastTickPosZ, f) - Globals.mc.getRenderManager().renderPosZ);
    }

    private Color Method1892(Color color, Color color2, float f) {
        if (f < 0.0f) {
            return color2;
        }
        if (f > 1.0f) {
            return color;
        }
        float f2 = 1.0f - f;
        float[] fArray = new float[3];
        float[] fArray2 = new float[3];
        color.getColorComponents(fArray);
        color2.getColorComponents(fArray2);
        return new Color(fArray[0] * f + fArray2[0] * f2, fArray[1] * f + fArray2[1] * f2, fArray[2] * f + fArray2[2] * f2);
    }

    private void Method1888(String string, float f, float f2, int n) {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            Class36.Method550().drawStringWithShadow(string, f, f2, n);
        } else {
            Class36.Method551().drawStringWithShadow(string, f, f2, n);
        }
    }

    private void Method1897(String string, float f, float f2, int n) {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            Class36.Method550().drawString(string, f, f2, n);
        } else {
            Class36.Method551().drawString(string, (int) f, (int) f2, n);
        }
    }

    private int Method1898(String string) {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            return Class36.Method550().getStringWidth(string);
        }
        return Class36.Method551().getStringWidth(string);
    }

    private int Method1899() {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            return Class36.Method550().getHeight();
        }
        return Class36.Method551().FONT_HEIGHT;
    }

    private void Method1900(float f, float f2, float f3, float f4, float f5, int n) {
        this.Method1901(f, f2, f - f5, f4, n);
        this.Method1901(f3 + f5, f2, f3, f4, n);
        this.Method1901(f, f2, f3, f2 - f5, n);
        this.Method1901(f, f4 + f5, f3, f4, n);
    }

    private void Method1901(float f, float f2, float f3, float f4, int n) {
        float f5 = (float) (n >> 24 & 0xFF) / 255.0f;
        float f6 = (float) (n >> 16 & 0xFF) / 255.0f;
        float f7 = (float) (n >> 8 & 0xFF) / 255.0f;
        float f8 = (float) (n & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GL11.glLineWidth((float) ((Float) this.thickness.getValue()).floatValue());
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int) 770, (int) 771, (int) 1, (int) 0);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double) f, (double) f4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double) f3, (double) f4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double) f3, (double) f2, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double) f, (double) f2, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private int Method1902(int n) {
        double d = Math.ceil((double) (System.currentTimeMillis() + (long) n) / (20.0 * (double) ((Float) this.rainbowspeed.getValue()).floatValue()));
        return Color.getHSBColor((float) ((d %= 360.0) / 360.0), ((Float) this.rainbowsaturation.getValue()).floatValue(), ((Float) this.rainbowbrightness.getValue()).floatValue()).getRGB();
    }

    private void Method1903(float f, float f2, float f3, float f4, int n, int n2) {
        this.Method1904(f, f2, f, f4, ((Float) this.thickness.getValue()).floatValue(), n, n2);
        this.Method1904(f3, f2, f3, f4, ((Float) this.thickness.getValue()).floatValue(), n2, n);
        this.Method1904(f, f2, f3, f2, ((Float) this.thickness.getValue()).floatValue(), n, n2);
        this.Method1904(f, f4, f3, f4, ((Float) this.thickness.getValue()).floatValue(), n2, n);
    }

    private void Method1904(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        float f6 = (float) (n >> 24 & 0xFF) / 255.0f;
        float f7 = (float) (n >> 16 & 0xFF) / 255.0f;
        float f8 = (float) (n >> 8 & 0xFF) / 255.0f;
        float f9 = (float) (n & 0xFF) / 255.0f;
        float f10 = (float) (n2 >> 24 & 0xFF) / 255.0f;
        float f11 = (float) (n2 >> 16 & 0xFF) / 255.0f;
        float f12 = (float) (n2 >> 8 & 0xFF) / 255.0f;
        float f13 = (float) (n2 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor) GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor) GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor) GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor) GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int) 7425);
        GL11.glLineWidth((float) f5);
        GL11.glEnable((int) 2848);
        GL11.glHint((int) 3154, (int) 4354);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double) f, (double) f2, 0.0).color(f7, f8, f9, f6).endVertex();
        bufferBuilder.pos((double) f3, (double) f4, 0.0).color(f11, f12, f13, f10).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int) 7424);
        GL11.glDisable((int) 2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private void Method1884(Class66 class66) {
        if (Globals.mc.world == null || Globals.mc.renderEngine == null || Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }
        if (Globals.mc.world == null || Globals.mc.renderEngine == null || Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Globals.mc.world.playerEntities.stream().filter(NameTags::Method1905).forEach(arg_0 -> NameTags.Method1906((List) arrayList, arg_0));
        arrayList.sort(NameTags::Method1907);
        for (Object entityPlayer1 : (Collection) arrayList) {
            EntityPlayer entityPlayer = (EntityPlayer) null;
            if ((entityPlayer.isInvisible() || entityPlayer.isDead) && !((Boolean) this.invisibles.getValue()).booleanValue() || ((Boolean) this.renderdistance.getValue()).booleanValue() && Globals.mc.player.getDistance((Entity) entityPlayer) > ((Float) this.distance.getValue()).floatValue())
                continue;
            Entity entity = Globals.mc.getRenderViewEntity();
            Vec3d vec3d = this.Method1896((Entity) entityPlayer, class66.Method1789());
            double d = vec3d.x;
            double d2 = vec3d.y + 0.65;
            double d3 = vec3d.z;
            double d4 = d2 + (entityPlayer.isSneaking() ? 0.0 : (double) 0.08f);
            vec3d = this.Method1896(entity, class66.Method1789());
            double d5 = entity.posX;
            double d6 = entity.posY;
            double d7 = entity.posZ;
            entity.posX = vec3d.x;
            entity.posY = vec3d.y;
            entity.posZ = vec3d.z;
            d2 = entity.getDistance(d, d2, d3);
            double d8 = 0.04;
            if (d2 > 0.0) {
                d8 = 0.02 + (double) (((Float) this.scaling.getValue()).floatValue() / 1000.0f) * d2;
            }
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enablePolygonOffset();
            GlStateManager.doPolygonOffset((float) 1.0f, (float) -1500000.0f);
            GlStateManager.disableLighting();
            GlStateManager.translate((float) ((float) d), (float) ((float) d4 + 1.4f), (float) ((float) d3));
            float f = -Globals.mc.getRenderManager().playerViewY;
            float f2 = 1.0f;
            float f3 = 0.0f;
            GlStateManager.rotate((float) f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            GlStateManager.rotate((float) Globals.mc.getRenderManager().playerViewX, (float) (Globals.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float) 0.0f, (float) 0.0f);
            GlStateManager.scale((double) (-d8), (double) (-d8), (double) d8);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            String string = this.Method1893(entityPlayer);
            float f4 = (float) this.Method1898(string) / 2.0f;
            float f5 = this.Method1899();
            GlStateManager.enableBlend();
            Class50.Method92(-f4 - 1.0f, -(f5 + 1.0f), f4 + 2.0f, 2.0f, ((Color) this.background.getValue()).getRGB());
            if (((Boolean) this.outline.getValue()).booleanValue()) {
                if (((Boolean) this.gradient.getValue()).booleanValue() && ((Boolean) this.rainbow.getValue()).booleanValue()) {
                    this.Method1903(-f4 - 1.0f, -(f5 + 1.0f), f4 + 2.0f, 2.0f, this.Method1902(0), this.Method1902((int) (6000.0f * ((Float) this.delta.getValue()).floatValue())));
                } else {
                    this.Method1900(-f4 - 1.0f, -(f5 + 1.0f), f4 + 2.0f, 2.0f, 0.0f, (Boolean) this.rainbow.getValue() != false ? this.Method1902(0) : ((Color) this.color.getValue()).getRGB());
                }
            }
            GlStateManager.disableBlend();
            this.Method1897(string, -f4 + 1.0f, -f5 + 3.0f, this.Method1891(entityPlayer.getMaxHealth(), entityPlayer.getHealth()));
            GlStateManager.pushMatrix();
            Iterator iterator = entityPlayer.getArmorInventoryList().iterator();
            ArrayList<ItemStack> arrayList2 = new ArrayList<ItemStack>();
            arrayList2.add(entityPlayer.getHeldItemOffhand());
            while (iterator.hasNext()) {
                ItemStack itemStack = (ItemStack) iterator.next();
                if (itemStack.isEmpty()) continue;
                arrayList2.add(itemStack);
            }
            arrayList2.add(entityPlayer.getHeldItemMainhand());
            Collections.reverse(arrayList2);
            int n = -(arrayList2.size() * 16 / 2);
            int n2 = -32;
            int n3 = 0;
            for (ItemStack itemStack : arrayList2) {
                if (!((Boolean) this.items.getValue()).booleanValue()) continue;
                this.Method1890(itemStack, n, n2, n3);
                this.Method1887(itemStack, n, -62);
                n += 16;
            }
            GlStateManager.popMatrix();
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.disablePolygonOffset();
            GlStateManager.doPolygonOffset((float) 1.0f, (float) 1500000.0f);
            GlStateManager.popMatrix();
            entity.posX = d5;
            entity.posY = d6;
            entity.posZ = d7;
        }
    }

    private static int Method1907(Object o, Object o1) {
        return Method1907(o, o1);
    }

    private static int Method1907(EntityPlayer entityPlayer, EntityPlayer entityPlayer2) {
        return Double.compare(entityPlayer2.getDistance(Globals.mc.getRenderViewEntity()), entityPlayer.getDistance(Globals.mc.getRenderViewEntity()));
    }

    private static void Method1906(List list, EntityPlayer entityPlayer) {
        Class50.Field638.setPosition(Globals.mc.getRenderViewEntity().posX, Globals.mc.getRenderViewEntity().posY, Globals.mc.getRenderViewEntity().posZ);
        if (Class50.Field638.isBoundingBoxInFrustum(entityPlayer.getEntityBoundingBox())) {
            list.add(entityPlayer);
        }
    }

    private static boolean Method1905(EntityPlayer entityPlayer) {
        return entityPlayer instanceof EntityPlayer && Class354.Method1908((Entity) entityPlayer) && entityPlayer != Globals.mc.getRenderViewEntity();
    }

    private boolean Method1883() {
        return (Boolean) this.rainbow.getValue() != false && (Boolean) this.outline.getValue() != false && (Boolean) this.gradient.getValue() != false;
    }

    private boolean Method1882() {
        return (Boolean) this.rainbow.getValue() != false && (Boolean) this.outline.getValue() != false;
    }

    private boolean Method1881() {
        return (Boolean) this.rainbow.getValue() != false && (Boolean) this.outline.getValue() != false;
    }

    private boolean Method1880() {
        return (Boolean) this.rainbow.getValue() != false && (Boolean) this.outline.getValue() != false;
    }

    private boolean Method1879() {
        return (Boolean) this.rainbow.getValue() != false && (Boolean) this.outline.getValue() != false;
    }
}
