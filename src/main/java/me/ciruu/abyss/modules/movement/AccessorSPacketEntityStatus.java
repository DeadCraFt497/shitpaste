package me.ciruu.abyss.modules.movement;

import net.minecraft.network.play.server.SPacketEntityStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SPacketEntityStatus.class)
public interface AccessorSPacketEntityStatus {

    @Accessor("logicOpcode") // Obfuscated name in MCP may vary (see below)
    byte getOpcode();

    @Accessor("entityId") // Obfuscated name in MCP may vary
    int getEntityId();
}
