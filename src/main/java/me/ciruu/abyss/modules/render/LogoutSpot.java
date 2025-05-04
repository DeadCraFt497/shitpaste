package me.ciruu.abyss.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.client.CustomFont;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class LogoutSpot
extends Module {
    private final Setting coords = new Setting("Coords", "", this, true);
    private final Setting renderdistance = new Setting("RenderDistance", "", (Module)this, (Object)120, 1, 500);
    private final Setting linewidth = new Setting("LineWidth", "LineWidth", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f));
    public final Setting scaling = new Setting("Scaling", "Scaling", (Module)this, (Object)Float.valueOf(3.0f), Float.valueOf(1.0f), Float.valueOf(10.0f));
    private final Setting background = new Setting("Background", "", this, true);
    private final Setting colorlabel = new Setting("ColorLabel", "", this, new Class25("Color Settings"));
    private final Setting color = new Setting("Color", "", (Module)this, (Object)new Color(255, 255, 255, 255), this.colorlabel, LogoutSpot::Method2506);
    private final Setting rainbow = new Setting("Rainbow", "", (Module)this, (Object)true, this.colorlabel, LogoutSpot::Method2507);
    private final Setting rainbowspeed = new Setting("Speed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.colorlabel, this.rainbow::getValue);
    private final Setting rainbowsaturation = new Setting("Saturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorlabel, this.rainbow::getValue);
    private final Setting rainbowbrightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.colorlabel, this.rainbow::getValue);
    private final List Field2090 = new CopyOnWriteArrayList();
    @EventHandler
    private Listener Field2091 = new Listener<Class66>(this::Method2508, new Predicate[0]);
    @EventHandler
    private Listener Field2092 = new Listener<EventPlayerUpdate>(this::Method2509, new Predicate[0]);
    @EventHandler
    private Listener Field2093 = new Listener<Class179>(this::Method2510, new Predicate[0]);

    public LogoutSpot() {
        super("LogoutSpot", "Render logout spot from players.", Class11.RENDER, "");
        this.addSetting(this.coords);
        this.addSetting(this.renderdistance);
        this.addSetting(this.linewidth);
        this.addSetting(this.scaling);
        this.addSetting(this.background);
        this.addSetting(this.colorlabel);
        this.addSetting(this.color);
        this.addSetting(this.rainbow);
        this.addSetting(this.rainbowspeed);
        this.addSetting(this.rainbowsaturation);
        this.addSetting(this.rainbowbrightness);
    }

    public void Method2512() {
        super.getDisable();
        this.Field2090.clear();
    }

    private void Method2513(String string, double d, double d2, double d3, float f, double d4, double d5, double d6, Entity entity, Class66 class66) {
        String string2 = string + ((Boolean)this.coords.getValue() != false ? " XYZ:" + (int)d4 + "," + (int)d5 + "," + (int)d6 : "");
        Entity entity2 = Globals.mc.getRenderViewEntity();
        Vec3d vec3d = this.Method2514(entity, class66.Method1789());
        double d7 = vec3d.x;
        double d8 = vec3d.y + 0.65;
        double d9 = vec3d.z;
        double d10 = d8 + (entity.isSneaking() ? 0.0 : (double)0.08f);
        vec3d = this.Method2514(entity2, class66.Method1789());
        double d11 = entity2.posX;
        double d12 = entity2.posY;
        double d13 = entity2.posZ;
        entity2.posX = vec3d.x;
        entity2.posY = vec3d.y;
        entity2.posZ = vec3d.z;
        d8 = entity2.getDistance(d7, d8, d9);
        double d14 = 0.04;
        if (d8 > 0.0) {
            d14 = 0.02 + (double)(((Float)this.scaling.getValue()).floatValue() / 1000.0f) * d8;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset((float)1.0f, (float)-1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)((float)d7), (float)((float)d10 + 1.4f), (float)((float)d9));
        float f2 = -Globals.mc.getRenderManager().playerViewY;
        float f3 = 1.0f;
        float f4 = 0.0f;
        GlStateManager.rotate((float)f2, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)Globals.mc.getRenderManager().playerViewX, (float)(Globals.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((double)(-d14), (double)(-d14), (double)d14);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        float f5 = (float)this.getStringWidth(string2) / 2.0f;
        float f6 = this.getFontHeight();
        if (((Boolean)this.background.getValue()).booleanValue()) {
            GlStateManager.enableBlend();
            Class50.Method92(-f5 - 1.0f, -(f6 + 1.0f), f5 + 2.0f, 2.0f, 0x55000000);
            GlStateManager.disableBlend();
        }
        this.Method2518(string2, -f5 + 1.0f, -f6 + 3.0f, this.Method2517());
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset((float)1.0f, (float)1500000.0f);
        GlStateManager.popMatrix();
        entity2.posX = d11;
        entity2.posY = d12;
        entity2.posZ = d13;
    }

    private void Method2518(String string, float f, float f2, int n) {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            Class36.Method550().drawString(string, f, f2, n);
        } else {
            Class36.Method551().drawString(string, (int)f, (int)f2, n);
        }
    }

    private int getStringWidth(String string) {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            return Class36.Method550().getStringWidth(string);
        }
        return Class36.Method551().getStringWidth(string);
    }

    private int getFontHeight() {
        if (Manager.moduleManager.isModuleEnabled(CustomFont.class)) {
            return Class36.Method550().getHeight();
        }
        return Class36.Method551().FONT_HEIGHT;
    }

    private Vec3d Method2514(Entity entity, float f) {
        return new Vec3d(this.Method2519(entity.posX, entity.lastTickPosX, f) - Globals.mc.getRenderManager().renderPosX, this.Method2519(entity.posY, entity.lastTickPosY, f) - Globals.mc.getRenderManager().renderPosY, this.Method2519(entity.posZ, entity.lastTickPosZ, f) - Globals.mc.getRenderManager().renderPosZ);
    }

    private double Method2519(double d, double d2, float f) {
        return d2 + (d - d2) * (double)Globals.mc.getRenderPartialTicks();
    }

    private double Method2520(double d, double d2, float f) {
        return d + (d2 - d) * (double)f;
    }

    private int Method2517() {
        return (Boolean)this.rainbow.getValue() != false ? this.Method2521(0) : ((Color)this.color.getValue()).getRGB();
    }

    public int Method2521(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)((Float)this.rainbowspeed.getValue()).floatValue()));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), ((Float)this.rainbowsaturation.getValue()).floatValue(), ((Float)this.rainbowbrightness.getValue()).floatValue()).getRGB();
    }

    private void Method2510(Class179 class179) {
        if (class179.getEra() == Class53.PRE) {
            UUID uUID = class179.Method494();
            EntityPlayer entityPlayer = Globals.mc.world.getPlayerEntityByUUID(uUID);
            if (entityPlayer != null) {
                Class547.printChatMessage(ChatFormatting.GREEN + entityPlayer.getName() + " connected" + ((Boolean)this.coords.getValue() != false ? " at (" + (int)entityPlayer.posX + "," + (int)entityPlayer.posY + "," + (int)entityPlayer.posZ + ")!" : "!"));
            }
            this.Field2090.removeIf(arg_0 -> LogoutSpot.Method2523(class179, (Class545) arg_0));
        } else if (class179.getEra() == Class53.PERI) {
            EntityPlayer entityPlayer = class179.Method495();
            UUID uUID = class179.Method494();
            String string = class179.Method493();
            Class547.printChatMessage(ChatFormatting.RED + class179.Method493() + " disconnected" + ((Boolean)this.coords.getValue() != false ? " at (" + (int)entityPlayer.posX + "," + (int)entityPlayer.posY + "," + (int)entityPlayer.posZ + ")!" : "!"));
            if (string != null && entityPlayer != null && uUID != null) {
                this.Field2090.add(new Class545(string, uUID, entityPlayer));
            }
        }
    }

    private static boolean Method2523(Class179 class179, Class545 class545) {
        return class545.Method2524().equalsIgnoreCase(class179.Method493());
    }

    private void Method2509(EventPlayerUpdate eventPlayerUpdate) {
        if (Globals.mc.player != null && Globals.mc.world != null) {
            this.Field2090.removeIf(this::Method2525);
        }
    }

    private boolean Method2525(Object o) {
        Class545 class545 = (Class545)o;
        Method2525(class545);
        return true;
    }

    private boolean Method2525(Class545 class545) {
        return Globals.mc.player.getDistanceSq((Entity)class545.Method2526()) >= Class29.getDouble6(((Integer)this.renderdistance.getValue()).intValue());
    }

    private void Method2508(Class66 class66) {
        block1: {
            if (this.Field2090.isEmpty()) break block1;
            List list = this.Field2090;
            synchronized (list) {
                this.Field2090.forEach(arg_0 -> this.Method2527(class66, (Class545) arg_0));
            }
        }
    }

    private void Method2527(Class66 class66, Class545 class545) {
        if (class545.Method2526() != null) {
            AxisAlignedBB axisAlignedBB = Class50.Method767(class545.Method2526().getEntityBoundingBox());
            Class50.Method798(axisAlignedBB, new Color(this.Method2517()), ((Float)this.linewidth.getValue()).floatValue());
            double d = this.Method2520(class545.Method2526().lastTickPosX, class545.Method2526().posX, class66.Method1789()) - Globals.mc.getRenderManager().renderPosX;
            double d2 = this.Method2520(class545.Method2526().lastTickPosY, class545.Method2526().posY, class66.Method1789()) - Globals.mc.getRenderManager().renderPosY;
            double d3 = this.Method2520(class545.Method2526().lastTickPosZ, class545.Method2526().posZ, class66.Method1789()) - Globals.mc.getRenderManager().renderPosZ;
            this.Method2513(class545.Method2524(), d, d2, d3, class66.Method1789(), class545.Method2528(), class545.Method2529(), class545.Method2530(), (Entity)class545.Method2526(), class66);
        }
    }

    private static boolean Method2507() {
        return true;
    }

    private static boolean Method2506() {
        return true;
    }
}
