package me.ciruu.abyss.events;

import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class53;
import me.zero.alpine.event.type.Cancellable;

public class MinecraftEvent
extends Cancellable {
    public Class53 era = Class53.PRE;
    public final float partialTicks;

    public MinecraftEvent() {
        this.partialTicks = Globals.mc.getRenderPartialTicks();
    }

    public MinecraftEvent(Class53 class53) {
        this.era = class53;
        this.partialTicks = Globals.mc.getRenderPartialTicks();
    }

    public Class53 getEra() {
        return this.era;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setEra(Class53 class53) {
        this.era = class53;
    }

    public Class53 getClass53() {
        return this.era;
    }
}
