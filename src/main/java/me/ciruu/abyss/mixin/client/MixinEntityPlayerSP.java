package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class325;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.player.EntityPlayerAttackEntityFrom;
import me.ciruu.abyss.events.player.EventPlayerJump;
import me.ciruu.abyss.events.player.EventPlayerMotionUpdate;
import me.ciruu.abyss.events.player.EventPlayerMove;
import me.ciruu.abyss.events.player.EventPlayerPushOutOfBlocks;
import me.ciruu.abyss.events.player.EventPlayerSwingArm;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.events.player.EventPlayerUpdateWalking;
import me.ciruu.abyss.modules.movement.BoatFly;
import me.ciruu.abyss.modules.render.OffhandSwing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.MoverType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP
extends MixinAbstractClientPlayer {
    private EventPlayerUpdateWalking _event;
    @Final
    public NetHandlerPlayClient connection;

    @Override
    @Shadow
    public abstract void swingArm(EnumHand var1);

    @Shadow
    protected abstract void updateAutoJump(float var1, float var2);

    @Redirect(method={"onLivingUpdate"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void closeScreen(EntityPlayerSP entityPlayerSP) {
    }

    @Redirect(method={"onLivingUpdate"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void closeScreen(Minecraft minecraft, GuiScreen guiScreen) {
    }

    @Inject(method={"move"}, at={@At(value="HEAD")}, cancellable=true)
    public void move(MoverType moverType, double d, double d2, double d3, CallbackInfo callbackInfo) {
        EventPlayerMove eventPlayerMove = new EventPlayerMove(Class53.PRE, moverType, d, d2, d3);
        AbyssMod.EVENT_BUS.post(eventPlayerMove);
        if (eventPlayerMove.isCancelled()) {
            double d4 = this.posX;
            double d5 = this.posZ;
            super.move(moverType, eventPlayerMove.getDoubVal1(), eventPlayerMove.getDoubVal2(), eventPlayerMove.getDoubVal3());
            this.updateAutoJump((float)(this.posX - d4), (float)(this.posZ - d5));
            callbackInfo.cancel();
        }
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void OnPreUpdateWalkingPlayer(CallbackInfo callbackInfo) {
        this._event = new EventPlayerUpdateWalking(Class53.PRE, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround);
        AbyssMod.EVENT_BUS.post(this._event);
        if (this._event.isCancelled()) {
            AbyssMod.EVENT_BUS.post(new EventPlayerMotionUpdate(Class53.PRE, this._event.getPitch(), this._event.getYaw()));
            callbackInfo.cancel();
            Class202.Method939(this._event);
            this.postWalkingUpdate();
        }
        if (this._event.getBoolean2()) {
            callbackInfo.cancel();
        }
    }

    private void postWalkingUpdate() {
        if (this._event.getConsumerValueStraight() != null) {
            this._event.getConsumerValueStraight().accept(null);
        }
        this._event.getClass53(Class53.POST);
        AbyssMod.EVENT_BUS.post(this._event);
        if (this._event.isCancelled()) {
            AbyssMod.EVENT_BUS.post(new EventPlayerMotionUpdate(Class53.PRE, this._event.getPitch(), this._event.getYaw()));
        }
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="RETURN")})
    public void OnPostUpdateWalkingPlayer(CallbackInfo callbackInfo) {
        this.postWalkingUpdate();
    }

    @Inject(method={"onUpdate"}, at={@At(value="HEAD")}, cancellable=true)
    public void onUpdate(CallbackInfo callbackInfo) {
        EventPlayerUpdate eventPlayerUpdate = new EventPlayerUpdate();
        AbyssMod.EVENT_BUS.post(eventPlayerUpdate);
        if (eventPlayerUpdate.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"swingArm"}, at={@At(value="HEAD")}, cancellable=true)
    public void swingArm(EnumHand enumHand, CallbackInfo callbackInfo) {
        EventPlayerSwingArm eventPlayerSwingArm = new EventPlayerSwingArm(enumHand);
        AbyssMod.EVENT_BUS.post(eventPlayerSwingArm);
        if (eventPlayerSwingArm.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"pushOutOfBlocks(DDD)Z"}, at={@At(value="HEAD")}, cancellable=true)
    public void pushOutOfBlocks(double d, double d2, double d3, CallbackInfoReturnable callbackInfoReturnable) {
        EventPlayerPushOutOfBlocks eventPlayerPushOutOfBlocks = new EventPlayerPushOutOfBlocks(d, d2, d3);
        AbyssMod.EVENT_BUS.post(eventPlayerPushOutOfBlocks);
        if (eventPlayerPushOutOfBlocks.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Override
    public void jump() {
        try {
            EventPlayerJump eventPlayerJump = new EventPlayerJump(this.motionX, this.motionZ);
            AbyssMod.EVENT_BUS.post(eventPlayerJump);
            if (!eventPlayerJump.isCancelled()) {
                super.jump();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Inject(method={"setServerBrand(Ljava/lang/String;)V"}, at={@At(value="HEAD")})
    public void getBrand(String string, CallbackInfo callbackInfo) {
        if (Manager.Field1645 != null) {
            Manager.Field1645.Method2313(string);
        }
    }

    @Inject(method={"attackEntityFrom"}, at={@At(value="HEAD")}, cancellable=false)
    public void attackEntityFrom(DamageSource damageSource, float f, CallbackInfoReturnable callbackInfoReturnable) {
        AbyssMod.EVENT_BUS.post(new EntityPlayerAttackEntityFrom(damageSource, f));
    }

    @Inject(method={"swingArm"}, at={@At(value="HEAD")}, cancellable=true)
    public void swingArmRender(EnumHand enumHand, CallbackInfo callbackInfo) {
        try {
            if (Manager.moduleManager.isModuleEnabled(OffhandSwing.class)) {
                super.swingArm(EnumHand.OFF_HAND);
                Globals.mc.player.connection.sendPacket((Packet)new CPacketAnimation(enumHand));
                callbackInfo.cancel();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Inject(method={"isRowingBoat"}, at={@At(value="RETURN")}, cancellable=true)
    private void isRowing(CallbackInfoReturnable callbackInfoReturnable) {
        if (Manager.moduleManager.getModuleByClass(BoatFly.class) != null && Manager.moduleManager.isModuleEnabled(BoatFly.class)) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Override
    public boolean isSpectator() {
        return this.isSpec() || Class325.Method1304();
    }

    private boolean isSpec() {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(Globals.mc.player.getGameProfile().getId());
        return networkPlayerInfo != null && networkPlayerInfo.getGameType() == GameType.SPECTATOR;
    }

    @Inject(method={"isCurrentViewEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void allowPlayerMovementInFreeCameraMode(CallbackInfoReturnable callbackInfoReturnable) {
        if (Class325.Method1305()) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
