package me.ciruu.abyss;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import me.ciruu.abyss.enums.Class434;
import me.ciruu.abyss.enums.Class80;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class DiscordRPC {
    public static DiscordRichPresence presence;
    private static final club.minnced.discord.rpc.DiscordRPC rpc;
    private static Thread thread;

    public static void start() {
        DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
        discordEventHandlers.ready = DiscordRPC::onDiscordReady;
        rpc.Discord_Initialize("777901560125980713", discordEventHandlers, true, "");
        DiscordRPC.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordRPC.presence.details = ((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).details.getValue() == Class80.CustomServer ? (String)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).detail.getValue() : (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "In the main menu." : "Playing" + (Minecraft.getMinecraft().currentServerData != null ? (((Boolean)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).showIP.getValue()).booleanValue() ? "on" + Minecraft.getMinecraft().currentServerData.serverIP + "." : " in multiplayer.") : " in singleplayer."));
        DiscordRPC.presence.state = (Boolean)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).queue.getValue() != false && me.ciruu.abyss.modules.misc.DiscordRPC.Field1448 != -1 ? "Queue:" + me.ciruu.abyss.modules.misc.DiscordRPC.Field1448 : (String)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).state.getValue();
        DiscordRPC.presence.largeImageKey = ((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).mainWindow.getValue() == Class434.Blue ? "abyss_blue" : "abyss_purple";
        DiscordRPC.presence.largeImageText = "Abyss 1.6.0";
        rpc.Discord_UpdatePresence(presence);
        thread = new Thread(DiscordRPC::update, "RPC-Callback-Handler");
        thread.start();
    }

    public static void shutdown() {
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        rpc.Discord_Shutdown();
    }

    private static void update() {
        while (!Thread.currentThread().isInterrupted()) {
            rpc.Discord_RunCallbacks();
            DiscordRPC.presence.details = ((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).details.getValue() == Class80.CustomServer ? (String)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).detail.getValue() : "Playing" + (Minecraft.getMinecraft().currentServerData != null ? (((Boolean)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).showIP.getValue()).booleanValue() ? "on" + Minecraft.getMinecraft().currentServerData.serverIP + "." : " in multiplayer.") : " in singleplayer.");
            DiscordRPC.presence.state = (Boolean)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).queue.getValue() != false && me.ciruu.abyss.modules.misc.DiscordRPC.Field1448 != -1 ? "Queue:" + me.ciruu.abyss.modules.misc.DiscordRPC.Field1448 : (String)((me.ciruu.abyss.modules.misc.DiscordRPC)Manager.moduleManager.getModuleByClass(me.ciruu.abyss.modules.misc.DiscordRPC.class)).state.getValue();
            rpc.Discord_UpdatePresence(presence);
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private static void onDiscordReady(DiscordUser discordUser) {
        System.out.println("Ready!");
    }

    static {
        rpc = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        presence = new DiscordRichPresence();
    }
}
