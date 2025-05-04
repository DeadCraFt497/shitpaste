package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class144;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Sprint
extends Module {
    private final Setting mode = new Setting("Mode", "Mode", this, (Object)Class144.Legit);
    @EventHandler
    private Listener Field1903 = new Listener<EventPlayerUpdate>(this::Method2281, new Predicate[0]);

    public Sprint() {
        super("Sprint", "Holds down your sprint key.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
    }

    public String Method2283() {
        return ChatFormatting.WHITE + ((Class144)((Object)this.mode.getValue())).name();
    }

    public void Method2284() {
        super.getDisable();
        if (Globals.mc.player == null) {
            return;
        }
        Globals.mc.player.setSprinting(false);
    }

    private void Method2281(EventPlayerUpdate eventPlayerUpdate) {
        if (Globals.mc.player == null) {
            return;
        }
        switch ((Class144)((Object)this.mode.getValue())) {
            case Rage: {
                if (Globals.mc.player.movementInput.moveForward == 0.0f && Globals.mc.player.movementInput.moveStrafe == 0.0f || Globals.mc.player.isSneaking() || Globals.mc.player.collidedHorizontally || !((float)Globals.mc.player.getFoodStats().getFoodLevel() > 6.0f)) break;
                Globals.mc.player.setSprinting(true);
                break;
            }
            case Legit: {
                if (!Globals.mc.gameSettings.keyBindForward.isKeyDown() || Globals.mc.player.isSneaking() || Globals.mc.player.isHandActive() || Globals.mc.player.collidedHorizontally || Globals.mc.currentScreen != null || !((float)Globals.mc.player.getFoodStats().getFoodLevel() > 6.0f)) break;
                Globals.mc.player.setSprinting(true);
            }
        }
    }
}
