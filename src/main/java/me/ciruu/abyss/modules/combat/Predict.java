package me.ciruu.abyss.modules.combat;

import java.util.ArrayDeque;
import java.util.function.Predicate;
import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Class19;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Class27;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class18;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Predict
extends Module {
    public static final Object Field18 = new Object();
    public static final Class19 Field19 = new Class19();
    private final AutoCrystal moduleAutoCrystal = (AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class);
    private final Setting mainwindow = new Setting("", "", this, new Class25("Main Settings"));
    public final Setting predict = new Setting("Predict", "", (Module)this, (Object)false, this.mainwindow, Predict::Method19);
    private final Setting mode = new Setting("Mode", "", (Module)this, (Object)Class18.Timer, this.mainwindow, this::Method20);
    public final Setting timer = new Setting("Timer(ms)", "", this, 1, 0, 500, this.mainwindow, this::Method21);
    public final Setting delay = new Setting("Delay(ticks)", "", this, 0, 0, 20, this.mainwindow, this::Method22);
    private final Setting packets = new Setting("Packets", "", this, 1, 1, 20, this.mainwindow, this::Method23);
    private final Setting packetlimit = new Setting("PacketLimit", "", this, 200, 1, 200, this.mainwindow, this::Method24);
    private final Setting samples = new Setting("Samples", "", this, 4, 1, 200, this.mainwindow, this::Method25);
    private final Setting moda = new Setting("Moda", "", (Module)this, (Object)false, this.mainwindow, this::Method26);
    private final Setting branchprediction = new Setting("BranchPrediction", "", (Module)this, (Object)false, this.mainwindow, this::Method27);
    private final Setting antikick = new Setting("AntiKick", "", (Module)this, (Object)false, this.mainwindow, this::Method28);
    private final Setting aktimer = new Setting("AKTimer", "Delay (MS)", this, Float.valueOf(500.0f), Float.valueOf(0.0f), Float.valueOf(1000.0f), this.mainwindow, this::Method29);
    private final Setting recalcID = new Setting("RecalcID", "", (Module)this, (Object)true, this.mainwindow, this::Method30);
    public final Setting placeafter = new Setting("PlaceAfter", "", (Module)this, (Object)false, this.mainwindow, this::Method31);
    public int Field35;
    public int Field36;
    private int Field37 = 0;
    public final ArrayDeque Field38 = new ArrayDeque();
    public final ArrayDeque Field39 = new ArrayDeque();
    public final ArrayDeque Field40 = new ArrayDeque();
    public final ArrayDeque Field41 = new ArrayDeque();
    public final ArrayDeque Field42 = new ArrayDeque();
    public final ArrayDeque Field43 = new ArrayDeque();
    public int Field44 = 0;
    public int Field45 = 0;
    public int Field46 = 0;
    public int Field47 = 0;
    private long Field48 = System.currentTimeMillis();
    private int Field49;
    public boolean Field50 = false;
    private final Class22 Field51 = new Class22();
    private boolean Field52 = false;
    private static final Class22 Field53 = new Class22();
    private final Class22 Field54 = new Class22();
    private boolean Field55 = true;
    private int Field56 = 0;
    @EventHandler
    private Listener Field57 = new Listener<EventNetworkPrePacketEvent>(this::Method32, 300, new Predicate[0]);
    @EventHandler
    private Listener Field58 = new Listener<Class26>(this::Method33, new Predicate[0]);
    @EventHandler
    private Listener Field59 = new Listener<LivingDeathEvent>(Predict::Method34, 500, new Predicate[0]);
    @EventHandler
    private Listener Field60 = new Listener<Class27>(Predict::Method35, 600, new Predicate[0]);

    public Predict() {
        super("Predict", "", Class11.COMBAT, "");
        this.addSetting(this.predict);
        this.addSetting(this.mode);
        this.addSetting(this.timer);
        this.addSetting(this.delay);
        this.addSetting(this.packets);
        this.addSetting(this.packetlimit);
        this.addSetting(this.samples);
        this.addSetting(this.moda);
        this.addSetting(this.branchprediction);
        this.addSetting(this.antikick);
        this.addSetting(this.aktimer);
        this.addSetting(this.recalcID);
        this.addSetting(this.placeafter);
    }

    public void Method37() {
        super.getEnable();
        AbyssMod.EVENT_BUS.subscribe(Predict.Field19.listen1);
    }

    public void Method38() {
        super.getDisable();
        AbyssMod.EVENT_BUS.unsubscribe(Predict.Field19.listen1);
    }

    public void Method39() {
        long l = System.currentTimeMillis() - 1000L;
        if (this.Field48 < l) {
            this.Field42.clear();
            this.Field44 = this.Field46;
            this.Field46 = 0;
            this.Field45 = this.Field47;
            this.Field47 = 0;
            this.Field48 = System.currentTimeMillis();
        }
        Object object = Field18;
        synchronized (object) {
            this.Field43.removeIf(arg_0 -> Predict.Method40(l, (Long) arg_0));
            while (!this.Field41.isEmpty() && this.Field43.size() < (Integer)this.packetlimit.getValue()) {
                Globals.mc.player.connection.sendPacket((Packet)this.Field41.removeLast());
                this.Field43.add(System.currentTimeMillis());
            }
            while (this.Field41.size() > 20) {
                this.Field41.removeFirst();
            }
        }
    }

    private void Method41(CPacketUseEntity cPacketUseEntity) {
        Object object = Field18;
        synchronized (object) {
            if (this.Field43.size() < (Integer) this.packetlimit.getValue()) {
                Globals.mc.player.connection.sendPacket((Packet) cPacketUseEntity);
                this.Field43.add(System.currentTimeMillis());
            }
        }
    }

    public int preInit$Predict() {
        Object object = Field18;
        synchronized (object) {
            float f = Class29.getFloatIterator(this.Field38, Class29.getFloatCollection(this.Field38));
            float f2 = Class29.getFloatCollectionAmount(this.Field38);
            float f3 = Class29.getFloatCollection(this.Field39);
            float f4 = (Boolean)this.moda.getValue() != false ? f2 : f;
            int n = Math.round((float)Predict.Field19.int1 + f4);
            if (((Boolean)this.branchprediction.getValue()).booleanValue()) {
                if (this.Field37 < 0) {
                    ++n;
                }
                if (this.Field37 > 0) {
                    --n;
                }
            }
            this.Field35 = Math.max(n, this.Field36);
            this.Field36 = n;
            this.Field35 = n;
            return this.Field35;
        }
    }

    private static void Method35(Class27 class27) {
        Predict.Field19.int1 = Math.max(class27.getEntity().getEntityId(), Predict.Field19.int1);
    }

    private static void Method34(LivingDeathEvent livingDeathEvent) {
        if (livingDeathEvent.getEntity() instanceof EntityPlayer && livingDeathEvent.getEntity().getDistance((Entity)Globals.mc.player) <= 6.0f) {
            Field53.getNanoTime();
        }
    }

    private static boolean Method40(long l, Long l2) {
        return l2 < l;
    }

    private void Method33(Class26 class26) {
        if (!Manager.moduleManager.isModuleEnabled(AutoCrystal.class)) {
            return;
        }
        this.Method39();
        if (this.Field56 >= (Integer)this.delay.getValue()) {
            this.Field55 = true;
            this.Field56 = 0;
        }
        ++this.Field56;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void Method32(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (Globals.mc.player == null) return;
        if (Globals.mc.world == null) return;
        if (this.moduleAutoCrystal == null) {
            return;
        }
        if (!Manager.moduleManager.isModuleEnabled(AutoCrystal.class)) {
            return;
        }
        if (((AutoCrystal)Manager.moduleManager.getModuleByClass(AutoCrystal.class)).bool2) {
            return;
        }
        Packet packet = eventNetworkPrePacketEvent.getPacket();
        String string = packet.getClass().getSimpleName();
        if (string.startsWith("C")) {
            return;
        }
        this.Method39();
        try {
            Object object;
            int n;
            if (packet instanceof SPacketSpawnObject) {
                SPacketSpawnObject sPacketSpawnObject = (SPacketSpawnObject)packet;
                this.Field49 = n = sPacketSpawnObject.getEntityID();
                if (sPacketSpawnObject.getType() == 51) {
                    Object object2 = Field18;
                    synchronized (object2) {
                        this.Field38.addLast(n - Predict.Field19.int1);
                        while (this.Field38.size() > (Integer)this.samples.getValue()) {
                            this.Field38.removeFirst();
                        }
                        if (this.Field35 < n && this.Field37 > -2) {
                            --this.Field37;
                        }
                        if (this.Field35 > n && this.Field37 < 2) {
                            ++this.Field37;
                        }
                        Predict.Field19.int1 = n;
                        object = new BlockPos(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ());
                        if (this.Field42.contains(object)) {
                            ++this.Field47;
                        }
                        this.Field39.add(this.Field35 - Predict.Field19.int1);
                        while (this.Field39.size() > (Integer)this.samples.getValue()) {
                            this.Field39.removeFirst();
                        }
                        this.Field40.addLast(this.Field35 == Predict.Field19.int1);
                        while (this.Field40.size() > 10) {
                            this.Field40.removeFirst();
                        }
                    }
                }
            }
            if ((Boolean)this.predict.getValue() == false) return;
            if (this.mode.getValue() != Class18.Timer || !this.Field51.getLongNanoTime(((Integer)this.timer.getValue()).intValue())) {
                if (this.mode.getValue() != Class18.Tick) return;
                if (!this.Field55) return;
            }
            int n2 = this.preInit$Predict();
            n = 0;
            this.Field55 = false;
            if (((Boolean)this.antikick.getValue()).booleanValue() && Class30.Method51()) {
                this.Field54.getNanoTime();
            }
            if (Globals.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
                if (Globals.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) return;
            }
            if (this.moduleAutoCrystal.blockPos1 == null) return;
            if (this.moduleAutoCrystal.blockPos2 == null) return;
            if (!this.Field54.getLongNanoTime(((Float)this.aktimer.getValue()).longValue())) return;
            if (!Field53.getLongNanoTime(((Float)this.aktimer.getValue()).longValue())) return;
            if (this.moduleAutoCrystal.entity == null) return;
            if (this.moduleAutoCrystal.entity.isDead) return;
            for (int i = 0; i < (Integer)this.packets.getValue(); ++i) {
                if (Globals.mc.player.getDistance((double)this.moduleAutoCrystal.blockPos2.getX(), (double)this.moduleAutoCrystal.blockPos2.getY(), (double)this.moduleAutoCrystal.blockPos2.getZ()) > (double)((Float)this.moduleAutoCrystal.placerange.getValue()).floatValue()) continue;
                object = Field18;
                synchronized (object) {
                    if (this.moduleAutoCrystal.blockPos2 != null) {
                        Class31.Method52(this.moduleAutoCrystal.blockPos2, Globals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, false, null);
                        this.Field42.add(this.moduleAutoCrystal.blockPos2.offset(EnumFacing.UP));
                        this.Field42.add(this.moduleAutoCrystal.blockPos2.offset(EnumFacing.DOWN));
                        this.Field42.add(this.moduleAutoCrystal.blockPos2);
                        ++this.Field46;
                        CPacketUseEntity cPacketUseEntity = new CPacketUseEntity();
                        cPacketUseEntity.entityId = n2;
                        cPacketUseEntity.action = CPacketUseEntity.Action.ATTACK;
                        this.Method41(cPacketUseEntity);
                        Globals.mc.player.swingArm(Globals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                        if ((Integer)this.packets.getValue() > 1) {
                            if (((Boolean)this.recalcID.getValue()).booleanValue()) {
                                n = n2;
                                if (n == (n2 = this.preInit$Predict())) {
                                    n2 = n + 1;
                                }
                            } else {
                                ++n2;
                            }
                        }
                        if (((Boolean)this.placeafter.getValue()).booleanValue()) {
                            Class31.Method52(this.moduleAutoCrystal.blockPos2, Globals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, false, null);
                        }
                    }
                    continue;
                }
            }
            this.Field51.getNanoTime();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private boolean Method31() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method30() {
        return (Integer)this.packets.getValue() > 1 && (Boolean)this.predict.getValue() != false;
    }

    private boolean Method29() {
        return (Boolean)this.antikick.getValue() != false && (Boolean)this.predict.getValue() != false;
    }

    private boolean Method28() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method27() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method26() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method25() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method24() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method23() {
        return (Boolean)this.predict.getValue();
    }

    private boolean Method22() {
        return (Boolean)this.predict.getValue() != false && this.mode.getValue() == Class18.Tick;
    }

    private boolean Method21() {
        return (Boolean)this.predict.getValue() != false && this.mode.getValue() == Class18.Timer;
    }

    private boolean Method20() {
        return (Boolean)this.predict.getValue();
    }

    private static boolean Method19() {
        return true;
    }

    static Class22 Method53() {
        return Field53;
    }
}
