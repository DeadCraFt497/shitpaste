package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerStoppedUsingItem;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class AutoGap
extends Module {
    public Setting health = new Setting("Health", "", (Module)this, (Object)Float.valueOf(14.0f), Float.valueOf(0.0f), Float.valueOf(36.0f));
    public Setting hunger = new Setting("Hunger", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(20.0f));
    public Setting autoSwitch = new Setting("AutoSwitch", "", this, true);
    private boolean Field2638 = false;
    private boolean Field2639 = false;
    private int Field2640 = -1;
    @EventHandler
    private Listener Field2641 = new Listener<EventPlayerUpdate>(this::Method3247, new Predicate[0]);
    @EventHandler
    private Listener Field2642 = new Listener<EventPlayerStoppedUsingItem>(this::Method3248, new Predicate[0]);

    public AutoGap() {
        super("AutoGap", "Automatically eats golden apples.", Class11.MISC, "");
        this.addSetting(this.health);
        this.addSetting(this.hunger);
        this.addSetting(this.autoSwitch);
    }

    private void Method3248(EventPlayerStoppedUsingItem eventPlayerStoppedUsingItem) {
        if (this.Field2638) {
            eventPlayerStoppedUsingItem.cancel();
        }
    }

    private void Method3247(EventPlayerUpdate eventPlayerUpdate) {
        boolean bl;
        boolean bl2 = Class202.Method933() <= ((Float)this.health.getValue()).floatValue();
        boolean bl3 = Globals.mc.player.getFoodStats().getSaturationLevel() <= ((Float)this.hunger.getValue()).floatValue();
        boolean bl4 = bl = Globals.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE;
        if (bl2 || bl3) {
            if (!bl && !Globals.mc.player.isHandActive()) {
                if (((Boolean)this.autoSwitch.getValue()).booleanValue()) {
                    Class155.Method1491(ItemAppleGold.class, false);
                    this.Field2640 = Globals.mc.player.inventory.currentItem;
                }
            } else {
                Globals.mc.rightClickDelayTimer = 4;
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.Field2638 = true;
                this.Field2639 = true;
                return;
            }
        }
        this.Field2638 = false;
        if (this.Field2639 && ((Boolean)this.autoSwitch.getValue()).booleanValue()) {
            Class155.Method522(this.Field2640, false);
            this.Field2639 = false;
        }
    }
}
