package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class76;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerMove;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class FastSwim
extends Module {
    private final Setting mode = new Setting("Mode", "", this, (Object)Class76.Pulse);
    private int Field3228 = 0;
    @EventHandler
    private Listener Field3229 = new Listener<EventPlayerUpdate>(this::Method3902, new Predicate[0]);
    @EventHandler
    private Listener Field3230 = new Listener<EventNetworkPrePacketEvent>(this::Method3903, new Predicate[0]);
    @EventHandler
    private Listener Field3231 = new Listener<EventPlayerMove>(this::Method3904, new Predicate[0]);

    public FastSwim() {
        super("FastSwim", "Swim faster.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
    }

    public String Method3906() {
        return ChatFormatting.WHITE + ((Class76)((Object)this.mode.getValue())).name();
    }

    public void Method3907() {
        super.getEnable();
        this.Field3228 = 0;
    }

    private void Method3904(EventPlayerMove eventPlayerMove) {
        if (eventPlayerMove.getEra() != Class53.PRE || this.mode.getValue() != Class76.Boost || !Globals.mc.player.isInWater() && !Globals.mc.player.isInLava() || eventPlayerMove.isCancelled()) {
            return;
        }
        ++this.Field3228;
        if (this.Field3228 < 15) {
            return;
        }
        if (Globals.mc.player.capabilities != null && (Globals.mc.player.capabilities.isFlying || Globals.mc.player.isElytraFlying())) {
            return;
        }
        if (Globals.mc.player.onGround) {
            return;
        }
        float f = 0.42f;
        float f2 = Globals.mc.player.movementInput.moveForward;
        float f3 = Globals.mc.player.movementInput.moveStrafe;
        float f4 = Globals.mc.player.rotationYaw;
        if (Globals.mc.player.isPotionActive(MobEffects.SPEED)) {
            int n = Globals.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            f *= 1.0f + 0.2f * (float)(n + 1);
        }
        if (f2 == 0.0f && f3 == 0.0f) {
            eventPlayerMove.getDoubVal4(0.0);
            eventPlayerMove.getDoubVal6(0.0);
        } else {
            if (f2 != 0.0f) {
                if (f3 > 0.0f) {
                    f4 += (float)(f2 > 0.0f ? -45 : 45);
                } else if (f3 < 0.0f) {
                    f4 += (float)(f2 > 0.0f ? 45 : -45);
                }
                f3 = 0.0f;
                if (f2 > 0.0f) {
                    f2 = 1.0f;
                } else if (f2 < 0.0f) {
                    f2 = -1.0f;
                }
            }
            eventPlayerMove.getDoubVal4((double)(f2 * f) * Math.cos(Math.toRadians(f4 + 90.0f)) + (double)(f3 * f) * Math.sin(Math.toRadians(f4 + 90.0f)));
            eventPlayerMove.getDoubVal6((double)(f2 * f) * Math.sin(Math.toRadians(f4 + 90.0f)) - (double)(f3 * f) * Math.cos(Math.toRadians(f4 + 90.0f)));
        }
        eventPlayerMove.cancel();
    }

    private void Method3903(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            this.Field3228 = 0;
        }
    }

    private void Method3902(EventPlayerUpdate eventPlayerUpdate) {
        if (!Globals.mc.player.isInWater() && !Globals.mc.player.isInLava() || Globals.mc.player.isElytraFlying()) {
            return;
        }
        switch ((Class76)((Object)this.mode.getValue())) {
            case AAC: {
                double[] dArray = Class29.getDouble10(0.095f);
                Globals.mc.player.motionX = dArray[0];
                Globals.mc.player.motionZ = dArray[1];
                break;
            }
            case Pulse: {
                ++this.Field3228;
                if (this.Field3228 == 4) {
                    double[] dArray = Class29.getDouble10(0.08f);
                    Globals.mc.player.motionX = dArray[0];
                    Globals.mc.player.motionZ = dArray[1];
                    break;
                }
                if (this.Field3228 < 5) break;
                this.Field3228 = 0;
                double[] dArray = Class29.getDouble10(0.1f);
                Globals.mc.player.motionX = dArray[0];
                Globals.mc.player.motionZ = dArray[1];
                break;
            }
        }
    }
}
