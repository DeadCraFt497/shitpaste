package me.ciruu.abyss.modules.misc;

import java.util.function.Predicate;
import me.ciruu.abyss.Class202;
import me.ciruu.abyss.Class22;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public class MCPearl
extends Module {
    private final Setting delay = new Setting("Delay", "Delay (MS)", (Module)this, (Object)Float.valueOf(100.0f), Float.valueOf(0.0f), Float.valueOf(1500.0f));
    private int Field1331;
    private boolean Field1332;
    private final Class22 Field1333 = new Class22();
    @EventHandler
    private Listener Field1334 = new Listener<InputEvent.MouseInputEvent>(this::Method1749, new Predicate[0]);
    @EventHandler
    private Listener Field1335 = new Listener<EventPlayerUpdate>(this::Method1750, new Predicate[0]);

    public MCPearl() {
        super("MCPearl", "Throw an ender pearl by clicking the wheel button in mouse.", Class11.MISC, "");
        this.addSetting(this.delay);
    }

    private void Method1750(EventPlayerUpdate eventPlayerUpdate) {
        if (this.Field1332 && this.Field1333.getLongNanoTime(((Float)this.delay.getValue()).longValue())) {
            this.Field1332 = false;
            Globals.mc.playerController.windowClick(Globals.mc.player.inventoryContainer.windowId, this.Field1331, Globals.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Globals.mc.player);
            Globals.mc.playerController.updateController();
        }
    }

    private void Method1749(InputEvent.MouseInputEvent mouseInputEvent) {
        if (Globals.mc.world == null || Globals.mc.player == null || Globals.mc.player.inventory == null) {
            return;
        }
        if (Mouse.getEventButton() == 2) {
            this.Field1331 = Class202.Method923(Items.ENDER_PEARL);
            int n = Class202.Method930(Items.ENDER_PEARL);
            if (this.Field1331 != -1 && this.Field1331 < 36) {
                Globals.mc.playerController.windowClick(Globals.mc.player.inventoryContainer.windowId, this.Field1331, Globals.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Globals.mc.player);
                Globals.mc.playerController.updateController();
                Globals.mc.playerController.processRightClick((EntityPlayer)Globals.mc.player, (World)Globals.mc.world, EnumHand.MAIN_HAND);
                this.Field1333.getNanoTime();
                this.Field1332 = true;
            } else if (n != -1) {
                int n2 = Globals.mc.player.inventory.currentItem;
                Globals.mc.player.inventory.currentItem = n;
                Globals.mc.playerController.updateController();
                Globals.mc.playerController.processRightClick((EntityPlayer)Globals.mc.player, (World)Globals.mc.world, EnumHand.MAIN_HAND);
                Globals.mc.player.inventory.currentItem = n2;
                Globals.mc.playerController.updateController();
            }
        }
    }
}
