package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.Predicate;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class85;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerJump;
import me.ciruu.abyss.events.player.EventPlayerMove;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.combat.Burrow;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class Anchor
extends Module {
    public final Setting activatepitch = new Setting("ActivatePitch", "", (Module)this, (Object)70, 90, -90);
    private final Setting toggle = new Setting("Toggle", "", this, false);
    private final Setting togglespeed = new Setting("ToggleSpeed", "", this, false);
    private final Setting togglestep = new Setting("ToggleStep", "", this, false);
    public final Setting blockdistance = new Setting("BlockDistance", "", (Module)this, (Object)Float.valueOf(6.0f), Float.valueOf(4.0f), Float.valueOf(12.0f));
    public static boolean Field473 = false;
    public static boolean Field333 = false;
    public static boolean Field474 = false;
    private boolean Field475 = false;
    @EventHandler
    private Listener Field476 = new Listener<EventPlayerMove>(this::Method569, new Predicate[0]);
    @EventHandler
    private Listener Field477 = new Listener<EventPlayerJump>(this::Method570, new Predicate[0]);

    public Anchor() {
        super("Anchor", "Stop movement over a hole.", Class11.MOVEMENT, "");
        this.addSetting(this.activatepitch);
        this.addSetting(this.toggle);
        this.addSetting(this.togglespeed);
        this.addSetting(this.togglestep);
        this.addSetting(this.blockdistance);
    }

    public String Method572() {
        return this.Field475 ? ChatFormatting.GREEN + "ON" : ChatFormatting.RED + "OFF";
    }

    private boolean Method573(Vec3d vec3d) {
        return Math.abs(Globals.mc.player.posX - vec3d.x) < 0.2 && Math.abs(Globals.mc.player.posZ - vec3d.z) < 0.2;
    }

    private BlockPos Method574() {
        RayTraceResult rayTraceResult = Globals.mc.objectMouseOver;
        BlockPos blockPos = null;
        if (rayTraceResult == null) {
            return null;
        }
        switch (rayTraceResult.typeOfHit) {
            case MISS: {
                blockPos = null;
                break;
            }
            case BLOCK: {
                blockPos = Globals.mc.objectMouseOver.getBlockPos();
                break;
            }
            case ENTITY: {
                blockPos = null;
            }
        }
        return blockPos;
    }

    private Vec3d Method575(double d, double d2, double d3) {
        double d4 = Math.floor(d) + 0.5;
        double d5 = Math.floor(d2);
        double d6 = Math.floor(d3) + 0.5;
        return new Vec3d(d4, d5, d6);
    }

    public void Method576() {
        super.getDisable();
        Field473 = false;
        Field333 = false;
        Field474 = false;
    }

    private void Method570(EventPlayerJump eventPlayerJump) {
        if (Class202.Method577() && Globals.mc.player.rotationPitch > (float)((Integer)this.activatepitch.getValue()).intValue()) {
            eventPlayerJump.cancel();
        }
    }

    private void Method569(EventPlayerMove eventPlayerMove) {
        if (Globals.mc.player.onGround) {
            Field473 = false;
        }
        if (Manager.moduleManager.isModuleEnabled(Burrow.class)) {
            return;
        }
        if (eventPlayerMove.isCancelled() || Globals.mc.player.rotationPitch < (float)((Integer)this.activatepitch.getValue()).intValue()) {
            Field333 = false;
            Field474 = false;
            this.Field475 = false;
            return;
        }
        Field333 = true;
        Field474 = true;
        BlockPos blockPos = this.Method574();
        if (blockPos == null) {
            this.Field475 = false;
            return;
        }
        if (!Class85.Method208(blockPos.up())) {
            this.Field475 = false;
            return;
        }
        Vec3d vec3d = this.Method575(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (Globals.mc.player.motionY > 0.0) {
            Globals.mc.player.motionY = 0.0;
        }
        if (Class202.Method577() && Globals.mc.player.onGround && ((Boolean)this.toggle.getValue()).booleanValue()) {
            isEnabled();
        }
        if (Class202.Method577() && Globals.mc.player.onGround && Globals.mc.player.rotationPitch > (float)((Integer)this.activatepitch.getValue()).intValue()) {
            this.Field475 = true;
            Globals.mc.player.motionX = 0.0;
            Globals.mc.player.motionY = 0.0;
            Globals.mc.player.motionZ = 0.0;
            Field473 = true;
            eventPlayerMove.getDoubVal4(0.0);
            eventPlayerMove.getDoubVal6(0.0);
            eventPlayerMove.cancel();
            return;
        }
        if (this.Method573(vec3d)) {
            if (Manager.moduleManager.isModuleEnabled(Speed.class) && ((Boolean)this.togglespeed.getValue()).booleanValue()) {
                ((Speed)Manager.moduleManager.getModuleByClass(Speed.class)).isEnabled();
            }
            if (Manager.moduleManager.isModuleEnabled(Step.class) && ((Boolean)this.togglestep.getValue()).booleanValue()) {
                ((Step)Manager.moduleManager.getModuleByClass(Step.class)).isEnabled();
            }
            if (!Field473) {
                Field473 = true;
                return;
            }
            Globals.mc.player.motionX = 0.0;
            Globals.mc.player.motionZ = 0.0;
            Globals.mc.player.movementInput.moveForward = 0.0f;
            Globals.mc.player.movementInput.moveStrafe = 0.0f;
            eventPlayerMove.getDoubVal4(0.0);
            eventPlayerMove.getDoubVal6(0.0);
            eventPlayerMove.cancel();
        } else {
            double d = vec3d.x - Globals.mc.player.posX;
            double d2 = vec3d.z - Globals.mc.player.posZ;
            double d3 = Math.sqrt(d * d + d2 * d2);
            double d4 = d / d3;
            double d5 = d2 / d3;
            double d6 = Math.sqrt(Globals.mc.player.motionX * Globals.mc.player.motionX + Globals.mc.player.motionZ * Globals.mc.player.motionZ);
            Globals.mc.player.motionX = d6 * d4;
            Globals.mc.player.motionZ = d6 * d5;
        }
        this.Field475 = true;
        eventPlayerMove.cancel();
    }
}
