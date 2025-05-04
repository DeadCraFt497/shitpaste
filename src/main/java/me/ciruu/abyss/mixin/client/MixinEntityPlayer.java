package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.player.EventPlayerApplyCollision;
import me.ciruu.abyss.events.player.EventPlayerPushedByWater;
import me.ciruu.abyss.events.player.EventPlayerTravel;
import me.ciruu.abyss.mixin.client.MixinEntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPlayer.class}, priority=0x7FFFFFFF)
public abstract class MixinEntityPlayer
extends MixinEntityLivingBase {
    @Shadow
    public void onUpdate() {
    }

    @Inject(method={"travel"}, at={@At(value="HEAD")}, cancellable=true)
    public void travel(float f, float f2, float f3, CallbackInfo callbackInfo) {
        if (EntityPlayerSP.class.isAssignableFrom(this.getClass())) {
            EventPlayerTravel eventPlayerTravel = new EventPlayerTravel(f, f2, f3);
            AbyssMod.EVENT_BUS.post(eventPlayerTravel);
            if (eventPlayerTravel.isCancelled()) {
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method={"applyEntityCollision"}, at={@At(value="HEAD")}, cancellable=true)
    public void applyEntityCollision(Entity entity, CallbackInfo callbackInfo) {
        EventPlayerApplyCollision eventPlayerApplyCollision = new EventPlayerApplyCollision(entity);
        AbyssMod.EVENT_BUS.post(eventPlayerApplyCollision);
        if (eventPlayerApplyCollision.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"isPushedByWater()Z"}, at={@At(value="HEAD")}, cancellable=true)
    public void isPushedByWater(CallbackInfoReturnable callbackInfoReturnable) {
        EventPlayerPushedByWater eventPlayerPushedByWater = new EventPlayerPushedByWater();
        AbyssMod.EVENT_BUS.post(eventPlayerPushedByWater);
        if (eventPlayerPushedByWater.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
