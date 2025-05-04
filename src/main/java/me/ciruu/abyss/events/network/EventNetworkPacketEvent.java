package me.ciruu.abyss.events.network;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.network.Packet;

public class EventNetworkPacketEvent
extends MinecraftEvent {
    public final Packet mb_packet;

    public EventNetworkPacketEvent(Packet packet) {
        this.mb_packet = packet;
    }

    public Packet getPacket() {
        return this.mb_packet;
    }
}
