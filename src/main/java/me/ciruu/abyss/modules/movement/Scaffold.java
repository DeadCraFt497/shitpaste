package me.ciruu.abyss.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.lang.reflect.Field;
import java.util.function.Predicate;
import me.ciruu.abyss.Class110;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class109;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class285;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class627;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerMove;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Scaffold
extends Module {
    private final Setting mode = new Setting("Mode", "", this, (Object)Class627.Tower);
    private final Setting rotate = new Setting("Rotate", "", this, true);
    private final Setting stopmotion = new Setting("StopMotion", "", this, true);
    private final Setting delay = new Setting("Delay(ms)", "", (Module)this, (Object)0, 0, 1000);
    private final Class22 Field3411 = new Class22();
    private final Class22 Field3412 = new Class22();
    private final Class22 Field3413 = new Class22();
    private float[] Field3414 = null;
    @EventHandler
    private Listener Field3415 = new Listener<EventPlayerUpdate>(this::Method4078, new Predicate[0]);
    @EventHandler
    private Listener Field3416 = new Listener<EventPlayerUpdateWalking>(this::Method4079, new Predicate[0]);
    @EventHandler
    private Listener Field3417 = new Listener<EventNetworkPrePacketEvent>(this::Method4080, new Predicate[0]);
    @EventHandler
    private Listener Field3418 = new Listener<EventNetworkPostPacketEvent>(this::Method4081, new Predicate[0]);
    @EventHandler
    private Listener Field3419 = new Listener<EventPlayerMove>(this::Method4082, new Predicate[0]);

    public Scaffold() {
        super("Scaffold", "Places blocks on your feet around you.", Class11.MOVEMENT, "");
        this.addSetting(this.mode);
        this.addSetting(this.rotate);
        this.addSetting(this.stopmotion);
        this.addSetting(this.delay);
    }

    public String Method4084() {
        return ChatFormatting.WHITE + ((Class627)((Object)this.mode.getValue())).name();
    }

    public void Method4085(boolean bl) {
        if (this.isEnabled()) {
            bl = false;
        }
        isEnabled();
    }

    private boolean Method4089(double d, double d2, double d3) {
        return Globals.mc.world.getCollisionBoxes((Entity)Globals.mc.player, Globals.mc.player.getEntityBoundingBox().offset(d, d2, d3)).isEmpty();
    }

    private boolean Method4090(BlockPos blockPos) {
        Class285 class285 = Class110.Method1043(blockPos);
        if (class285 == Class285.AlreadyBlockThere) {
            return Globals.mc.world.getBlockState(blockPos).getMaterial().isReplaceable();
        }
        return class285 == Class285.Ok;
    }

    private boolean Method4091(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem() instanceof ItemBlock;
    }

    private void Method4082(EventPlayerMove eventPlayerMove) {
        if (!((Boolean)this.stopmotion.getValue()).booleanValue()) {
            return;
        }
        double d = eventPlayerMove.getDoubVal1();
        double d2 = eventPlayerMove.getDoubVal2();
        double d3 = eventPlayerMove.getDoubVal3();
        if (Globals.mc.player.onGround && !Globals.mc.player.noClip) {
            double d4 = 0.05;
            while (d != 0.0 && this.Method4089(d, -1.0, 0.0)) {
                if (d < d4 && d >= -d4) {
                    d = 0.0;
                    continue;
                }
                if (d > 0.0) {
                    d -= d4;
                    continue;
                }
                d += d4;
            }
            while (d3 != 0.0 && this.Method4089(0.0, -1.0, d3)) {
                if (d3 < d4 && d3 >= -d4) {
                    d3 = 0.0;
                    continue;
                }
                if (d3 > 0.0) {
                    d3 -= d4;
                    continue;
                }
                d3 += d4;
            }
            while (d != 0.0 && d3 != 0.0 && this.Method4089(d, -1.0, d3)) {
                d = d < d4 && d >= -d4 ? 0.0 : (d > 0.0 ? (d -= d4) : (d += d4));
                if (d3 < d4 && d3 >= -d4) {
                    d3 = 0.0;
                    continue;
                }
                if (d3 > 0.0) {
                    d3 -= d4;
                    continue;
                }
                d3 += d4;
            }
        }
        eventPlayerMove.getDoubVal4(d);
        eventPlayerMove.getDoubVal5(d2);
        eventPlayerMove.getDoubVal6(d3);
        eventPlayerMove.cancel();
    }

    private void Method4081(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getEra() != Class53.PRE || !((Boolean)this.rotate.getValue()).booleanValue()) {
            return;
        }
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer.Rotation && Globals.mc.player.isRiding() && this.Field3414 != null) {
            CPacketPlayer.Rotation rotation = (CPacketPlayer.Rotation)eventNetworkPostPacketEvent.getPacket();
            rotation.pitch = this.Field3414[1];
            rotation.yaw = this.Field3414[0];
        }
    }

    private void Method4080(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent.getEra()== Class53.PRE && eventNetworkPrePacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            this.Field3413.getNanoTime();
            if (Globals.mc.player.movementInput.jump) {
                Globals.mc.player.motionY = 0.42f;
            }
        }
    }

    private void Method4079(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.isCancelled() || Globals.mc.player.inventory == null || Globals.mc.world == null) {
            return;
        }

        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }

        if (!this.Field3411.getLongNanoTime((Integer) this.delay.getValue())) {
            return;
        }

        ItemStack itemStack = Globals.mc.player.getHeldItemMainhand();
        int oldSlot = -1;

        if (!this.Method4091(itemStack)) {
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = Globals.mc.player.inventory.getStackInSlot(i);
                if (!this.Method4091(stack)) continue;
                oldSlot = Globals.mc.player.inventory.currentItem;
                Class155.Method522(i, false);
                itemStack = stack;
                break;
            }
        }

        if (!this.Method4091(itemStack)) {
            return;
        }

        this.Field3411.getNanoTime();

        BlockPos blockToPlace = null;
        BlockPos playerPos = Class202.Method931().down();
        boolean canPlaceBelow = playerPos != BlockPos.ORIGIN && this.Method4090(playerPos);

        if (this.mode.getValue() == Class627.Tower && canPlaceBelow && Globals.mc.player.movementInput.jump && this.Field3413.getLongNanoTime(250L) && !Globals.mc.player.isElytraFlying()) {
            if (this.Field3412.getLongNanoTime(1500L)) {
                this.Field3412.getNanoTime();
                Globals.mc.player.motionY = -0.28f;
            } else {
                Globals.mc.player.setVelocity(0.0, 0.42, 0.0);
            }
        }

        if (canPlaceBelow) {
            blockToPlace = playerPos;
        } else {
            Class285 placeability = Class110.Method1043(playerPos);
            if (placeability != Class285.Ok && placeability != Class285.AlreadyBlockThere) {
                BlockPos[] neighbors = new BlockPos[]{
                        playerPos.north(), playerPos.south(), playerPos.east(), playerPos.west()
                };
                BlockPos closest = null;
                double closestDist = 420.0;

                for (BlockPos neighbor : neighbors) {
                    if (!this.Method4090(neighbor)) continue;

                    double dist = neighbor.distanceSq((int) Globals.mc.player.posX, (int) Globals.mc.player.posY, (int) Globals.mc.player.posZ);
                    if (dist < closestDist) {
                        closestDist = dist;
                        closest = neighbor;
                    }
                }

                if (closest != null) {
                    blockToPlace = closest;
                }
            }
        }

        if (blockToPlace != null) {
            Vec3d eyesPos = new Vec3d(Globals.mc.player.posX, Globals.mc.player.posY + Globals.mc.player.getEyeHeight(), Globals.mc.player.posZ);

            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos offsetPos = blockToPlace.offset(facing);
                EnumFacing opposite = facing.getOpposite();

                if (!Globals.mc.world.getBlockState(offsetPos).getBlock().canCollideCheck(Globals.mc.world.getBlockState(offsetPos), false)) continue;

                Vec3d hitVec = new Vec3d(offsetPos).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) > 5.0) continue;

                float[] rotations = Class110.Method2185(blockToPlace.getX(), blockToPlace.getY(), blockToPlace.getZ(), facing);

                eventPlayerUpdateWalking.cancel();
                eventPlayerUpdateWalking.setYaw(rotations[0]);
                eventPlayerUpdateWalking.setPitch(rotations[1]);

                break;
            }

            if (eventPlayerUpdateWalking.isCancelled()) {
                BlockPos finalBlockToPlace = blockToPlace;
                int finalOldSlot = oldSlot;

                eventPlayerUpdateWalking.getConsumerValue(player -> Scaffold.Method4092(finalBlockToPlace, finalOldSlot, (EntityPlayerSP) player));
            } else {
                this.Field3412.getNanoTime();
            }
        } else {
            this.Field3412.getNanoTime();
        }

        if (!eventPlayerUpdateWalking.isCancelled() && oldSlot != -1) {
            Globals.mc.player.inventory.currentItem = oldSlot;
            Globals.mc.playerController.updateController();
        }
    }


    private static void Method4092(BlockPos blockPos, int n, EntityPlayerSP entityPlayerSP) {
        if (Class110.Method2182(blockPos, 5.0f, false, false, false) == Class109.Placed) {
            // empty if block
        }
        if (n != -1) {
            Class155.Method522(n, false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void Method4078(EventPlayerUpdate eventPlayerUpdate) {
        boolean bl;
        Entity entity = Globals.mc.player.getRidingEntity();
        if (entity == null) {
            return;
        }
        try {
            Field field = AbstractHorse.class.getDeclaredField("horseJumpPower");
            field.setAccessible(true);
            field.setFloat(Globals.mc.player, 0.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!this.Field3411.getLongNanoTime(((Integer)this.delay.getValue()).longValue())) {
            return;
        }
        if (Globals.mc.player.movementInput.jump && entity.onGround) {
            entity.motionY = 0.65;
        }
        ItemStack itemStack = Globals.mc.player.getHeldItemMainhand();
        int n = -1;
        if (!this.Method4091(itemStack)) {
            for (int i = 0; i < 9; ++i) {
                itemStack = Globals.mc.player.inventory.getStackInSlot(i);
                if (!this.Method4091(itemStack)) continue;
                n = Globals.mc.player.inventory.currentItem;
                Class155.Method522(i, false);
                break;
            }
        }
        if (!this.Method4091(itemStack)) {
            return;
        }
        this.Field3411.getNanoTime();
        BlockPos blockPos = null;
        BlockPos blockPos2 = Class202.Method931().down();
        boolean bl2 = bl = blockPos2 != BlockPos.ORIGIN ? this.Method4090(blockPos2) : false;
        if (this.mode.getValue() == Class627.Tower && bl && Globals.mc.player.movementInput.jump && this.Field3413.getLongNanoTime(250L) && !Globals.mc.player.isElytraFlying()) {
            if (this.Field3412.getLongNanoTime(1500L)) {
                this.Field3412.getNanoTime();
                entity.motionY = -0.28f;
            } else {
                float f = 0.42f;
                entity.setVelocity(0.0, (double)0.42f, 0.0);
            }
        }
        if (bl) {
            blockPos = blockPos2;
        } else {
            Class285 class285 = Class110.Method1043(blockPos2);
            if (class285 != Class285.Ok && class285 != Class285.AlreadyBlockThere) {
                BlockPos[] blockPos3 = new BlockPos[]{blockPos2.north(), blockPos2.south(), blockPos2.east(), blockPos2.west()};
                BlockPos blockPos4 = null;
                double d = 420.0;
                for (BlockPos blockPos5 : blockPos3) {
                    double d2;
                    if (!this.Method4090(blockPos5) || !(d > (d2 = blockPos5.getDistance((int)Globals.mc.player.posX, (int)Globals.mc.player.posY, (int)Globals.mc.player.posZ)))) continue;
                    d = d2;
                    blockPos4 = blockPos5;
                }
                if (blockPos4 != null) {
                    blockPos = blockPos4;
                }
            }
        }
        if (blockPos == null) {
            this.Field3414 = null;
            this.Field3412.getNanoTime();
        } else {
            Vec3d vec3d = new Vec3d(Globals.mc.player.posX, Globals.mc.player.posY + (double)Globals.mc.player.getEyeHeight(), Globals.mc.player.posZ);
            for (EnumFacing enumFacing : EnumFacing.values()) {
                Vec3d vec3d2;
                BlockPos blockPos6 = blockPos.offset(enumFacing);
                EnumFacing enumFacing2 = enumFacing.getOpposite();
                if (!Globals.mc.world.getBlockState(blockPos6).getBlock().canCollideCheck(Globals.mc.world.getBlockState(blockPos6), false) || !(vec3d.distanceTo(vec3d2 = new Vec3d((Vec3i)blockPos6).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing2.getDirectionVec()).scale(0.5))) <= 5.0)) continue;
                this.Field3414 = Class110.Method2185(blockPos.getX(), blockPos.getY(), blockPos.getZ(), enumFacing);
                break;
            }
            if (Class110.Method2182(blockPos, 5.0f, false, false, false) != Class109.Placed) {
                // empty if block
            }
        }
        if (n != -1) {
            Class155.Method522(n, false);
        }
    }
}
