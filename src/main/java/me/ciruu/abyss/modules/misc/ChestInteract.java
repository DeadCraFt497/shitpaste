package me.ciruu.abyss.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class261;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class380;
import me.ciruu.abyss.enums.Class61;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;

public class ChestInteract
extends Module {
    private final Setting mode = new Setting("Mode", "", this, Class61.Steal);
    private final Setting delay = new Setting("Delay", "Delay in seconds", this, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    private final Setting itemMode = new Setting("ItemMode", "", this, Class380.All);
    private final Setting onlyShulkers = new Setting("OnlyShulkers", "", this, false);
    private final Setting storeShulkers = new Setting("StoreShulkers", "", this, false);
    private final Setting entityChests = new Setting("EntityChests", "", this, false);
    private final Class22 Field1251 = new Class22();
    @EventHandler
    private final Listener Field1252 = new Listener<Class261>(this::Method1590);

    public ChestInteract() {
        super("ChestInteract", "Steal, store or drops items from a chest.", Class11.MISC, "");
        this.addSetting(this.mode);
        this.addSetting(this.delay);
        this.addSetting(this.itemMode);
        this.addSetting(this.onlyShulkers);
        this.addSetting(this.storeShulkers);
        this.addSetting(this.entityChests);
    }

    public String Method1592() {
        return ChatFormatting.WHITE + ((Class61) this.mode.getValue()).name();
    }

    private void Method1593(int n, int n2) {
        if (this.mode.getValue() == Class61.Store) {
            for (int i = 9; i < Globals.mc.player.inventoryContainer.inventorySlots.size() - 1; ++i) {
                ItemStack itemStack = Globals.mc.player.inventoryContainer.getSlot(i).getStack();
                if (itemStack.isEmpty() || itemStack.getItem() == Items.AIR || !this.Method1594(itemStack) || ((Boolean) this.storeShulkers.getValue()).booleanValue() && !(itemStack.getItem() instanceof ItemShulkerBox))
                    continue;
                Globals.mc.playerController.windowClick(n, i + n2, 0, ClickType.QUICK_MOVE, Globals.mc.player);
                return;
            }
        }
    }

    private boolean Method1594(ItemStack itemStack) {
        String string = itemStack.item.getRegistryName().getPath();
        if (string == null) {
            return this.itemMode.getValue() == Class380.All;
        }
        switch ((Class380) this.itemMode.getValue()) {
            case All: {
                return true;
            }
            case BlackList: {
                return !Manager.Field1255.Method1595(string, true);
            }
            case Whitelist: {
                return Manager.Field1255.Method1595(string, false);
            }
        }
        return true;
    }

    private void Method1590(Class261 class261) {
        block20:
        {
            block21:
            {
                {
                    if (!this.Field1251.getLongNanoTime((long) (((Float) this.delay.getValue()).floatValue() * 1000.0f))) {
                        return;
                    }
                    this.Field1251.getNanoTime();

                    // Chest GUI
                    if (Globals.mc.currentScreen instanceof GuiChest) {
                        GuiChest guiChest = (GuiChest) Globals.mc.currentScreen;
                        IInventory inventory = (IInventory) Class261.getPrivateField(guiChest, "lowerChestInventory", "field_147015_w");
                        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                            ItemStack itemStack = inventory.getStackInSlot(i);
                            if ((itemStack.isEmpty() || itemStack.getItem() == Items.AIR) && this.mode.getValue() == Class61.Store) {
                                this.Method1593(guiChest.inventorySlots.windowId, inventory.getSizeInventory() - 9);
                                return;
                            }
                            if (((Boolean) this.onlyShulkers.getValue()).booleanValue() && !(itemStack.getItem() instanceof ItemShulkerBox) || itemStack.isEmpty() || !this.Method1594(itemStack)) {
                                continue;
                            }
                            switch ((Class61) this.mode.getValue()) {
                                case Steal: {
                                    Globals.mc.playerController.windowClick(guiChest.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, Globals.mc.player);
                                    return;
                                }
                                case Drop: {
                                    Globals.mc.playerController.windowClick(guiChest.inventorySlots.windowId, i, -999, ClickType.THROW, Globals.mc.player);
                                    return;
                                }
                            }
                        }
                        break block20;
                    }

                    // Horse Inventory
                    if (!(Globals.mc.currentScreen instanceof GuiScreenHorseInventory) || !((Boolean) this.entityChests.getValue()).booleanValue())
                        break block21;
                    GuiScreenHorseInventory guiHorse = (GuiScreenHorseInventory) Globals.mc.currentScreen;
                    IInventory inventory = (IInventory) Class261.getPrivateField(guiHorse, "horseInventory", "field_110051_h");
                    for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                        ItemStack itemStack = inventory.getStackInSlot(i);
                        if ((itemStack.isEmpty() || itemStack.getItem() == Items.AIR) && this.mode.getValue() == Class61.Store) {
                            this.Method1593(guiHorse.inventorySlots.windowId, inventory.getSizeInventory() - 9);
                            return;
                        }
                        if (((Boolean) this.onlyShulkers.getValue()).booleanValue() && !(itemStack.getItem() instanceof ItemShulkerBox) || itemStack.isEmpty() || !this.Method1594(itemStack)) {
                            continue;
                        }
                        switch ((Class61) this.mode.getValue()) {
                            case Steal: {
                                Globals.mc.playerController.windowClick(guiHorse.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, Globals.mc.player);
                                return;
                            }
                            case Drop: {
                                Globals.mc.playerController.windowClick(guiHorse.inventorySlots.windowId, i, -999, ClickType.THROW, Globals.mc.player);
                                return;
                            }
                        }
                    }
                }
                if (!(Globals.mc.currentScreen instanceof GuiShulkerBox) || !((Boolean) this.onlyShulkers.getValue()).booleanValue())
                    break block20;
                GuiShulkerBox guiShulkerBox = (GuiShulkerBox) Globals.mc.currentScreen;
                IInventory inventory = (IInventory) Class261.getPrivateField(guiShulkerBox, "inventory", "field_190001_f");
                for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                    ItemStack itemStack = inventory.getStackInSlot(i);
                    if ((itemStack.isEmpty() || itemStack.getItem() == Items.AIR) && this.mode.getValue() == Class61.Store) {
                        this.Method1593(guiShulkerBox.inventorySlots.windowId, inventory.getSizeInventory() - 9);
                        return;
                    }
                    if (((Boolean) this.onlyShulkers.getValue()).booleanValue() && !(itemStack.getItem() instanceof ItemShulkerBox) || itemStack.isEmpty() || !this.Method1594(itemStack)) {
                        continue;
                    }
                    switch ((Class61) this.mode.getValue()) {
                        case Steal: {
                            Globals.mc.playerController.windowClick(guiShulkerBox.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, Globals.mc.player);
                            return;
                        }
                        case Drop: {
                            Globals.mc.playerController.windowClick(guiShulkerBox.inventorySlots.windowId, i, -999, ClickType.THROW, Globals.mc.player);
                            return;
                        }
                    }
                }
            }
        }
    }
}
