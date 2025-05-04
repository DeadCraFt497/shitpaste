package me.ciruu.abyss.modules.movement;

import java.util.function.Predicate;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiVoid
extends Module {
    private final Setting onground = new Setting("Onground", "", this, true);
    private final Setting packet = new Setting("Abyss$preInit$Packet", "", this, true);
    private final Setting pos = new Setting("Pos", "", this, true);
    @EventHandler
    private Listener Field2556 = new Listener<Class26>(this::Method3062, new Predicate[0]);

    public AntiVoid() {
        super("AntiVoid", "Prevents to fall into the void.", Class11.MOVEMENT, "");
        this.addSetting(this.onground);
        this.addSetting(this.packet);
        this.addSetting(this.pos);
    }

    private void Method3062(Class26 class26) {
        if (Globals.mc.player.posY < 0.0) {
            if (((Boolean)this.packet.getValue()).booleanValue()) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.prevPosX, Globals.mc.player.prevPosY, Globals.mc.player.prevPosZ, ((Boolean)this.onground.getValue()).booleanValue()));
            }
            if (((Boolean)this.pos.getValue()).booleanValue()) {
                Globals.mc.player.setPosition(Globals.mc.player.prevPosX, Globals.mc.player.prevPosY, Globals.mc.player.prevPosZ);
            }
        }
    }
}
