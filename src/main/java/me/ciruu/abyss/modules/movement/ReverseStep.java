package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class123;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.exploit.PacketFly;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class ReverseStep
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class123.Motion);
    private final Setting timer = new Setting("Timer", "", this, Float.valueOf(3.0f), Float.valueOf(1.0f), Float.valueOf(10.0f), this.mainwindow, this::Method333);
    private final Setting anyblock = new Setting("AnyBlock", "", this, false);
    private boolean Field292 = true;
    private boolean Field293 = false;
    @EventHandler
    private Listener Field294 = new Listener<EventPlayerUpdateWalking>(this::Method334, -5000, new Predicate[0]);

    public ReverseStep() {
        super("ReverseStep", "Makes you fall faster.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.timer);
        this.addSetting(this.anyblock);
    }

    public String Method336() {
        return ChatFormatting.WHITE + ((Class123)((Object)this.mode.getValue())).name();
    }

    private void Method334(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE || Manager.moduleManager.isModuleEnabled(PacketFly.class)) {
            return;
        }
        IBlockState iBlockState = Globals.mc.world.getBlockState(new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ).down(2));
        IBlockState iBlockState2 = Globals.mc.world.getBlockState(new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ).down(3));
        IBlockState iBlockState3 = Globals.mc.world.getBlockState(new BlockPos(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ).down(4));
        if (!(iBlockState.getBlock() != Blocks.BEDROCK && iBlockState.getBlock() != Blocks.OBSIDIAN && !((Boolean)this.anyblock.getValue()).booleanValue() || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
            if (Globals.mc.player.onGround && this.mode.getValue() == Class123.Motion) {
                Globals.mc.player.motionY -= 1.0;
            }
            if (!(this.mode.getValue() != Class123.Timer || !this.Field293 || Globals.mc.player.onGround || !(Globals.mc.player.motionY < -0.1) || this.Field292 || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
                Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
                this.Field292 = true;
            }
        } else if (!(iBlockState2.getBlock() != Blocks.BEDROCK && iBlockState2.getBlock() != Blocks.OBSIDIAN && !((Boolean)this.anyblock.getValue()).booleanValue() || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
            if (Globals.mc.player.onGround && this.mode.getValue() == Class123.Motion) {
                Globals.mc.player.motionY -= 1.0;
            }
            if (!(this.mode.getValue() != Class123.Timer || !this.Field293 || Globals.mc.player.onGround || !(Globals.mc.player.motionY < -0.1) || this.Field292 || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
                Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
                this.Field292 = true;
            }
        } else if (!(iBlockState3.getBlock() != Blocks.BEDROCK && iBlockState3.getBlock() != Blocks.OBSIDIAN && !((Boolean)this.anyblock.getValue()).booleanValue() || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
            if (Globals.mc.player.onGround && this.mode.getValue() == Class123.Motion) {
                Globals.mc.player.motionY -= 1.0;
            }
            if (!(this.mode.getValue() != Class123.Timer || !this.Field293 || Globals.mc.player.onGround || !(Globals.mc.player.motionY < -0.1) || this.Field292 || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
                Manager.Field298.Method337(((Float)this.timer.getValue()).floatValue());
                this.Field292 = true;
            }
        }
        if (this.Field292 && (Globals.mc.player.onGround || Globals.mc.player.isInLava() || Globals.mc.player.isInWater() || Globals.mc.player.isInWeb || Globals.mc.player.isElytraFlying() || Globals.mc.player.capabilities.isFlying)) {
            this.Field292 = false;
            Manager.Field298.Method337(1.0f);
        }
        this.Field293 = Globals.mc.player.onGround;
    }

    private boolean Method333() {
        return this.mode.getValue() == Class123.Timer;
    }
}
