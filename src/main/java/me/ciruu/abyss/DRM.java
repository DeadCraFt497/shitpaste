package me.ciruu.abyss;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.security.Key;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.launchwrapper.Launch;

public class DRM {
    private static String token;

    public static void checkDRM() {
        boolean bl = DRM.class.getSimpleName().equals("DRM");
        if (!bl && Launch.blackboard.get("DRM-InjectData") == null) {
            throw new IllegalStateException("Missing Rocket DRM, please install it");
        }
    }

    public static boolean isBeta() {
        try {
            HashMap hashMap = (HashMap)Launch.blackboard.get("DRM-InjectData");
            if (hashMap == null) {
                return false;
            }
            String string = (String)hashMap.get("Abyss-Info");
            if (string == null) {
                return false;
            }
            if ((string = DRM.decrypt(string)) == null) {
                return false;
            }
            JsonObject jsonObject = new JsonParser().parse(string).getAsJsonObject();
            token = jsonObject.get("token") != null ? jsonObject.get("token").getAsString() : "";
            JsonArray jsonArray = jsonObject.get("roles").getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                if (!jsonElement.getAsString().equals("beta")) continue;
                return true;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static String decrypt(String string) {
        if (string == null) {
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(DRM.hex2bin("320184ae2fe5a740722a420d94bb1e21"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(DRM.hex2bin("6b61499284acab1ef47fca087eff654e"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, (Key)secretKeySpec, ivParameterSpec);
            byte[] byArray = cipher.doFinal(DRM.hex2bin(string));
            return new String(byArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] hex2bin(String string) {
        int n = string.length();
        if (n % 2 != 0) {
            throw new IllegalArgumentException("hex2bin needs to be even-length:" + string);
        }
        byte[] byArray = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            int n2 = DRM.Method3898(string.charAt(i));
            int n3 = DRM.Method3898(string.charAt(i + 1));
            if (n2 == -1 || n3 == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary:" + string);
            }
            byArray[i / 2] = (byte)(n2 * 16 + n3);
        }
        return byArray;
    }

    private static int Method3898(char c) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        if ('A' <= c && c <= 'F') {
            return c - 65 + 10;
        }
        if ('a' <= c && c <= 'f') {
            return c - 97 + 10;
        }
        return -1;
    }

    public static String getToken() {
        return token;
    }
}
