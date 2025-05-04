package me.ciruu.abyss.events.player;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.util.DamageSource;

public class EntityPlayerAttackEntityFrom
extends MinecraftEvent {
    public DamageSource dmg_source;
    public float fl_value;

    public EntityPlayerAttackEntityFrom(DamageSource damageSource, float f) {
        this.dmg_source = damageSource;
        this.fl_value = f;
    }

    public DamageSource getDMGSource() {
        return this.dmg_source;
    }

    public float getFloatValue() {
        return this.fl_value;
    }
}
