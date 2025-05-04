package me.ciruu.abyss.modules.movement;

import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;

public class HighJump
extends Module {
    public final Setting boost = new Setting("Boost", "", (Module)this, (Object)Float.valueOf(1.3f), Float.valueOf(0.1f), Float.valueOf(10.0f));

    public HighJump() {
        super("HighJump", "", Class11.MOVEMENT, "");
        this.addSetting(this.boost);
    }
}
