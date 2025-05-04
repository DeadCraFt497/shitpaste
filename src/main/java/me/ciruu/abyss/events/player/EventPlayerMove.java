package me.ciruu.abyss.events.player;

import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.entity.MoverType;

public class EventPlayerMove
extends MinecraftEvent {
    public MoverType movet;
    public double dub_1;
    public double dub_2;
    public double dub_3;

    public EventPlayerMove(Class53 class53, MoverType moverType, double d, double d2, double d3) {
        this.getEra().equals(class53);
        this.movet = moverType;
        this.dub_1 = d;
        this.dub_2 = d2;
        this.dub_3 = d3;
        this.getEra();
    }

    public MoverType movet() {
        return this.movet;
    }

    public void getMoveTVal(MoverType moverType) {
        this.movet = moverType;
    }

    public double getDoubVal1() {
        return this.dub_1;
    }

    public double getDoubVal2() {
        return this.dub_2;
    }

    public double getDoubVal3() {
        return this.dub_3;
    }

    public void getDoubVal4(double d) {
        this.dub_1 = d;
    }

    public void getDoubVal5(double d) {
        this.dub_2 = d;
    }

    public void getDoubVal6(double d) {
        this.dub_3 = d;
    }

    public void Method987() {
        this.cancel();
        this.dub_1 = 0.0;
        this.dub_3 = 0.0;
    }
}
