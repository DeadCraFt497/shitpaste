package me.ciruu.abyss.modules.misc;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import me.ciruu.abyss.Class142;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class385;
import me.ciruu.abyss.Class386;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;

public class HotbarRefill
extends Module {
    public final Setting durability = new Setting("Durability", "DurabilityThreshold", (Module)this, (Object)50, 0, 100);
    public final Setting stackThreshold = new Setting("StackThreshold", "StackThreshold", (Module)this, (Object)1, 1, 63);
    public final Setting delay = new Setting("Delay", "TickDelay", (Module)this, (Object)1, 1, 100);
    public final Setting nogui = new Setting("NoGui", "", this, true);
    private Class142 Field2986 = Class142.empty();
    private long Field2987 = 0L;
    @EventHandler
    private Listener Field2988 = new Listener<Class26>(this::addSetting64, new Predicate[0]);
    public static Class385 class3852;
    public HotbarRefill() {
        super("HotbarRefill", "Refill items from your inventory to your hotbar.", Class11.MISC, "");
        this.addSetting(this.durability);
        this.addSetting(this.stackThreshold);
        this.addSetting(this.delay);
        this.addSetting(this.nogui);
    }

    private boolean addSetting66(int n) {
        if ((Integer)this.delay.getValue() == 0) {
            return true;
        }
        if ((Integer)this.delay.getValue() < 0) {
            return n < Math.abs((Integer)this.delay.getValue());
        }
        return n == 0 && this.Field2987 % (long)((Integer)this.delay.getValue()).intValue() == 0L;
    }

    private boolean addSetting67(Class385 class385) {
        return class385.isItemDamageable() || class385.isStackable();
    }

    private boolean addSetting68(Class385 class385) {
        return class385.isItemDamageable() ? class385.getDurability() > (Integer)this.durability.getValue() : class385.getStackCount() > (Integer)this.stackThreshold.getValue();
    }

    private int addSetting69(Class385 class385) {
        return class385.isNull() ? 0 : (class385.isItemDamageable() ? class385.getDurability() : class385.getStackCount());
    }

    private void addSetting70() {
        Class385 class385 = Class386.addSetting71();
        if (class385.isEmpty()) {
            return;
        }
        if (class3852 == Class385.Field1513) {
            HotbarRefill.addSetting75(class385, 0, ClickType.PICKUP);
        } else {
            HotbarRefill.addSetting75(class3852, 0, ClickType.PICKUP);
            if (Class386.addSetting71().nonEmpty()) {
                throw new RuntimeException();
            }
        }
    }

    public void addSetting76() {
        super.getDisable();
        Globals.mc.addScheduledTask((Callable<Object>) null);
    }

    private static void addSetting78(Class385 class385) {
        Class385 class3852 = (Class385)Class386.addSetting79().get(class385.getIndex());
        if (!class385.isItemsEqual(class3852)) {
            throw new IllegalArgumentException();
        }
    }

    private static void addSetting80(Class385 class385) {
    }

    private static void addSetting81(int n, int n2, ClickType clickType, ItemStack itemStack) {
        Globals.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(0, n, n2, clickType, itemStack, Class386.addSetting82().getNextTransactionID(Class386.addSetting83())));
    }

    private static ItemStack addSetting75(Class385 class385, int n, ClickType clickType) {
        if (class385.getIndex() == -1) {
            throw new IllegalArgumentException();
        }
        ItemStack itemStack = Class386.addSetting82().slotClick(class385.getSlotNumber(), n, clickType, (EntityPlayer)Globals.mc.player);
        HotbarRefill.addSetting81(class385.getSlotNumber(), n, clickType, itemStack);
        return itemStack;
    }

    private void addSetting64(Class26 class26) {
        if (Globals.mc.player == null) {
            return;
        }
        if (Globals.mc.currentScreen != null && ((Boolean)this.nogui.getValue()).booleanValue()) {
            return;
        }
        if (this.Field2986.isEmpty()) {
            List list = Class386.addSetting72();
        }
        int n = 0;
        while (this.addSetting66(n++) && this.Field2986.hasNext()) {
            try {
                ((Runnable)this.Field2986.next()).run();
            }
            catch (Throwable throwable) {
                this.Field2986 = Class142.singleton(null);
            }
        }
        ++this.Field2987;
    }
}
