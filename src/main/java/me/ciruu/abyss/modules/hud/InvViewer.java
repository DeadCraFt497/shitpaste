package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class InvViewer
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting x = new Setting("X", "X", (Module)this, (Object)10, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)10, 0, 1000);
    private final Setting background = new Setting("Background", "", this, true);
    private final Setting outline = new Setting("OutLine", "", this, true);
    private final Setting offset = new Setting("Offset", "", this, 2, 1, 6, this.main, this.outline::getValue);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    @EventHandler
    private Listener Field1744 = new Listener<Class35>(this::Method2145, new Predicate[0]);

    public InvViewer() {
        super("InvViewer", "", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.background);
        this.addSetting(this.outline);
        this.addSetting(this.offset);
        this.addSetting(this.scale);
    }

    public int Method2147(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / 20.0);
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), 1.0f, 1.0f).getRGB();
    }

    private void Method2145(Class35 class35) {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.scale((float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue());
        if (((Boolean)this.background.getValue()).booleanValue()) {
            Class50.Method92(((Integer)this.x.getValue()).intValue(), ((Integer)this.y.getValue()).intValue(), (Integer)this.x.getValue() + 144 + 1, (Integer)this.y.getValue() + 48 + 1, 1963986960);
        }
        if (((Boolean)this.outline.getValue()).booleanValue()) {
            Class50.Method864((Integer)this.x.getValue(), (Integer)this.y.getValue(), 145, 49, this.Method2147(0), this.Method2147(6000 * (Integer)this.offset.getValue()));
        }
        for (int i = 0; i < 27; ++i) {
            ItemStack itemStack = (ItemStack)Globals.mc.player.inventory.mainInventory.get(i + 9);
            int n = (Integer)this.x.getValue() + i % 9 * 16;
            int n2 = (Integer)this.y.getValue() + i / 9 * 16;
            Globals.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2);
            Globals.mc.getRenderItem().renderItemOverlayIntoGUI(Globals.mc.fontRenderer, itemStack, n, n2, null);
        }
        RenderHelper.disableStandardItemLighting();
        Globals.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
    }
}
