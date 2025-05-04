package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.render.EventRenderSkyColor;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor
extends Module {
    Setting skycolor = new Setting("SkyColor", "", this, new Color(70, 2, 121, 255));
    Setting nethercolor = new Setting("NetherColor", "", this, new Color(255, 2, 121, 255));
    Setting endcolor = new Setting("EndColor", "", this, new Color(121, 121, 121, 255));
    Setting fog = new Setting("Fog", "", this, false);
    @EventHandler
    private Listener Field429 = new Listener<EventRenderSkyColor>(this::Method506, new Predicate[0]);

    public SkyColor() {
        super("SkyColor", "Change the color of the sky.", Class11.RENDER, "");
        this.addSetting(this.skycolor);
        this.addSetting(this.nethercolor);
        this.addSetting(this.endcolor);
        this.addSetting(this.fog);
    }

    @SubscribeEvent
    public void Method508(EntityViewRenderEvent.FogColors fogColors) {
        if (!((Boolean)this.fog.getValue()).booleanValue()) {
            Color color;
            switch (Globals.mc.player.dimension) {
                case -1: {
                    color = (Color)this.nethercolor.getValue();
                    break;
                }
                case 1: {
                    color = (Color)this.endcolor.getValue();
                    break;
                }
                default: {
                    color = (Color)this.skycolor.getValue();
                }
            }
            fogColors.setRed((float)color.getRed() / 255.0f);
            fogColors.setGreen((float)color.getGreen() / 255.0f);
            fogColors.setBlue((float)color.getBlue() / 255.0f);
        }
    }

    @SubscribeEvent
    public void Method509(EntityViewRenderEvent.FogDensity fogDensity) {
        if (!((Boolean)this.fog.getValue()).booleanValue()) {
            fogDensity.setDensity(0.0f);
            fogDensity.setCanceled(true);
        }
    }

    public void Method510() {
        super.getEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public void Method511() {
        super.getDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    private void Method506(EventRenderSkyColor eventRenderSkyColor) {
        if (Globals.mc.world == null) {
            return;
        }
        eventRenderSkyColor.cancel();
        if (Globals.mc.player.dimension == -1) {
            eventRenderSkyColor.Method512(new Vec3d((double)((float)((Color)this.nethercolor.getValue()).getRed() / 255.0f), (double)((float)((Color)this.nethercolor.getValue()).getGreen() / 255.0f), (double)((float)((Color)this.nethercolor.getValue()).getBlue() / 255.0f)));
        }
        if (Globals.mc.player.dimension == 0) {
            eventRenderSkyColor.Method512(new Vec3d((double)((float)((Color)this.skycolor.getValue()).getRed() / 255.0f), (double)((float)((Color)this.skycolor.getValue()).getGreen() / 255.0f), (double)((float)((Color)this.skycolor.getValue()).getBlue() / 255.0f)));
        }
        if (Globals.mc.player.dimension == 1) {
            eventRenderSkyColor.Method512(new Vec3d((double)((float)((Color)this.endcolor.getValue()).getRed() / 255.0f), (double)((float)((Color)this.endcolor.getValue()).getGreen() / 255.0f), (double)((float)((Color)this.endcolor.getValue()).getBlue() / 255.0f)));
        }
    }
}
