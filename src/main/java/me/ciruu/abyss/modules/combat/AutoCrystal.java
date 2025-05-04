package me.ciruu.abyss.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import me.ciruu.abyss.Class152;
import me.ciruu.abyss.Class155;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class308;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Class352;
import me.ciruu.abyss.Class353;
import me.ciruu.abyss.Class354;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Class67;
import me.ciruu.abyss.Class84;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class158;
import me.ciruu.abyss.enums.Class159;
import me.ciruu.abyss.enums.Class252;
import me.ciruu.abyss.enums.Class45;
import me.ciruu.abyss.enums.Class460;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.enums.Class577;
import me.ciruu.abyss.enums.Class649;
import me.ciruu.abyss.enums.Class659;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.hud.CrystalCounter;
import me.ciruu.abyss.modules.misc.AutoEz;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/*
 * Illegal identifiers - recommend switching to table mode
 */
public class AutoCrystal
extends Module {
    public static final Object Field3272 = new Object();
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting sswitch = new Setting("Switch", "", this, (Object)Class649.Target);
    private final Setting rotate = new Setting("Rotate", "Rotate", this, (Object)Class159.Off);
    private final Setting strict = new Setting("Strict", "", (Module)this, (Object)false, this.mainwindow, this::getRotatePlaceVal);
    private final Setting target = new Setting("Target", "", this, (Object)Class659.Damage);
    private final Setting targetrange = new Setting("TargetRange", "", (Module)this, (Object)Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(16.0f));
    private final Setting pauseeating = new Setting("PauseEating", "Pause While Eating", this, false);
    private final Setting nosounddesync = new Setting("NoSoundDesync", "No Sound Desync", this, false);
    private final Setting swing = new Setting("Swing", "", this, (Object)Class158.Auto);
    private final Setting metadata = new Setting("Metadata", "", this, (Object)Class45.Target);
    private final Setting placewindow = new Setting("Place Settings", "", this, new Class25("Place Settings"));
    private final Setting place = new Setting("Place", "Place crystals", (Module)this, (Object)true, this.placewindow, AutoCrystal::getBoolean10);
    public final Setting placerange = new Setting("PlaceRange", "Place Range", this, Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.placewindow, this.place::getValue);
    private final Setting faceplace = new Setting("FacePlace", "FacePlace", this, Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(37.0f), this.placewindow, this.place::getValue);
    private final Setting armorbreaker = new Setting("ArmourBreaker", "", this, 0, 0, 100, this.placewindow, this.place::getValue);
    public final Setting mode1_13 = new Setting("1.13", "1.13 mode", (Module)this, (Object)false, this.placewindow, this.place::getValue);
    private final Setting breakwindow = new Setting("Break Settings", "", this, new Class25("Break Settings"));
    private final Setting bbreak = new Setting("Break", "Break crystals", (Module)this, (Object)true, this.breakwindow, AutoCrystal::getBoolean9);
    public final Setting breakrange = new Setting("BreakRange", "Break Range", this, Float.valueOf(6.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.breakwindow, this.bbreak::getValue);
    private final Setting instabreak = new Setting("InstaBreak", "", (Module)this, (Object)false, this.breakwindow, AutoCrystal::getBoolean8);
    private final Setting timer = new Setting("Timer", "InstaBreak Delay (MS)", this, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1000.0f), this.breakwindow, this::getInstaBreak2);
    private final Setting cancel = new Setting("Cancel", "", (Module)this, (Object)false, this.breakwindow, this::getInstaBreak);
    private final Setting damagewindow = new Setting("Damage Settings", "", this, new Class25("Damage Settings"));
    private final Setting nosuicide = new Setting("NoSuicide", "", (Module)this, (Object)true, this.damagewindow, AutoCrystal::getBoolean6);
    private final Setting mindamage = new Setting("MinDamage", "MinDamage", this, Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(20.0f), this.damagewindow, AutoCrystal::getBoolean5);
    private final Setting ignoreselfdamage = new Setting("IgnoreSelfDMG", "", (Module)this, (Object)false, this.damagewindow, AutoCrystal::getBoolean4);
    private final Setting maxselfdamage = new Setting("MaxSelfDMG", "Maximum self damage", this, Float.valueOf(9.0f), Float.valueOf(0.0f), Float.valueOf(20.0f), this.damagewindow, this::Method3951);
    private final Setting raytracewindow = new Setting("Raytrace Settings", "", this, new Class25("Raytrace Settings"));
    private final Setting Field3299 = new Setting("", "", (Module)this, (Object)Class460.Auto, this.raytracewindow, AutoCrystal::getBoolean3);
    private final Setting placetrace = new Setting("PlaceTrace", "", this, Float.valueOf(3.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.raytracewindow, this::getManual3);
    private final Setting breaktrace = new Setting("BreakTrace", "", this, Float.valueOf(3.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.raytracewindow, this::getManual2);
    private final Setting placecheck = new Setting("PlaceCheck", "Only will place crystals that the player can break", (Module)this, (Object)false, this.raytracewindow, this::getManual1);
    private final Setting packetpos = new Setting("PacketPos", "", (Module)this, (Object)false, this.raytracewindow, this::getPlace4);
    private final Setting recalc = new Setting("Recalc", "", (Module)this, (Object)true, this.raytracewindow, this::getPlace3);
    private final Setting RPlaceTrace = new Setting("RPlaceTrace", "", this, Float.valueOf(3.0f), Float.valueOf(0.0f), Float.valueOf(6.0f), this.raytracewindow, this::getPlace2);
    private final Setting RMinDamage = new Setting("RMinDamage", "", this, Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(20.0f), this.raytracewindow, this::getPlace1);
    private final Setting delaywindow = new Setting("Delay Settings", "", this, new Class25("Delay Settings"));
    private final Setting delaymode = new Setting("DelayMode", "", (Module)this, (Object)Class252.Ticks, this.delaywindow, AutoCrystal::getBoolean2);
    private final Setting placetimer = new Setting("PlaceTimer", "Place Delay (MS)", this, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1000.0f), this.delaywindow, this::getDelayTimer2);
    private final Setting breaktimer = new Setting("BreakTimer", "Break Delay (MS)", this, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1000.0f), this.delaywindow, this::getDelayTimer);
    private final Setting placeticks = new Setting("PlaceTicks", "", this, 0, 0, 10, this.delaywindow, this::getDelay2);
    private final Setting breakticks = new Setting("BreakTicks", "", this, 0, 0, 10, this.delaywindow, this::getDelay1);
    private final Setting renderwindow = new Setting("Render Settings", "", this, new Class25("Render Settings"));
    private final Setting render = new Setting("Render", "Render", (Module)this, (Object)true, this.renderwindow, AutoCrystal::getBoolean);
    private final Setting renderbox;
    private final Setting renderfade;
    private final Setting fadeticks;
    private final Setting renderoutline;
    private final Setting rendertext;
    private final Setting rendertextcolor;
    private final Setting renderrainbow;
    private final Setting rendergradient;
    private final Setting renderdelta;
    private final Setting boxcolor;
    private final Setting boxcolor2;
    private final Setting renderlinewidth;
    private final Setting rendercustomline;
    private final Setting outlinecolor;
    private final Setting outlinecolor2;
    private final Setting renderspeed;
    private final Setting rendersaturation;
    private final Setting renderbrightness;
    public EntityPlayer entity = null;
    public BlockPos blockPos1 = null;
    private float fl_1 = 0.0f;
    public boolean bool1 = false;
    public BlockPos blockPos2;
    private double[] dl_1;
    public Vec3d vec3d;
    public boolean bool2 = false;
    private final HashMap hashMap;
    private int int1 = 0;
    private Class22 clazz;
    private int int2 = 0;
    private int int3 = 0;
    private final Class22 clazz1;
    private final Class22 clazz2;
    private final Class22 clazz3;
    private Vec3d vec3d1;
    private int int4 = -1;
    @EventHandler
    private Listener listen2;
    @EventHandler
    private Listener listen3;
    @EventHandler
    private Listener listen4;
    @EventHandler
    private Listener listen5;
    @EventHandler
    private Listener listen6;
    @EventHandler
    private Listener listen7;

    private Collection<BlockPos> L_collection;

    public AutoCrystal() {
        super("AutoCrystal", "Attacks nearby players by placing and breaking end crystals.", Class11.COMBAT, "");
        this.renderfade = new Setting("Fade", "", (Module)this, (Object)false, this.renderwindow, this::getRender4);
        this.fadeticks = new Setting("FadeTicks", "", this, 20, 1, 40, this.renderwindow, this.renderfade::getValue);
        this.renderbox = new Setting("Box", "Box", (Module)this, (Object)true, this.renderwindow, this::getRender3);
        this.renderoutline = new Setting("Outline", "Outline", (Module)this, (Object)true, this.renderwindow, this::getRender2);
        this.rendertext = new Setting("Text", "Text", (Module)this, (Object)true, this.renderwindow, this::getRender1);
        this.rendertextcolor = new Setting("TextColor", "", (Module)this, (Object)Color.WHITE, this.renderwindow, this::getRenderText);
        this.renderrainbow = new Setting("Rainbow", "", (Module)this, (Object)true, this.renderwindow, this.render::getValue);
        this.rendergradient = new Setting("Gradient", "", (Module)this, (Object)true, this.renderwindow, this::getRenderBox);
        this.renderdelta = new Setting("Delta", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.renderwindow, this::getRainbowGradient1);
        this.boxcolor = new Setting("BoxColor", "Box color", (Module)this, (Object)new Color(0, 0, 0, 125), this.renderwindow, this::getRainbowValue);
        this.boxcolor2 = new Setting("BoxColor2", "Box color", (Module)this, (Object)new Color(0, 0, 0, 125), this.renderwindow, this::getRainbow8);
        this.renderlinewidth = new Setting("LineWidth", "LineWidth", this, Float.valueOf(1.5f), Float.valueOf(0.1f), Float.valueOf(5.0f), this.renderwindow, this::getRainbow7);
        this.rendercustomline = new Setting("CustomLine", "CustomLine", (Module)this, (Object)true, this.renderwindow, this::getRainbow6);
        this.outlinecolor = new Setting("OutLinecolor", "OutLine color", (Module)this, (Object)new Color(0, 0, 0, 255), this.renderwindow, this::getRainbow5);
        this.outlinecolor2 = new Setting("OutLinecolor2", "OutLine color", (Module)this, (Object)new Color(0, 0, 0, 255), this.renderwindow, this::getRainbow4);
        this.renderspeed = new Setting("Speed", "", this, Float.valueOf(7.0f), Float.valueOf(0.1f), Float.valueOf(10.0f), this.renderwindow, this::getRainbow3);
        this.rendersaturation = new Setting("Saturation", "", this, Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.renderwindow, this::getRainbow2);
        this.renderbrightness = new Setting("Brightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.renderwindow, this::getRainbow1);
        this.L_collection = new ArrayList();
        this.dl_1 = new double[2];
        this.vec3d = new Vec3d(0.0, 0.0, 0.0);
        this.hashMap = new HashMap();
        this.clazz = new Class22();
        this.clazz1 = new Class22();
        this.clazz2 = new Class22();
        this.clazz3 = new Class22();
        this.vec3d1 = new Vec3d(0.0, 0.0, 0.0);
        this.listen2 = new Listener<EventPlayerUpdateWalking>(this::getEraEntity, new Predicate[0]);
        this.listen3 = new Listener<Class66>(this::getHashedTickReturnType, 5000, new Predicate[0]);
        this.listen4 = new Listener<EventNetworkPrePacketEvent>(this::getSound, new Predicate[0]);
        this.listen5 = new Listener<EventNetworkPrePacketEvent>(this::getSPacketBlockPosAction, 3000, new Predicate[0]);
        this.listen6 = new Listener<EventPlayerUpdateWalking>(this::getEraEvent, new Predicate[0]);
        this.listen7 = new Listener<EventNetworkPostPacketEvent>(this::getVectorPlayer, new Predicate[0]);
        this.addSetting(this.sswitch);
        this.addSetting(this.rotate);
        this.addSetting(this.strict);
        this.addSetting(this.target);
        this.addSetting(this.targetrange);
        this.addSetting(this.pauseeating);
        this.addSetting(this.nosounddesync);
        this.addSetting(this.swing);
        this.addSetting(this.metadata);
        this.addSetting(this.placewindow);
        this.addSetting(this.place);
        this.addSetting(this.placerange);
        this.addSetting(this.faceplace);
        this.addSetting(this.armorbreaker);
        this.addSetting(this.mode1_13);
        this.addSetting(this.breakwindow);
        this.addSetting(this.bbreak);
        this.addSetting(this.breakrange);
        this.addSetting(this.instabreak);
        this.addSetting(this.timer);
        this.addSetting(this.cancel);
        this.addSetting(this.damagewindow);
        this.addSetting(this.nosuicide);
        this.addSetting(this.mindamage);
        this.addSetting(this.ignoreselfdamage);
        this.addSetting(this.maxselfdamage);
        this.addSetting(this.raytracewindow);
        this.addSetting(this.Field3299);
        this.addSetting(this.placetrace);
        this.addSetting(this.breaktrace);
        this.addSetting(this.placecheck);
        this.addSetting(this.packetpos);
        this.addSetting(this.recalc);
        this.addSetting(this.RPlaceTrace);
        this.addSetting(this.RMinDamage);
        this.addSetting(this.delaywindow);
        this.addSetting(this.delaymode);
        this.addSetting(this.placetimer);
        this.addSetting(this.breaktimer);
        this.addSetting(this.placeticks);
        this.addSetting(this.breakticks);
        this.addSetting(this.renderwindow);
        this.addSetting(this.renderbox);
        this.addSetting(this.render);
        this.addSetting(this.renderfade);
        this.addSetting(this.fadeticks);
        this.addSetting(this.renderbox);
        this.addSetting(this.renderoutline);
        this.addSetting(this.rendertext);
        this.addSetting(this.rendertextcolor);
        this.addSetting(this.rendergradient);
        this.addSetting(this.renderdelta);
        this.addSetting(this.boxcolor);
        this.addSetting(this.boxcolor2);
        this.addSetting(this.renderlinewidth);
        this.addSetting(this.rendercustomline);
        this.addSetting(this.outlinecolor);
        this.addSetting(this.outlinecolor2);
        this.addSetting(this.renderrainbow);
        this.addSetting(this.renderspeed);
        this.addSetting(this.rendersaturation);
        this.addSetting(this.renderbrightness);
    }

    public void getKillaura() {
        super.getEnable();
        if (Manager.moduleManager.isModuleEnabled(KillAura.class) && ((Boolean)((KillAura)Manager.moduleManager.getModuleByClass(KillAura.class)).toggleOnAutoCrystal.getValue()).booleanValue()) {
            if((KillAura)Manager.moduleManager.getModuleByClass(KillAura.class) !=null)return;
            this.getTarget();

        }
        this.int1 = 20;
    }

    public void getHashMap() {
        super.getDisable();
        try {
            Object object = Field3272;
            synchronized (object) {
                this.hashMap.clear();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getEnabledReturnType(boolean bl) {
        super.getDisable();
        if (this.int1 != -1) {
            Class155.Method522(this.int1, false);
        }
    }

    public String Method3995() {
        return this.getMetaDataChat();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean getHashMap2(BlockPos blockPos) {
        Object object = Field3272;
        synchronized (object) {
            if (this.hashMap.get(blockPos) == null) return false;
            return true;
        }
    }

    private void getEntity() {
        if (!this.getMode()) {
            return;
        }
        this.getCollectionRange();
        this.entity = this.getRangeDamageEntity();
        this.getAutoCrystalPwn();
        this.getPlaceBreakMode();
        this.getInt1();
        if (this.entity != null) {
            AutoEz.Method1512(this.entity.getName());
        }
    }

    private boolean getMode() {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return false;
        }
        if (this.sswitch.getValue() == Class649.Always) {
            if (Class30.Method2776((EntityPlayer)Globals.mc.player)) {
                return true;
            }
            Class155.Method1491(ItemEndCrystal.class, false);
        }
        if (this.sswitch.getValue() == Class649.None && !Class30.Method2776((EntityPlayer)Globals.mc.player)) {
            return false;
        }
        if (Manager.moduleManager.isModuleEnabled(HoleFiller.class) || Class152.Field1104 || Class152.Field1105 || ((Boolean)this.pauseeating.getValue()).booleanValue() && this.getHandApple() || WebFill.Field2425 || Manager.moduleManager.isModuleEnabled(Burrow.class)) {
            this.bool2 = true;
            return false;
        }
        this.bool2 = false;
        return true;
    }

    private boolean getHandApple() {
        return Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && Globals.mc.player.isHandActive();
    }

    private void getCollectionRange() {
        this.L_collection = Class308.Method2664(((Float)this.placerange.getValue()).floatValue(), (Boolean)this.mode1_13.getValue());
    }

    private EntityPlayer getRangeDamageEntity() {
        Object object = null;
        if (this.target.getValue() == Class659.Closest) {
            for (Object object2 : Globals.mc.world.playerEntities) {
                if (Class30.Method749((Entity)object2, ((Float)this.targetrange.getValue()).floatValue())) continue;
                if (object == null) {
                    object = object2;
                    continue;
                }
                if (!(Globals.mc.player.getDistanceSq((Entity)object2) < Globals.mc.player.getDistanceSq((Entity)object))) continue;
                object = object2;
            }
        }
        if (this.target.getValue() == Class659.Damage) {
            float f = 0.0f;
            for (EntityPlayer entityPlayer : Globals.mc.world.playerEntities) {
                float f2;
                if (Class30.Method749((Entity)entityPlayer, ((Float)this.targetrange.getValue()).floatValue()) || !((f2 = this.getDistance(entityPlayer)) > f)) continue;
                f = f2;
                object = entityPlayer;
            }
        }
        return (EntityPlayer) object;
    }

    private float getDistance(EntityPlayer entityPlayer) {
        Collection Field3334;
        float f = 0.5f;
        for (BlockPos blockPos : this.L_collection) {
            float f2;
            float f3;
            if (!Class31.Method1223(blockPos, Globals.mc.player.getDistanceSq(blockPos) > Class29.getDouble6(this.Field3299.getValue() == Class460.Auto ? 6.0 : (double)((Float)this.placetrace.getValue()).floatValue()), 1.0f) || !((double)(f3 = Class352.Method1510(blockPos, (Entity)Globals.mc.player)) + 0.5 < (double)Class30.Method1454((Entity)Globals.mc.player)) && ((Boolean)this.nosuicide.getValue()).booleanValue() || !((Boolean)this.ignoreselfdamage.getValue()).booleanValue() && !(f3 < ((Float)this.maxselfdamage.getValue()).floatValue()) || !((f2 = Class352.Method1510(blockPos, (Entity)entityPlayer)) >= ((Float)this.mindamage.getValue()).floatValue()) && !(f2 > Class30.Method1454((Entity)this.entity)) && (!(Class30.Method1454((Entity)entityPlayer) < ((Float)this.faceplace.getValue()).floatValue()) || !(f2 >= 2.0f)) || !(f2 > f)) continue;
            f = f2;
        }
        return f;
    }

    private void getSwitchMode() {
        if (this.entity != null && !Class30.Method2776((EntityPlayer)Globals.mc.player)) {
            int n;
            if (this.sswitch.getValue() == Class649.Target) {
                Class155.Method1491(ItemEndCrystal.class, false);
            }
            if (this.sswitch.getValue() == Class649.Silent && (n = Class155.Method544(ItemEndCrystal.class)) != -1) {
                this.int4 = Globals.mc.player.inventory.currentItem;
                Globals.mc.player.inventory.currentItem = n;
                Globals.mc.playerController.updateController();
            }
        }
    }

    private void getSoundMode() {
        int n;
        if (this.sswitch.getValue() == Class649.Silent && Globals.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && (n = Class155.Method544(ItemEndCrystal.class)) != -1 && this.int4 != -1) {
            Globals.mc.player.inventory.currentItem = this.int4;
            Globals.mc.playerController.updateController();
        }
    }

    private void getAutoCrystalPwn() {
        boolean bl;
        if (!((Boolean)this.bbreak.getValue()).booleanValue() || this.bool1 || this.int3 < (Integer)this.breakticks.getValue() && this.delaymode.getValue() == Class252.Ticks || !this.clazz2.getLongNanoTime(((Float)this.breaktimer.getValue()).longValue()) && this.delaymode.getValue() == Class252.Timer) {
            return;
        }
        float f = 0.0f;
        EntityEnderCrystal entityEnderCrystal = null;
        boolean bl2 = bl = (this.Field3299.getValue() == Class460.Auto || (Boolean)this.placecheck.getValue() != false) && (Boolean)this.recalc.getValue() != false;
        if (((Boolean)this.bbreak.getValue()).booleanValue() && this.entity != null) {
            for (Entity entity : Globals.mc.world.loadedEntityList) {
                if (!this.getAutoCrystalMode(entity)) continue;
                float f2 = Class352.Method1469(entity, (Entity)this.entity);
                float f3 = Class352.Method1469(entity, (Entity)Globals.mc.player);
                if ((f2 < ((Float)this.mindamage.getValue()).floatValue() && !bl || bl && f2 < ((Float)this.RMinDamage.getValue()).floatValue()) && Class30.Method1454((Entity)this.entity) > ((Float)this.faceplace.getValue()).floatValue() || Class30.Method1454((Entity)this.entity) < ((Float)this.faceplace.getValue()).floatValue() && f2 < 1.0f || f3 >= ((Float)this.maxselfdamage.getValue()).floatValue() && !((Boolean)this.ignoreselfdamage.getValue()).booleanValue() || !(f2 > f)) continue;
                f = f2;
                entityEnderCrystal = (EntityEnderCrystal)entity;
            }
            if (entityEnderCrystal != null) {
                this.getPlaceValue((Entity)entityEnderCrystal);
                this.getPacketMode(entityEnderCrystal);
            }
        }
    }

    private boolean getAutoCrystalMode(Entity entity) {
        if (!(entity instanceof EntityEnderCrystal)) return false;
        if (!(Globals.mc.player.getDistanceSq(entity) <= Class29.getDouble6(((Float)this.breakrange.getValue()).floatValue()))) return false;
        if (Globals.mc.player.canEntityBeSeen(entity)) return true;
        if (Globals.mc.player.canEntityBeSeen(entity)) return false;
        double d = Globals.mc.player.getDistanceSq(entity);
        double d2 = this.Field3299.getValue() == Class460.Auto ? 3.0 : (double)((Float)this.breaktrace.getValue()).floatValue();
        if (!(d <= Class29.getDouble6(d2))) return false;
        return true;
    }

    private void getPlaceValue(Entity entity) {
        switch ((Class159)((Object)this.rotate.getValue())) {
            case Off: {
                break;
            }
            case Break: 
            case All: {
                this.dl_1 = Class354.Method1505(entity.posX + 0.5, entity.posY - 0.5, entity.posZ + 0.5, (EntityPlayer)Globals.mc.player);
                this.clazz.getNanoTime();
            }
        }
    }

    private void getPacketMode(EntityEnderCrystal entityEnderCrystal) {
        Globals.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)entityEnderCrystal));
        Globals.mc.player.swingArm(this.getSwing());
        this.int3 = 0;
        this.clazz2.getNanoTime();
    }

    private void getPlaceBreakMode() {
        List object;
        if (this.entity == null || !((Boolean)this.place.getValue()).booleanValue() || this.int2 < (Integer)this.placeticks.getValue() && this.delaymode.getValue() == Class252.Ticks || !this.clazz1.getLongNanoTime(((Float)this.placetimer.getValue()).longValue()) && this.delaymode.getValue() == Class252.Timer) {
            return;
        }
        float f = 0.5f;
        BlockPos blockPos = null;
        this.blockPos1 = null;
        for (BlockPos blockPos2 : this.L_collection) {
            float f2;
            float f3;
            if (!Class31.Method1223(blockPos2, Globals.mc.player.getDistanceSq(blockPos2) > Class29.getDouble6(this.Field3299.getValue() == Class460.Auto ? 6.0 : (double)((Float)this.placetrace.getValue()).floatValue()), 1.0f)) continue;
            if (this.Field3299.getValue() == Class460.Manual && ((Boolean)this.placecheck.getValue()).booleanValue() || this.Field3299.getValue() == Class460.Auto) {
                boolean bl;
                EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)Globals.mc.world, (double)((float)blockPos2.getX() + 0.5f), (double)(blockPos2.getY() + 1), (double)((float)blockPos2.getZ() + 0.5f));
                double d = this.Field3299.getValue() == Class460.Auto ? 3.0 : (double)((Float)this.breaktrace.getValue()).floatValue();
                boolean bl2 = bl = ((Boolean)this.packetpos.getValue() != false ? this.vec3d1.distanceTo(entityEnderCrystal.getPositionVector()) : Globals.mc.player.getDistanceSq((Entity)entityEnderCrystal)) > ((Boolean)this.packetpos.getValue() != false ? d : Class29.getDouble6(d));
                if (bl && !this.getPacketPos((Entity)entityEnderCrystal)) {
                    if (this.getPacketPos((Entity)entityEnderCrystal)) continue;
                    double d2 = Globals.mc.player.getDistanceSq((Entity)entityEnderCrystal);
                    double d3 = this.Field3299.getValue() == Class460.Auto ? 3.0 : (double)((Float)this.breaktrace.getValue()).floatValue();
                    if (!(d2 <= Class29.getDouble6(d3))) continue;
                }
            }
            if (!((double)(f3 = Class352.Method1510(blockPos2, (Entity)Globals.mc.player)) + 0.5 < (double)Class30.Method1454((Entity)Globals.mc.player)) && ((Boolean)this.nosuicide.getValue()).booleanValue() || !((Boolean)this.ignoreselfdamage.getValue()).booleanValue() && !(f3 < ((Float)this.maxselfdamage.getValue()).floatValue()) || !((f2 = Class352.Method1510(blockPos2, (Entity)this.entity)) >= ((Float)this.mindamage.getValue()).floatValue()) && !(f2 > Class30.Method1454((Entity)this.entity)) && (!(Class30.Method1454((Entity)this.entity) < ((Float)this.faceplace.getValue()).floatValue()) && !this.getArmor(this.entity) || !(f2 >= 2.0f)) || !(f2 > f)) continue;
            f = f2;
            blockPos = blockPos2;
        }
        if (blockPos == null && ((Boolean)this.recalc.getValue()).booleanValue() && (this.Field3299.getValue() == Class460.Auto || ((Boolean)this.placecheck.getValue()).booleanValue())) {
            object = (List) this.L_collection.iterator();

            Iterator<BlockPos> iterator = this.L_collection.iterator();
            while (iterator.hasNext()) {
                float f4;
                float f5;
                BlockPos blockPos2 = iterator.next();
                if (!Class31.Method1223(blockPos2, Globals.mc.player.getDistanceSq(blockPos2 = (BlockPos)object.stream().iterator().next()) > Class29.getDouble6(((Float)this.RPlaceTrace.getValue()).floatValue()), 1.0f) || !((double)(f5 = Class352.Method1510(blockPos2, (Entity)Globals.mc.player)) + 0.5 < (double)Class30.Method1454((Entity)Globals.mc.player)) && ((Boolean)this.nosuicide.getValue()).booleanValue() || !((Boolean)this.ignoreselfdamage.getValue()).booleanValue() && !(f5 < ((Float)this.maxselfdamage.getValue()).floatValue()) || !((f4 = Class352.Method1510(blockPos2, (Entity)this.entity)) >= ((Float)this.RMinDamage.getValue()).floatValue()) && !(f4 > Class30.Method1454((Entity)this.entity)) && (!(Class30.Method1454((Entity)this.entity) < ((Float)this.faceplace.getValue()).floatValue()) && !this.getArmor(this.entity) || !(f4 >= 2.0f)) || !(f4 > f)) continue;
                f = f4;
                blockPos = blockPos2;
            }
        }
        if (blockPos != null) {
            this.getSwitchMode();
            this.blockPos1 = blockPos;
            this.fl_1 = f;
            this.blockPos2 = blockPos;
            this.getRotate(blockPos);
            Class31.Method52(blockPos, Globals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (Boolean)this.strict.getValue(), this.vec3d);
            this.int2 = 0;
            this.clazz1.getNanoTime();
            object = (List) Field3272;
            synchronized (object) {
                int n = this.int1;
                this.hashMap.put(blockPos, n);
            }
            this.getSoundMode();
        }
    }

    private boolean getBlock(BlockPos blockPos) {
        EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)Globals.mc.world, (double)((float)blockPos.getX() + 0.5f), (double)(blockPos.getY() + 1), (double)((float)blockPos.getZ() + 0.5f));
        return this.getPacketPos((Entity)entityEnderCrystal) || !this.getPacketPos((Entity)entityEnderCrystal) && Globals.mc.player.getDistanceSq((Entity)entityEnderCrystal) <= Class29.getDouble6(this.Field3299.getValue() == Class460.Auto ? 3.0 : (double)((Float)this.breaktrace.getValue()).floatValue());
    }

    private void getRotate(BlockPos blockPos) {
        switch ((Class159)((Object)this.rotate.getValue())) {
            case Off: {
                break;
            }
            case All: 
            case Place: {
                Class353 class353 = null;
                try {
                    class353 = Class84.Method1492(blockPos);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (class353 != null) {
                    this.vec3d = class353.Method1493();
                }
                this.dl_1 = Class354.Method1505((double)blockPos.getX() + 0.5, (double)blockPos.getY() - 0.5, (double)blockPos.getZ() + 0.5, (EntityPlayer)Globals.mc.player);
                this.clazz.getNanoTime();
            }
        }
    }

    public boolean getPacketPos(Entity entity) {
        return Globals.mc.world.rayTraceBlocks((Boolean)this.packetpos.getValue() != false ? this.vec3d1.add(0.0, (double)Globals.mc.player.getEyeHeight(), 0.0) : new Vec3d(Globals.mc.player.posX, Globals.mc.player.posY + (double)Globals.mc.player.getEyeHeight(), Globals.mc.player.posZ), new Vec3d(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), false, true, false) == null;
    }

    private void getInt1() {
        ++this.int2;
        ++this.int3;
        this.bool1 = false;
        ++this.int1;
    }

    private BiConsumer getTicks() {
        Map.Entry<BlockPos, Integer> entry = (Map.Entry<BlockPos, Integer>) null;
        BlockPos blockPos = entry.getKey();
        int ticks = entry.getValue();

        int alpha = 255 - ticks * 8; // fade out over time (adjust multiplier as needed)
        if (alpha <= 0) return null;

        this.getRenderBox(blockPos, true, MathHelper.clamp(alpha, 0, 255), false);
        return null;
    }


    private void getHashedTicks() {
        if ((Class30.Method2776((EntityPlayer)Globals.mc.player) || this.sswitch.getValue() == Class649.Silent) && this.blockPos1 != null && ((Boolean)this.render.getValue()).booleanValue() && (((Boolean)this.renderbox.getValue()).booleanValue() || ((Boolean)this.rendertext.getValue()).booleanValue() || ((Boolean)this.renderoutline.getValue()).booleanValue())) {
            this.getRenderBox(this.blockPos1, true, 255, true);
            if (((Boolean)this.renderfade.getValue()).booleanValue()) {
                this.hashMap.forEach(getTicks());
            }
        }
    }

    private boolean getBlockPosition(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos.getX() == blockPos2.getX() && blockPos.getY() == blockPos2.getY() && blockPos.getZ() == blockPos2.getZ();
    }

    private void getRenderBox(BlockPos blockPos, boolean bl, int n, boolean bl2) {
        if (this.renderbox.getValue() == Class577.FullBox) {
            if (!((Boolean)this.rendergradient.getValue()).booleanValue()) {
                Class50.Method137(blockPos, (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.boxcolor.getValue(), (Boolean)this.rendercustomline.getValue(), (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.outlinecolor.getValue(), ((Float)this.renderlinewidth.getValue()).floatValue(), (Boolean)this.renderoutline.getValue(), (Boolean)this.renderbox.getValue(), bl ? ((Color)this.boxcolor.getValue()).getAlpha() : n, false);
            } else {
                Color color;
                Color color2 = (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.boxcolor.getValue();
                Color color3 = (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)((((Float)this.renderspeed.getValue()).floatValue() + ((Float)this.renderdelta.getValue()).floatValue()) * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.boxcolor2.getValue();
                Color color4 = (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.outlinecolor.getValue();
                Color color5 = color = (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)((((Float)this.renderspeed.getValue()).floatValue() + ((Float)this.renderdelta.getValue()).floatValue()) * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.outlinecolor2.getValue();
                if (((Boolean)this.renderbox.getValue()).booleanValue()) {
                    Class50.Method135(blockPos, new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), bl ? ((Color)this.boxcolor.getValue()).getAlpha() : n), new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), bl ? ((Color)this.boxcolor.getValue()).getAlpha() : n));
                }
                if (((Boolean)this.renderoutline.getValue()).booleanValue()) {
                    Class50.Method136(blockPos, (Boolean)this.rendercustomline.getValue() != false ? color4 : new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), bl ? 255 : n), (Boolean)this.rendercustomline.getValue() != false ? color : new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), bl ? 255 : n), ((Float)this.renderlinewidth.getValue()).floatValue());
                }
            }
        } else if (this.renderbox.getValue() == Class577.CustomBox) {
            Class50.Method793(blockPos, (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.boxcolor.getValue(), (Boolean)this.rendercustomline.getValue(), (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : (Color)this.outlinecolor.getValue(), ((Float)this.renderlinewidth.getValue()).floatValue(), (Boolean)this.renderoutline.getValue(), (Boolean)this.renderbox.getValue(), bl ? ((Color)this.boxcolor.getValue()).getAlpha() : n, false);
        } else if (this.renderbox.getValue() == Class577.Clock) {
            Class67.Method138(blockPos, (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : ((Boolean)this.rendercustomline.getValue() != false ? (Color)this.outlinecolor.getValue() : (Color)this.boxcolor.getValue()), ((Float)this.renderlinewidth.getValue()).floatValue());
        } else if (this.renderbox.getValue() == Class577.Triangle) {
            Class67.Method139(blockPos, (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : ((Boolean)this.rendercustomline.getValue() != false ? (Color)this.outlinecolor.getValue() : (Color)this.boxcolor.getValue()), ((Float)this.renderlinewidth.getValue()).floatValue());
        } else if (this.renderbox.getValue() == Class577.Butterfly) {
            Class67.Method140(blockPos, (Boolean)this.renderrainbow.getValue() != false ? this.getColorSetting((int)(((Float)this.renderspeed.getValue()).floatValue() * 1000.0f), ((Float)this.rendersaturation.getValue()).floatValue(), ((Float)this.renderbrightness.getValue()).floatValue()) : ((Boolean)this.rendercustomline.getValue() != false ? (Color)this.outlinecolor.getValue() : (Color)this.boxcolor.getValue()), ((Float)this.renderlinewidth.getValue()).floatValue());
        }
        if (((Boolean)this.rendertext.getValue()).booleanValue() && bl2) {
            if (this.renderbox.getValue() == Class577.FullBox) {
                Class50.Method846(blockPos, (Math.floor(this.fl_1) == (double)this.fl_1 ? Float.valueOf(this.fl_1) : String.format("%.1f", Float.valueOf(this.fl_1))) + "", ((Color)this.rendertextcolor.getValue()).getRGB());
            } else if (this.renderbox.getValue() == Class577.CustomBox) {
                Class50.Method841(blockPos, (Math.floor(this.fl_1) == (double)this.fl_1 ? Float.valueOf(this.fl_1) : String.format("%.1f", Float.valueOf(this.fl_1))) + "", ((Color)this.rendertextcolor.getValue()).getRGB());
            }
        }
    }

    private boolean getArmor(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return false;
        }
        for (ItemStack itemStack : entityPlayer.inventory.armorInventory) {
            if (itemStack == null) {
                return true;
            }
            if (!(Class352.Method1847(itemStack) < (float)((Integer)this.armorbreaker.getValue()).intValue())) continue;
            return true;
        }
        return false;
    }

    private Color getColorSetting(int n, float f, float f2) {
        float f3 = System.currentTimeMillis() % (long)n;
        return Color.getHSBColor(f3 /= (float)n, f, f2);
    }

    private String getMetaDataChat() {
        switch ((Class45)((Object)this.metadata.getValue())) {
            case Counter: {
                if (Manager.moduleManager.isModuleEnabled(CrystalCounter.class)) {
                    return ChatFormatting.WHITE + String.valueOf(CrystalCounter.Field2055) + ":" + CrystalCounter.Field2056;
                }
            }
            case Target: {
                return this.entity != null ? ChatFormatting.WHITE + this.entity.getName() : ChatFormatting.GREEN + "ON";
            }
            case Facing: {
                if (((Boolean)this.strict.getValue()).booleanValue()) {
                    return ChatFormatting.WHITE + Class31.Field934.getName();
                }
                return this.entity != null ? ChatFormatting.WHITE + this.entity.getName() : ChatFormatting.GREEN + "ON";
            }
        }
        return null;
    }

    private EnumHand getSwing() {
        switch ((Class158)((Object)this.swing.getValue())) {
            case Auto: {
                return Globals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            }
            case MainHand: {
                return EnumHand.MAIN_HAND;
            }
            case OffHand: {
                return EnumHand.OFF_HAND;
            }
        }
        return Globals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }

    private void getBoxColorPos(BlockPos blockPos, Integer n) {
        if (!this.getBlockPosition(blockPos, this.blockPos1) && this.int1 - n <= (Integer)this.fadeticks.getValue()) {
            int n2 = ((Color)this.boxcolor.getValue()).getAlpha();
            int n3 = (int)((float)n2 - (float)(this.int1 - n) / ((Integer)this.fadeticks.getValue()).floatValue() * (float)n2);
            this.getRenderBox(blockPos, false, n3, false);
        }
    }

    private void getVectorPlayer(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer.Position || eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer.PositionRotation) {
            CPacketPlayer cPacketPlayer = (CPacketPlayer)eventNetworkPostPacketEvent.getPacket();
            this.vec3d1 = new Vec3d(cPacketPlayer.x, cPacketPlayer.y, cPacketPlayer.z);
        }
    }

    private void getEraEvent(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() != Class53.PRE) {
            return;
        }
        if (eventPlayerUpdateWalking.isCancelled()) {
            this.dl_1 = null;
            return;
        }
        if (this.bool2) {
            this.dl_1 = null;
            return;
        }
        if (this.clazz.getLongNanoTime(1000L)) {
            this.dl_1 = null;
        }
        if (this.dl_1 != null) {
            eventPlayerUpdateWalking.setYaw(this.dl_1[0]);
            eventPlayerUpdateWalking.setPitch(this.dl_1[1]);
            eventPlayerUpdateWalking.cancel();
            Class202.Method934((float)this.dl_1[1], (float)this.dl_1[0]);
        }
    }

    private void getSPacketBlockPosAction(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        SPacketSpawnObject sPacketSpawnObject;
        Packet packet = eventNetworkPrePacketEvent.getPacket();
        if (((Boolean)this.instabreak.getValue()).booleanValue() && packet instanceof SPacketSpawnObject && (sPacketSpawnObject = (SPacketSpawnObject)packet).getType() == 51) {
            BlockPos blockPos = new BlockPos(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ());
            if ((this.blockPos2 != null && this.blockPos2.equals((Object)blockPos.down()) || this.getHashMap2(blockPos.down())) && this.clazz3.getLongNanoTime(((Float)this.timer.getValue()).longValue()) && !this.bool2 && Globals.mc.player.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()) <= (double)(((Float)this.breakrange.getValue()).floatValue() + 1.0f)) {
                CPacketUseEntity cPacketUseEntity = new CPacketUseEntity();
                cPacketUseEntity.entityId = sPacketSpawnObject.getEntityID();
                cPacketUseEntity.action = CPacketUseEntity.Action.ATTACK;
                Globals.mc.player.connection.sendPacket((Packet)cPacketUseEntity);
                Globals.mc.player.swingArm(this.getSwing());
                this.int3 = 0;
                this.clazz3.getNanoTime();
                if (((Boolean)this.cancel.getValue()).booleanValue()) {
                    this.bool1 = true;
                }
            }
        }
    }

    private void getSound(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        SPacketSoundEffect sPacketSoundEffect;
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketSoundEffect && ((Boolean)this.nosounddesync.getValue()).booleanValue() && (sPacketSoundEffect = (SPacketSoundEffect)eventNetworkPrePacketEvent.getPacket()).getCategory() == SoundCategory.BLOCKS && sPacketSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistance(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) <= 6.0)) continue;
                entity.setDead();
            }
        }
    }

    private void getHashedTickReturnType(Class66 class66) {
        this.getHashedTicks();
    }

    private void getEraEntity(EventPlayerUpdateWalking eventPlayerUpdateWalking) {
        if (eventPlayerUpdateWalking.getClass53() == Class53.PRE) {
            this.getEntity();
        }
    }

    private boolean getRainbow1() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderrainbow.getValue() != false;
    }

    private boolean getRainbow2() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderrainbow.getValue() != false;
    }

    private boolean getRainbow3() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderrainbow.getValue() != false;
    }

    private boolean getRainbow4() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderoutline.getValue() != false && (Boolean)this.rendercustomline.getValue() != false && (Boolean)this.renderrainbow.getValue() == false;
    }

    private boolean getRainbow5() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderoutline.getValue() != false && (Boolean)this.rendercustomline.getValue() != false;
    }

    private boolean getRainbow6() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderoutline.getValue() != false;
    }

    private boolean getRainbow7() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.renderoutline.getValue() != false;
    }

    private boolean getRainbow8() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.rendergradient.getValue() != false && (Boolean)this.renderrainbow.getValue() == false;
    }

    private boolean getRainbowValue() {
        return (Boolean)this.render.getValue();
    }

    private boolean getRainbowGradient1() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.rendergradient.getValue() != false;
    }

    private boolean getRenderBox() {
        return (Boolean)this.render.getValue() != false && this.renderbox.getValue() == Class577.FullBox;
    }

    private boolean getRenderText() {
        return (Boolean)this.render.getValue() != false && (Boolean)this.rendertext.getValue() != false;
    }

    private boolean getRender1() {
        return (Boolean)this.render.getValue();
    }

    private boolean getRender2() {
        return (Boolean)this.render.getValue();
    }

    private boolean getRender3() {
        return (Boolean)this.render.getValue();
    }

    private boolean getRender4() {
        return (Boolean)this.render.getValue();
    }

    private boolean getRender5() {
        return (Boolean)this.render.getValue();
    }

    private static boolean getBoolean() {
        return true;
    }

    private boolean getDelay1() {
        return this.delaymode.getValue() == Class252.Ticks;
    }

    private boolean getDelay2() {
        return this.delaymode.getValue() == Class252.Ticks;
    }

    private boolean getDelayTimer() {
        return this.delaymode.getValue() == Class252.Timer;
    }

    private boolean getDelayTimer2() {
        return this.delaymode.getValue() == Class252.Timer;
    }

    private static boolean getBoolean2() {
        return true;
    }

    private boolean getPlace1() {
        return (this.Field3299.getValue() == Class460.Auto || (Boolean)this.placecheck.getValue() != false) && (Boolean)this.recalc.getValue() != false;
    }

    private boolean getPlace2() {
        return (this.Field3299.getValue() == Class460.Auto || (Boolean)this.placecheck.getValue() != false) && (Boolean)this.recalc.getValue() != false;
    }

    private boolean getPlace3() {
        return this.Field3299.getValue() == Class460.Auto || (Boolean)this.placecheck.getValue() != false;
    }

    private boolean getPlace4() {
        return this.Field3299.getValue() == Class460.Manual && (Boolean)this.placecheck.getValue() != false || this.Field3299.getValue() == Class460.Auto;
    }

    private boolean getManual1() {
        return this.Field3299.getValue() == Class460.Manual;
    }

    private boolean getManual2() {
        return this.Field3299.getValue() == Class460.Manual;
    }

    private boolean getManual3() {
        return this.Field3299.getValue() == Class460.Manual;
    }

    private static boolean getBoolean3() {
        return true;
    }

    private boolean Method3951() {
        return (Boolean)this.ignoreselfdamage.getValue() == false;
    }

    private static boolean getBoolean4() {
        return true;
    }

    private static boolean getBoolean5() {
        return true;
    }

    private static boolean getBoolean6() {
        return true;
    }

    private boolean getInstaBreak() {
        return (Boolean)this.instabreak.getValue();
    }

    private boolean getInstaBreak2() {
        return (Boolean)this.instabreak.getValue();
    }

    private static boolean getBoolean8() {
        return true;
    }

    private static boolean getBoolean9() {
        return true;
    }

    private static boolean getBoolean10() {
        return true;
    }

    private boolean getRotatePlaceVal() {
        return this.rotate.getValue() == Class159.Place || this.rotate.getValue() == Class159.All;
    }

    public Setting getTarget() {
        try {
            if (!this.isEnabled() == false) {
                try {
                    this.getAutoCrystalPwn();
                    this.getAutoCrystalMode(this.entity.ridingEntity);
                    this.getAutoCrystalMode(this.entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();;
        }
        this.isEnabled();
        return this.target;
    }

}
