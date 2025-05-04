package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;

public class EventPlayerTravel
extends MinecraftEvent {
    public float fl_1;
    public float fl_2;
    public float fl_3;

    public EventPlayerTravel(float f, float f2, float f3) {
        this.fl_1 = f;
        this.fl_2 = f2;
        this.fl_3 = f3;
    }
}
