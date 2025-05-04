package me.ciruu.abyss.events.minecraft;

import me.ciruu.abyss.events.MinecraftEvent;
import net.minecraft.crash.CrashReport;

public class EventMinecraftCrashReport
extends MinecraftEvent {
    public final CrashReport crashvalue;

    public EventMinecraftCrashReport(CrashReport crashReport) {
        this.crashvalue = crashReport;
    }
}
