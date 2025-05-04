package me.ciruu.abyss;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;

import net.minecraft.network.Packet;
import net.minecraft.util.StringUtils;

public class CheckGuard implements PrivilegedExceptionAction<Object> {
    private final Field field;
    private final Packet packet;
    private final Class<?> clazz1;
    private final PacketLogNew clazz2;

    public CheckGuard(PacketLogNew clazz2, Field field, Packet packet, Class<?> clazz1) {
        this.field = field;
        this.packet = packet;
        this.clazz1 = clazz1;
        this.clazz2 = clazz2;
    }

    public void JavaCheckGuard() {

    }

    @Override
    public Object run() {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Object value = field.get(packet);
            String fieldType = field.getType().getSimpleName();
            String fieldName = field.getName();

            String message;

            if (field.getType().equals(packet.getClass()) || field.getType().equals(clazz1)) {
                message = fieldType + " " + fieldName;
            } else if (value instanceof String[]) {
                message = fieldType + " " + fieldName + " = " + Arrays.toString((String[]) value);
            } else if (value instanceof int[]) {
                message = fieldType + " " + fieldName + " = " + Arrays.toString((int[]) value);
            } else {
                message = fieldType + " " + fieldName + " = " + String.valueOf(value);
            }

            String cleanedMessage = StringUtils.stripControlCodes(message);
            PacketLogNew.getPacketByClassString(clazz2, cleanedMessage);
            System.out.println(cleanedMessage);

            return value;

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
