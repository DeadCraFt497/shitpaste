package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Objects;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class575;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.combat.KillAura;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class Criticals
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public Setting mode = new Setting("Mode", "", this, (Object)Class575.Packet);
    private Setting packets = new Setting("Packets", "", this, 2, 1, 5, this.mainwindow, this::Method4166);
    public Setting offsetY = new Setting("OffsetY", "", this, Float.valueOf(0.1f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.mainwindow, this::Method4167);
    public Setting ongroundonly = new Setting("OnGroundOnly", "", (Module)this, (Object)true, this.mainwindow, this::Method4168);
    @EventHandler
    private Listener Field3453 = new Listener<EventNetworkPostPacketEvent>(this::Method4169, new Predicate[0]);
    @EventHandler
    private Listener Field3454 = new Listener<AttackEntityEvent>(this::Method4170, new Predicate[0]);

    public Criticals() {
        super("Criticals", "Deal a critical hit on ground.", Class11.COMBAT, "");
        this.addSetting(this.mode);
        this.addSetting(this.packets);
        this.addSetting(this.offsetY);
        this.addSetting(this.ongroundonly);
    }

    public String Method4172() {
        return ChatFormatting.WHITE + ((Class575)((Object)this.mode.getValue())).name();
    }

    private void Method4170(AttackEntityEvent attackEntityEvent) {
        if (this.mode.getValue() == Class575.New) {
            if (Globals.mc.player.isInWater() || Globals.mc.player.isInLava()) {
                return;
            }
            if (!Globals.mc.player.onGround && ((Boolean)this.ongroundonly.getValue()).booleanValue()) {
                return;
            }
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + (double)((Float)this.offsetY.getValue()).floatValue(), Globals.mc.player.posZ, false));
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
            Globals.mc.player.onCriticalHit(attackEntityEvent.getTarget());
        } else if (this.mode.getValue() == Class575.Visual) {
            Globals.mc.player.onCriticalHit(attackEntityEvent.getTarget());
        }
    }

    private void Method4169(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        CPacketUseEntity cPacketUseEntity;
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketUseEntity && this.mode.getValue() != Class575.New && this.mode.getValue() != Class575.Visual && (cPacketUseEntity = (CPacketUseEntity)eventNetworkPostPacketEvent.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && Globals.mc.player.onGround && !Globals.mc.gameSettings.keyBindJump.isKeyDown() && cPacketUseEntity.getEntityFromWorld((World)Globals.mc.world) instanceof EntityLivingBase && !Globals.mc.player.isInWater() && !Globals.mc.player.isInLava()) {
            if (this.mode.getValue() == Class575.Packet) {
                switch ((Integer)this.packets.getValue()) {
                    case 1: {
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + (double)0.1f, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        break;
                    }
                    case 2: {
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.0625101, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 1.1E-5, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        break;
                    }
                    case 3: {
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.0625101, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.0125, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        break;
                    }
                    case 4: {
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.05, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.03, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        break;
                    }
                    case 5: {
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.1625, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 4.0E-6, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 1.0E-6, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
                        Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer());
                        Globals.mc.player.onCriticalHit(Objects.requireNonNull(cPacketUseEntity.getEntityFromWorld((World)Globals.mc.world)));
                        break;
                    }
                }
            } else if (this.mode.getValue() == Class575.Strict) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.06260240169277, Globals.mc.player.posZ, false));
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.07260239960661, Globals.mc.player.posZ, false));
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, false));
            } else if (this.mode.getValue() == Class575.Bypass) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.11, Globals.mc.player.posZ, false));
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.1100013579, Globals.mc.player.posZ, false));
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Globals.mc.player.posX, Globals.mc.player.posY + 0.1100013579, Globals.mc.player.posZ, false));
            } else if (!Manager.moduleManager.isModuleEnabled(KillAura.class)) {
                Globals.mc.player.jump();
                if (this.mode.getValue() == Class575.MiniJump) {
                    Globals.mc.player.motionY /= 2.0;
                }
            }
        }
    }

    private boolean Method4168() {
        return this.mode.getValue() == Class575.New;
    }

    private boolean Method4167() {
        return this.mode.getValue() == Class575.New;
    }

    private boolean Method4166() {
        return this.mode.getValue() == Class575.Packet;
    }
}
