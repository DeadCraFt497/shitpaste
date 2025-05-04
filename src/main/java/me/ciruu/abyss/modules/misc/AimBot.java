package me.ciruu.abyss.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class258;
import me.ciruu.abyss.enums.Class410;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class570;
import me.ciruu.abyss.enums.Class582;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.hause.alienware.AlienKeyAgent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static me.ciruu.abyss.enums.Class258.Left;
import static me.ciruu.abyss.enums.Class258.Right;
import static me.ciruu.abyss.enums.Class268.Both;

public class AimBot
extends Module {
    private final Setting main = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting mode = new Setting("Mode", "", this, (Object)Class410.Closest);
    private final Setting point = new Setting("Point", "", this, (Object)Class570.Head);
    private final Setting range = new Setting("Range", "", (Module)this, (Object)Float.valueOf(250.0f), Float.valueOf(1.0f), Float.valueOf(350.0f));
    public final Setting invisibles = new Setting("Invisibles", "", this, false);
    private final Setting raytrace = new Setting("RayTrace", "", this, true);
    private final Setting packet = new Setting("Abyss$preInit$Packet", "", this, false);
    private final Setting tracer = new Setting("Tracer", "", this, true);
    private final Setting instant = new Setting("Instant", "", this, true);
    private final Setting click = new Setting("Click", "", (Module)this, (Object)Class258.Left, this.main, this.instant::getValue);
    private final Setting delay = new Setting("Delay(ms)", "", this, Float.valueOf(1000.0f), Float.valueOf(0.0f), Float.valueOf(10000.0f), this.main, this.instant::getValue);
    private final Setting predict = new Setting("Predict", "", this, (Object)Class582.None);
    private final Setting autoTicks = new Setting("AutoTicks", "", (Module)this, (Object)true, this.main, this::Method3361);
    private final Setting ticksAhead = new Setting("TicksAhead", "", this, 3, 1, 20, this.main, this::Method3362);
    private final Setting interpolation = new Setting("Interpolation", "", (Module)this, (Object)false, this.main, this::Method3363);
    private final Setting predictTracer = new Setting("PredictTracer", "", (Module)this, (Object)true, this.main, this::Method3364);
    private final Setting color = new Setting("Color", "", (Module)this, (Object)Color.BLACK, this.main, this::Method3365);
    private final Setting players = new Setting("Players", "", this, true);
    private final Setting mobs = new Setting("Mobs", "", this, false);
    private final Setting animals = new Setting("Animals", "", this, false);
    private final Setting entities = new Setting("Entities", "", this, false);
    private Entity Field2769;
    private final Class22 Field2770 = new Class22();
    private Vec3d Field2771;
    private final Map Field2772 = new HashMap();
    private int Field2773 = 10;
    @EventHandler
    private Listener Field2774 = new Listener<EventPlayerUpdateWalking>(this::Method3366, new Predicate[0]);
    @EventHandler
    private Listener Field2775 = new Listener<Class66>(this::Method3367, new Predicate[0]);

    public AimBot() {
        super("AimBot", "Automatically look at entities.", Class11.MISC, "");
        this.addSetting(this.mode);
        this.addSetting(this.point);
        this.addSetting(this.range);
        this.addSetting(this.invisibles);
        this.addSetting(this.raytrace);
        this.addSetting(this.packet);
        this.addSetting(this.tracer);
        this.addSetting(this.instant);
        this.addSetting(this.click);
        this.addSetting(this.delay);
        this.addSetting(this.predict);
        this.addSetting(this.autoTicks);
        this.addSetting(this.ticksAhead);
        this.addSetting(this.interpolation);
        this.addSetting(this.predictTracer);
        this.addSetting(this.color);
        this.addSetting(this.players);
        this.addSetting(this.mobs);
        this.addSetting(this.animals);
        this.addSetting(this.entities);
    }

    public String Method3369() {
        return this.Field2769 != null ? ChatFormatting.WHITE + this.Field2769.getName() : null;
    }

    public void Method3370() {
        super.getDisable();
        Manager.Field1644.Method2260();
    }

    private Entity Method3371(boolean bl) {
        Entity entity = null;
        double d = ((Float)this.range.getValue()).floatValue();
        double d2 = 36.0;
        for (Entity entity2 : Globals.mc.world.loadedEntityList) {
            if (entity2.isInvisible() && !((Boolean)this.invisibles.getValue()).booleanValue() || Globals.mc.player.getDistance(entity2) > ((Float)this.range.getValue()).floatValue() || !((Boolean)this.players.getValue() != false && entity2 instanceof EntityPlayer || (Boolean)this.animals.getValue() != false && Class30.Method231(entity2) || (Boolean)this.mobs.getValue() != false && Class30.Method2722(entity2)) && (!((Boolean)this.entities.getValue()).booleanValue() || !Class30.Method2726(entity2)) || entity2 instanceof EntityLivingBase && Class30.Method749(entity2, d) || !Globals.mc.player.canEntityBeSeen(entity2) && ((Boolean)this.raytrace.getValue()).booleanValue() && bl) continue;
            if (entity == null) {
                entity = entity2;
                d = Globals.mc.player.getDistanceSq(entity2);
                d2 = Class30.Method1454(entity2);
                continue;
            }
            if (this.mode.getValue() == Class410.Closest && Globals.mc.player.getDistanceSq(entity2) < d) {
                entity = entity2;
                d = Globals.mc.player.getDistanceSq(entity2);
                d2 = Class30.Method1454(entity2);
            }
            if (this.mode.getValue() != Class410.Health || !((double)Class30.Method1454(entity2) < d2)) continue;
            entity = entity2;
            d = Globals.mc.player.getDistanceSq(entity2);
            d2 = Class30.Method1454(entity2);
        }
        return entity;
    }

    public Vec3d Method3372(Entity entity, float f, Class570 class570) {
        double d = 0.0;
        if (class570 == Class570.Mid) {
            d = entity.getEyeHeight() / 2.0f;
        } else if (class570 == Class570.Head) {
            d = entity.getEyeHeight();
        }
        if (f == 1.0f) {
            return new Vec3d(entity.posX, entity.posY + d, entity.posZ);
        }
        double d2 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)f;
        double d3 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)f + d;
        double d4 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)f;
        return new Vec3d(d2, d3, d4);
    }

    public Entity Method3373(EntityPlayer entityPlayer, int n) {
        List<Vec3d> list;  // Explicitly specify the type
        if (this.Field2772.containsKey(entityPlayer) && (list = (List<Vec3d>) this.Field2772.get(entityPlayer)).size() > 1) {
            Vec3d vec3d = list.get(0);
            ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
            Vec3d vec3d2 = vec3d;
            for (Vec3d vec3d3 : list) {  // Now the list is correctly declared
                arrayList.add(new Vec3d(vec3d3.x - vec3d2.x, vec3d3.y - vec3d2.y, vec3d3.z - vec3d2.z));
                vec3d2 = vec3d3;
            }
            double d = 0.0;
            double d2 = 0.0;
            double d3 = 0.0;
            for (Vec3d vec3d4 : arrayList) {
                d += vec3d4.x;
                d2 += vec3d4.y;
                d3 += vec3d4.z;
            }
            d /= arrayList.size();
            d2 /= arrayList.size();
            d3 /= arrayList.size();
            EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)Globals.mc.world, entityPlayer.getGameProfile());
            entityOtherPlayerMP.noClip = false;
            entityOtherPlayerMP.setPosition(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
            for (int i = 0; i < n; ++i) {
                entityOtherPlayerMP.move(MoverType.PLAYER, d, d2, d3);
            }
            return entityOtherPlayerMP;
        }
        return entityPlayer;
    }


    private void setupCameraTransform(float partialTicks, int pass) {
        try {
            Method method = EntityRenderer.class.getDeclaredMethod("setupCameraTransform", float.class, int.class);
            method.setAccessible(true);
            method.invoke(Minecraft.getMinecraft().entityRenderer, partialTicks, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Method3367(Class66 class66) {
        Vec3d vec3d;
        if (!((Boolean)this.tracer.getValue()).booleanValue() || Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }
        if (this.Field2769 != null && (vec3d = Class29.getVector(this.Field2769, class66.Method1789()).subtract(Globals.mc.getRenderManager().renderPosX, Globals.mc.getRenderManager().renderPosY, Globals.mc.getRenderManager().renderPosZ)) != null) {
            boolean bl = Globals.mc.gameSettings.viewBobbing;
            Globals.mc.gameSettings.viewBobbing = false;
            setupCameraTransform(class66.Method1789(), 0);
            Vec3d vec3d2 = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw)));
            float f = this.point.getValue() == Class570.Head ? this.Field2769.getEyeHeight() : (this.point.getValue() == Class570.Mid ? this.Field2769.getEyeHeight() / 2.0f : 0.0f);
            Class50.Method915((float)vec3d2.x, (float)vec3d2.y + Globals.mc.player.getEyeHeight(), (float)vec3d2.z, (float)vec3d.x, (float)vec3d.y + f, (float)vec3d.z, 1.0f, Globals.mc.player.canEntityBeSeen(this.Field2769) ? Color.GREEN.getRGB() : Color.RED.getRGB());
            if (this.predict.getValue() != Class582.None && ((Boolean)this.predictTracer.getValue()).booleanValue() && this.Field2771 != null) {
                Vec3d vec3d3 = this.Field2771.subtract(Globals.mc.getRenderManager().renderPosX, Globals.mc.getRenderManager().renderPosY, Globals.mc.getRenderManager().renderPosZ);
                Class50.Method915((float)vec3d2.x, (float)vec3d2.y + Globals.mc.player.getEyeHeight(), (float)vec3d2.z, (float)vec3d3.x, (float)vec3d3.y, (float)vec3d3.z, 1.0f, Globals.mc.player.canEntityBeSeen(this.Field2769) ? ((Color)this.color.getValue()).getRGB() : Color.RED.getRGB());
            }
            Globals.mc.gameSettings.viewBobbing = bl;
            setupCameraTransform(class66.Method1789(), 0);
        }
    }

    public AlienKeyAgent __agent;
    public Vec3d vec3d;
    /* April 13 8:11 PST North East Arlington Hights Chicago HauseHauseHause_ */
    private void Method3366(EventPlayerUpdateWalking eventPlayerUpdateWalking) {

        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) return;
        Object[] object;
        for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
            this.Field2772.putIfAbsent(entityPlayer, new ArrayList<>());

            List<Vec3d> positions = (List<Vec3d>) this.Field2772.get(entityPlayer);
            positions.add(new Vec3d(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));

            while (positions.size() > this.Field2773) {
                positions.remove(0); // Remove oldest
            }
        }

        this.Field2769 = this.Method3371(this.raytrace.getValue());
        this.Field2769 = this.Method3371((Boolean)this.raytrace.getValue());
        if (this.Field2769 == null && ((Boolean)this.raytrace.getValue()).booleanValue()) {
            this.Field2769 = this.Method3371(false);
        }

        if (this.Field2769 != null && Globals.mc.player.canEntityBeSeen(this.Field2769)) {
            Vec3d predictedVec = null;

            if (this.predict.getValue() == Class582.Old) {
                Manager.Field1644.Method2259(this.Field2769);
                predictedVec = Manager.Field1644.Method2263(this.ticksAhead.getValue(), this.interpolation.getValue());
            } else if (this.predict.getValue() == Class582.New && this.Field2769 instanceof EntityPlayer) {
                int estimatedTicks = (int) Math.ceil(Class29.getFloat2(
                        Globals.mc.player.connection.getPlayerInfo(Globals.mc.player.getUniqueID()).getResponseTime(),
                        0.0f, 10000.0f) / 50.0);
                int ticks = this.autoTicks.getValue() ? estimatedTicks : this.ticksAhead.getValue();
                predictedVec = this.Method3373((EntityPlayer)this.Field2769, ticks).getPositionVector();
            }

            if (predictedVec != null && this.point.getValue() != Class570.Feet) {
                float eyeHeight = this.Field2769.getEyeHeight();
                if (this.point.getValue() == Class570.Head) {
                    predictedVec = predictedVec.add(0.0, eyeHeight, 0.0);
                } else {
                    predictedVec = predictedVec.add(0.0, eyeHeight / 2.0, 0.0);
                }
            }

            if (predictedVec != null) {
                this.Field2771 = predictedVec;
            }

            Vec3d aimVec = this.Method3372(this.Field2769, Globals.mc.getRenderPartialTicks(), this.point.getValue());
            float[] rotation = Class29.getFloatVector(
                    Globals.mc.player.getPositionEyes(Globals.mc.getRenderPartialTicks()),
                    (this.predict.getValue() != Class582.None &&
                            (this.predict.getValue() != Class582.Old || Manager.Field1644.Method2258() != null)
                            && predictedVec != null) ? predictedVec : aimVec
            );

            if (this.packet.getValue()) {
                eventPlayerUpdateWalking.setYaw(rotation[0]);
                eventPlayerUpdateWalking.setPitch(rotation[1]);
                eventPlayerUpdateWalking.cancel();
                Class202.Method934(rotation[1], rotation[0]);
            } else {
                Manager.Field456.Method523(rotation[0], rotation[1]);
            }
            Vec3d vec3d2 = this.Method3372(this.Field2769, Globals.mc.getRenderPartialTicks(), (Class570)((Object)this.point.getValue()));
            object = new float[][]{Class29.getFloatVector(Globals.mc.player.getPositionEyes(Globals.mc.getRenderPartialTicks()), this.predict.getValue() != Class582.None && (this.predict.getValue() != Class582.Old && Manager.Field1644.Method2258() != null || this.predict.getValue() == Class582.New) && vec3d != null ? vec3d : vec3d2)};
            if (((Boolean)this.packet.getValue()).booleanValue()) {
                eventPlayerUpdateWalking.setYaw((float)object[0]);
                eventPlayerUpdateWalking.setPitch((float)object[1]);
                eventPlayerUpdateWalking.cancel();
                Class202.Method934((float)object[1], (float)object[0]);
            } else {
                Manager.Field456.Method523((float)object[0], (float)object[1]);
            }
            if (!this.Field2770.getLongNanoTime(this.delay.getValue())) {
                return;
            }

            Object value = this.click.getValue();
            if (value.equals(Left)) {
                __agent.leftClick();
            } else if (value.equals(Right)) {
                __agent.rightClick();
            } else if (value.equals(Both)) {
                __agent.bothClick();
                __agent.rightClick();
            }
            this.Field2770.getNanoTime();

        }
    }

    private boolean Method3365() {
        return this.predict.getValue() != Class582.None && (Boolean)this.predictTracer.getValue() != false;
    }

    private boolean Method3364() {
        return this.predict.getValue() != Class582.None;
    }

    private boolean Method3363() {
        return this.predict.getValue() == Class582.Old;
    }

    private boolean Method3362() {
        return this.predict.getValue() != Class582.None && (this.predict.getValue() == Class582.New && (Boolean)this.autoTicks.getValue() == false || this.predict.getValue() == Class582.Old);
    }

    private boolean Method3361() {
        return this.predict.getValue() == Class582.New;
    }
}
