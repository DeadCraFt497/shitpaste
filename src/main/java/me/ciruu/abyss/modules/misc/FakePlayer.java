package me.ciruu.abyss.modules.misc;

import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.UUID;

import me.ciruu.abyss.Class547;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FakePlayer
extends Module {
    public final Setting name = new Setting("Name", "Name of the fake player", this, "jared2013");
    private EntityOtherPlayerMP Field768;

    public FakePlayer() {
        super("FakePlayer", "Summon a client-side fake player to test modules.", Class11.MISC, "");
        this.addSetting(this.name);
    }

    public String Method1017() {
        return ChatFormatting.WHITE + (String)this.name.getValue();
    }

    public void Method1018() {
        super.getEnable();
        this.Field768 = null;
        if (Globals.mc.world == null) {
            FakePlayer.module.disable();
            System.out.println("Abyss: abyssclient cannot enable this function because it apply's to being in a world.");
            FakePlayer.module.disable();
            return;
        }
        this.Field768 = new EntityOtherPlayerMP((World)Globals.mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), (String)this.name.getValue()));
        this.Field768.inventory.copyInventory(Globals.mc.player.inventory);
        this.Field768.setHealth(Globals.mc.player.getHealth() + Globals.mc.player.getAbsorptionAmount());
        Class547.printChatMessage(String.format("%s has been spawned.", this.name.getValue()));
        for (PotionEffect potionEffect : Globals.mc.player.getActivePotionEffects()) {
            this.Field768.addPotionEffect(potionEffect);
        }
        this.Field768.copyLocationAndAnglesFrom((Entity)Globals.mc.player);
        this.Field768.rotationYawHead = Globals.mc.player.rotationYawHead;
        Globals.mc.world.addEntityToWorld(-100, (Entity)this.Field768);
    }

    public void Method1020() {
        super.getDisable();
        if (this.Field768 != null) {
            Globals.mc.world.removeEntity((Entity)this.Field768);
        }
    }

    public void Method1021(boolean bl) {
        if (isEnabled()) {
            bl = false;
        }
    }
}
