package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class29;
import me.ciruu.abyss.Class354;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.Manager;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class Tracers
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting players = new Setting("Players", "Traces players", (Module)this, (Object)true, this.mainwindow, Tracers::Method4035);
    private final Setting playersColor = new Setting("PlayerColor", "", (Module)this, (Object)new Color(-16711936), this.mainwindow, this::Method4036);
    public final Setting friends = new Setting("Friends", "Traces friends", (Module)this, (Object)true, this.mainwindow, Tracers::Method4037);
    private final Setting friendsColor = new Setting("FriendColor", "", (Module)this, (Object)new Color(-16711698), this.mainwindow, this::Method4038);
    public final Setting invisibles = new Setting("Invisibles", "Traces invisibles", (Module)this, (Object)true, this.mainwindow, Tracers::Method4039);
    private final Setting invisiblesColor = new Setting("InvisibleColor", "", (Module)this, (Object)new Color(-16777216), this.mainwindow, this::Method4040);
    public final Setting monsters = new Setting("Monsters", "Traces monsters", (Module)this, (Object)false, this.mainwindow, Tracers::Method4041);
    private final Setting monstersColor = new Setting("MonsterColor", "", (Module)this, (Object)new Color(-65536), this.mainwindow, this::Method4042);
    public final Setting animals = new Setting("Animals", "Traces animals", (Module)this, (Object)false, this.mainwindow, Tracers::Method4043);
    private final Setting animalsColor = new Setting("AnimalColor", "", (Module)this, (Object)new Color(-29440), this.mainwindow, this::Method4044);
    public final Setting vehicles = new Setting("Vehicles", "Traces Vehicles", (Module)this, (Object)false, this.mainwindow, Tracers::Method4045);
    private final Setting vehiclesColor = new Setting("VehicleColor", "", (Module)this, (Object)new Color(-256), this.mainwindow, this::Method4046);
    public final Setting items = new Setting("Items", "Traces items", (Module)this, (Object)true, this.mainwindow, Tracers::Method4047);
    private final Setting itemsColor = new Setting("ItemColor", "", (Module)this, (Object)new Color(-5635841), this.mainwindow, this::Method4048);
    public final Setting others = new Setting("Others", "Traces others", (Module)this, (Object)false, this.mainwindow, Tracers::Method4049);
    private final Setting othersColor = new Setting("OtherColor", "", (Module)this, (Object)new Color(-1), this.mainwindow, this::Method4050);
    public final Setting width = new Setting("Width", "Width", this, 1, 1, 30, this.mainwindow, Tracers::Method4051);
    public final Setting yradius = new Setting("YRadius", "", (Module)this, (Object)false, this.mainwindow, Tracers::Method4052);
    public final Setting blocks = new Setting("Blocks", "", this, 30, 1, 255, this.mainwindow, this::Method4053);
    @EventHandler
    private Listener Field3395 = new Listener<Class66>(this::Method4054, new Predicate[0]);

    public Tracers() {
        super("Tracers", "Draws tracer lines to players.", Class11.RENDER, "");
        this.addSetting(this.players);
        this.addSetting(this.playersColor);
        this.addSetting(this.friends);
        this.addSetting(this.friendsColor);
        this.addSetting(this.invisibles);
        this.addSetting(this.invisiblesColor);
        this.addSetting(this.monsters);
        this.addSetting(this.monstersColor);
        this.addSetting(this.animals);
        this.addSetting(this.animalsColor);
        this.addSetting(this.vehicles);
        this.addSetting(this.vehiclesColor);
        this.addSetting(this.items);
        this.addSetting(this.itemsColor);
        this.addSetting(this.others);
        this.addSetting(this.othersColor);
        this.addSetting(this.width);
        this.addSetting(this.yradius);
        this.addSetting(this.blocks);
    }

    public boolean Method4056(Entity entity) {
        if (entity == Minecraft.getMinecraft().player) {
            return false;
        }
        if (((Boolean)this.yradius.getValue()).booleanValue() && Math.abs(Globals.mc.player.posY - entity.posY) > (double)((Integer)this.blocks.getValue()).intValue()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            if (!((Boolean)this.players.getValue()).booleanValue()) {
                return false;
            }
            if (((Boolean)this.friends.getValue()).booleanValue()) {
                return true;
            }
            return !Manager.Field223.Method711(entity);
        }
        if (Class354.Method3265(entity) || Class354.Method3256(entity)) {
            return (Boolean)this.monsters.getValue();
        }
        if (Class354.Method3257(entity)) {
            return (Boolean)this.animals.getValue();
        }
        if (entity instanceof EntityBoat || entity instanceof EntityMinecart) {
            return (Boolean)this.vehicles.getValue();
        }
        if (entity instanceof EntityItem) {
            return (Boolean)this.items.getValue();
        }
        return (Boolean)this.others.getValue();
    }

    private int Method4057(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Manager.Field223.Method711(entity)) {
                return ((Color)this.friendsColor.getValue()).getRGB();
            }
            return ((Color)this.playersColor.getValue()).getRGB();
        }
        if (entity.isInvisible()) {
            return ((Color)this.invisiblesColor.getValue()).getRGB();
        }
        if (Class354.Method3265(entity) || Class354.Method3256(entity)) {
            return ((Color)this.monstersColor.getValue()).getRGB();
        }
        if (Class354.Method3257(entity)) {
            return ((Color)this.animalsColor.getValue()).getRGB();
        }
        if (entity instanceof EntityBoat || entity instanceof EntityMinecart) {
            return ((Color)this.vehiclesColor.getValue()).getRGB();
        }
        if (entity instanceof EntityItem) {
            return ((Color)this.itemsColor.getValue()).getRGB();
        }
        return ((Color)this.othersColor.getValue()).getRGB();
    }

    private void Method4054(Class66 class66) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }

        for (Entity entity : Globals.mc.world.loadedEntityList) {
            Vec3d vec3d;
            if (!this.Method4056(entity) || (vec3d = Class29.getVector(entity, class66.Method1789()).subtract(Globals.mc.getRenderManager().renderPosX, Globals.mc.getRenderManager().renderPosY, Globals.mc.getRenderManager().renderPosZ)) == null) {
                continue;
            }

            boolean viewBobbingState = Globals.mc.gameSettings.viewBobbing;
            Globals.mc.gameSettings.viewBobbing = false;

            // Use reflection to call the private setupCameraTransform method
            invokeSetupCameraTransformReflection(class66.Method1789());

            // Apply rotation for the entity
            Vec3d vec3d2 = new Vec3d(0.0, 0.0, 1.0)
                    .rotatePitch(-((float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)))
                    .rotateYaw(-((float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw)));

            // Render the tracer
            Class50.Method915(
                    (float) vec3d2.x,
                    (float) vec3d2.y + Globals.mc.player.getEyeHeight(),
                    (float) vec3d2.z,
                    (float) vec3d.x,
                    (float) vec3d.y,
                    (float) vec3d.z,
                    ((Integer) this.width.getValue()).intValue(),
                    this.Method4057(entity)
            );

            // Restore the original view bobbing state
            Globals.mc.gameSettings.viewBobbing = viewBobbingState;

            // Ensure the camera transform is reset with reflection
            invokeSetupCameraTransformReflection(class66.Method1789());
        }
    }

    // Local method to invoke setupCameraTransform via reflection
    private void invokeSetupCameraTransformReflection(float partialTicks) {
        try {
            // Use reflection to access the private setupCameraTransform method
            Method setupCameraTransformMethod = EntityRenderer.class.getDeclaredMethod("setupCameraTransform", float.class, int.class);
            setupCameraTransformMethod.setAccessible(true); // Make it accessible
            setupCameraTransformMethod.invoke(Globals.mc.entityRenderer, partialTicks, 0);
        } catch (Exception e) {
            e.printStackTrace(); // Handle any reflection-related exceptions
        }
    }


    private boolean Method4053() {
        return (Boolean)this.yradius.getValue();
    }

    private static boolean Method4052() {
        return true;
    }

    private static boolean Method4051() {
        return true;
    }

    private boolean Method4050() {
        return (Boolean)this.others.getValue();
    }

    private static boolean Method4049() {
        return true;
    }

    private boolean Method4048() {
        return (Boolean)this.items.getValue();
    }

    private static boolean Method4047() {
        return true;
    }

    private boolean Method4046() {
        return (Boolean)this.vehicles.getValue();
    }

    private static boolean Method4045() {
        return true;
    }

    private boolean Method4044() {
        return (Boolean)this.animals.getValue();
    }

    private static boolean Method4043() {
        return true;
    }

    private boolean Method4042() {
        return (Boolean)this.monsters.getValue();
    }

    private static boolean Method4041() {
        return true;
    }

    private boolean Method4040() {
        return (Boolean)this.invisibles.getValue();
    }

    private static boolean Method4039() {
        return true;
    }

    private boolean Method4038() {
        return (Boolean)this.friends.getValue();
    }

    private static boolean Method4037() {
        return true;
    }

    private boolean Method4036() {
        return (Boolean)this.players.getValue();
    }

    private static boolean Method4035() {
        return true;
    }
}
