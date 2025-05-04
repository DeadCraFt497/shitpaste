package me.ciruu.abyss.events.network;

import me.ciruu.abyss.events.network.EventNetworkPacketEvent;
import net.minecraft.network.Packet;

/*
 * Exception performing whole class analysis ignored.
 */
public class EventNetworkPostPacketEvent
extends EventNetworkPacketEvent {
    public EventNetworkPostPacketEvent(Packet packet) {
        super(packet);
    }
}
