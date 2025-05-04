package me.ciruu.abyss.modules.movement;

import java.util.function.Predicate;
import me.ciruu.abyss.Class219;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class53;
import me.ciruu.abyss.events.client.EventClientKeyDown;
import me.ciruu.abyss.events.network.EventNetworkPostPacketEvent;
import me.ciruu.abyss.events.player.EventPlayerStoppedUsingItem;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import org.lwjgl.input.Keyboard;

public class NoSlowDown
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public Setting guimove = new Setting("GuiMove", "", this, true);
    public Setting anygui = new Setting("AnyGUI", "", (Module)this, (Object)true, this.mainwindow, this.guimove::getValue);
    public Setting items = new Setting("Items", "", this, true);
    public Setting soulsand = new Setting("SoulSand", "", this, true);
    public Setting strict = new Setting("Strict", "", this, false);
    public Setting setting2b2t = new Setting("2b2t", "", this, false);
    boolean Field3036 = false;
    private static final KeyBinding[] Field3037 = new KeyBinding[]{Globals.mc.gameSettings.keyBindForward, Globals.mc.gameSettings.keyBindBack, Globals.mc.gameSettings.keyBindLeft, Globals.mc.gameSettings.keyBindRight, Globals.mc.gameSettings.keyBindJump, Globals.mc.gameSettings.keyBindSprint};
    @EventHandler
    private Listener Field3038 = new Listener<EventPlayerUpdate>(this::Method3715, new Predicate[0]);
    @EventHandler
    private Listener Field3039 = new Listener<InputUpdateEvent>(this::Method3716, new Predicate[0]);
    @EventHandler
    private Listener Field3040 = new Listener<EventClientKeyDown>(this::Method3717, new Predicate[0]);
    @EventHandler
    private Listener Field3041 = new Listener<EventNetworkPostPacketEvent>(this::Method3718, new Predicate[0]);
    @EventHandler
    private Listener Field3042 = new Listener<EventPlayerStoppedUsingItem>(this::Method3719, new Predicate[0]);

    public NoSlowDown() {
        super("NoSlowDown", "Prevents slowing when eating or keeping inventory open.", Class11.MOVEMENT, "");
        this.addSetting(this.guimove);
        this.addSetting(this.anygui);
        this.addSetting(this.items);
        this.addSetting(this.soulsand);
        this.addSetting(this.strict);
        this.addSetting(this.setting2b2t);
    }

    private boolean Method3721() {
        return Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && Globals.mc.player.isHandActive();
    }

    private void Method3719(EventPlayerStoppedUsingItem eventPlayerStoppedUsingItem) {
        if (((Boolean)this.setting2b2t.getValue()).booleanValue() && Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold) {
            this.Field3036 = true;
        }
    }

    private void Method3718(EventNetworkPostPacketEvent eventNetworkPostPacketEvent) {
        if (eventNetworkPostPacketEvent.getPacket() instanceof CPacketPlayer && ((Boolean)this.strict.getValue()).booleanValue() && ((Boolean)this.items.getValue()).booleanValue() && Globals.mc.player.isHandActive() && !Globals.mc.player.isRiding()) {
            Globals.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(Globals.mc.player.posX), Math.floor(Globals.mc.player.posY), Math.floor(Globals.mc.player.posZ)), EnumFacing.DOWN));
        }
    }

    private void Method3717(EventClientKeyDown eventClientKeyDown) {
        if (((Boolean)this.guimove.getValue()).booleanValue() && eventClientKeyDown.getEra() == Class53.PRE && !(Globals.mc.currentScreen instanceof GuiChat)) {
            eventClientKeyDown.value = eventClientKeyDown.toggled;
        }
    }

    private void Method3716(InputUpdateEvent inputUpdateEvent) {
        if (((Boolean)this.items.getValue()).booleanValue() && Globals.mc.player.isHandActive() && !Globals.mc.player.isRiding()) {
            inputUpdateEvent.getMovementInput().moveStrafe *= 5.0f;
            inputUpdateEvent.getMovementInput().moveForward *= 5.0f;
        }
    }

    private void Method3715(EventPlayerUpdate eventPlayerUpdate) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (((Boolean)this.guimove.getValue()).booleanValue()) {
            if (Globals.mc.currentScreen instanceof GuiOptions || Globals.mc.currentScreen instanceof GuiVideoSettings || Globals.mc.currentScreen instanceof GuiScreenOptionsSounds || Globals.mc.currentScreen instanceof GuiContainer || Globals.mc.currentScreen instanceof GuiIngameMenu || Globals.mc.currentScreen instanceof Class219 || ((Boolean)this.anygui.getValue()).booleanValue() && Globals.mc.currentScreen != null) {
                for (KeyBinding keyBinding : Field3037) {
                    try {
                        KeyBinding.setKeyBindState((int)keyBinding.getKeyCode(), (boolean)Keyboard.isKeyDown((int)keyBinding.getKeyCode()));
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            } else if (Globals.mc.currentScreen == null) {
                for (KeyBinding keyBinding : Field3037) {
                    try {
                        if (Keyboard.isKeyDown((int)keyBinding.getKeyCode())) continue;
                        KeyBinding.setKeyBindState((int)keyBinding.getKeyCode(), (boolean)false);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        try {
            if (((Boolean)this.setting2b2t.getValue()).booleanValue() && this.Method3721() && !Globals.mc.player.isRiding()) {
                KeyBinding.setKeyBindState((int)Globals.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)true);
            }
            if (((Boolean)this.setting2b2t.getValue()).booleanValue() && this.Field3036 && !Globals.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                this.Field3036 = false;
                KeyBinding.setKeyBindState((int)Globals.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
                Globals.mc.gameSettings.keyBindSneak.pressed = false;
                Globals.mc.gameSettings.keyBindSneak.pressTime = 0;
                Globals.mc.player.setSneaking(false);
                Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
