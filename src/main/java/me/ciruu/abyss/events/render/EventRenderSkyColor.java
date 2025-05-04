package me.ciruu.abyss.events.render;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.math.Vec3d;

public class EventRenderSkyColor
extends MinecraftEvent {
    public Vec3d vec;

    public void Method512(Vec3d vec3d) {
        this.vec = vec3d;
    }

    public Vec3d Method2003() {
        return this.vec;
    }
}
