package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerApplyCollision;
import me.ciruu.abyss.events.player.EventPlayerPushOutOfBlocks;
import me.ciruu.abyss.events.player.EventPlayerPushedByWater;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.world.World;

public class Velocity
extends Module {
    public final Setting horizontal = new Setting("Horizontal", "The horizontal velocity you will take.", (Module)this, (Object)0, 0, 100);
    public final Setting vertical = new Setting("Veritcal", "The vertical velocity you will take.", (Module)this, (Object)0, 0, 100);
    public final Setting explosions = new Setting("Explosions", "Apply velocity modifier on explosion velocity.", this, true);
    public final Setting bobbers = new Setting("Bobbers", "Apply velocity modifier on fishing bobber velocity.", this, true);
    public final Setting nopush = new Setting("NoPush", "Disable collision with entities, blocks and water", this, true);
    @EventHandler
    private Listener Field687 = new Listener<EventPlayerPushOutOfBlocks>(this::Method971, new Predicate[0]);
    @EventHandler
    private Listener Field688 = new Listener<EventPlayerPushedByWater>(this::Method972, new Predicate[0]);
    @EventHandler
    private Listener Field689 = new Listener<EventPlayerApplyCollision>(this::Method973, new Predicate[0]);
    @EventHandler
    private Listener Field690 = new Listener<EventNetworkPrePacketEvent>(this::Method974, new Predicate[0]);

    public Velocity() {
        super("Velocity", "Modify the velocity you take.", Class11.MOVEMENT, "");
        this.addSetting(this.horizontal);
        this.addSetting(this.vertical);
        this.addSetting(this.explosions);
        this.addSetting(this.bobbers);
        this.addSetting(this.nopush);
    }

    public String Method976() {
        return ChatFormatting.WHITE + String.format("H:%s%%|V:%s%%", this.horizontal.getValue(), this.vertical.getValue());
    }

    private void Method974(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }

        // Handle Entity Status (e.g. fishhook retraction)
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) eventNetworkPrePacketEvent.getPacket();

            int opcode = ((AccessorSPacketEntityStatus) (Object) packet).getOpcode(); // Mixin accessor
            int entityId = ((AccessorSPacketEntityStatus) (Object) packet).getEntityId();

            if (((Boolean) this.bobbers.getValue()) && opcode == 31) {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(entityId);
                if (entity instanceof EntityFishHook) {
                    EntityFishHook entityFishHook = (EntityFishHook) entity;
                    if (entityFishHook.caughtEntity == Minecraft.getMinecraft().player) {
                        eventNetworkPrePacketEvent.cancel();
                    }
                }
            }
        }

        // Handle Player Velocity
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity packet = (SPacketEntityVelocity) eventNetworkPrePacketEvent.getPacket();

            if (packet.getEntityID() == Globals.mc.player.getEntityId()) {
                int horz = (Integer) this.horizontal.getValue();
                int vert = (Integer) this.vertical.getValue();

                if (horz == 0 && vert == 0) {
                    eventNetworkPrePacketEvent.cancel();
                    return;
                }

                if (horz != 100) {
                    packet.motionX = packet.motionX / 100 * horz;
                    packet.motionZ = packet.motionZ / 100 * horz;
                }

                if (vert != 100) {
                    packet.motionY = packet.motionY / 100 * vert;
                }
            }
        }

        // Handle Explosion Motion
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketExplosion && ((Boolean) this.explosions.getValue())) {
            SPacketExplosion explosion = (SPacketExplosion) eventNetworkPrePacketEvent.getPacket();

            int horz = (Integer) this.horizontal.getValue();
            int vert = (Integer) this.vertical.getValue();

            explosion.motionX *= horz / 100.0f;
            explosion.motionY *= vert / 100.0f;
            explosion.motionZ *= horz / 100.0f;
        }
    }


    private void Method973(EventPlayerApplyCollision eventPlayerApplyCollision) {
        if (!((Boolean)this.nopush.getValue()).booleanValue()) {
            return;
        }
        eventPlayerApplyCollision.cancel();
    }

    private void Method972(EventPlayerPushedByWater eventPlayerPushedByWater) {
        if (!((Boolean)this.nopush.getValue()).booleanValue()) {
            return;
        }
        eventPlayerPushedByWater.cancel();
    }

    private void Method971(EventPlayerPushOutOfBlocks eventPlayerPushOutOfBlocks) {
        if (!((Boolean)this.nopush.getValue()).booleanValue()) {
            return;
        }
        eventPlayerPushOutOfBlocks.cancel();
    }
}
