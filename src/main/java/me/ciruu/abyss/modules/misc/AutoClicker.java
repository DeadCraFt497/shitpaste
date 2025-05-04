package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class26;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class366;
import me.ciruu.abyss.enums.Class367;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.hause.alienware.AlienKeyAgent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class AutoClicker
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class367.Packet);
    private final Setting click = new Setting("Click", "", this, (Object)Class366.Left);
    private final Setting delay = new Setting("Delay", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f));
    private final Setting clicksPerTick = new Setting("ClicksPerTick", "", (Module)this, (Object)1, 1, 20);
    private final Class22 Field1218 = new Class22();
    @EventHandler
    private Listener Field1219 = new Listener<Class26>(this::Method1567, new Predicate[0]);

    public AutoClicker() {
        super("AutoClicker", "", Class11.MISC, "");
        this.addSetting(this.mode);
        this.addSetting(this.click);
        this.addSetting(this.delay);
        this.addSetting(this.clicksPerTick);
    }

    private void Method1569(boolean bl, boolean bl2, boolean bl3) {
        AlienKeyAgent __agent = new AlienKeyAgent();
        if (bl) {
            if (bl3) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            } else {
                __agent.bothClick();
            }
        }
        if (bl2) {
            if (bl3) {
                Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            } else {
                __agent.rightClick();
            }
        }
    }

    private void Method1567(Class26 class26) {
        if (this.Field1218.getLongNanoTime(((Float)this.delay.getValue()).longValue() * 1000L)) {
            block5: for (int i = 0; i < (Integer)this.clicksPerTick.getValue(); ++i) {
                switch ((Class366)((Object)this.click.getValue())) {
                    case Left: {
                        this.Method1569(true, false, this.mode.getValue() == Class367.Packet);
                        continue block5;
                    }
                    case Right: {
                        this.Method1569(false, true, this.mode.getValue() == Class367.Packet);
                        continue block5;
                    }
                    case Both: {
                        this.Method1569(true, true, this.mode.getValue() == Class367.Packet);
                    }
                }
            }
            this.Field1218.getNanoTime();
        }
    }
}
