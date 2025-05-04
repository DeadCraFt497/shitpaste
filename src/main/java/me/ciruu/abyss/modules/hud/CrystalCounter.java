package me.ciruu.abyss.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class36;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class120;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class CrystalCounter
extends Module {
    public static final Object Field3069 = new Object();
    public final Setting mode = new Setting("Mode", "", this, (Object)Class120.Both);
    private final Setting x = new Setting("X", "X", (Module)this, (Object)1, 0, 1000);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)200, 0, 1000);
    private final Setting color = new Setting("Color", "", this, new Color(0, 255, 255, 255));
    private final Setting confirmPlace = new Setting("ConfirmPlace", "", this, true);
    private final Setting confirmBreak = new Setting("ConfirmBreak", "", this, true);
    private static Thread Field3076;
    private int Field3077 = 0;
    private int Field3078 = 0;
    private final Class22 Field3079 = new Class22();
    private final ArrayList Field3080 = new ArrayList();
    private final ArrayList Field3081 = new ArrayList();
    private final Set Field3082 = new LinkedHashSet();
    private final Set Field3083 = new LinkedHashSet();
    public static int Field2055;
    public static int Field2056;
    @EventHandler
    private Listener Field3084 = new Listener<EventNetworkPrePacketEvent>(this::Method3724, -100, new Predicate[0]);
    @EventHandler
    private Listener Field3085 = new Listener<EventNetworkPostPacketEvent>(this::Method3725, -100, new Predicate[0]);
    @EventHandler
    private Listener Field3086 = new Listener<Class35>(this::Method3726, new Predicate[0]);
    @EventHandler
    private Listener Field3087 = new Listener<LivingDeathEvent>(this::Method3727, 500, new Predicate[0]);

    public CrystalCounter() {
        super("CrystalCounter", "", Class11.HUD, "");
        this.addSetting(this.confirmPlace);
        this.addSetting(this.confirmBreak);
        this.addSetting(this.color);
        this.addSetting(this.mode);
        this.addSetting(this.x);
        this.addSetting(this.y);
    }

    public void Method3729() {
        super.getEnable();
        Field3076 = new Thread(this::Method3730, "CounterThread");
        Field3076.start();
    }

    public void Method3731() {
        super.getDisable();
        if (Field3076 != null && !Field3076.isInterrupted()) {
            Field3076.interrupt();
        }
        this.Field3083.clear();
        this.Field3082.clear();
    }

    private void Method3732() {
        Object object = Field3069;
        synchronized (object) {
            Field2055 = this.Field3077;
            Field2056 = this.Field3078;
            this.Field3077 = 0;
            this.Field3078 = 0;
        }
    }

    private void Method3727(LivingDeathEvent livingDeathEvent) {
        block1: {
            if (livingDeathEvent.getEntity() != Globals.mc.player) break block1;
            Object object = Field3069;
            synchronized (object) {
                this.Field3082.clear();
                this.Field3083.clear();
            }
        }
    }

    private void Method3726(Class35 class35) {
        if (this.mode.getValue() == Class120.None) {
            return;
        }
        float f = ((Integer)this.y.getValue()).intValue();
        if (this.mode.getValue() == Class120.Place || this.mode.getValue() == Class120.Both) {
            Class36.Method63("PPS:" + ChatFormatting.WHITE + Field2055, ((Integer)this.x.getValue()).intValue(), f, ((Color)this.color.getValue()).getRGB());
            f += 10.0f;
        }
        if (this.mode.getValue() == Class120.Break || this.mode.getValue() == Class120.Both) {
            Class36.Method63("BPS:" + ChatFormatting.WHITE + Field2056, ((Integer)this.x.getValue()).intValue(), f, ((Color)this.color.getValue()).getRGB());
        }
    }

    private void Method3725(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        block5: {
            Object object;
            CPacketUseEntity cPacketUseEntity;
            if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketUseEntity && (cPacketUseEntity = (CPacketUseEntity)eventNetworkPostPacketEvent.getPacket()).getEntityFromWorld((World)Globals.mc.world) instanceof EntityEnderCrystal) {
                object = Field3069;
                synchronized (object) {
                    this.Field3082.add(cPacketUseEntity.entityId);
                    if (!((Boolean)this.confirmBreak.getValue()).booleanValue()) {
                        ++this.Field3078;
                    }
                }
            }
            if (!(eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) || ((cPacketUseEntity = (CPacketUseEntity) eventNetworkPostPacketEvent.getPacket()).getHand() != EnumHand.MAIN_HAND || Globals.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) && (cPacketUseEntity.getHand() != EnumHand.OFF_HAND || Globals.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL)) break block5;
            object = Field3069;
            synchronized (object) {
                this.Field3083.add(cPacketUseEntity.getAction());
                if (!((Boolean)this.confirmPlace.getValue()).booleanValue()) {
                    ++this.Field3077;
                }
            }
        }
    }

    private void Method3724(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        BlockPos object;
        SPacketSpawnObject sPacketSpawnObject;
        Packet packet = eventNetworkPrePacketEvent.getPacket();
        if (packet instanceof SPacketSpawnObject && (sPacketSpawnObject = (SPacketSpawnObject)packet).getType() == 51 && this.Field3083.contains((object = new BlockPos(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ())).down()) && ((Boolean)this.confirmPlace.getValue()).booleanValue()) {
            Object object2 = Field3069;
            synchronized (object2) {
                ++this.Field3077;
                this.Field3083.remove(object.down());
            }
        }

        if (packet instanceof SPacketDestroyEntities) {
            SPacketDestroyEntities destroyPacket = (SPacketDestroyEntities) packet;
            for (int n : destroyPacket.getEntityIDs()) {
                if (!this.Field3082.contains(n) || !((Boolean) this.confirmBreak.getValue()).booleanValue()) continue;
                Object object3 = Field3069;
                synchronized (object3) {
                    ++this.Field3078;
                    this.Field3082.remove(n);
                }
            }
        }
    }

    private void Method3730() {
        while (this.isEnabled()) {
            if (!this.Field3079.getLongNanoTime(1000L)) continue;
            this.Method3732();
            this.Field3079.getNanoTime();
        }
    }

    static {
        Field2055 = 0;
        Field2056 = 0;
    }
}
