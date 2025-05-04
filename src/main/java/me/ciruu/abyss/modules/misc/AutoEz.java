package me.ciruu.abyss.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class AutoEz
extends Module {
    private final Setting message = new Setting("Message", "Message", this, "GG Abyss on top!");
    private final Setting playerName = new Setting("PlayerName", "", this, false);
    private final Setting randomNumber = new Setting("RandomNumber", "", this, false);
    int Field1790 = 0;
    private static final ConcurrentHashMap Field1791 = new ConcurrentHashMap();
    @EventHandler
    private Listener Field1792 = new Listener<EventNetworkPostPacketEvent>(AutoEz::Method2166, new Predicate[0]);
    @EventHandler
    private Listener Field1793 = new Listener<LivingDeathEvent>(this::Method2167, new Predicate[0]);
    @EventHandler
    private Listener Field1794 = new Listener<Class26>(this::Method2168, new Predicate[0]);

    public AutoEz() {
        super("AutoEz", "Sends a custom message if you kill a player.", Class11.MISC, "");
        this.addSetting(this.message);
        this.addSetting(this.playerName);
        this.addSetting(this.randomNumber);
    }

    public String Method2170() {
        return ChatFormatting.WHITE + (String)this.message.getValue();
    }

    private void Method2171(String string) {
        if (this.Field1790 < 150) {
            return;
        }
        this.Field1790 = 0;
        Field1791.remove(string);
        Globals.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(((String)this.message.getValue()).trim() + ((Boolean)this.playerName.getValue() != false ? "" + string : "") + ((Boolean)this.randomNumber.getValue() != false ? "" + new Random().nextInt(100) : "")));
    }

    public static void Method1512(String string) {
        if (!Objects.equals(string, Globals.mc.player.getName())) {
            Field1791.put(string, 20);
        }
    }

    private void Method2168(Class26 class26) {
        for (Entity entity : Globals.mc.world.getLoadedEntityList()) {
            EntityPlayer entityPlayer;
            if (!(entity instanceof EntityPlayer) || !((entityPlayer = (EntityPlayer)entity).getHealth() <= 0.0f) || !Field1791.containsKey(entityPlayer.getName())) continue;
            this.Method2171(entityPlayer.getName());
        }
        Field1791.forEach(Method2172());
        ++this.Field1790;
    }

    public static BiConsumer Method2172() {
        String string = (String)null;
        Integer n = (Integer)null;
        if (n <= 0) {
            Field1791.remove(string);
        } else {
            Field1791.put(string, n - 1);
        }
        return null;
    }

    private void Method2167(LivingDeathEvent livingDeathEvent) {
        EntityPlayer entityPlayer;
        if (Globals.mc.player == null) {
            return;
        }
        EntityLivingBase entityLivingBase = livingDeathEvent.getEntityLiving();
        if (entityLivingBase == null) {
            return;
        }
        if (entityLivingBase instanceof EntityPlayer && (entityPlayer = (EntityPlayer)entityLivingBase).getHealth() <= 0.0f && Field1791.containsKey(entityPlayer.getName())) {
            this.Method2171(entityPlayer.getName());
        }
    }

    private static void Method2166(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        Entity entity;
        CPacketUseEntity cPacketUseEntity;
        if (Globals.mc.player == null) {
            return;
        }
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketUseEntity && (cPacketUseEntity = (CPacketUseEntity)eventNetworkPostPacketEvent.getPacket()).getAction().equals((Object)CPacketUseEntity.Action.ATTACK) && (entity = cPacketUseEntity.getEntityFromWorld((World)Globals.mc.world)) instanceof EntityPlayer) {
            AutoEz.Method1512(entity.getName());
        }
    }
}
