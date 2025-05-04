package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import me.ciruu.abyss.Class27;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Class470;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class592;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

import static me.ciruu.abyss.Globals.mc;

public class HitCrosshair
extends Module {
    private final Setting color = new Setting("Color", "", this, new Color(255, 255, 255, 255));
    private final Setting sound = new Setting("Sound", "", this, (Object)Class592.None);
    private final Setting thickmess = new Setting("Thickness", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(3.0f));
    private final Setting delay = new Setting("Delay", "", (Module)this, (Object)6, 2, 12);
    private final Setting weapons = new Setting("Weapons", "", this, false);
    private boolean Field2398 = false;
    private int Field2399 = 0;
    private Map Field2400 = new HashMap();
    @EventHandler
    private Listener Field2401 = new Listener<EventNetworkPrePacketEvent>(this::Method2933, new Predicate[0]);
    @EventHandler
    private Listener Field2402 = new Listener<EventNetworkPostPacketEvent>(this::Method2934, new Predicate[0]);
    @EventHandler
    private Listener Field2403 = new Listener<ProjectileImpactEvent.Throwable>(this::Method2935, new Predicate[0]);
    @EventHandler
    private Listener Field2404 = new Listener<Class27>(this::Method2936, new Predicate[0]);
    @EventHandler
    private Listener Field2405 = new Listener<EventPlayerUpdate>(this::Method2937, new Predicate[0]);
    @EventHandler
    private Listener Field2406 = new Listener<Class35>(this::Method2938, new Predicate[0]);

    public HitCrosshair() {
        super("HitCrosshair", "", Class11.HUD, "");
        this.addSetting(this.color);
        this.addSetting(this.sound);
        this.addSetting(this.thickmess);
        this.addSetting(this.delay);
    }

    public void Method2940() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int n = scaledResolution.getScaledWidth() + 1;
        int n2 = scaledResolution.getScaledHeight();
        Class50.Method802((float)n / 2.0f - 4.0f, (float)n2 / 2.0f - 4.0f, (float)n / 2.0f - 8.0f, (float)n2 / 2.0f - 8.0f, ((Float)this.thickmess.getValue()).floatValue(), ((Color)this.color.getValue()).getRGB());
        Class50.Method802((float)n / 2.0f + 4.0f, (float)n2 / 2.0f - 4.0f, (float)n / 2.0f + 8.0f, (float)n2 / 2.0f - 8.0f, ((Float)this.thickmess.getValue()).floatValue(), ((Color)this.color.getValue()).getRGB());
        Class50.Method802((float)n / 2.0f - 4.0f, (float)n2 / 2.0f + 4.0f, (float)n / 2.0f - 8.0f, (float)n2 / 2.0f + 8.0f, ((Float)this.thickmess.getValue()).floatValue(), ((Color)this.color.getValue()).getRGB());
        Class50.Method802((float)n / 2.0f + 4.0f, (float)n2 / 2.0f + 4.0f, (float)n / 2.0f + 8.0f, (float)n2 / 2.0f + 8.0f, ((Float)this.thickmess.getValue()).floatValue(), ((Color)this.color.getValue()).getRGB());
    }

    private void Method2938(Class35 class35) {
        if (this.Field2399 > 0) {
            this.Method2940();
        }
    }

    private void Method2937(EventPlayerUpdate eventPlayerUpdate) {
        if (this.Field2398) {
            ++this.Field2399;
        }
        if (this.Field2399 == (Integer)this.delay.getValue()) {
            this.Field2399 = 0;
            this.Field2398 = false;
        }
    }

    private void Method2936(Class27 class27) {
        if (!((Boolean)this.weapons.getValue()).booleanValue()) {
            return;
        }
        Entity entity = class27.getEntity();

        if (entity instanceof EntityThrowable && this.Field2400.containsKey(entity.getEntityId())) {
            EntityThrowable throwable = (EntityThrowable) entity;
        }
    }

    private void Method2935(ProjectileImpactEvent.Throwable throwable) {
        if (!((Boolean)this.weapons.getValue()).booleanValue()) {
            return;
        }
        if (this.Field2400.containsKey(throwable.getThrowable().entityId)) {
            if (throwable.getThrowable().getThrower() != null) {
                System.out.println(throwable.getThrowable().getThrower().getName());
            }
            if (throwable.getRayTraceResult().typeOfHit == RayTraceResult.Type.ENTITY && throwable.getRayTraceResult().entityHit instanceof EntityLivingBase && throwable.getRayTraceResult().entityHit != mc.player) {
                this.Field2398 = true;
                this.Field2400.remove(throwable.getThrowable().entityId);
            }
        }
    }

    private void Method2934(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)eventNetworkPostPacketEvent.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
            this.Field2398 = true;
            switch ((Class592)((Object)this.sound.getValue())) {
                case COD: {
                    mc.world.playSound((EntityPlayer) mc.player, mc.player.posX, mc.player.posY, mc.player.posZ, Class470.Field2409, SoundCategory.PLAYERS, 10.0f, 1.0f);
                    break;
                }
                case CSGO: {
                    mc.world.playSound((EntityPlayer) mc.player, mc.player.posX, mc.player.posY, mc.player.posZ, Class470.Field2410, SoundCategory.PLAYERS, 3.0f, 1.0f);
                    break;
                }
            }
        }
    }

    private void Method2933(EventNetworkPrePacketEvent event) {
        if (!((Boolean) this.weapons.getValue()).booleanValue()) {
            return;
        }
        Packet<?> packet = event.getPacket();

        if (packet instanceof SPacketSpawnObject) {
            SPacketSpawnObject spawnPacket = (SPacketSpawnObject) packet;

            // Check for type 61 (snowball) or 62 (egg), for example
            if (spawnPacket.getType() == 61 || spawnPacket.getType() == 62) {
                Vec3d vec = new Vec3d(spawnPacket.getX(), spawnPacket.getY(), spawnPacket.getZ());
                if (mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) < 2.0) {
                    this.Field2400.put(spawnPacket.getEntityID(), false);
                }
            }
        }

        if (packet instanceof SPacketEntityStatus) {
            SPacketEntityStatus statusPacket = (SPacketEntityStatus) packet;
            Entity entity = statusPacket.getEntity(mc.world);
            if (entity != null && statusPacket.getOpCode() == 3 && this.Field2400.containsKey(entity.getEntityId())) {
                this.Field2400.put(entity.getEntityId(), true);
            }
        }
    }
}
