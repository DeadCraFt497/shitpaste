package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate
extends Module {
    public final Setting extrapacket = new Setting("ExtraPacket", "", this, false);
    @EventHandler
    private Listener Field794 = new Listener<EventNetworkPrePacketEvent>(this::Method1046, 5000, new Predicate[0]);

    public NoRotate() {
        super("NoRotate", "Prevents server-side rotations. This may desync you from the server.", Class11.MISC, "");
        this.addSetting(this.extrapacket);
    }

    private void Method1046(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (Globals.mc.world == null || Globals.mc.player == null) {
            return;
        }
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventNetworkPrePacketEvent.getPacket();
            if (((Boolean)this.extrapacket.getValue()).booleanValue()) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(sPacketPlayerPosLook.x, sPacketPlayerPosLook.y, sPacketPlayerPosLook.z, sPacketPlayerPosLook.yaw, sPacketPlayerPosLook.pitch, Globals.mc.player.onGround));
            }
            sPacketPlayerPosLook.pitch = Globals.mc.player.rotationPitch;
            sPacketPlayerPosLook.yaw = Globals.mc.player.rotationYaw;
        }
    }
}
