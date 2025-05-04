package me.ciruu.abyss.modules.client;

import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import net.minecraft.client.gui.GuiScreen;

public class BubbleGUI
extends Module {
    public final Setting animation = new Setting("Animation", "", this, true);

    public BubbleGUI() {
        super("BubbleGUI", "Bubble GUI (Not SAO)", Class11.CLIENT, "");
        this.addSetting(this.animation);
    }


    public void getScreen() {
        super.getEnable();
        Globals.mc.displayGuiScreen((GuiScreen)Manager.Field280);
    }

    public void getScreenSuper() {
        super.getDisable();
        Globals.mc.displayGuiScreen((GuiScreen)Manager.Field280);
    }

    public void getBoolean(boolean b) {

    }
}
