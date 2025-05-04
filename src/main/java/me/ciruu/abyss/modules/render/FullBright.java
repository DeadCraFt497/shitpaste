package me.ciruu.abyss.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class131;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

public class FullBright
extends Module {
    private final Setting mode = new Setting("Mode", "", this, (Object)Class131.Gamma);
    private final Setting effects = new Setting("Effects", "", this, false);
    private float Field1417 = 1.0f;
    @EventHandler
    private Listener Field1418 = new Listener<Class26>(this::Method1816, new Predicate[0]);
    @EventHandler
    private Listener Field1419 = new Listener<EventNetworkPrePacketEvent>(this::Method1817, new Predicate[0]);

    public FullBright() {
        super("FullBright", "Turns up brightness.", Class11.RENDER, "");
        this.addSetting(this.mode);
        this.addSetting(this.effects);
    }

    public void Method1819() {
        super.getEnable();
        this.Field1417 = Globals.mc.gameSettings.gammaSetting;
    }

    public void Method1820() {
        super.getDisable();
        if (this.mode.getValue() == Class131.Potion) {
            Globals.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
        Globals.mc.gameSettings.gammaSetting = this.Field1417;
    }

    public String Method1821() {
        return ChatFormatting.WHITE + ((Class131)((Object)this.mode.getValue())).name();
    }

    private void Method1817(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getEra()== Class53.PRE && eventNetworkPrePacketEvent.getPacket() instanceof SPacketEntityEffect && ((Boolean)this.effects.getValue()).booleanValue()) {
            SPacketEntityEffect sPacketEntityEffect = (SPacketEntityEffect)eventNetworkPrePacketEvent.getPacket();
            if (Globals.mc.player != null && sPacketEntityEffect.getEntityId() == Globals.mc.player.getEntityId() && (sPacketEntityEffect.getEffectId() == 9 || sPacketEntityEffect.getEffectId() == 15)) {
                eventNetworkPrePacketEvent.cancel();
            }
        }
    }

    private void Method1816(Class26 class26) {
        if (this.mode.getValue() == Class131.Gamma) {
            Globals.mc.gameSettings.gammaSetting = 1000.0f;
        }
        if (this.mode.getValue() == Class131.Potion) {
            Globals.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }
}
