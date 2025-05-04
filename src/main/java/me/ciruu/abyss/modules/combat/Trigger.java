package me.ciruu.abyss.modules.combat;

import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class504;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

public class Trigger
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting hitdelay = new Setting("Hit Delay", "Use vanilla hit delay", this, true);
    public final Setting tpssync = new Setting("TpsSync", "Tps syncs the HitDelay", this, false);
    public final Setting legit = new Setting("Legit", "", this, false);
    public final Setting onlysword = new Setting("OnlySword", "", this, false);
    public final Setting onlyplayers = new Setting("OnlyPlayers", "", this, true);
    public final Setting sswitch = new Setting("Switch", "", this, false);
    private final Setting switchmode = new Setting("SwitchMode", "", (Module)this, (Object)Class504.None, this.mainwindow, this.sswitch::getValue);
    private final Setting switchback = new Setting("SwitchBack", "", (Module)this, (Object)true, this.mainwindow, this::Method2222);
    private int Field1843 = -1;
    @EventHandler
    private Listener Field1844 = new Listener<EventPlayerUpdate>(this::Method2223, new Predicate[0]);

    public Trigger() {
        super("Trigger", "Attacks the entity in the crosshair.", Class11.COMBAT, "");
        this.addSetting(this.hitdelay);
        this.addSetting(this.tpssync);
        this.addSetting(this.legit);
        this.addSetting(this.onlysword);
        this.addSetting(this.onlyplayers);
        this.addSetting(this.sswitch);
        this.addSetting(this.switchmode);
        this.addSetting(this.switchback);
    }

    public void Method2225() {
        super.getEnable();
        if (((Boolean)this.sswitch.getValue()).booleanValue() && this.switchmode.getValue() == Class504.Auto && !this.Method2226()) {
            this.Field1843 = Globals.mc.player.inventory.currentItem;
            int n = this.Method2227(Items.DIAMOND_SWORD);
            int n2 = this.Method2227(Items.DIAMOND_AXE);
            if (n != -1) {
                Globals.mc.player.inventory.currentItem = n;
                Globals.mc.playerController.updateController();
                return;
            }
            if (n2 != -1) {
                Globals.mc.player.inventory.currentItem = n2;
                Globals.mc.playerController.updateController();
            }
        }
    }

    private int Method2227(Item item) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Globals.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || itemStack.getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public void Method2228() {
        super.getDisable();
        if (this.switchmode.getValue() == Class504.Auto && ((Boolean)this.switchback.getValue()).booleanValue() && ((Boolean)this.sswitch.getValue()).booleanValue() && this.Field1843 != -1) {
            Globals.mc.player.inventory.currentItem = this.Field1843;
            Globals.mc.playerController.updateController();
        }
    }

    private boolean Method2226() {
        return Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean Method2229() {
        float f = 20.0f - Manager.Field1645.Method2230();
        if ((Boolean)this.hitdelay.getValue() == false) return true;
        EntityPlayerSP entityPlayerSP = Globals.mc.player;
        float f2 = (Boolean)this.tpssync.getValue() != false ? -f : 0.0f;
        if (!(entityPlayerSP.getCooledAttackStrength(f2) >= 1.0f)) return false;
        return true;
    }

    private void Method2223(EventPlayerUpdate eventPlayerUpdate) {
        if (Manager.moduleManager.isModuleEnabled(KillAura.class)) {
            return;
        }
        if (((Boolean)this.onlysword.getValue()).booleanValue() && !this.Method2226()) {
            return;
        }
        if (Globals.mc.objectMouseOver != null && Globals.mc.objectMouseOver.entityHit != null) {
            Entity entity = Globals.mc.objectMouseOver.entityHit;
            if (!(entity instanceof EntityLivingBase) || Manager.Field223.Method711(entity) || entity.isInvisible()) {
                return;
            }
            if (((Boolean)this.onlyplayers.getValue()).booleanValue() && !(entity instanceof EntityPlayer)) {
                return;
            }
            if (this.Method2229()) {
                if (((Boolean)this.legit.getValue()).booleanValue()) {
                    // Assuming 'mc' is the Minecraft instance and 'playerController' is the PlayerControllerMP instance
                    if (Globals.mc.playerController != null) {
                        // Simulate a mouse click (left click in this case)
                        Globals.mc.playerController.processRightClick(Globals.mc.player, Globals.mc.world, EnumHand.MAIN_HAND);
                    }

                } else {
                    Globals.mc.playerController.attackEntity((EntityPlayer)Globals.mc.player, entity);
                    Globals.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }

    private boolean Method2222() {
        return this.switchmode.getValue() == Class504.Auto && (Boolean)this.sswitch.getValue() != false;
    }
}
