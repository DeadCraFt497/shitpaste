package me.ciruu.abyss.modules.render;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.render.EventRenderWorld;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Aspect
extends Module {
    private final Setting ratio;
    @EventHandler
    private Listener Field3498;

    public Aspect() {
        super("Aspect", "", Class11.RENDER, "");
        this.ratio = new Setting("Ratio", "", (Module)this, (Object)Float.valueOf((float)Globals.mc.displayWidth / (float)Globals.mc.displayHeight), Float.valueOf(0.0f), Float.valueOf(6.0f));
        this.Field3498 = new Listener<EventRenderWorld>(this::Method4249, new Predicate[0]);
        this.addSetting(this.ratio);
    }

    private void Method4249(EventRenderWorld eventRenderWorld) {
        eventRenderWorld.getFloatVoid(((Float)this.ratio.getValue()).floatValue());
    }
}
