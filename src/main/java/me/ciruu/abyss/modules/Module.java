package me.ciruu.abyss.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class128;
import me.ciruu.abyss.managers.RunnableManager;
import me.ciruu.abyss.modules.client.ChatNotifier;
import me.ciruu.abyss.modules.hud.Notifications;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.Listenable;
import net.minecraft.client.Minecraft;

public class Module
extends SettingsClass
implements Class205,
Listenable {
    private static final Object Field479 = new Object();
    private final String Field480;
    private boolean bool_1;
    private boolean bool_2;
    private String Field483;
    private float Field484 = 0.0f;
    public Setting setting;
    private static final List<Setting> settings = new ArrayList<>();
    private String s;
    private Class11 category;
    public boolean enabled = (boolean) this.getEnable();
    public Module(String string, String string2, Class11 class11, Boolean bl) {
        super(string, class11);
        this.Field480 = string2;
        this.bool_2 = bl;
        this.Field483 = "NONE";
        this.enabled = false;
    }

    public Module(String string, String string2, Class11 class11, String string3) {
        super(string, class11);
        this.Field480 = string2;
        this.Field483 = string3;
        this.enabled = false;
    }

    public String getName() {
        return module.getName();
    }


    public void Method581() {
        this.bool_1 = !this.bool_1;
        this.isEnabledValue(this.bool_1);
    }

    public Object getEnable() {
        this.Field484 = 0.0f;
        AbyssMod.EVENT_BUS.subscribe(this);
        if (Manager.moduleManager.getModuleByClass(ChatNotifier.class) != null && Manager.moduleManager.isModuleEnabled(ChatNotifier.class)) {
            Class547.printNewChatMessage(ChatFormatting.WHITE + "" + ChatFormatting.GREEN + " ON");
        }
        if (Manager.moduleManager.getModuleByClass(Notifications.class) != null && ((Boolean) ((Notifications) Manager.moduleManager.getModuleByClass(Notifications.class)).toggleFeature.getValue()).booleanValue()) {
            Manager.Field424.Method342(Class128.Info, ChatFormatting.WHITE + "" + ChatFormatting.GREEN + " enabled!");
        }
        return null;
    }

    public boolean isEnabled1(Boolean bl) {
        if (bl) {
            this.getEnable();
        } else {
            this.getDisable();
        }
        return bl;
    }


    public void getDisable() {
        AbyssMod.EVENT_BUS.unsubscribe(this);
        if (Manager.moduleManager.getModuleByClass(ChatNotifier.class) != null && Manager.moduleManager.isModuleEnabled(ChatNotifier.class)) {
            Class547.printNewChatMessage(ChatFormatting.WHITE + "" + ChatFormatting.RED + " OFF");
        }
        if (Manager.moduleManager.getModuleByClass(Notifications.class) != null && ((Boolean) ((Notifications) Manager.moduleManager.getModuleByClass(Notifications.class)).toggleFeature.getValue()).booleanValue()) {
            Manager.Field424.Method342(Class128.Info, ChatFormatting.WHITE + "" + ChatFormatting.RED + " disabled!");
        }
    }

    public Object isEnabledValue(boolean bl) {
        if (bl == true) {
             getEnable();
        } else {
            getDisable();
        }
        return null;
    }

    public void isDisabledValue(boolean bl) {
        if (this.bool_1 && bl) {
            return;
        }
        if (!this.bool_1 && !bl) {
            return;
        }
        this.bool_1 = bl;
        if (bl == true) {
            this.getDisable();
        } else {
            this.getEnable();
        }
    }

    public Class11 getCategory() {
        return this.category;
    }

    public boolean getBoolean18() {
        return this.bool_1;
    }

    public void Method584(boolean bl) {
        this.bool_1 = bl;
    }

    public void Method177(String string) {
        this.Field483 = string;
    }

    public String Method160() {
        return this.Field483;
    }

    public void Method585(boolean bl) {
        this.bool_2 = bl;
    }

    public boolean Method492() {
        return this.bool_2;
    }

    public String Method586() {
        return this.Field480;
    }

    public String Method587() {
        return null;
    }

    public float Method588() {
        return this.Field484;
    }

    public void Method589(float f) {
        this.Field484 = f;
    }

    private JsonObject Method590() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("display", "");
        jsonObject.addProperty("keybind", this.Method160());
        jsonObject.addProperty("hidden", Boolean.valueOf(this.Method492()));
        JsonObject jsonObject2 = new JsonObject();
        for (Setting setting : this.getSettings()) {
            JsonPrimitive jsonPrimitive;
            String string;
            Object object = setting.getValue();
            if (object instanceof Class25) continue;
            JsonObject jsonObject3 = new JsonObject();
            try {
                string = object.getClass().getSimpleName();
            } catch (InternalError internalError) {
                string = object.getClass().getName();
            }
            if (object instanceof Integer) {
                jsonPrimitive = new JsonPrimitive(String.valueOf(object));
            } else if (object instanceof String) {
                jsonPrimitive = new JsonPrimitive((String) object);
            } else if (object instanceof Boolean) {
                jsonPrimitive = new JsonPrimitive((Boolean) object);
            } else if (object instanceof Float) {
                jsonPrimitive = new JsonPrimitive(Float.valueOf(((Float) object).floatValue()));
            } else if (object instanceof Double) {
                jsonPrimitive = new JsonPrimitive(String.valueOf(object));
            } else if (object instanceof Enum) {
                string = "Enum";
                jsonPrimitive = new JsonPrimitive(((Enum) object).ordinal());
            } else if (object instanceof Color) {
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(((Color) object).getRed());
                jsonArray.add(((Color) object).getGreen());
                jsonArray.add(((Color) object).getBlue());
                jsonArray.add(((Color) object).getAlpha());
                jsonPrimitive = jsonArray.getAsJsonPrimitive();
            } else if (object instanceof Class207) {
                string = "Bind";
                jsonPrimitive = new JsonPrimitive(((Class207) object).Method592());
            } else {
                throw new IllegalStateException("No se puede guardar" + object.getClass() + ", valor:" + object);
            }
            jsonObject3.addProperty("type", string);
            jsonObject3.add("value", jsonPrimitive);
            jsonObject2.add(setting.Method396(), jsonObject3);
        }
        return jsonObject;
    }

    private void Method593(JsonObject jsonObject) {
        this.isDisabledValue(jsonObject.get("enabled").getAsBoolean());
        this.Method177(jsonObject.get("keybind").getAsString());
        this.Method585(jsonObject.get("hidden").getAsBoolean());
        if (!jsonObject.has("settings")) {
            return;
        }
        JsonObject jsonObject2 = jsonObject.get("settings").getAsJsonObject();
        JsonObject jsonObject3 = this.Method590().getAsJsonObject("settings");
        for (; ; ) {
            if (!jsonObject2.has(setting.Method396())) continue;
            try {
                JsonObject jsonObject4 = jsonObject3.get(setting.Method396()).getAsJsonObject();
                JsonObject jsonObject5 = jsonObject2.get(setting.Method396()).getAsJsonObject();
                String string = jsonObject4.get("type").getAsString();
                String string2 = jsonObject5.get("type").getAsString();
                JsonElement jsonElement = jsonObject5.get("value");
                if (!string.equals(string2)) {
                    System.err.println("Setting type changed from" + string2 + " to" + string + ", reverting to default (" + setting.Method396() + ")");
                    setting.setValue(setting.Method595());
                    continue;
                }
                switch (string2) {
                    case "Integer": {
                        setting.setValue(jsonElement.getAsNumber().intValue());
                        break;
                    }
                    case "Boolean": {
                        setting.setValue(jsonElement.getAsBoolean());
                        break;
                    }
                    case "String": {
                        setting.setValue(jsonElement.getAsString());
                        break;
                    }
                    case "Float": {
                        setting.setValue(Float.valueOf(jsonElement.getAsNumber().floatValue()));
                        break;
                    }
                    case "Double": {
                        setting.setValue(jsonElement.getAsNumber().doubleValue());
                        break;
                    }
                    case "Enum": {
                        Object[] objArray = setting.getValue().getClass().getEnumConstants();
                        setting.setValue(objArray[jsonElement.getAsInt()]);
                        break;
                    }
                    case "Color": {
                        JsonArray jsonArray = jsonElement.getAsJsonArray();
                        setting.setValue(new Color(jsonArray.get(0).getAsInt(), jsonArray.get(1).getAsInt(), jsonArray.get(2).getAsInt(), jsonArray.get(3).getAsInt()));
                        break;
                    }
                    case "Bind": {
                        setting.setValue(new Class207(jsonElement.getAsInt()));
                    }
                }
            } catch (Exception exception) {
                System.err.println("Corrupted value for" + setting.Method396() + ", ignoring");
                setting.setValue(setting.Method595());
            }
        }
    }

    public void Method596() {
        File file = new File("Abyss/Features/" + Class208.Field486 + "/" + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = this.Method590();
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath());
            gson.toJson(jsonObject, bufferedWriter);
            bufferedWriter.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void getSaveSettings() {
        System.out.println("Saving all settings...");
        try {
            RunnableManager.runRunnable(Module::getModulesMain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Saved all settings!");
    }

    public void Method600() {
        JsonObject jsonObject;
        File file = new File("Abyss/Features/" + Class208.Field486 + "/" + ".json");
        if (!file.exists()) {
            return;
        }
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(file.toPath());
            jsonObject = new JsonParser().parse(bufferedReader).getAsJsonObject();
            bufferedReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        Minecraft.getMinecraft().addScheduledTask(() -> this.Method601(jsonObject));
    }

    public static void Method602() {
        RunnableManager.runRunnable(Module::getSettingsMain);
    }

    public static Module module;

    private static Object getSettingsMain() {
        getSettings();
        System.out.println("+----(Loading all settings...)----+");
        System.out.println("Module:\n" + getSettings() + "\n\n");

        Object object = (Object) Field479;
        synchronized ((Setting)object) {
            for (; ; ) {
                if (Manager.moduleManager.getModules().values() != null) {
                    module.Method600();
                }
            }
        }
    }

    private static void Method605() {
        System.out.println("Loaded all settings!");
        AbyssMod.EVENT_BUS.post(new Class210());
    }

    private void Method601(JsonObject jsonObject) {
        this.Method593(jsonObject);
    }

    private static boolean getModulesMain() {
        Object object = Field479;
        synchronized (object) {
            // Loop through all modules and call Method600() or Method596()
            for (Object mod_ : Manager.moduleManager.getModules().values()) {
                if (mod_ instanceof Module) {
                    Module mod = (Module) mod_;
                    mod.Method600(); // Or use Method596() depending on the behavior you need
                }
            }
        }
        return true;
    }


    public static void addSetting(Setting setting) {
        settings.add(setting);
    }

    public static List<Setting> getSettings() {
        return settings;
    }


    public static void setup() {
    }

    public void Method684(String keyName) {
        this.Method177(keyName);
    }

    @SuppressWarnings("deprecation")
    public void enable() {
        getEnable();
        this.getEnable();
    }

    @SuppressWarnings("deprecation")
    public void disable() {
        getDisable();
        this.getDisable();
    }

    public static boolean isEnabled() {
        return true;
    }

    @Override
    public void Method1048() {

    }

    @Override
    public void Method1049() {

    }

    @Override
    public void Method1050() {

    }

    @Override
    public void Method1051(boolean var1) {

    }
}
