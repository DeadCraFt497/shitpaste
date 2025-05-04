package me.ciruu.abyss.modules.client;

import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;

public class NameChanger
extends Module {
    public final Setting newname = new Setting("NewName", "", this, "nn");

    public NameChanger() {
        super("NameChanger", "Changes player's name clientside", Class11.CLIENT, "");
        this.addSetting(this.newname);
    }
}
