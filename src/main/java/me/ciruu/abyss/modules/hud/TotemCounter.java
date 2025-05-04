package me.ciruu.abyss.modules.hud;

import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class492;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TotemCounter
extends Module {
    private final Setting mainWindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting main = new Setting("Main", "", this, (Object)Class492.Auto);
    private final Setting y = new Setting("Y", "", this, 300, 0, 1000, this.mainWindow, this::Method2136);
    private int Field1727 = 0;
    private int Field1728 = 0;
    private int Field1729 = 0;
    @EventHandler
    private Listener Field1730 = new Listener<Class35>(this::Method2137, new Predicate[0]);

    public TotemCounter() {
        super("TotemCounter", "", Class11.HUD, "");
        this.addSetting(this.main);
        this.addSetting(this.y);
    }

    public static int Method2139(Item item) {
        int n = 0;
        for (int i = 0; i < Globals.mc.player.inventoryContainer.getInventory().size(); ++i) {
            ItemStack itemStack;
            if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8 || (itemStack = (ItemStack)Globals.mc.player.inventoryContainer.getInventory().get(i)).isEmpty() || itemStack.getItem() != item) continue;
            ++n;
        }
        return n;
    }

    private void Method2137(Class35 class35) {
        this.Field1729 = TotemCounter.Method2139(Item.getItemById((int)449));
        this.Field1727 = class35.Method2006() / 2 - 9;
        this.Field1728 = this.main.getValue() == Class492.Auto ? (int)((double)class35.Method1967() * 0.88) : (Integer)this.y.getValue();
        ItemStack itemStack = new ItemStack(Item.getItemById((int)449), this.Field1729);
        RenderHelper.enableStandardItemLighting();
        Globals.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, this.Field1727, this.Field1728);
        if (this.Field1729 > 0) {
            Globals.mc.getRenderItem().renderItemOverlayIntoGUI(Globals.mc.fontRenderer, itemStack, this.Field1727, this.Field1728, null);
        }
        RenderHelper.disableStandardItemLighting();
    }

    private boolean Method2136() {
        return this.main.getValue() == Class492.Manual;
    }
}
