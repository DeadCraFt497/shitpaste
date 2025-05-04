package me.ciruu.abyss;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.AccessController;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.managers.RunnableManager;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.util.StringUtils;
import sun.misc.Unsafe;

public class PacketLogNew
extends Module {
    public Class700 Packet;
    private final Setting setting1 = new Setting("Invoke", "", this, false);
    private final Setting setting2 = new Setting("OnlyUseEntity", "", this, false);
    private final Setting setting3 = new Setting("PrintMethods", "", this, true);
    private final Setting setting4 = new Setting("GetDestroyEntities", "", this, true);
    private final Map Linkedhash = new LinkedHashMap();
    private long long1 = 0L;
    @EventHandler
    private final Listener listen1 = new Listener<EventNetworkPostPacketEvent>(this::getSettingEra2);
    @EventHandler
    private final Listener listen2 = new Listener<EventNetworkPrePacketEvent>(this::getSettingEra1);
    static final Unsafe unsafe1 = PacketLogNew.getUnsafe();
    static final boolean bool_1 = true;

    public PacketLogNew() {
        super("PacketLogNew", "", Class11.MISC, "");
        this.addSetting(this.setting1);
        this.addSetting(this.setting2);
        this.addSetting(this.setting3);
        this.addSetting(this.setting4);
    }

    public void getEnabled() {
        super.getEnable();
        this.long1 = System.currentTimeMillis();
        this.getPacketLoggerVoid();
        this.Linkedhash.clear();
    }

    public void getDisabled() {
        super.getDisable();
        try {
            this.disable();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void getPacketHash(long l, Packet packet) {
        this.Linkedhash.put(l, packet);
    }

    private void getPacketLoggerVoid() {
            RunnableManager.runRunnable(this::getPacketLogger1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void getPacketLogByString(String string) {
        string = string;
        try {
            Path path = Paths.get("Abyss/PacketLogger/", this.long1 + " client.txt");
            try {
                BufferedWriter bufferedWriter;
                block9:
                {
                    bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    Throwable throwable = null;
                    try {
                        bufferedWriter.write(string);
                        if (bufferedWriter == null) return;
                        if (throwable == null) break block9;
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    try {
                        bufferedWriter.close();
                        return;
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    return;
                }
                bufferedWriter.close();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void getBaseOffSetPackets(String string, Object... objectArray) {
        System.out.print(string + ": 0x");
        long l = 0L;
        int n = unsafe1.arrayBaseOffset(objectArray.getClass());
        int n2 = unsafe1.arrayIndexScale(objectArray.getClass());
        switch (n2) {
            case 4: {
                long l2 = 8L;
                long l3 = ((long) unsafe1.getInt(objectArray, n) & 0xFFFFFFFFL) * l2;
                System.out.print(Long.toHexString(l3));
                l = l3;
                for (int i = 1; i < objectArray.length; ++i) {
                    long l4 = ((long) unsafe1.getInt(objectArray, n + i * 4L) & 0xFFFFFFFFL) * l2;
                    if (l4 > l) {
                        System.out.print(", +" + Long.toHexString(l4 - l));
                    } else {
                        System.out.print(", -" + Long.toHexString(l - l4));
                    }
                    l = l4;
                }
                break;
            }
            case 8: {
                throw new AssertionError("Not supported");
            }
        }
        System.out.println();
    }

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }

    private void getPacketLogger1() {
        Iterator iterator2 = this.Linkedhash.keySet().iterator();
        while (iterator2.hasNext()) {
            long l = (Long)iterator2.next();
            try {
                Packet packet = (Packet)this.Linkedhash.get(l);
                if (packet.getClass().getSimpleName().startsWith("S") && !packet.getClass().getSimpleName().equals("SPacketDestroyEntities") || ((Boolean)this.setting2.getValue()).booleanValue() && packet.getClass().getSimpleName().startsWith("C") && !packet.getClass().getSimpleName().equals("CPacketUseEntity") || ((Boolean)this.setting2.getValue()).booleanValue() && (packet.getClass().getSimpleName().equalsIgnoreCase("Position") || packet.getClass().getSimpleName().equalsIgnoreCase("Rotation") || packet.getClass().getSimpleName().equalsIgnoreCase("PositionRotation"))) continue;
                this.getPacketLogByString("[Client]" + packet.getClass().getSimpleName() + " > Time:" + l);
                System.out.println("[Client]" + packet.getClass().getSimpleName() + " > Time:" + l);
                for (Class<?> clazz = packet.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                    this.getPacketLogByString("-----------------------------FIELDS-----------------------------");
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field == null) continue;
                        Class<?> clazz2 = clazz;
                        AccessController.doPrivileged(new CheckGuard(this, field, packet, clazz2));
                    }
                    if (!((Boolean)this.setting3.getValue()).booleanValue()) continue;
                    this.getPacketLogByString("-----------------------------METHODS-----------------------------");
                    for (AccessibleObject accessibleObject : clazz.getDeclaredMethods()) {
                        if (accessibleObject == null) continue;
                        AccessController.doPrivileged(new Class180(this, (Method)accessibleObject, packet));
                        this.getPacketLogByString(StringUtils.stripControlCodes((String)("" + ((Method)accessibleObject).getName() + "" + ((Method)accessibleObject).toGenericString())));
                        System.out.println(StringUtils.stripControlCodes((String)("" + ((Method)accessibleObject).getName() + "" + ((Method)accessibleObject).toGenericString())));
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            this.getPacketLogByString("");
        }
    }

    private void getSettingEra1(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getEra() != Class53.PRE || !((Boolean) this.setting4.getValue()).booleanValue()) {
            return;
        }
        this.getPacketHash(ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now()), eventNetworkPrePacketEvent.getPacket());
    }

    private void getSettingEra2(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getEra() != Class53.PRE) {
            return;
        }
        this.getPacketHash(ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now()), eventNetworkPostPacketEvent.getPacket());
    }

    static void getPacketByClassString(PacketLogNew class6, String string) {
        class6.getPacketLogByString(string);
    }

    static Setting getSetting(PacketLogNew class6) {
        return class6.setting1;
    }
}
