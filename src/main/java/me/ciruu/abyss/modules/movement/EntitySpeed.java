package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerTravel;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;

public class EntitySpeed
extends Module {
    private final Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    @EventHandler
    private Listener Field2874 = new Listener<EventPlayerTravel>(this::Method3549, new Predicate[0]);

    public EntitySpeed() {
        super("EntitySpeed", "Increases the speed of rideable entities.", Class11.MOVEMENT, "");
        this.addSetting(this.speed);
    }

    public String Method3551() {
        return ChatFormatting.WHITE + String.valueOf(this.speed.getValue());
    }

    private void Method3549(EventPlayerTravel eventPlayerTravel) {
        if (Globals.mc.player == null || Globals.mc.player.getRidingEntity() == null) {
            return;
        }
        Entity entity = Globals.mc.player.getRidingEntity();
        double[] dArray = Class29.getDouble10(((Float)this.speed.getValue()).floatValue());
        if (Globals.mc.player.movementInput.moveStrafe != 0.0f || Globals.mc.player.movementInput.moveForward != 0.0f) {
            entity.motionX = dArray[0];
            entity.motionZ = dArray[1];
        }
        eventPlayerTravel.cancel();
    }
}
