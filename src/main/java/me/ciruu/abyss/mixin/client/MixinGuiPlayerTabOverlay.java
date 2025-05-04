package me.ciruu.abyss.mixin.client;

import java.util.List;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.render.TabFriends;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GuiPlayerTabOverlay.class})
public class MixinGuiPlayerTabOverlay {
    @Redirect(method={"renderPlayerlist"}, at=@At(value="INVOKE", target="Ljava/util/List;subList(II)Ljava/util/List;"))
    public List subListHook(List list, int n, int n2) {
        return list.subList(n, Manager.moduleManager.isModuleEnabled(TabFriends.class) && ((Boolean)((TabFriends)Manager.moduleManager.getModuleByClass(TabFriends.class)).extratab.getValue()).booleanValue() ? (list.size() < 255 ? list.size() : list.size() - 1) : n2);
    }

    @Inject(method={"getPlayerName"}, at={@At(value="HEAD")}, cancellable=true)
    public void getPlayerName(NetworkPlayerInfo networkPlayerInfo, CallbackInfoReturnable callbackInfoReturnable) {
        if (Manager.moduleManager.isModuleEnabled(TabFriends.class)) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(((TabFriends)Manager.moduleManager.getModuleByClass(TabFriends.class)).Method1596(networkPlayerInfo));
        }
    }
}
