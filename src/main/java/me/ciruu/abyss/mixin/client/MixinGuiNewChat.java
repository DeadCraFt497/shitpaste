package me.ciruu.abyss.mixin.client;

import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={GuiNewChat.class}, priority=0x7FFFFFFE)
public abstract class MixinGuiNewChat {
}
