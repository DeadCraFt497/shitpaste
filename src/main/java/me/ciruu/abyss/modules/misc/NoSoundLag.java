package me.ciruu.abyss.modules.misc;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.network.EventNetworkPrePacketEvent;
import me.ciruu.abyss.modules.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class NoSoundLag
extends Module {
    private static final Set Field2551 = Sets.newHashSet(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    @EventHandler
    private Listener Field2552 = new Listener<EventNetworkPrePacketEvent>(NoSoundLag::Method3060, new Predicate[0]);

    public NoSoundLag() {
        super("NoSoundLag", "Prevents lag from sound packets.", Class11.MISC, "");
    }

    public static void Method3061(SPacketSoundEffect sPacketSoundEffect, float f) {
        BlockPos blockPos = new BlockPos(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ());
        ArrayList<Entity> arrayList = new ArrayList<Entity>();
        for (Entity entity : Globals.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || !(entity.getDistanceSq(blockPos) <= Class29.getDouble6(f))) continue;
            arrayList.add(entity);
        }
        for (Entity entity : arrayList) {
            entity.setDead();
        }
    }

    private static void Method3060(EventNetworkPrePacketEvent eventNetworkPrePacketEvent) {
        if (eventNetworkPrePacketEvent != null && eventNetworkPrePacketEvent.getPacket() != null && Globals.mc.player != null && Globals.mc.world != null && eventNetworkPrePacketEvent.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect)eventNetworkPrePacketEvent.getPacket();
            if (sPacketSoundEffect.getCategory() == SoundCategory.BLOCKS && sPacketSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                try {
                    NoSoundLag.Method3061(sPacketSoundEffect, 6.0f);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (Field2551.contains(sPacketSoundEffect.getSound())) {
                eventNetworkPrePacketEvent.cancel();
            }
        }
    }
}
