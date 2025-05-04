package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class290;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Class423;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.combat.AutoCrystal;
import me.ciruu.abyss.modules.combat.KillAura;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class TargetHUD
extends Module {
    private final Setting x = new Setting("X", "X", (Module)this, (Object)10, 0, 1920);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)10, 0, 1080);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)20, 0, 100);
    private final Color Field1385 = new Color(0, 0, 0, 128);
    private final Color Field1386 = new Color(0, 0, 0, 255);
    @EventHandler
    private Listener Field1387 = new Listener<Class35>(this::Method1790, new Predicate[0]);

    public TargetHUD() {
        super("TargetHUD", "", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
    }

    private void Method1792() {
        EntityLivingBase entityLivingBase = this.Method1793();
        if (entityLivingBase == null || entityLivingBase.isDead) {
            return;
        }
        float f = Class30.Method1454((Entity)entityLivingBase);
        Class423.Method1794((Integer)this.x.getValue(), (Integer)this.y.getValue(), (Integer)this.x.getValue() + 120, (Integer)this.y.getValue() + 80, 6, this.Field1385.getRGB());
        Class423.Method1795((Integer)this.x.getValue(), (Integer)this.y.getValue(), (Integer)this.x.getValue() + 120, (Integer)this.y.getValue() + 80, 6, 2.0f, this.Field1386.getRGB());
        Class36.Method63(entityLivingBase.getName(), (Integer)this.x.getValue() + 10, (Integer)this.y.getValue() + 10, -1);
        Class36.Method63(String.format("%.1f", Float.valueOf(f)), (Integer)this.x.getValue() + 90, (Integer)this.y.getValue() + 55, this.Method1796(f, entityLivingBase));
        Class50.Method817((Integer)this.x.getValue() + 10, (Integer)this.y.getValue() + 70, (float)((Integer)this.x.getValue() + 10) + 100.0f * f / this.Method1797(entityLivingBase), (Integer)this.y.getValue() + 70, 6.0f, Color.RED.getRGB(), this.Method1796(f, entityLivingBase));
        this.Method1798(entityLivingBase, 30, 60);
    }

    private void Method1798(EntityLivingBase entityLivingBase, int n, int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
        try {
            GuiInventory.drawEntityOnScreen((int)((Integer)this.x.getValue() + n), (int)((Integer)this.y.getValue() + n2), (int)((Integer)this.scale.getValue()), (float)0.0f, (float)(-entityLivingBase.rotationPitch), (EntityLivingBase)entityLivingBase);
        }
        catch (Exception exception) {
            // empty catch block
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.popMatrix();
    }

    private EntityLivingBase Method1793() {
        return ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).entity != null ? ((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).entity : (KillAura.Field1388 instanceof EntityLivingBase ? (EntityLivingBase)KillAura.Field1388 : this.Method1799());
    }

    private int Method1796(float f, EntityLivingBase entityLivingBase) {
        float f2 = f / this.Method1797(entityLivingBase);
        float f3 = 1.0f - f2;
        return Class290.Method1055((int)(f3 * 255.0f), (int)(f2 * 255.0f), 0);
    }

    private EntityLivingBase Method1799() {
        if (Globals.mc.world.loadedEntityList.isEmpty()) {
            return null;
        }
        EntityLivingBase entityLivingBase = null;
        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (entity == Globals.mc.player || !(entity instanceof EntityLivingBase) || Manager.Field223.Method711(entity) || entity.isDead || entityLivingBase != null && Minecraft.getMinecraft().player.getDistance(entity) > Minecraft.getMinecraft().player.getDistance((Entity)entityLivingBase)) continue;
            entityLivingBase = (EntityLivingBase)entity;
        }
        return entityLivingBase;
    }

    private float Method1797(EntityLivingBase entityLivingBase) {
        return entityLivingBase instanceof EntityPlayer ? 36.0f : entityLivingBase.getMaxHealth();
    }

    private void Method1790(Class35 class35) {
        this.Method1792();
    }
}
