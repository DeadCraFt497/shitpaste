package me.ciruu.abyss.modules.render;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.EnumHand;

public class OldAnimations
extends Module {
    @EventHandler
    private Listener Field919 = new Listener<EventPlayerUpdate>(OldAnimations::Method1180, new Predicate[0]);

    public OldAnimations() {
        super("OldAnimations", "Cancels the change item animation", Class11.RENDER, "");
    }

    private static void Method1180(EventPlayerUpdate eventPlayerUpdate) {
        if ((double)Globals.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            Globals.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Globals.mc.entityRenderer.itemRenderer.itemStackMainHand = Globals.mc.player.getHeldItem(EnumHand.MAIN_HAND);
        }
        if ((double)Globals.mc.entityRenderer.itemRenderer.prevEquippedProgressOffHand >= 0.9) {
            Globals.mc.entityRenderer.itemRenderer.equippedProgressOffHand = 1.0f;
            Globals.mc.entityRenderer.itemRenderer.itemStackOffHand = Globals.mc.player.getHeldItem(EnumHand.OFF_HAND);
        }
    }
}
