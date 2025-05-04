package me.ciruu.abyss.modules.movement;

import java.util.function.Predicate;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdateMoveState;
import me.ciruu.abyss.modules.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityBoat;

public class AutoWalk
extends Module {
    @EventHandler
    private Listener Field2170 = new Listener<EventPlayerUpdateMoveState>(AutoWalk::Method2647, new Predicate[0]);

    public AutoWalk() {
        super("AutoWalk", "Walks automatically.", Class11.MOVEMENT, "");
    }

    private static void Method2647(EventPlayerUpdateMoveState eventPlayerUpdateMoveState) {
        Globals.mc.player.movementInput.moveForward += 1.0f;
        if (Globals.mc.player.getRidingEntity() instanceof EntityBoat) {
            double[] dArray = Class29.getDouble10(0.47f);
            Globals.mc.player.getRidingEntity().motionX = dArray[0];
            Globals.mc.player.getRidingEntity().motionZ = dArray[1];
        }
    }
}
