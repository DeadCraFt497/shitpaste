package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class292;
import me.ciruu.abyss.modules.render.EnchantColor;
import me.ciruu.abyss.modules.render.NoRender;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LayerArmorBase.class})
public class MixinLayerArmorBase {
    @Redirect(method={"renderEnchantedGlint"}, at=@At(value="INVOKE", target="net/minecraft/client/renderer/GlStateManager.color(FFFF)V", ordinal=1))
    private static void renderEnchantedGlint(float f, float f2, float f3, float f4) {
        GlStateManager.color((float)(Manager.moduleManager.isModuleEnabled(EnchantColor.class) ? (float)((EnchantColor)Manager.moduleManager.getModuleByClass(EnchantColor.class)).Method1573().getRed() : f), (float)(Manager.moduleManager.isModuleEnabled(EnchantColor.class) ? (float)((EnchantColor)Manager.moduleManager.getModuleByClass(EnchantColor.class)).Method1573().getGreen() : f2), (float)(Manager.moduleManager.isModuleEnabled(EnchantColor.class) ? (float)((EnchantColor)Manager.moduleManager.getModuleByClass(EnchantColor.class)).Method1573().getBlue() : f3), (float)(Manager.moduleManager.isModuleEnabled(EnchantColor.class) ? (float)((EnchantColor)Manager.moduleManager.getModuleByClass(EnchantColor.class)).Method1573().getAlpha() : f4));
    }

    @Inject(method={"doRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void doRenderLayer(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, float f7, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).noarmor.getValue() == Class292.REMOVE) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderArmorLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderArmorLayer(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, float f7, EntityEquipmentSlot entityEquipmentSlot, CallbackInfo callbackInfo) {
        if (Manager.moduleManager.isModuleEnabled(NoRender.class) && ((NoRender)Manager.moduleManager.getModuleByClass(NoRender.class)).noarmor.getValue() == Class292.HEAD && entityEquipmentSlot == EntityEquipmentSlot.HEAD) {
            callbackInfo.cancel();
        }
    }
}
