package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Class486;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class NoGlitchBlocks
extends Module {
    public final Setting placeSetting = new Setting("Place", "", this, true);
    public final Setting breakSetting = new Setting("Break", "", this, true);
    @EventHandler
    private Listener Field1719 = new Listener<Class486>(this::Method2121, new Predicate[0]);

    public NoGlitchBlocks() {
        super("NoGlitchBlocks", "Removes invisible blocks.", Class11.MISC, "");
        this.addSetting(this.placeSetting);
        this.addSetting(this.breakSetting);
    }

    private void Method2121(Class486 class486) {
        if (((Boolean)this.placeSetting.getValue()).booleanValue()) {
            class486.cancel();
        }
    }
}
