package me.ciruu.abyss.events.network;

import me.ciruu.abyss.events.network.EventNetworkPacketEvent;
import net.minecraft.network.Packet;

/*
 * Exception performing whole class analysis ignored.
 */
public class EventNetworkPrePacketEvent
extends EventNetworkPacketEvent {
    public EventNetworkPrePacketEvent(Packet packet) {
        super(packet);
    }
}
