package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.entity.EventHorseSaddled;
import me.ciruu.abyss.events.entity.EventSteerEntity;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractHorse.class})
public class MixinAbstractHorse {
    @Inject(method={"canBeSteered"}, at={@At(value="HEAD")}, cancellable=true)
    public void canBeSteered(CallbackInfoReturnable callbackInfoReturnable) {
        EventSteerEntity eventSteerEntity = new EventSteerEntity();
        AbyssMod.EVENT_BUS.post(eventSteerEntity);
        if (eventSteerEntity.isCancelled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    @Inject(method={"isHorseSaddled"}, at={@At(value="HEAD")}, cancellable=true)
    public void isHorseSaddled(CallbackInfoReturnable callbackInfoReturnable) {
        EventHorseSaddled eventHorseSaddled = new EventHorseSaddled();
        AbyssMod.EVENT_BUS.post(eventHorseSaddled);
        if (eventHorseSaddled.isCancelled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
