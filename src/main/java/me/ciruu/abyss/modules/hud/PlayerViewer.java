package me.ciruu.abyss.modules.hud;

import java.util.function.Predicate;
import me.ciruu.abyss.Class35;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;

public class PlayerViewer
extends Module {
    private final Setting x = new Setting("X", "X", (Module)this, (Object)10, 0, 1920);
    private final Setting y = new Setting("Y", "Y", (Module)this, (Object)10, 0, 1080);
    private final Setting scale = new Setting("Scale", "", (Module)this, (Object)20, 0, 100);
    @EventHandler
    private Listener Field1510 = new Listener<Class35>(this::Method1924, new Predicate[0]);

    public PlayerViewer() {
        super("PlayerViewer", "", Class11.HUD, "");
        this.addSetting(this.x);
        this.addSetting(this.y);
        this.addSetting(this.scale);
    }

    private void Method1924(Class35 class35) {
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
        GuiInventory.drawEntityOnScreen((int)((Integer)this.x.getValue()), (int)((Integer)this.y.getValue()), (int)((Integer)this.scale.getValue()), (float)0.0f, (float)(-Globals.mc.player.rotationPitch), (EntityLivingBase)Globals.mc.player);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.popMatrix();
    }
}
