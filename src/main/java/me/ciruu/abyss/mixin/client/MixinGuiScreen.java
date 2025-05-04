package me.ciruu.abyss.mixin.client;

import java.util.List;
import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.render.EventRenderToolTip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiScreen.class})
public abstract class MixinGuiScreen {
    @Inject(method={"renderToolTip"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderToolTip(ItemStack itemStack, int n, int n2, CallbackInfo callbackInfo) {
        EventRenderToolTip eventRenderToolTip = new EventRenderToolTip(itemStack, n, n2);
        AbyssMod.EVENT_BUS.post(eventRenderToolTip);
        if (eventRenderToolTip.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
