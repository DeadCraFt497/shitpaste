package me.ciruu.abyss.settings;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import me.ciruu.abyss.Class25;
import me.ciruu.abyss.modules.Module;

public class Setting implements Predicate {
    private final String Field3582;
    private final String Field3583;
    private final Module Field3584;
    private BooleanSupplier Field3585;
    public String mainSettings = "Main Settings";
    private Object Field3586;
    private Object value;
    private Object Field3588;
    private Object Field3589;

    private Setting(String string, String string2, Module module) {
        this.Field3582 = string;
        this.Field3583 = string2;
        this.Field3584 = module;
    }

    public Setting(String string, String string2, Module module, Object object) {
        this(string, string2, module);
        this.Field3586 = object;
        this.value = object;
    }

    public Setting(String string, String string2, Module module, Object object, Object object2, Object object3) {
        this(string, string2, module, object);
        this.Field3588 = object2;
        this.Field3589 = object3;
    }

    public Setting(String string, String string2, Module module, Setting setting, BooleanSupplier booleanSupplier) {
        this.Field3582 = string;
        this.Field3583 = string2;
        this.Field3584 = module;
        this.Field3585 = booleanSupplier;
        this.mainSettings = ((Class25)setting.getValue()).getString();
    }

    public Setting(String string, String string2, Module module, Object object, Setting setting, BooleanSupplier booleanSupplier) {
        this(string, string2, module, object);
        this.Field3585 = booleanSupplier;
        this.mainSettings = ((Class25)setting.getValue()).getString();
    }

    public Setting(String string, String string2, Module module, Object object, Object object2, Object object3, Setting setting, BooleanSupplier booleanSupplier) {
        this(string, string2, module, object);
        this.Field3588 = object2;
        this.Field3589 = object3;
        this.Field3585 = booleanSupplier;
        this.mainSettings = ((Class25)setting.getValue()).getString();
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) this.value;
    }


    public void setValue(Object object) {
        this.value = object;
    }

    public Object Method595() {
        return this.Field3586;
    }

    public Module Method4328() {
        return this.Field3584;
    }

    public Object Method386() {
        return this.Field3588;
    }

    public void Method4329(Object object) {
        this.Field3588 = object;
    }

    public Object Method389() {
        return this.Field3589;
    }

    public void Method4330(Object object) {
        this.Field3589 = object;
    }

    public String Method396() {
        return this.Field3582;
    }

    public String Method4331() {
        return this.Field3583;
    }

    public BooleanSupplier Method2561() {
        return this.Field3585;
    }

    public String Method3462(boolean bl) {
        Enum enum_;
        int n;
        Enum enum_2 = (Enum)this.getValue();
        for (n = 0; n < this.value.getClass().getEnumConstants().length && !(enum_ = (Enum)this.value.getClass().getEnumConstants()[n]).name().equalsIgnoreCase(enum_2.name()); ++n) {
        }
        return this.value.getClass().getEnumConstants()[(bl ? (n != 0 ? n - 1 : this.value.getClass().getEnumConstants().length - 1) : n + 1) % this.value.getClass().getEnumConstants().length].toString();
    }

    public int Method4332(String string) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            Enum enum_ = (Enum)this.value.getClass().getEnumConstants()[i];
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            return i;
        }
        return -1;
    }

    public Enum Method4333(String string) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            Enum enum_ = (Enum)this.value.getClass().getEnumConstants()[i];
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            return enum_;
        }
        return null;
    }

    public void Method3463(String string) {
        for (Enum enum_ : (Enum[])((Enum)this.value).getClass().getEnumConstants()) {
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            this.setValue(enum_);
            break;
        }
    }

    @Override
    public boolean test(Object o) {
        return false;
    }
}
