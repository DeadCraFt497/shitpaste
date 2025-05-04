package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.AbyssMod;
import me.ciruu.abyss.events.render.EventRenderCullCavesEvent;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={VisGraph.class})
public class MixinVisGraph {
    @Inject(method={"setOpaqueCube"}, at={@At(value="HEAD")}, cancellable=true)
    public void setOpaqueCube(BlockPos blockPos, CallbackInfo callbackInfo) {
        EventRenderCullCavesEvent eventRenderCullCavesEvent = new EventRenderCullCavesEvent();
        AbyssMod.EVENT_BUS.post(eventRenderCullCavesEvent);
        if (eventRenderCullCavesEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
