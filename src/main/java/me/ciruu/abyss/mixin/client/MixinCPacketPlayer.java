package me.ciruu.abyss.mixin.client;

import me.ciruu.abyss.mixin.accessor.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={CPacketPlayer.class})
public abstract class MixinCPacketPlayer
implements ICPacketPlayer {
    @Shadow
    protected double x;
    @Shadow
    protected double y;
    @Shadow
    protected double z;
    @Shadow
    protected float yaw;
    @Shadow
    protected float pitch;
    @Shadow
    protected boolean onGround;

    @Override
    public void setX(double d) {
        this.x = d;
    }

    @Override
    public void setY(double d) {
        this.y = d;
    }

    @Override
    public void setZ(double d) {
        this.z = d;
    }

    @Override
    public void setYaw(float f) {
        this.yaw = f;
    }

    @Override
    public void setPitch(float f) {
        this.pitch = f;
    }

    @Override
    public void setOnGround(boolean bl) {
        this.onGround = bl;
    }
}
