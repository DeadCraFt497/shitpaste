package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.mixin.client.MixinEntity;
import me.ciruu.abyss.modules.movement.HighJump;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityLivingBase.class})
public abstract class MixinEntityLivingBase
extends MixinEntity {
    @Shadow
    protected ItemStack activeItemStack;
    @Shadow
    public float moveStrafing;
    @Shadow
    public float moveVertical;
    @Shadow
    public float moveForward;

    @Shadow
    public void jump() {
    }

    @Shadow
    public void swingArm(EnumHand enumHand) {
    }

    @Shadow
    public abstract boolean isElytraFlying();

    @Inject(method={"getJumpUpwardsMotion"}, at={@At(value="HEAD")}, cancellable=true)
    public void getModifiedJumpBoost(CallbackInfoReturnable callbackInfoReturnable) {
        if (Manager.moduleManager.isModuleEnabled(HighJump.class)) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.42f * ((Float)((HighJump)Manager.moduleManager.getModuleByClass(HighJump.class)).boost.getValue()).floatValue()));
        }
    }
}
