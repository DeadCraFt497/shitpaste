package me.ciruu.abyss.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.Pair;
import me.ciruu.abyss.*;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class184;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WebChat
extends Module {
    private final Setting openchat = new Setting("OpenChat", "", this, new Class207(0));
    private final Setting sound = new Setting("Sound", "", this, true);
    private final Setting color = new Setting("Color", "", this, (Object)Class184.WHITE);
    private Set set1;
    private Set set2;
    @EventHandler
    private Listener listen1;
    @EventHandler
    private Listener listen2;

    public WebChat() {
        super("WebChat", "", Class11.CLIENT, "");
        this.getWebChat();
    }

    public void getWebChat() {
        WebChat webChat = this;
        Object object = Class636.Method3494();
        object = Class636.Method3494();
        this.addSetting(this.openchat);
        this.addSetting(this.sound);
        this.addSetting(this.color);
    }

    @NotNull
    public final Map getMappedWebChat() {
        Iterable iterable = this.set2;
        boolean bl = false;
        Iterable iterable2 = iterable;
        Collection collection = new ArrayList(Class636.Method3471(iterable, 10));
        boolean bl2 = false;
        Iterator iterator = Class636.Method3472(iterable2);
        while (Class636.Method3473(iterator)) {
            Object object = Class636.Method3474(iterator);
            String string = (String)object;
            Collection collection2 = collection;
            boolean bl3 = false;
            Pair pair = Class636.Method3476(string, Class636.Method3475(this.set1, string));
            Class636.Method3477(collection2, pair);
        }
        return Class636.Method3478((List)collection);
    }

    public void getBlockedWebMethod() {
        block2: {
            super.getEnable();
            try {
                Class40.Field96.Method3734();
                Class547.printNewChatMessage(Class636.Method3480(Class636.Method3479(Class636.Method3480(Class636.Method3479(Class636.Method3482(), ChatFormatting.GOLD), null), ChatFormatting.GREEN), " Connected").toString());
            }
            catch (Throwable throwable) {
                Class636.Method3481(throwable);
            }
        }
    }

    public void getSuperWebChatFN() {
        super.getDisable();
        Class40.Field96.Method3735();
        Class636.Method3483(this.set2);
        Class636.Method3483(this.set1);
    }

    public final void getListWebChatMembers(@NotNull List list, @NotNull List list2) {
        Class636.Method3483(this.set1);
        Class636.Method3484(this.set1, list);
        Class636.Method3483(this.set2);
        Class636.Method3484(this.set2, list2);
    }

    public final void getListSet1(@NotNull String string) {
        Class636.Method3485(this.set1, string);
    }

    public final void getListSet2(@NotNull String string) {
        Class636.Method3486(this.set1, string);
    }

    public final void getWebChatFunc(@NotNull String string, @NotNull String string2, @Nullable Instant instant) {
        String string3 = Class636.Method3480(Class636.Method3480(Class636.Method3487(Class636.Method3480(Class636.Method3487(Class636.Method3479(Class636.Method3487(Class636.Method3480(Class636.Method3487(Class636.Method3479(Class636.Method3482(), ChatFormatting.GOLD), '['), null), ']'), ChatFormatting.DARK_AQUA), ' '), string), ' '), Class185.Method502((Class184)((Object)this.color.getValue()))), string2).toString();
        GuiIngame guiIngame = Globals.mc.ingameGUI;
        if (Class636.Method3490((Boolean)this.sound.getValue()) && Class636.Method3492(string, Class636.Method3491(Globals.mc.player)) ^ true) {
            Class636.Method3493(Globals.mc.world, (EntityPlayer)Globals.mc.player, Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, Class470.Field3256, SoundCategory.PLAYERS, 10.0f, 1.0f);
        }
    }

    public final void getMessage() {
        Class547.printNewChatMessage(Class636.Method3480(Class636.Method3479(Class636.Method3480(Class636.Method3479(Class636.Method3482(), ChatFormatting.GOLD), null), ChatFormatting.RED), " Disconnected").toString());
    }

    public static final Setting getSetting(WebChat webChat) {
        return webChat.openchat;
    }
}
