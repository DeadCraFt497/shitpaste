package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.hause.alienware.AlienKeyAgent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class AutoFish
extends Module {
    private final Setting castingDelay = new Setting("CastingDelay", "Number of ticks to wait after casting the rod to attempt a recast", (Module)this, (Object)20, 0, 40);
    private final Setting maxSoundDistance = new Setting("MaxSoundDistance", "Maximum distance between the splash sound and hook entity allowed (set to 0 to disable this feature)", (Module)this, (Object)Float.valueOf(2.0f), Float.valueOf(0.0f), Float.valueOf(12.0f));
    private final Setting failSafeTime = new Setting("FailSafeTime", "Maximum amount of time (in ticks) allowed until the hook is pulled in (set to 0 to disable this feature)", (Module)this, (Object)600, 0, 3000);
    private int Field1601 = 0;
    private int Field1602 = 0;
    private boolean Field1603 = false;
    public AlienKeyAgent __agent;

    @EventHandler
    private Listener Field1604 = new Listener<EventPlayerUpdate>(this::Method1995, new Predicate[0]);
    @EventHandler
    private Listener Field1605 = new Listener<InputEvent.MouseInputEvent>(this::Method1996, new Predicate[0]);
    @EventHandler
    private Listener Field1606 = new Listener<EventNetworkPrePacketEvent>(this::Method1997, new Predicate[0]);
    public AutoFish() {
        super("AutoFish", "Fish automatically.", Class11.MISC, "");
        this.addSetting(this.castingDelay);
        this.addSetting(this.maxSoundDistance);
        this.addSetting(this.failSafeTime);
    }

    public void Method1999() {
        super.getEnable();
        this.Method2000();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean Method2001(SPacketSoundEffect sPacketSoundEffect) {
        EntityPlayerSP entityPlayerSP = Globals.mc.player;
        if (!sPacketSoundEffect.getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH)) return false;
        if (entityPlayerSP == null) return false;
        if (entityPlayerSP.fishEntity == null) return false;
        if (((Float)this.maxSoundDistance.getValue()).floatValue() == 0.0f) return true;
        Vec3d vec3d = new Vec3d(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ());
        if (!(entityPlayerSP.fishEntity.getPositionVector().distanceTo(vec3d) <= (double)((Float)this.maxSoundDistance.getValue()).floatValue())) return false;
        return true;
    }

    private void Method2002() {
        if (this.Field1601 <= 0) {
            __agent.rightClick();
            this.Field1601 = (Integer)this.castingDelay.getValue();
        }
    }

    private void Method2000() {
        this.Field1601 = 0;
        this.Field1602 = 0;
        this.Field1603 = false;
    }

    private void Method1997(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        SPacketSoundEffect sPacketSoundEffect;
        if (eventNetworkPrePacketEvent.getPacket() instanceof SPacketSoundEffect && this.Method2001(sPacketSoundEffect = (SPacketSoundEffect)eventNetworkPrePacketEvent.getPacket())) {
            this.Method2002();
        }
    }

    private void Method1996(InputEvent.MouseInputEvent mouseInputEvent) {
        if (Globals.mc.gameSettings.keyBindUseItem.isKeyDown() && this.Field1602 > 0) {
            this.Field1601 = (Integer)this.castingDelay.getValue();
        }
    }

    private void Method1995(EventPlayerUpdate eventPlayerUpdate) {
        EntityPlayerSP entityPlayerSP = Globals.mc.player;
        ItemStack itemStack = entityPlayerSP.getHeldItemMainhand();
        if (this.Field1601 > (Integer)this.castingDelay.getValue()) {
            this.Field1601 = (Integer)this.castingDelay.getValue();
        } else if (this.Field1601 > 0) {
            --this.Field1601;
        }
        if (itemStack != null && itemStack.getItem() instanceof ItemFishingRod) {
            if (!this.Field1603) {
                this.Field1601 = (Integer)this.castingDelay.getValue();
                this.Field1603 = true;
            } else if (entityPlayerSP.fishEntity == null) {
                this.Method2002();
            } else {
                ++this.Field1602;
                if ((Integer)this.failSafeTime.getValue() != 0 && this.Field1602 > (Integer)this.failSafeTime.getValue()) {
                    this.Method2002();
                    this.Method2000();
                }
            }
        } else {
            this.Method2000();
        }
    }
}
