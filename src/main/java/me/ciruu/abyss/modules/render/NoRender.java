package me.ciruu.abyss.modules.render;

import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class292;
import me.ciruu.abyss.events.particles.EventParticleEmitParticleAtEntity;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting explosion = new Setting("Explosion", "", this, true);
    public final Setting fire = new Setting("Fire", "", this, true);
    public final Setting portal = new Setting("Portal", "", this, true);
    public final Setting pumpkin = new Setting("Pumpkin", "", this, true);
    public final Setting totempop = new Setting("Items", "", this, false);
    public final Setting nausea = new Setting("Nausea", "", this, true);
    public final Setting hurtcam = new Setting("HurtCam", "", this, true);
    public final Setting fog = new Setting("Fog", "", this, false);
    public final Setting weather = new Setting("Weather", "", this, true);
    public final Setting bossbars = new Setting("BossBars", "", this, true);
    public final Setting bats = new Setting("Bats", "", this, false);
    public final Setting noarmor = new Setting("NoArmor", "", this, (Object)Class292.REMOVE);
    public final Setting skylight = new Setting("Skylight", "", this, false);
    public final Setting blocks = new Setting("Blocks", "", this, true);
    @EventHandler
    private Listener Field806 = new Listener<EventPlayerUpdate>(this::Method1056, new Predicate[0]);
    @EventHandler
    private Listener Field807 = new Listener<EventParticleEmitParticleAtEntity>(this::Method1057, new Predicate[0]);

    public NoRender() {
        super("NoRender", "Prevents to render certain things.", Class11.RENDER, "");
        this.addSetting(this.explosion);
        this.addSetting(this.fire);
        this.addSetting(this.portal);
        this.addSetting(this.pumpkin);
        this.addSetting(this.totempop);
        this.addSetting(this.totempop);
        this.addSetting(this.nausea);
        this.addSetting(this.hurtcam);
        this.addSetting(this.fog);
        this.addSetting(this.weather);
        this.addSetting(this.bossbars);
        this.addSetting(this.bats);
        this.addSetting(this.noarmor);
        this.addSetting(this.skylight);
        this.addSetting(this.blocks);
    }

    public void Method1059() {
        super.getEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public void Method1060() {
        super.getDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    private boolean Method1061(int n) {
        return EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() == n || EnumParticleTypes.EXPLOSION_LARGE.getParticleID() == n || EnumParticleTypes.EXPLOSION_HUGE.getParticleID() == n || EnumParticleTypes.SMOKE_NORMAL.getParticleID() == n || EnumParticleTypes.SMOKE_LARGE.getParticleID() == n;
    }

    @SubscribeEvent
    public void Method1062(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && ((Boolean)this.bossbars.getValue()).booleanValue()) {
            pre.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void Method1063(RenderLivingEvent.Pre pre) {
        if (((Boolean)this.bats.getValue()).booleanValue() && pre.getEntity() instanceof EntityBat) {
            pre.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void Method1064(PlaySoundAtEntityEvent playSoundAtEntityEvent) {
        if (((Boolean)this.bats.getValue()).booleanValue() && playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_BAT_AMBIENT) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_BAT_DEATH) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_BAT_HURT) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_BAT_LOOP) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_BAT_TAKEOFF)) {
            playSoundAtEntityEvent.setVolume(0.0f);
            playSoundAtEntityEvent.setPitch(0.0f);
            playSoundAtEntityEvent.setCanceled(true);
        }
    }

    private void Method1057(EventParticleEmitParticleAtEntity eventParticleEmitParticleAtEntity) {
        if (((Boolean)this.explosion.getValue()).booleanValue() && this.Method1061(eventParticleEmitParticleAtEntity.getEmit())) {
            eventParticleEmitParticleAtEntity.cancel();
        }
    }

    private void Method1056(EventPlayerUpdate eventPlayerUpdate) {
        // Check totem pop functionality
        if (((Boolean) this.totempop.getValue()).booleanValue()) {
            // Iterate through loaded entities and remove any EntityItem (items on the ground)
            Globals.mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityItem)
                    .map(entity -> (EntityItem) entity)
                    .forEach(entityItem -> entityItem.setDead());  // Using setDead() to remove the entity
        }

        // Check if weather is enabled and raining
        if (((Boolean) this.weather.getValue()).booleanValue() && Globals.mc.world.isRaining()) {
            // Set rain strength to 0 to stop the rain
            Globals.mc.world.setRainStrength(0.0f);
        }
    }

}
