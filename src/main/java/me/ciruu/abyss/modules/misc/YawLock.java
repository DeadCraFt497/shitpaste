package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class YawLock
extends Module {
    private final Setting yaw = new Setting("Yaw", "", (Module)this, (Object)0, 0, 360);
    @EventHandler
    private Listener Field2548 = new Listener<EventPlayerUpdateWalking>(this::Method3057, new Predicate[0]);

    public YawLock() {
        super("YawLock", "Locks yaw in a certain direction.", Class11.MISC, "");
        this.addSetting(this.yaw);
    }

    private void Method3057(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        Globals.mc.player.rotationYaw = ((Integer)this.yaw.getValue()).intValue();
        if (Globals.mc.player.isRiding()) {
            Globals.mc.player.getRidingEntity().rotationYaw = ((Integer)this.yaw.getValue()).intValue();
        }
    }
}
