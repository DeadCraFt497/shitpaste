package me.ciruu.abyss.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashMap;
import java.util.function.Predicate;

import me.ciruu.abyss.Class547;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;

public class TotemPopCount
extends Module {
    private final Setting sendchatmsg = new Setting("SendChatMsg", "Sends to you a message when somebody pops a totem", this, true);
    public static HashMap Field1498 = new HashMap();
    @EventHandler
    private Listener Field2743 = new Listener<EventNetworkPrePacketEvent>(this::addSetting7, new Predicate[0]);
    @EventHandler
    private Listener Field2744 = new Listener<EventPlayerUpdate>(this::addSetting8, new Predicate[0]);

    public TotemPopCount() {
        super("TotemPopCount", "Counts totem pops from players.", Class11.MISC, "");
        this.addSetting(this.sendchatmsg);
    }

    @EventHandler
    private void addSetting8(EventPlayerUpdate eventPlayerUpdate) {
        TotemPopCount tot = this;
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
            if (!Field1498.containsKey(entityPlayer.getName()) || !entityPlayer.isDead && !(entityPlayer.getHealth() <= 0.0f)) continue;
            int n = (Integer)Field1498.get(entityPlayer.getName());
            Field1498.remove(entityPlayer.getName());
            if (!((Boolean)this.sendchatmsg.getValue()).booleanValue()) continue;
            Class547.printChatMessage(ChatFormatting.WHITE + entityPlayer.getName() + " died after popping" + n + " totem(s)!");
        }
    }

    @EventHandler
    private void addSetting7(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        TotemPopCount tot = this;
        SPacketEntityStatus sPacketEntityStatus;
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityStatus && (sPacketEntityStatus = (SPacketEntityStatus)eventNetworkPrePacketEvent.getPacket()).getOpCode() == 35) {
            Entity entity = sPacketEntityStatus.getEntity((World)Globals.mc.world);
            if (entity == null) {
                return;
            }
            int n = 1;
            if (Field1498.containsKey(entity.getName())) {
                n = (Integer)Field1498.get(entity.getName());
                Field1498.put(entity.getName(), ++n);
            } else {
                Field1498.put(entity.getName(), n);
            }
            if (((Boolean)this.sendchatmsg.getValue()).booleanValue()) {
                Class547.printChatMessage(ChatFormatting.WHITE + entity.getName() + " popped" + n + " totem(s)!");
            }
        }
    }
}
