package me.ciruu.abyss.events.player;

import me.ciruu.abyss.enums.Class53;

public class EventPlayerMotionUpdate
extends EventPlayerUpdateWalking {
    public final float float_val_1;
    public final float float_val_2;
    public EventPlayerMotionUpdate(Class53 class53, float f, float f2) {
        super(class53, 0.0, 0.0, 0.0, false);
        this.float_val_1 = f;
        this.float_val_2 = f2;
    }
}
