package me.ciruu.abyss.modules.render;

import java.util.function.Predicate;
import me.ciruu.abyss.Class207;
import me.ciruu.abyss.Class219;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class350;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.events.render.EventRenderItemSide;
import me.ciruu.abyss.events.render.EventRenderPostFirstPerson;
import me.ciruu.abyss.events.render.EventRenderPreFirstPerson;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemViewModel
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting items = new Setting("Items", "", this, true);
    public final Setting itemsFOV = new Setting("ItemsFOV", "", this, 140, 30, 170, this.mainwindow, this.items::getValue);
    public final Setting playerFOV = new Setting("PlayerFOV", "", this, 140, 30, 170, this.mainwindow, this::addSetting98);
    public final Setting offset = new Setting("Offset", "", this, true);
    private final Setting zoombind = new Setting("ZoomBind", "", this, new Class207(11));
    public final Setting noeat = new Setting("NoEat", "", this, true);
    public final Setting alpha = new Setting("Alpha", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public final Setting leftX = new Setting("LeftX", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(-5.0f), Float.valueOf(5.0f));
    public final Setting leftY = new Setting("LeftY", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(-5.0f), Float.valueOf(5.0f));
    public final Setting leftZ = new Setting("LeftZ", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(-5.0f), Float.valueOf(5.0f));
    public final Setting rightX = new Setting("RightX", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(-5.0f), Float.valueOf(5.0f));
    public final Setting rightY = new Setting("RightY", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(-5.0f), Float.valueOf(5.0f));
    public final Setting rightZ = new Setting("RightZ", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(-5.0f), Float.valueOf(5.0f));
    public final Setting scaleX = new Setting("ScaleX", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    public final Setting scaleY = new Setting("ScaleY", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    public final Setting scaleZ = new Setting("ScaleZ", "", (Module)this, (Object)Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f));
    public final Setting rotateMainX = new Setting("RotateMainX", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f));
    public final Setting rotateMainY = new Setting("RotateMainY", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f));
    public final Setting rotateMainZ = new Setting("RotateMainZ", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f));
    public final Setting rotateOffX = new Setting("RotateOffX", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f));
    public final Setting rotateOffY = new Setting("RotateOffY", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f));
    public final Setting rotateOffZ = new Setting("RotateOffZ", "", (Module)this, (Object)Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f));
    public final Setting animationX = new Setting("AnimationX", "", this, false);
    public final Setting animationY = new Setting("AnimationY", "", this, false);
    public final Setting animationZ = new Setting("AnimationZ", "", this, false);
    public final Setting animationSpeed = new Setting("AnimationSpeed", "", (Module)this, (Object)Float.valueOf(5.0f), Float.valueOf(0.5f), Float.valueOf(10.0f));
    private float Field3020;
    private boolean Field3021 = false;
    @EventHandler
    private Listener Field3022 = new Listener<EventPlayerUpdate>(this::addSetting99, new Predicate[0]);
    @EventHandler
    private Listener Field3023 = new Listener<Class350>(this::Method3700, new Predicate[0]);
    @EventHandler
    private final Listener Field3024 = new Listener<EventRenderPreFirstPerson>(this::Method3701, new Predicate[0]);
    @EventHandler
    private final Listener Field3025 = new Listener<EventRenderPostFirstPerson>(this::Method3702, new Predicate[0]);
    @EventHandler
    private final Listener Field3026 = new Listener<EventRenderItemSide>(this::Method3703, new Predicate[0]);

    public ItemViewModel() {
        super("ItemViewModel", "Allows you to change your item view perspective.", Class11.RENDER, "");
        this.addSetting(this.items);
        this.addSetting(this.itemsFOV);
        this.addSetting(this.playerFOV);
        this.addSetting(this.offset);
        this.addSetting(this.zoombind);
        this.addSetting(this.noeat);
        this.addSetting(this.alpha);
        this.addSetting(this.leftX);
        this.addSetting(this.leftY);
        this.addSetting(this.leftZ);
        this.addSetting(this.rightX);
        this.addSetting(this.rightY);
        this.addSetting(this.rightZ);
        this.addSetting(this.scaleX);
        this.addSetting(this.scaleY);
        this.addSetting(this.scaleZ);
        this.addSetting(this.rotateMainX);
        this.addSetting(this.rotateMainY);
        this.addSetting(this.rotateMainZ);
        this.addSetting(this.rotateOffX);
        this.addSetting(this.rotateOffY);
        this.addSetting(this.rotateOffZ);
        this.addSetting(this.animationX);
        this.addSetting(this.animationY);
        this.addSetting(this.animationZ);
        this.addSetting(this.animationSpeed);
    }

    public void Method3705() {
        super.getEnable();
        this.Field3020 = Globals.mc.gameSettings.fovSetting;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public void Method3706() {
        super.getDisable();
        Globals.mc.gameSettings.fovSetting = this.Field3020;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void Method3707(EntityViewRenderEvent.FOVModifier fOVModifier) {
        if (((Boolean)this.items.getValue()).booleanValue() && !this.Field3021) {
            fOVModifier.setFOV((float)((Integer)this.itemsFOV.getValue()).intValue());
        }
    }

    private void Method3703(EventRenderItemSide eventRenderItemSide) {
        if (!(eventRenderItemSide.getBool() || ((Boolean)this.noeat.getValue()).booleanValue() && ((Boolean)this.noeat.getValue()).booleanValue() && Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && Globals.mc.player.isHandActive())) {
            eventRenderItemSide.setX(((Float)this.scaleX.getValue()).floatValue());
            eventRenderItemSide.setY(((Float)this.scaleY.getValue()).floatValue());
            eventRenderItemSide.setZ(((Float)this.scaleZ.getValue()).floatValue());
        } else if (!(!eventRenderItemSide.getBool() || ((Boolean)this.noeat.getValue()).booleanValue() && ((Boolean)this.noeat.getValue()).booleanValue() && Globals.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood && Globals.mc.player.isHandActive())) {
            eventRenderItemSide.setX(((Float)this.scaleX.getValue()).floatValue());
            eventRenderItemSide.setY(((Float)this.scaleY.getValue()).floatValue());
            eventRenderItemSide.setZ(((Float)this.scaleZ.getValue()).floatValue());
        }
    }

    private void Method3702(EventRenderPostFirstPerson eventRenderPostFirstPerson) {
        if (!(eventRenderPostFirstPerson.Method1052() != EnumHandSide.RIGHT || ((Boolean)this.noeat.getValue()).booleanValue() && ((Boolean)this.noeat.getValue()).booleanValue() && Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && Globals.mc.player.isHandActive())) {
            GlStateManager.rotate((float)((Float)this.rotateMainY.getValue()).floatValue(), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((Float)this.rotateMainX.getValue()).floatValue(), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)((Float)this.rotateMainZ.getValue()).floatValue(), (float)0.0f, (float)0.0f, (float)1.0f);
        } else if (!(eventRenderPostFirstPerson.Method1052() != EnumHandSide.LEFT || ((Boolean)this.noeat.getValue()).booleanValue() && ((Boolean)this.noeat.getValue()).booleanValue() && Globals.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood && Globals.mc.player.isHandActive())) {
            GlStateManager.rotate((float)((Float)this.rotateOffY.getValue()).floatValue(), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((Float)this.rotateOffX.getValue()).floatValue(), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)((Float)this.rotateOffZ.getValue()).floatValue(), (float)0.0f, (float)0.0f, (float)1.0f);
        }
    }

    private void Method3701(EventRenderPreFirstPerson eventRenderPreFirstPerson) {
        if (!(eventRenderPreFirstPerson.Method1052() != EnumHandSide.RIGHT || ((Boolean)this.noeat.getValue()).booleanValue() && ((Boolean)this.noeat.getValue()).booleanValue() && Globals.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && Globals.mc.player.isHandActive())) {
            GlStateManager.translate((float)((Float)this.rightX.getValue()).floatValue(), (float)((Float)this.rightY.getValue()).floatValue(), (float)((Float)this.rightZ.getValue()).floatValue());
        } else if (!(eventRenderPreFirstPerson.Method1052() != EnumHandSide.LEFT || ((Boolean)this.noeat.getValue()).booleanValue() && ((Boolean)this.noeat.getValue()).booleanValue() && Globals.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood && Globals.mc.player.isHandActive())) {
            GlStateManager.translate((float)((Float)this.leftX.getValue()).floatValue(), (float)((Float)this.leftY.getValue()).floatValue(), (float)((Float)this.leftZ.getValue()).floatValue());
        }
    }

    private void Method3700(Class350 class350) {
        if (!(Globals.mc.currentScreen instanceof Class219)) {
            if (((Class207)this.zoombind.getValue()).Method592() != class350.Method1535()) {
                this.Field3021 = false;
            }
            if (((Class207)this.zoombind.getValue()).Method592() == class350.Method1535()) {
                this.Field3021 = true;
            }
        }
    }

    private void addSetting99(EventPlayerUpdate eventPlayerUpdate) {
        Globals.mc.gameSettings.fovSetting = ((Integer)this.playerFOV.getValue()).intValue();
        if (((Boolean)this.animationY.getValue()).booleanValue()) {
            this.rotateMainY.setValue(Float.valueOf((((Float)this.rotateMainY.getValue()).floatValue() + ((Float)this.animationSpeed.getValue()).floatValue()) % 360.0f));
            this.rotateOffY.setValue(Float.valueOf((((Float)this.rotateOffY.getValue()).floatValue() - ((Float)this.animationSpeed.getValue()).floatValue()) % 360.0f));
            if (((Float)this.rotateOffY.getValue()).floatValue() < 0.0f) {
                this.rotateOffY.setValue(Float.valueOf(((Float)this.rotateOffY.getValue()).floatValue() + 360.0f));
            }
        }
        if (((Boolean)this.animationX.getValue()).booleanValue()) {
            this.rotateMainX.setValue(Float.valueOf((((Float)this.rotateMainX.getValue()).floatValue() - ((Float)this.animationSpeed.getValue()).floatValue()) % 360.0f));
            if (((Float)this.rotateMainX.getValue()).floatValue() < 0.0f) {
                this.rotateMainX.setValue(Float.valueOf(((Float)this.rotateMainX.getValue()).floatValue() + 360.0f));
            }
            this.rotateOffX.setValue(Float.valueOf((((Float)this.rotateOffX.getValue()).floatValue() - ((Float)this.animationSpeed.getValue()).floatValue()) % 360.0f));
            if (((Float)this.rotateOffX.getValue()).floatValue() < 0.0f) {
                this.rotateOffX.setValue(Float.valueOf(((Float)this.rotateOffX.getValue()).floatValue() + 360.0f));
            }
        }
        if (((Boolean)this.animationZ.getValue()).booleanValue()) {
            this.rotateMainZ.setValue(Float.valueOf((((Float)this.rotateMainZ.getValue()).floatValue() + ((Float)this.animationSpeed.getValue()).floatValue()) % 360.0f));
            this.rotateOffZ.setValue(Float.valueOf((((Float)this.rotateOffZ.getValue()).floatValue() - ((Float)this.animationSpeed.getValue()).floatValue()) % 360.0f));
            if (((Float)this.rotateOffZ.getValue()).floatValue() < 0.0f) {
                this.rotateOffZ.setValue(Float.valueOf(((Float)this.rotateOffZ.getValue()).floatValue() + 360.0f));
            }
        }
    }

    private boolean addSetting98() {
        return (Boolean)this.items.getValue() == false;
    }
}
