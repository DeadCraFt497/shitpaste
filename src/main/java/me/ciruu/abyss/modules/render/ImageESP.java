package me.ciruu.abyss.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.imageio.ImageIO;

import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ImageESP
extends Module {
    private final Setting renderplayer = new Setting("RenderPlayer", "", this, false);
    private final Setting franco = new Setting("Franco", "", this, false);
    public static ResourceLocation Field420;
    public static ResourceLocation Field1820;
    private final ICamera Field1821 = new Frustum();
    @EventHandler
    private Listener Field1822 = new Listener<Class139>(this::Method2211, new Predicate[0]);
    @EventHandler
    private Listener Field1823 = new Listener<Class35>(this::Method2212, new Predicate[0]);
    @EventHandler
    private Listener Field1824 = new Listener<RenderPlayerEvent.Pre>(this::Method2213, new Predicate[0]);

    public ImageESP() {
        super("ImageESP", "Render images over players.", Class11.RENDER, "");
        this.addSetting(this.renderplayer);
        if (Manager.beta) {
            this.addSetting(this.franco);
        }
    }

    public void Method2215() {
        super.getEnable();
        if (((Boolean)this.franco.getValue()).booleanValue() && Manager.beta) {
            this.Method2216();
        }
        if (Field420 == null) {
            Class547.printChatMessage(ChatFormatting.RED + "Image not loaded!");
            this.module.disable();
        }
    }

    public void Method2218() {
        super.getDisable();
        Globals.mc.getSoundHandler().stopSounds();
    }

    private void Method2216() {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(Globals.mc.getResourceManager().getResource(Class234.Field1809).getInputStream());
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (bufferedImage != null) {
            DynamicTexture dynamicTexture = new DynamicTexture(bufferedImage);
            try {
                dynamicTexture.loadTexture(Globals.mc.getResourceManager());
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            Field1820 = Field420;
            Field420 = Globals.mc.getTextureManager().getDynamicTextureLocation("ABYSS_FRANCO", dynamicTexture);
        }
        Globals.mc.getSoundHandler().stopSounds();
        Globals.mc.world.playSound((EntityPlayer)Globals.mc.player, Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, Class470.Field1825, SoundCategory.PLAYERS, 3.0f, 1.0f);
    }

    private void Method2213(RenderPlayerEvent.Pre pre) {
        if (!((Boolean)this.renderplayer.getValue()).booleanValue() && !pre.getEntity().equals((Object)Globals.mc.player)) {
            pre.setCanceled(true);
        }
    }

    private void Method2212(Class35 class35) {
        if (Field420 == null || Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }

        // Interpolated player position
        double interpX = Globals.mc.player.lastTickPosX + (Globals.mc.player.posX - Globals.mc.player.lastTickPosX) * class35.getPartialTicks();
        double interpY = Globals.mc.player.lastTickPosY + (Globals.mc.player.posY - Globals.mc.player.lastTickPosY) * class35.getPartialTicks();
        double interpZ = Globals.mc.player.lastTickPosZ + (Globals.mc.player.posZ - Globals.mc.player.lastTickPosZ) * class35.getPartialTicks();

        // Update frustum position
        this.Field1821.setPosition(interpX, interpY, interpZ);

        // Sort players by distance to the camera (furthest first)
        ArrayList<EntityPlayer> players = new ArrayList<>(Globals.mc.world.playerEntities);
        players.sort(Comparator.comparingDouble(ImageESP::Method2220).reversed());

        for (EntityPlayer entityPlayer : players) {
            if (entityPlayer == Globals.mc.getRenderViewEntity() || !entityPlayer.isEntityAlive()) continue;

            // Only render players inside view frustum
            if (!this.Field1821.isBoundingBoxInFrustum(entityPlayer.getEntityBoundingBox())) continue;

            // Get interpolated position for entity
            Vec3d base = Class354.Method2221(entityPlayer, class35.getPartialTicks());
            Vec3d top = base.add(0.0, entityPlayer.getRenderBoundingBox().maxY - entityPlayer.posY, 0.0);

            Class502 screenTop = Class47.Method2205(top.x, top.y, top.z);
            Class502 screenBase = Class47.Method2205(base.x, base.y, base.z);

            // Skip rendering if both are off-screen
            if (!screenTop.Field1826 && !screenBase.Field1826) continue;

            // Compute width and height of the ESP box
            int height = screenBase.Field1827 - screenTop.Field1827;
            int drawX = (int) (screenTop.Field1828 - height / 1.8);
            int drawY = screenTop.Field1827;

            // Bind ESP texture
            Globals.mc.renderEngine.bindTexture(Field420);

            // Set draw color to white
            GlStateManager.color(1.0f, 1.0f, 1.0f);

            // Draw scaled image over entity
            Gui.drawScaledCustomSizeModalRect(
                    drawX, drawY,            // Position
                    0.0f, 0.0f,              // Texture U/V
                    height, height,          // Texture size
                    height, height,          // Draw size
                    (float) height, (float) height // Scale
            );
        }
    }

    private static Float Method2220(Object object) {
        return Float.valueOf(Globals.mc.player.getDistance((Entity)((EntityPlayer)object)));
    }

    private void Method2211(Class139 class139) {
        if (class139.Method1564() == this) {
            if (((Boolean)((ImageESP)class139.Method1564()).franco.getValue()).booleanValue() && Manager.beta) {
                this.Method2216();
            } else {
                if (Field1820 != null) {
                    Field420 = Field1820;
                }
                Globals.mc.getSoundHandler().stopSounds();
            }
        }
    }
}
