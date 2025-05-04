package me.ciruu.abyss.modules.hud;

import java.util.Collection;
import java.util.function.Predicate;
import me.ciruu.abyss.Class290;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.GameType;

public class ArmourHUD
extends Module {
    private final Setting damage = new Setting("Damage", "", this, true);
    private static RenderItem Field2003 = Minecraft.getMinecraft().getRenderItem();
    @EventHandler
    private Listener Field2004 = new Listener<Class35>(this::Method2435, new Predicate[0]);

    public ArmourHUD() {
        super("ArmourHUD", "", Class11.HUD, "");
        this.addSetting(this.damage);
    }

    private Collection<ItemStack> Method2437() {
        if (Globals.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE) || Globals.mc.playerController.getCurrentGameType().equals((Object)GameType.SPECTATOR)) {
            return NonNullList.withSize((int)4, (ItemStack) ItemStack.EMPTY);
        }
        return Globals.mc.player.inventory.armorInventory;
    }

    private void Method2435(Class35 class35) {
        GlStateManager.enableTexture2D();
        ScaledResolution scaledResolution = new ScaledResolution(Globals.mc);
        int centerX = scaledResolution.getScaledWidth() / 2;
        int itemIndex = 0;
        int y = scaledResolution.getScaledHeight() - 55 - (Globals.mc.player.isInWater() ? 10 : 0);

        for (ItemStack itemStack : this.Method2437()) { // Now assumes Collection<ItemStack>
            itemIndex++;
            if (itemStack.isEmpty()) continue;

            int x = centerX - 90 + (9 - itemIndex) * 20 + 2;

            GlStateManager.enableDepth();
            ArmourHUD.Field2003.zLevel = 200.0f;
            Field2003.renderItemAndEffectIntoGUI(itemStack, x, y);
            Field2003.renderItemOverlayIntoGUI(Globals.mc.fontRenderer, itemStack, x, y, "");
            ArmourHUD.Field2003.zLevel = 0.0f;

            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();

            String countText = itemStack.getCount() > 1 ? String.valueOf(itemStack.getCount()) : "";
            Class36.Method63(countText, x + 19 - 2 - Class36.Method259(countText), y + 9, 0xFFFFFF);

            if ((Boolean) this.damage.getValue()) {
                float durability = (float)(itemStack.getMaxDamage() - itemStack.getItemDamage()) / itemStack.getMaxDamage();
                float damageRatio = 1.0f - durability;
                int percent = 100 - (int)(damageRatio * 100.0f);
                int color = Class290.Method1055((int)(damageRatio * 255.0f), (int)(durability * 255.0f), 0);
                Class36.Method63(String.valueOf(percent), x + 8 - Class36.Method259(String.valueOf(percent)) / 2, y - 11, color);
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

}
