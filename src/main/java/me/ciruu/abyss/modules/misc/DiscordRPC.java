package me.ciruu.abyss.modules.misc;

import java.awt.SystemTray;
import java.util.function.Predicate;
import me.ciruu.abyss.Class156;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class434;
import me.ciruu.abyss.enums.Class80;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;

public class DiscordRPC
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    public Setting mainWindow = new Setting("Details", "", this, (Object)Class434.Blue);
    public Setting details = new Setting("Details", "", this, (Object)Class80.Server);
    public Setting showIP = new Setting("ShowIP", "", (Module)this, (Object)false, this.main, this::Method1836);
    public Setting detail = new Setting("Detail", "", (Module)this, (Object)"abyss.win", this.main, this::Method1837);
    public Setting state = new Setting("State", "", this, "https://discord.gg/XY8emYJfJn");
    public Setting queue = new Setting("Queue", "", this, true);
    public Setting notification = new Setting("Notification", "", (Module)this, (Object)true, this.main, this.queue::getValue);
    public Setting position = new Setting("Position", "", this, 20, 1, 100, this.main, this::Method1838);
    public Setting onlyOneNotif = new Setting("OnlyOneNotif", "", (Module)this, (Object)true, this.main, this::Method1839);
    public static int Field1448 = -1;
    private boolean Field1449 = false;
    private Class156 Field1450;
    @EventHandler
    private Listener Field1451 = new Listener<EventNetworkPrePacketEvent>(this::Method1840, new Predicate[0]);

    public DiscordRPC() {
        super("DiscordRPC", "Displays a custom discord rich presence.", Class11.MISC, "");
        this.addSetting(this.mainWindow);
        this.addSetting(this.details);
        this.addSetting(this.showIP);
        this.addSetting(this.detail);
        this.addSetting(this.state);
        this.addSetting(this.queue);
        this.addSetting(this.notification);
        this.addSetting(this.position);
        this.addSetting(this.onlyOneNotif);
        try {
            this.Field1450 = new Class156();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void Method1842() {
        super.getEnable();
        me.ciruu.abyss.DiscordRPC.start();
    }

    public void Method1844() {
        super.getDisable();
        me.ciruu.abyss.DiscordRPC.shutdown();
        Field1448 = -1;
        this.Field1449 = false;
    }

    private void Method1840(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketChat && ((Boolean)this.queue.getValue()).booleanValue()) {
            SPacketChat sPacketChat = (SPacketChat)eventNetworkPrePacketEvent.getPacket();
            if (sPacketChat.getChatComponent().getUnformattedText().contains("Position in queue:")) {
                try {
                    Field1448 = Integer.parseInt(((ITextComponent)sPacketChat.getChatComponent().getSiblings().get(1)).getUnformattedText());
                }
                catch (Exception exception) {
                    Field1448 = -1;
                }
            }
            if (sPacketChat.getChatComponent().getUnformattedText().contains("Connecting") || Globals.mc.world == null) {
                Field1448 = -1;
                this.Field1449 = false;
            }
            if (((Boolean)this.notification.getValue()).booleanValue() && Field1448 >= 0 && Field1448 <= (Integer)this.position.getValue() && this.Field1450 != null) {
                if (SystemTray.isSupported()) {
                    if (this.Field1449 && ((Boolean)this.onlyOneNotif.getValue()).booleanValue()) {
                        return;
                    }
                    this.Field1450.Method440(Field1448);
                    if (((Boolean)this.onlyOneNotif.getValue()).booleanValue()) {
                        this.Field1449 = true;
                    }
                } else {
                    System.err.println("System tray not supported!");
                }
            }
        }
    }

    private boolean Method1839() {
        return (Boolean)this.queue.getValue() != false && (Boolean)this.notification.getValue() != false;
    }

    private boolean Method1838() {
        return (Boolean)this.queue.getValue() != false && (Boolean)this.notification.getValue() != false;
    }

    private boolean Method1837() {
        return this.details.getValue() == Class80.CustomServer;
    }

    private boolean Method1836() {
        return this.details.getValue() == Class80.Server;
    }
}
