package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.Class325;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.debug.DebugRendererChunkBorder;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={DebugRendererChunkBorder.class})
public abstract class MixinDebugRendererChunkBorder {
    @Redirect(method={"render"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP useCameraEntity(Minecraft minecraft) {
        Entity entity;
        if (Class325.Method1305() && (entity = minecraft.getRenderViewEntity()) instanceof EntityPlayerSP) {
            return (EntityPlayerSP)entity;
        }
        return minecraft.player;
    }
}
