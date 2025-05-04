package me.ciruu.abyss.modules.misc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Predicate;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class547;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.util.StringUtils;

public class PacketLogger
extends Module {
    public final Setting client = new Setting("Client", "Logs client packets", this, true);
    public final Setting server = new Setting("Server", "Logs server packets", this, true);
    public final Setting logdata = new Setting("LogData", "Logs data in packets", this, true);
    public final Setting savefile = new Setting("SaveFile", "Saves to a file", this, false);
    public final Setting showinchat = new Setting("ShowInChat", "Show in chat", this, false);
    public final Setting cancel = new Setting("Cancel", "Show in chat", this, true);
    private Class22 Field2461 = new Class22();
    private int Field2462 = 0;
    private long Field2463;
    @EventHandler
    private Listener Field2464 = new Listener<Class26>(this::Method2977, new Predicate[0]);
    private int Field2465 = 0;
    @EventHandler
    private Listener Field2466 = new Listener<EventNetworkPostPacketEvent>(this::Method2978, -500, new Predicate[0]);
    @EventHandler
    private Listener Field2467 = new Listener<EventNetworkPrePacketEvent>(this::Method2979, new Predicate[0]);

    public PacketLogger() {
        super("PacketLogger", "Allows you to log certain types of packets", Class11.MISC, "");
        this.addSetting(this.client);
        this.addSetting(this.server);
        this.addSetting(this.logdata);
        this.addSetting(this.savefile);
        this.addSetting(this.showinchat);
        this.addSetting(this.cancel);
    }

    public void Method2981(boolean bl) {
        if(isEnabled()) {
            bl = false;
        }
    }

    public void Method2985() {
        super.getEnable();
        this.Field2463 = System.currentTimeMillis();
        this.Field2462 = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void Method2986(String string, boolean bl) {
        if (((Boolean)this.showinchat.getValue()).booleanValue()) {
            Class547.printChatMessage(string);
        }
        if (!((Boolean)this.savefile.getValue()).booleanValue()) {
            return;
        }
        string = string + "";
        try {
            Path path = Paths.get("Abyss/PacketLogger/", this.Field2463 + " server.txt");
            if (bl) {
                path = Paths.get("Abyss/PacketLogger/", this.Field2463 + " client.txt");
            }
            try {
                BufferedWriter bufferedWriter;
                block12: {
                    bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    Throwable throwable = null;
                    try {
                        bufferedWriter.write(string);
                        if (bufferedWriter == null) return;
                        if (throwable == null) break block12;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    try {
                        bufferedWriter.close();
                        return;
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    return;
                }
                bufferedWriter.close();
                return;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
            return;
        }
    }

    private void Method2979(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getEra() != Class53.PRE || !((Boolean)this.server.getValue()).booleanValue()) {
            return;
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketMoveVehicle || eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityHeadLook) {
            return;
        }
        this.Method2986("[Server] ->" + eventNetworkPrePacketEvent.getClass().getSimpleName(), false);
        if (!((Boolean)this.logdata.getValue()).booleanValue()) {
            return;
        }
        try {
            for (Class<?> clazz = eventNetworkPrePacketEvent.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field == null) continue;
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    this.Method2986(StringUtils.stripControlCodes((String)("" + field.getType().getSimpleName() + "" + field.getName() + " =" + field.get(eventNetworkPrePacketEvent.getPacket()))), false);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void Method2978(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getEra() != Class53.PRE || !((Boolean)this.client.getValue()).booleanValue()) {
            return;
        }
        if (Globals.mc.player == null) {
            return;
        }
        if (eventNetworkPostPacketEvent.isCancelled() && ((Boolean)this.cancel.getValue()).booleanValue()) {
            return;
        }
        this.Method2986("-------------------------------", true);
        this.Method2986("[Tick] ->" + this.Field2462, true);
        this.Method2986("[Client] ->" + eventNetworkPostPacketEvent.getClass().getSimpleName(), true);
        if (!((Boolean)this.logdata.getValue()).booleanValue()) {
            return;
        }
        try {
            for (Class<?> clazz = eventNetworkPostPacketEvent.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field == null) continue;
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    this.Method2986(StringUtils.stripControlCodes((String)("" + field.getType().getSimpleName() + "" + field.getName() + " =" + field.get(eventNetworkPostPacketEvent.getPacket()))), true);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void Method2977(Class26 class26) {
        ++this.Field2462;
    }
}
