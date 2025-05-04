package me.ciruu.abyss.modules.hud;

import java.util.function.Predicate;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

public class Notifications
extends Module {
    public final Setting scale = new Setting("Scale", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(3.0f));
    private final Setting timeout = new Setting("Timeout", "", (Module)this, (Object)Float.valueOf(3.0f), Float.valueOf(0.5f), Float.valueOf(10.0f));
    private final Setting size = new Setting("Size", "", (Module)this, (Object)4, 1, 6);
    private final Setting step = new Setting("Step", "", (Module)this, (Object)10, 1, 50);
    private final Setting speed = new Setting("Speed", "", (Module)this, (Object)Float.valueOf(3.0f), Float.valueOf(0.5f), Float.valueOf(10.0f));
    public final Setting toggleFeature = new Setting("ToggleFeature", "", this, false);
    public final Setting friends = new Setting("Friends", "", this, true);
    public final Setting alias = new Setting("Alias", "", this, true);
    public final Setting profile = new Setting("Profile", "", this, true);
    public final Setting image = new Setting("Image", "", this, true);
    @EventHandler
    private Listener Field1618 = new Listener<Class35>(this::Method2004, -1000, new Predicate[0]);

    public Notifications() {
        super("Notifications", "", Class11.HUD, "");
        this.addSetting(this.scale);
        this.addSetting(this.timeout);
        this.addSetting(this.size);
        this.addSetting(this.step);
        this.addSetting(this.speed);
        this.addSetting(this.toggleFeature);
        this.addSetting(this.friends);
        this.addSetting(this.alias);
        this.addSetting(this.profile);
        this.addSetting(this.image);
    }

    private void Method2004(Class35 class35) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue(), (float)((Float)this.scale.getValue()).floatValue());
        Manager.Field424.Method344(((float)class35.Method2006() - 160.0f * ((Float)this.scale.getValue()).floatValue()) / ((Float)this.scale.getValue()).floatValue(), ((float)class35.Method1967() - 100.0f * ((Float)this.scale.getValue()).floatValue()) / ((Float)this.scale.getValue()).floatValue(), (Integer)this.size.getValue(), ((Float)this.timeout.getValue()).floatValue(), (Integer)this.step.getValue(), ((Float)this.speed.getValue()).floatValue());
        GlStateManager.popMatrix();
    }
}
