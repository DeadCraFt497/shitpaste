package me.ciruu.abyss.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class50;
import me.ciruu.abyss.Class587;
import me.ciruu.abyss.Class66;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class588;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;

public class StorageESP
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    public final Setting echests = new Setting("EnderChests", "Highlights EnderChests", this, true);
    public final Setting echestsColor = new Setting("EnderChestColor", "", (Module)this, (Object)new Color(125, 0, 255, 125), this.mainwindow, this.echests::getValue);
    public final Setting chests = new Setting("Chests", "Highlights Chests", this, true);
    public final Setting chestsColor = new Setting("ChestColor", "", (Module)this, (Object)new Color(255, 183, 0, 125), this.mainwindow, this.chests::getValue);
    public final Setting shulkers = new Setting("Shulkers", "Highlights Shulkers", this, true);
    public final Setting shulkersColor = new Setting("ShulkerColor", "", (Module)this, (Object)new Color(0, 225, 255, 125), this.mainwindow, this.shulkers::getValue);
    public final Setting furnaces = new Setting("Furnaces", "Highlights Furnaces", this, true);
    public final Setting furnacesColor = new Setting("FurnaceColor", "", (Module)this, (Object)new Color(76, 76, 76, 205), this.mainwindow, this.furnaces::getValue);
    public final Setting hoppers = new Setting("Hoppers", "Highlights Hoppers", this, true);
    public final Setting hoppersColor = new Setting("HopperColor", "", (Module)this, (Object)new Color(255, 0, 140, 125), this.mainwindow, this.hoppers::getValue);
    public final Setting dispensers = new Setting("Dispensers", "Highlights Dispensers", this, true);
    public final Setting dispensersColor = new Setting("DispenserColor", "", (Module)this, (Object)new Color(0, 255, 153, 125), this.mainwindow, this.dispensers::getValue);
    public final List Field2901 = new ArrayList();
    private ICamera Field2902 = new Frustum();
    @EventHandler
    private Listener Field2903 = new Listener<EventPlayerUpdate>(this::Method3581, new Predicate[0]);
    @EventHandler
    private Listener Field2904 = new Listener<Class66>(this::Method3582, new Predicate[0]);

    public StorageESP() {
        super("StorageESP", "Highlights storage blocks.", Class11.RENDER, "");
        this.addSetting(this.echests);
        this.addSetting(this.echestsColor);
        this.addSetting(this.chests);
        this.addSetting(this.chestsColor);
        this.addSetting(this.shulkers);
        this.addSetting(this.shulkersColor);
        this.addSetting(this.furnaces);
        this.addSetting(this.furnacesColor);
        this.addSetting(this.hoppers);
        this.addSetting(this.hoppersColor);
        this.addSetting(this.dispensers);
        this.addSetting(this.dispensersColor);
    }

    private void Method3582(Class66 class66) {
        if (Globals.mc.getRenderManager() == null || Globals.mc.getRenderManager().options == null) {
            return;
        }
        new ArrayList<Class587>(this.Field2901).forEach(this::Method3584);
    }

    private void Method3584(Class587 class587) {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB((double)class587.getX() - Globals.mc.getRenderManager().viewerPosX, (double)class587.getY() - Globals.mc.getRenderManager().viewerPosY, (double)class587.getZ() - Globals.mc.getRenderManager().viewerPosZ, (double)(class587.getX() + 1) - Globals.mc.getRenderManager().viewerPosX, (double)(class587.getY() + 1) - Globals.mc.getRenderManager().viewerPosY, (double)(class587.getZ() + 1) - Globals.mc.getRenderManager().viewerPosZ);
        this.Field2902.setPosition(Globals.mc.getRenderViewEntity().posX, Globals.mc.getRenderViewEntity().posY, Globals.mc.getRenderViewEntity().posZ);
        if (this.Field2902.isBoundingBoxInFrustum(new AxisAlignedBB(axisAlignedBB.minX + Globals.mc.getRenderManager().viewerPosX, axisAlignedBB.minY + Globals.mc.getRenderManager().viewerPosY, axisAlignedBB.minZ + Globals.mc.getRenderManager().viewerPosZ, axisAlignedBB.maxX + Globals.mc.getRenderManager().viewerPosX, axisAlignedBB.maxY + Globals.mc.getRenderManager().viewerPosY, axisAlignedBB.maxZ + Globals.mc.getRenderManager().viewerPosZ))) {
            switch (class587.getType()) {
                case Chest: {
                    Class50.Method879(axisAlignedBB, ((Color)this.chestsColor.getValue()).getRGB());
                    break;
                }
                case Ender: {
                    Class50.Method879(axisAlignedBB, ((Color)this.echestsColor.getValue()).getRGB());
                    break;
                }
                case Shulker: {
                    Class50.Method879(axisAlignedBB, ((Color)this.shulkersColor.getValue()).getRGB());
                    break;
                }
                case Furnace: {
                    Class50.Method879(axisAlignedBB, ((Color)this.furnacesColor.getValue()).getRGB());
                    break;
                }
                case Hopper: {
                    Class50.Method879(axisAlignedBB, ((Color)this.hoppersColor.getValue()).getRGB());
                    break;
                }
                case Dispenser: {
                    Class50.Method879(axisAlignedBB, ((Color)this.dispensersColor.getValue()).getRGB());
                    break;
                }
            }
        }
    }

    private void Method3581(EventPlayerUpdate eventPlayerUpdate) {
        this.Field2901.clear();
        Globals.mc.world.loadedTileEntityList.forEach(this::Method3585);
    }

    private void Method3585(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityEnderChest && ((Boolean)this.echests.getValue()).booleanValue()) {
            this.Field2901.add(new Class587(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), Class588.Ender));
        } else if (tileEntity instanceof TileEntityChest && ((Boolean)this.chests.getValue()).booleanValue()) {
            this.Field2901.add(new Class587(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), Class588.Chest));
        } else if (tileEntity instanceof TileEntityShulkerBox && ((Boolean)this.shulkers.getValue()).booleanValue()) {
            this.Field2901.add(new Class587(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), Class588.Shulker));
        } else if (tileEntity instanceof TileEntityFurnace && ((Boolean)this.furnaces.getValue()).booleanValue()) {
            this.Field2901.add(new Class587(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), Class588.Furnace));
        } else if (tileEntity instanceof TileEntityHopper && ((Boolean)this.hoppers.getValue()).booleanValue()) {
            this.Field2901.add(new Class587(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), Class588.Hopper));
        } else if (tileEntity instanceof TileEntityDispenser && ((Boolean)this.dispensers.getValue()).booleanValue()) {
            this.Field2901.add(new Class587(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), Class588.Dispenser));
        }
    }
}
