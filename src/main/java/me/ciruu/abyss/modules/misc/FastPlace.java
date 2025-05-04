package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemExpBottle;

public class FastPlace
extends Module {
    public final Setting onlyxp = new Setting("OnlyXP", "", this, true);
    private final Setting breakSetting = new Setting("Break", "", this, true);
    public final Setting placedelay = new Setting("PlaceDelay", "", (Module)this, (Object)0, 0, 4);
    private final Setting breakdelay = new Setting("BreakDelay", "", (Module)this, (Object)0, 0, 4);
    @EventHandler
    private Listener Field3234 = new Listener<EventPlayerUpdate>(this::Method3909, new Predicate[0]);

    public FastPlace() {
        super("FastPlace", "Prevents the click delay timer.", Class11.MISC, "");
        this.addSetting(this.onlyxp);
        this.addSetting(this.breakSetting);
        this.addSetting(this.placedelay);
        this.addSetting(this.breakdelay);
    }

    private void Method3909(EventPlayerUpdate eventPlayerUpdate) {
        if (((Boolean)this.onlyxp.getValue()).booleanValue() && (Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || Globals.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle)) {
            Globals.mc.rightClickDelayTimer = (Integer)this.placedelay.getValue();
        }
        if (((Boolean)this.breakSetting.getValue()).booleanValue()) {
            Globals.mc.playerController.blockHitDelay = (Integer)this.breakdelay.getValue();
        }
    }
}
