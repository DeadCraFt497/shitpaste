package me.ciruu.abyss.modules.render;

import com.google.common.collect.Sets;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.ciruu.abyss.Class108;
import me.ciruu.abyss.Class25;
import me.ciruu.abyss.Class273;
import me.ciruu.abyss.Class277;
import me.ciruu.abyss.Class278;
import me.ciruu.abyss.Class30;
import me.ciruu.abyss.Class31;
import me.ciruu.abyss.Globals;
import me.ciruu.abyss.enums.Class11;
import me.ciruu.abyss.enums.Class268;
import me.ciruu.abyss.enums.Class269;
import me.ciruu.abyss.enums.Class270;
import me.ciruu.abyss.enums.Class271;
import me.ciruu.abyss.enums.Class272;
import me.ciruu.abyss.enums.Class274;
import me.ciruu.abyss.events.player.EventPlayerUpdate;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.settings.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class HoleESP
extends Module {
    private final Setting mainwindow = new Setting("Main", "", this, new Class25("Main Settings"));
    private final Setting radius = new Setting("Radius", "", (Module)this, (Object)10, 0, 30);
    private final Setting hole = new Setting("Hole", "", this, (Object)Class270.DoubleExtended);
    private final Setting render = new Setting("Render", "", this, (Object)Class268.Outline);
    private final Setting width = new Setting("Width", "", this, 1, 1, 10, this.mainwindow, this::Method988);
    private final Setting mode = new Setting("Mode", "", this, (Object)Class269.Flat);
    private final Setting slabheight = new Setting("SlabHeight", "", this, Float.valueOf(0.1f), Float.valueOf(0.1f), Float.valueOf(1.5f), this.mainwindow, this::Method989);
    private final Setting hideown = new Setting("HideOwn", "", this, false);
    private final Setting flatown = new Setting("FlatOwn", "", this, false);
    private final Setting sidealpha = new Setting("SideAlpha", "", (Module)this, (Object)255, 0, 255);
    private final Setting obbylabel = new Setting("ObbyLabel", "", this, new Class25("Obsidian Color"));
    private final Setting obbyColor = new Setting("Obsidian color", "", (Module)this, (Object)new Color(0, 55, 255, 125), this.obbylabel, HoleESP::Method990);
    private final Setting obbyRainbow = new Setting("ObbyRainbow", "", (Module)this, (Object)false, this.obbylabel, HoleESP::Method991);
    private final Setting obbyRainbowSpeed = new Setting("OSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.obbylabel, this.obbyRainbow::getValue);
    private final Setting obbyRainbowSaturation = new Setting("OSaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.obbylabel, this.obbyRainbow::getValue);
    private final Setting obbyRainbowBrightness = new Setting("OBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.obbylabel, this.obbyRainbow::getValue);
    private final Setting bedlabel = new Setting("BedLabel", "", this, new Class25("Bedrock Color"));
    private final Setting bedrockcolor = new Setting("Bedrock color", "", (Module)this, (Object)new Color(125, 0, 255, 125), this.bedlabel, HoleESP::Method992);
    private final Setting bedRainbow = new Setting("BedRainbow", "", (Module)this, (Object)false, this.bedlabel, HoleESP::Method993);
    private final Setting bedRainbowSpeed = new Setting("BSpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.bedlabel, this.bedRainbow::getValue);
    private final Setting bedRainbowSaturation = new Setting("BSaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.bedlabel, this.bedRainbow::getValue);
    private final Setting bedRainbowBrightness = new Setting("BBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.bedlabel, this.bedRainbow::getValue);
    private final Setting extendedlabel = new Setting("ExtLabel", "", this, new Class25("Extended Color"));
    private final Setting extendedcolor = new Setting("Extended color", "", (Module)this, (Object)new Color(255, 0, 255, 125), this.extendedlabel, HoleESP::Method994);
    private final Setting extendedRainbow = new Setting("ExtRainbow", "", (Module)this, (Object)false, this.extendedlabel, HoleESP::Method995);
    private final Setting extendedRainbowSpeed = new Setting("ESpeed", "", this, Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(2.0f), this.extendedlabel, this.extendedRainbow::getValue);
    private final Setting extendedRainbowSaturation = new Setting("ESaturation", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.extendedlabel, this.extendedRainbow::getValue);
    private final Setting extendedRainbowBrightness = new Setting("EBrightness", "", this, Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), this.extendedlabel, this.extendedRainbow::getValue);
    private ConcurrentHashMap Field743;
    @EventHandler
    private Listener Field744 = new Listener<EventPlayerUpdate>(this::Method996, new Predicate[0]);
    @EventHandler
    private Listener Field745 = new Listener<Class277>(this::Method997, new Predicate[0]);

    public HoleESP() {
        super("HoleESP", "Highlights hole blocks.", Class11.RENDER, "");
        this.addSetting(this.radius);
        this.addSetting(this.hole);
        this.addSetting(this.render);
        this.addSetting(this.width);
        this.addSetting(this.mode);
        this.addSetting(this.slabheight);
        this.addSetting(this.hideown);
        this.addSetting(this.sidealpha);
        this.addSetting(this.obbylabel);
        this.addSetting(this.obbyColor);
        this.addSetting(this.obbyRainbow);
        this.addSetting(this.obbyRainbowSpeed);
        this.addSetting(this.obbyRainbowSaturation);
        this.addSetting(this.obbyRainbowBrightness);
        this.addSetting(this.bedlabel);
        this.addSetting(this.bedrockcolor);
        this.addSetting(this.bedRainbow);
        this.addSetting(this.bedRainbowSpeed);
        this.addSetting(this.bedRainbowSaturation);
        this.addSetting(this.bedRainbowBrightness);
        this.addSetting(this.extendedlabel);
        this.addSetting(this.extendedcolor);
        this.addSetting(this.extendedRainbow);
        this.addSetting(this.extendedRainbowSpeed);
        this.addSetting(this.extendedRainbowSaturation);
        this.addSetting(this.extendedRainbowBrightness);
    }

    public String Method999() {
        return ChatFormatting.WHITE + ((Class269)((Object)this.mode.getValue())).name();
    }

    private void Method1000(AxisAlignedBB axisAlignedBB, Color color) {
        switch ((Class268)((Object)this.render.getValue())) {
            case Outline: {
                this.Method1001(axisAlignedBB, new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
                break;
            }
            case Box: {
                this.Method1002(axisAlignedBB, color);
                break;
            }
            case Both: {
                this.Method1002(axisAlignedBB, color);
                this.Method1001(axisAlignedBB, new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
            }
        }
    }

    private void Method1002(AxisAlignedBB axisAlignedBB, Color color) {
        int n = (Integer)this.sidealpha.getValue() * 50 / 255;
        if (((Boolean)this.hideown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
            return;
        }
        switch ((Class269)((Object)this.mode.getValue())) {
            case Air: {
                if (((Boolean)this.flatown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
                    Class278.Method1003(axisAlignedBB, true, 1.0, color, n, 1);
                    break;
                }
                Class278.Method1003(axisAlignedBB, true, 1.0, color, n, 63);
                break;
            }
            case Ground: {
                Class278.Method1003(axisAlignedBB.offset(0.0, -1.0, 0.0), true, 1.0, new Color(color.getRed(), color.getGreen(), color.getBlue(), n), color.getAlpha(), 63);
                break;
            }
            case Flat: {
                Class278.Method1003(axisAlignedBB, true, 1.0, color, n, 1);
                break;
            }
            case Slab: {
                if (((Boolean)this.flatown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
                    Class278.Method1003(axisAlignedBB, true, 1.0, color, n, 1);
                    break;
                }
                Class278.Method1003(axisAlignedBB, false, ((Float)this.slabheight.getValue()).floatValue(), color, n, 63);
                break;
            }
            case Double: {
                if (((Boolean)this.flatown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
                    Class278.Method1003(axisAlignedBB, true, 1.0, color, n, 1);
                    break;
                }
                Class278.Method1003(axisAlignedBB.setMaxY(axisAlignedBB.maxY + 1.0), true, 2.0, color, n, 63);
            }
        }
    }

    private void Method1001(AxisAlignedBB axisAlignedBB, Color color) {
        if (((Boolean)this.hideown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
            return;
        }
        switch ((Class269)((Object)this.mode.getValue())) {
            case Air: {
                if (((Boolean)this.flatown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
                    Class278.Method1004(axisAlignedBB, (Integer)this.width.getValue(), color, (Integer)this.sidealpha.getValue(), 1);
                    break;
                }
                Class278.Method1005(axisAlignedBB, ((Integer)this.width.getValue()).intValue(), color, (Integer)this.sidealpha.getValue());
                break;
            }
            case Ground: {
                Class278.Method1005(axisAlignedBB.offset(0.0, -1.0, 0.0), ((Integer)this.width.getValue()).intValue(), new Color(color.getRed(), color.getGreen(), color.getBlue(), (Integer)this.sidealpha.getValue()), 255);
                break;
            }
            case Flat: {
                Class278.Method1004(axisAlignedBB, (Integer)this.width.getValue(), color, (Integer)this.sidealpha.getValue(), 1);
                break;
            }
            case Slab: {
                if (((Boolean)this.flatown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
                    Class278.Method1004(axisAlignedBB, (Integer)this.width.getValue(), color, (Integer)this.sidealpha.getValue(), 1);
                    break;
                }
                Class278.Method1005(axisAlignedBB.setMaxY(axisAlignedBB.minY + (double)((Float)this.slabheight.getValue()).floatValue()), ((Integer)this.width.getValue()).intValue(), color, (Integer)this.sidealpha.getValue());
                break;
            }
            case Double: {
                if (((Boolean)this.flatown.getValue()).booleanValue() && axisAlignedBB.intersects(Globals.mc.player.getEntityBoundingBox())) {
                    Class278.Method1004(axisAlignedBB, (Integer)this.width.getValue(), color, (Integer)this.sidealpha.getValue(), 1);
                    break;
                }
                Class278.Method1005(axisAlignedBB.setMaxY(axisAlignedBB.maxY + 1.0), ((Integer)this.width.getValue()).intValue(), color, (Integer)this.sidealpha.getValue());
            }
        }
    }

    private Color Method1006(Color color, Class271 class271) {
        switch (class271) {
            case Obsidian: {
                return this.Method1007(color);
            }
            case Bedrock: {
                return this.Method1008(color);
            }
            case Extended: {
                return this.Method1009(color);
            }
        }
        return Color.BLACK;
    }

    private Color Method1007(Color color) {
        if (((Boolean)this.obbyRainbow.getValue()).booleanValue()) {
            Color color2 = this.Method1010(0, ((Float)this.obbyRainbowSpeed.getValue()).floatValue(), ((Float)this.obbyRainbowSaturation.getValue()).floatValue(), ((Float)this.obbyRainbowBrightness.getValue()).floatValue());
            return new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), color.getAlpha());
        }
        return color;
    }

    private Color Method1008(Color color) {
        if (((Boolean)this.bedRainbow.getValue()).booleanValue()) {
            Color color2 = this.Method1010(0, ((Float)this.bedRainbowSpeed.getValue()).floatValue(), ((Float)this.bedRainbowSaturation.getValue()).floatValue(), ((Float)this.bedRainbowBrightness.getValue()).floatValue());
            return new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), color.getAlpha());
        }
        return color;
    }

    private Color Method1009(Color color) {
        if (((Boolean)this.extendedRainbow.getValue()).booleanValue()) {
            Color color2 = this.Method1010(0, ((Float)this.extendedRainbowSpeed.getValue()).floatValue(), ((Float)this.extendedRainbowSaturation.getValue()).floatValue(), ((Float)this.extendedRainbowBrightness.getValue()).floatValue());
            return new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), color.getAlpha());
        }
        return color;
    }

    public Color Method1010(int n, float f, float f2, float f3) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / (20.0 * (double)f));
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), f2, f3);
    }

    private void Method997(Class277 class277) {
        if (Globals.mc.player == null || Globals.mc.world == null || this.Field743 == null || this.Field743.isEmpty()) {
            return;
        }
        this.Field743.forEach(this::Method1000);
    }

    private void Method1000(Object o, Object o1) {
    }

    private void Method996(EventPlayerUpdate eventPlayerUpdate) {
        if (Globals.mc.player == null || Globals.mc.world == null) {
            return;
        }
        if (this.Field743 == null) {
            this.Field743 = new ConcurrentHashMap();
        } else {
            this.Field743.clear();
        }
        int n = (int)Math.ceil(((Integer)this.radius.getValue()).intValue());
        HashSet<BlockPos> hashSet = Sets.newHashSet();
        Collection list = (Collection)Class31.Method210(Class30.Method209((EntityPlayer)Globals.mc.player), n, n, false, true, 0);
        BlockPos blockPos = (BlockPos)null;

        for (Object b = (Object)null;b ==(Collection)list;) {
            if (!Globals.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || Globals.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock().equals(Blocks.AIR) || !Globals.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !Globals.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) continue;
            hashSet.add(blockPos);
        }
        hashSet.forEach(this::Method1011);
    }

    private void Method1011(BlockPos blockPos) {
        Class273 class273 = Class108.Method1012(blockPos, false, false);
        Class274 class274 = class273.Method1013();
        if (class274 != Class274.NONE) {
            Class270 class270;
            Class272 class272 = class273.Method1014();
            AxisAlignedBB axisAlignedBB = class273.Method1015();
            if (axisAlignedBB == null) {
                return;
            }
            Color color = class272 == Class272.UNBREAKABLE ? this.Method1006((Color)this.bedrockcolor.getValue(), Class271.Bedrock) : this.Method1006((Color)this.obbyColor.getValue(), Class271.Obsidian);
            if (class274 == Class274.CUSTOM) {
                color = this.Method1006((Color)this.extendedcolor.getValue(), Class271.Extended);
            }
            if ((class270 = (Class270)((Object)this.hole.getValue())) == Class270.DoubleExtended && (class274 == Class274.CUSTOM || class274 == Class274.DOUBLE)) {
                this.Field743.put(axisAlignedBB, color);
            } else if (class270 == Class270.Double && class274 == Class274.DOUBLE) {
                this.Field743.put(axisAlignedBB, color);
            } else if (class274 == Class274.SINGLE) {
                this.Field743.put(axisAlignedBB, color);
            }
        }
    }

    private static boolean Method995() {
        return true;
    }

    private static boolean Method994() {
        return true;
    }

    private static boolean Method993() {
        return true;
    }

    private static boolean Method992() {
        return true;
    }

    private static boolean Method991() {
        return true;
    }

    private static boolean Method990() {
        return true;
    }

    private boolean Method989() {
        return this.mode.getValue() == Class269.Slab;
    }

    private boolean Method988() {
        return this.render.getValue() != Class268.Box;
    }
}
