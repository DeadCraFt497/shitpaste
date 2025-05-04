package me.ciruu.abyss.modules.render;

import me.ciruu.abyss.Class185;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class184;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

public class TabFriends
extends Module {
    private final Setting color = new Setting("Color", "", this, (Object)Class184.LIGHT_PURPLE);
    private final Setting selfcolor = new Setting("SelfColor", "", this, (Object)Class184.BLACK);
    public final Setting extratab = new Setting("ExtraTab", "", this, true);

    public TabFriends() {
        super("TabFriends", "Display friends in tab with other color.", Class11.RENDER, "");
        this.addSetting(this.color);
        this.addSetting(this.selfcolor);
        this.addSetting(this.extratab);
    }

    public String Method1596(NetworkPlayerInfo networkPlayerInfo) {
        String string;
        String string2 = string = networkPlayerInfo.getDisplayName() != null ? networkPlayerInfo.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfo.getPlayerTeam(), (String)networkPlayerInfo.getGameProfile().getName());
        return Globals.mc.player.getName().equals(string) ? Class185.Method502((Class184)((Object)this.selfcolor.getValue())) + string : (Manager.Field223.Method233(string) ? Class185.Method502((Class184)((Object)this.color.getValue())) + string : string);
    }
}
