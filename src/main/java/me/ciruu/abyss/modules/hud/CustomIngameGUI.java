package me.ciruu.abyss.modules.hud;

import java.awt.Color;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CustomIngameGUI
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting hotbar = new Setting("Hotbar", "", this, true);
    public final Setting expBar = new Setting("ExpBar", "", this, true);
    public final Setting Field103 = new Setting("Stats", "", this, true);
    private final Setting mainHotbar = new Setting("Main", "", this, new Class25("Hotbar Settings"));
    private final Setting hStatic = new Setting("HStatic", "", (Module)this, (Object)true, this.mainHotbar, CustomIngameGUI::Method3822);
    private final Setting hX = new Setting("HX", "X", this, 10, 0, 1920, this.mainHotbar, this::Method3823);
    private final Setting hY = new Setting("HY", "Y", this, 10, 0, 1080, this.mainHotbar, this::Method3824);
    private final Setting hScale = new Setting("HScale", "", this, Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(5.0f), this.mainHotbar, CustomIngameGUI::Method3825);
    private final Setting hBackground = new Setting("HBackground", "", (Module)this, (Object)true, this.mainHotbar, CustomIngameGUI::Method3826);
    private final Setting hBgColor = new Setting("HBgColor", "", (Module)this, (Object)new Color(0, 0, 0, 100), this.mainHotbar, this.hBackground::getValue);
    private final Setting radious = new Setting("Radious", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(60.0f), this.mainHotbar, CustomIngameGUI::Method3827);

    public CustomIngameGUI() {
        super("CustomIngameGUI", "", Class11.HUD, "");
        this.addSetting(this.hotbar);
        this.addSetting(this.expBar);
        this.addSetting(this.Field103);
        this.addSetting(this.mainHotbar);
        this.addSetting(this.hStatic);
        this.addSetting(this.hX);
        this.addSetting(this.hY);
        this.addSetting(this.hScale);
        this.addSetting(this.hBackground);
        this.addSetting(this.hBgColor);
        this.addSetting(this.radious);
    }

    public void Method73(ScaledResolution scaledResolution, float f) {
        if (Globals.mc.getRenderViewEntity() instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)Globals.mc.getRenderViewEntity();
            ItemStack itemStack = entityPlayer.getHeldItemOffhand();
            GlStateManager.pushMatrix();
            GlStateManager.scale((float)((Float)this.hScale.getValue()).floatValue(), (float)((Float)this.hScale.getValue()).floatValue(), (float)((Float)this.hScale.getValue()).floatValue());
            int n = 16;
            float f2 = (float)scaledResolution.getScaledWidth() / ((Float)this.hScale.getValue()).floatValue();
            float f3 = (float)scaledResolution.getScaledHeight() / ((Float)this.hScale.getValue()).floatValue();
            float f4 = ((Boolean)this.hStatic.getValue() != false ? (float)scaledResolution.getScaledWidth() - 16.0f * ((Float)this.hScale.getValue()).floatValue() - 3.0f : (float)((Integer)this.hX.getValue()).intValue()) / ((Float)this.hScale.getValue()).floatValue();
            float f5 = ((Boolean)this.hStatic.getValue() != false ? (float)scaledResolution.getScaledHeight() / 2.0f - 72.0f * ((Float)this.hScale.getValue()).floatValue() : (float)((Integer)this.hY.getValue()).intValue()) / ((Float)this.hScale.getValue()).floatValue();
            if (((Boolean)this.hBackground.getValue()).booleanValue()) {
                Class50.Method92(f4, f5, f4 + 16.0f + 1.0f, f5 + 144.0f + 1.0f, this.Method3829().getRGB());
            }
            int n2 = entityPlayer.inventory.currentItem;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();
            int n3 = 0;
            for (int i = 0; i < 9; ++i) {
                this.Method3830((int)f4, (int)f5 + n3, f, entityPlayer, (ItemStack)entityPlayer.inventory.mainInventory.get(i));
                n3 += 16;
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            Class50.Method864((int)f4, (int)f5 + n2 * 16, 17, 17, this.Method3831(0), this.Method3831(12000));
            GlStateManager.popMatrix();
        }
    }

    protected void Method3830(int n, int n2, float f, EntityPlayer entityPlayer, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            float f2 = (float)itemStack.getAnimationsToGo() - f;
            if (f2 > 0.0f) {
                GlStateManager.pushMatrix();
                float f3 = 1.0f + f2 / 5.0f;
                GlStateManager.translate((float)(n + 8), (float)(n2 + 12), (float)0.0f);
                GlStateManager.scale((float)(1.0f / f3), (float)((f3 + 1.0f) / 2.0f), (float)1.0f);
                GlStateManager.translate((float)(-(n + 8)), (float)(-(n2 + 12)), (float)0.0f);
            }
            Globals.mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)entityPlayer, itemStack, n, n2);
            if (f2 > 0.0f) {
                GlStateManager.popMatrix();
            }
            Globals.mc.getRenderItem().renderItemOverlays(Globals.mc.fontRenderer, itemStack, n, n2);
        }
    }

    public int Method3831(int n) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / 20.0);
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), 1.0f, 1.0f).getRGB();
    }

    private Color Method3829() {
        return (Color)this.hBgColor.getValue();
    }

    public void Method74(ScaledResolution scaledResolution, int n) {
    }

    public void Method75(ScaledResolution scaledResolution) {
    }

    private static boolean Method3827() {
        return true;
    }

    private static boolean Method3826() {
        return true;
    }

    private static boolean Method3825() {
        return true;
    }

    private boolean Method3824() {
        return (Boolean)this.hStatic.getValue() == false;
    }

    private boolean Method3823() {
        return (Boolean)this.hStatic.getValue() == false;
    }

    private static boolean Method3822() {
        return true;
    }
}
