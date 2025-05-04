package me.ciruu.abyss;

import java.util.ArrayList;
import java.util.List;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.settings.Setting;

public abstract class SettingsClass {
    private String string;
    private Class11 class11;
    private final List list = new ArrayList();

    public SettingsClass(String string, Class11 class11) {
        this.string = string;
        this.class11 = class11;
    }

    public SettingsClass(String antiHoleCamp, String s, Class11 class11) {
    }

    public Enum getEnum() {
        return this.class11;
    }

    public String getString() {
        return this.string;
    }

    public void getString(String string) {
        this.string = string;
    }

    public void getSetting(Setting setting) {
        this.list.add(setting);
    }

    public List getList() {
        return this.list;
    }
}
