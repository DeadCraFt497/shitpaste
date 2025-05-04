package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;

public class AutoRespawn
extends Module {
    @EventHandler
    private Listener Field1514 = new Listener<GuiOpenEvent>(AutoRespawn::Method1926, new Predicate[0]);

    public AutoRespawn() {
        super("AutoRespawn", "Automatically respawns", Class11.MISC, "");
    }

    private static void Method1926(GuiOpenEvent guiOpenEvent) {
        if (guiOpenEvent.getGui() instanceof GuiGameOver) {
            guiOpenEvent.setCanceled(true);
            Globals.mc.player.respawnPlayer();
        }
    }
}
