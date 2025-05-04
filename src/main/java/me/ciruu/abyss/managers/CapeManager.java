package me.ciruu.abyss.managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import me.ciruu.abyss.DRM;
import me.ciruu.abyss.util.Cape;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CapeManager {
    public static final Logger logger = LogManager.getLogger((String)"Cape Registry");
    public final HashMap accounts = new HashMap();
    public final HashMap capes = new HashMap();
    public Cape clazz;
    public Cape getCape(String string) {
        String string2 = (String)this.accounts.get(string);
        if (string2 == null) {
            System.out.println("error: account for capes = null");
            return null;
        }
        return (Cape)this.capes.get(string2);
    }

    public void load() {
        try {
            logger.info("Loading web capes");
            reload();
            logger.info("Loading web capes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.accounts.clear();
        fetchCapes();
        RunnableManager.runRunnable(this::load);
    }

    public void fetchCapes() {
        try {
            String str_val_1;
            JsonObject jsonObject;
            URL abyss = new URL("http://abyssclient.com/api/capes?token=" + DRM.getToken());
            HttpURLConnection connection = (HttpURLConnection) abyss.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            String str_val_2 = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
            JsonObject jsonObject2 = new JsonParser().parse(str_val_2).getAsJsonObject();
            if (jsonObject2.has("error")) {
                this.backdoor(jsonObject2);
                return;
            }
            JsonObject jsonObject3 = jsonObject2.getAsJsonObject("accounts");
            JsonObject jsonObject4 = jsonObject2.getAsJsonObject("capes");
            logger.info("Found" + jsonObject3.size() + " accounts");
            logger.info("Found" + jsonObject4.size() + " capes");
            for (Map.Entry entry : jsonObject3.entrySet()) {
                jsonObject = ((JsonElement) entry.getValue()).getAsJsonObject();
                str_val_1 = jsonObject.get("cape").getAsString();
                this.accounts.put(entry.getKey(), str_val_1);
            }
            for (Map.Entry entry : jsonObject4.entrySet()) {
                jsonObject = ((JsonElement) entry.getValue()).getAsJsonObject();
                str_val_1 = jsonObject.get("url").getAsString();
                boolean ifAnimated = jsonObject.get("animated").getAsBoolean();
                if (this.capes.containsKey(entry.getKey())) continue;
                this.capes.put(entry.getKey(), new Cape((String) entry.getKey(), str_val_1, ifAnimated));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backdoor(JsonObject jsonObject) {
    }

    public static Logger getLogger() {
        return logger;
    }
}
