package me.ciruu.abyss.mixin.client;

import java.awt.Color;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.render.EnchantColor;
import me.ciruu.abyss.modules.render.ItemViewModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderItem.class})
public class MixinRenderItem {
    @Shadow
    private void renderModel(IBakedModel iBakedModel, int n, ItemStack itemStack) {
    }

    @ModifyArg(method={"renderEffect"}, at=@At(value="INVOKE", target="net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index=1)
    private int renderEffect(int n) {
        return Manager.moduleManager.isModuleEnabled(EnchantColor.class) ? ((EnchantColor)Manager.moduleManager.getModuleByClass(EnchantColor.class)).Method1573().getRGB() : n;
    }

    @Redirect(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private void color(float f, float f2, float f3, float f4) {
        if (Manager.moduleManager.isModuleEnabled(ItemViewModel.class)) {
            GlStateManager.color((float)f, (float)f2, (float)f3, (float)((Float)((ItemViewModel)Manager.moduleManager.getModuleByClass(ItemViewModel.class)).alpha.getValue()).floatValue());
        } else {
            GlStateManager.color((float)f, (float)f2, (float)f3, (float)f4);
        }
    }

    @Redirect(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void renderModelColor(RenderItem renderItem, IBakedModel iBakedModel, ItemStack itemStack) {
        if (Manager.moduleManager.isModuleEnabled(ItemViewModel.class)) {
            this.renderModel(iBakedModel, new Color(1.0f, 1.0f, 1.0f, ((Float)((ItemViewModel)Manager.moduleManager.getModuleByClass(ItemViewModel.class)).alpha.getValue()).floatValue()).getRGB(), itemStack);
        } else {
            this.renderModel(iBakedModel, -1, itemStack);
        }
    }
}
