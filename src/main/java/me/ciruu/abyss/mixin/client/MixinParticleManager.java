package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.particles.EventParticleEmitParticleAtEntity;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ParticleManager.class})
public class MixinParticleManager {
    @Inject(method={"spawnEffectParticle"}, at={@At(value="HEAD")}, cancellable=true)
    private void onSpawnEffectParticle(int n, double d, double d2, double d3, double d4, double d5, double d6, int[] nArray, CallbackInfoReturnable callbackInfoReturnable) {
        EventParticleEmitParticleAtEntity eventParticleEmitParticleAtEntity = new EventParticleEmitParticleAtEntity(n);
        AbyssMod.EVENT_BUS.post(eventParticleEmitParticleAtEntity);
        if (eventParticleEmitParticleAtEntity.isCancelled()) {
            callbackInfoReturnable.cancel();
        }
    }
}
