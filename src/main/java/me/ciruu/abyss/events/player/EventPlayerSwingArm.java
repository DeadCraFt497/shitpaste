package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.EnumHand;

public class EventPlayerSwingArm
extends MinecraftEvent {
    public EnumHand enum_1;

    public EventPlayerSwingArm(EnumHand enumHand) {
        this.enum_1 = enumHand;
    }
}
