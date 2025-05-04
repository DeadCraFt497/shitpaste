package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry
extends Module {
    private final Setting forcecancel = new Setting("ForceCancel", "ForceCancel", this, false);
    @EventHandler
    private Listener Field3374 = new Listener<EventNetworkPostPacketEvent>(this::Method4033, new Predicate[0]);

    public XCarry() {
        super("XCarry", "Carry items in the crafting slots.", Class11.MISC, "");
    }

    public void Method4034() {
        super.getDisable();
        if (Globals.mc.world != null) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(Globals.mc.player.inventoryContainer.windowId));
        }
    }

    private void Method4033(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketCloseWindow) {
            CPacketCloseWindow cPacketCloseWindow = (CPacketCloseWindow)eventNetworkPostPacketEvent.getPacket();
            if (cPacketCloseWindow.windowId == Globals.mc.player.inventoryContainer.windowId || ((Boolean)this.forcecancel.getValue()).booleanValue()) {
                eventNetworkPostPacketEvent.cancel();
            }
        }
    }
}
