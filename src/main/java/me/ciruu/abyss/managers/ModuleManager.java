package me.ciruu.abyss.managers;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.ciruu.abyss.Manager;
import me.ciruu.abyss.modules.Module;
import me.ciruu.abyss.modules.client.Alias;
import me.ciruu.abyss.modules.client.BubbleGUI;
import me.ciruu.abyss.modules.client.Capes;
import me.ciruu.abyss.modules.client.ChatNotifier;
import me.ciruu.abyss.modules.client.ClickGUI;
import me.ciruu.abyss.modules.client.Client;
import me.ciruu.abyss.modules.client.CustomFont;
import me.ciruu.abyss.modules.client.HUDColorSync;
import me.ciruu.abyss.modules.client.NameChanger;
import me.ciruu.abyss.modules.client.Particles;
import me.ciruu.abyss.modules.client.ScreenShaders;
import me.ciruu.abyss.modules.client.VoiceAssistant;
import me.ciruu.abyss.modules.client.WebChat;
import me.ciruu.abyss.modules.combat.AntiHoleCamp;
import me.ciruu.abyss.modules.combat.AntiRegear;
import me.ciruu.abyss.modules.combat.AutoArmor;
import me.ciruu.abyss.modules.combat.AutoBed;
import me.ciruu.abyss.modules.combat.AutoCity;
import me.ciruu.abyss.modules.combat.AutoCrystal;
import me.ciruu.abyss.modules.combat.AutoMinecart;
import me.ciruu.abyss.modules.combat.AutoTrap;
import me.ciruu.abyss.modules.combat.AutoWeb;
import me.ciruu.abyss.modules.combat.BowSpam;
import me.ciruu.abyss.modules.combat.Burrow;
import me.ciruu.abyss.modules.combat.Criticals;
import me.ciruu.abyss.modules.combat.HoleFillRewrite;
import me.ciruu.abyss.modules.combat.HoleFiller;
import me.ciruu.abyss.modules.combat.KillAura;
import me.ciruu.abyss.modules.combat.MultiTask;
import me.ciruu.abyss.modules.combat.OffHand;
import me.ciruu.abyss.modules.combat.Predict;
import me.ciruu.abyss.modules.combat.SelfTrap;
import me.ciruu.abyss.modules.combat.Surround;
import me.ciruu.abyss.modules.combat.Trigger;
import me.ciruu.abyss.modules.combat.WebFill;
import me.ciruu.abyss.modules.exploit.*;
import me.ciruu.abyss.modules.hud.ArmourHUD;
import me.ciruu.abyss.modules.hud.ArrayList;
import me.ciruu.abyss.modules.hud.BindList;
import me.ciruu.abyss.modules.hud.ChatWatermark;
import me.ciruu.abyss.modules.hud.CombatInfo;
import me.ciruu.abyss.modules.hud.Coordinates;
import me.ciruu.abyss.modules.hud.CrystalCounter;
import me.ciruu.abyss.modules.hud.CustomIngameGUI;
import me.ciruu.abyss.modules.hud.DVDLogo;
import me.ciruu.abyss.modules.hud.Greeter;
import me.ciruu.abyss.modules.hud.HitCrosshair;
import me.ciruu.abyss.modules.hud.InfoList;
import me.ciruu.abyss.modules.hud.InvViewer;
import me.ciruu.abyss.modules.hud.Logo;
import me.ciruu.abyss.modules.hud.Notifications;
import me.ciruu.abyss.modules.hud.PlayerViewer;
import me.ciruu.abyss.modules.hud.Radar;
import me.ciruu.abyss.modules.hud.TargetHUD;
import me.ciruu.abyss.modules.hud.TotemCounter;
import me.ciruu.abyss.modules.hud.VoiceResult;
import me.ciruu.abyss.modules.hud.Watermark;
import me.ciruu.abyss.modules.hud.WatermarkCool;
import me.ciruu.abyss.modules.hud.WebChatList;
import me.ciruu.abyss.modules.misc.AimBot;
import me.ciruu.abyss.modules.misc.AntiAFK;
import me.ciruu.abyss.modules.misc.AutoClicker;
import me.ciruu.abyss.modules.misc.AutoDisconnect;
import me.ciruu.abyss.modules.misc.AutoEz;
import me.ciruu.abyss.modules.misc.AutoFish;
import me.ciruu.abyss.modules.misc.AutoGap;
import me.ciruu.abyss.modules.misc.AutoRespawn;
import me.ciruu.abyss.modules.misc.BuildHeight;
import me.ciruu.abyss.modules.misc.ChatSuffix;
import me.ciruu.abyss.modules.misc.ChestInteract;
import me.ciruu.abyss.modules.misc.CrackedUtils;
import me.ciruu.abyss.modules.misc.DisableFriends;
import me.ciruu.abyss.modules.misc.DiscordRPC;
import me.ciruu.abyss.modules.misc.FakePlayer;
import me.ciruu.abyss.modules.misc.FastPlace;
import me.ciruu.abyss.modules.misc.HotbarRefill;
import me.ciruu.abyss.modules.misc.MCFriends;
import me.ciruu.abyss.modules.misc.MCPearl;
import me.ciruu.abyss.modules.misc.NoGlitchBlocks;
import me.ciruu.abyss.modules.misc.NoRotate;
import me.ciruu.abyss.modules.misc.NoSoundLag;
import me.ciruu.abyss.modules.misc.PacketLogger;
import me.ciruu.abyss.modules.misc.Spammer;
import me.ciruu.abyss.modules.misc.SpeedMine;
import me.ciruu.abyss.modules.misc.Timer;
import me.ciruu.abyss.modules.misc.TotemPopCount;
import me.ciruu.abyss.modules.misc.XCarry;
import me.ciruu.abyss.modules.misc.YawLock;
import me.ciruu.abyss.modules.movement.Anchor;
import me.ciruu.abyss.modules.movement.AntiVoid;
import me.ciruu.abyss.modules.movement.AutoWalk;
import me.ciruu.abyss.modules.movement.BoatFly;
import me.ciruu.abyss.modules.movement.EntitySpeed;
import me.ciruu.abyss.modules.movement.FastSwim;
import me.ciruu.abyss.modules.movement.Flight;
import me.ciruu.abyss.modules.movement.HighJump;
import me.ciruu.abyss.modules.movement.HoleTP;
import me.ciruu.abyss.modules.movement.LongJump;
import me.ciruu.abyss.modules.movement.NoSlowDown;
import me.ciruu.abyss.modules.movement.ReverseStep;
import me.ciruu.abyss.modules.movement.Scaffold;
import me.ciruu.abyss.modules.movement.Speed;
import me.ciruu.abyss.modules.movement.Sprint;
import me.ciruu.abyss.modules.movement.Step;
import me.ciruu.abyss.modules.movement.Velocity;
import me.ciruu.abyss.modules.movement.WebBypass;
import me.ciruu.abyss.modules.render.Aspect;
import me.ciruu.abyss.modules.render.BlockHighlight;
import me.ciruu.abyss.modules.render.BreakESP;
import me.ciruu.abyss.modules.render.BurrowESP;
import me.ciruu.abyss.modules.render.Chams;
import me.ciruu.abyss.modules.render.CityESP;
import me.ciruu.abyss.modules.render.CrystalESP;
import me.ciruu.abyss.modules.render.EnchantColor;
import me.ciruu.abyss.modules.render.Freecam;
import me.ciruu.abyss.modules.render.FullBright;
import me.ciruu.abyss.modules.render.HoleESP;
import me.ciruu.abyss.modules.render.ImageESP;
import me.ciruu.abyss.modules.render.ItemPhysics;
import me.ciruu.abyss.modules.render.ItemViewModel;
import me.ciruu.abyss.modules.render.LogoutSpot;
import me.ciruu.abyss.modules.render.NameTags;
import me.ciruu.abyss.modules.render.NoRender;
import me.ciruu.abyss.modules.render.OffhandSwing;
import me.ciruu.abyss.modules.render.OldAnimations;
import me.ciruu.abyss.modules.render.PearlTracker;
import me.ciruu.abyss.modules.render.Skeleton;
import me.ciruu.abyss.modules.render.SkyColor;
import me.ciruu.abyss.modules.render.StorageESP;
import me.ciruu.abyss.modules.render.TabFriends;
import me.ciruu.abyss.modules.render.Tooltips;
import me.ciruu.abyss.modules.render.Tracers;
import me.ciruu.abyss.modules.render.Trajectories;
import me.ciruu.abyss.modules.render.ViewClip;
import me.ciruu.abyss.modules.render.VoidESP;
import me.ciruu.abyss.modules.render.WallHack;
import me.ciruu.abyss.modules.render.WebESP;

public class ModuleManager {
    public static final ClassToInstanceMap<Module> module_val = MutableClassToInstanceMap.create();

    // Use wildcard-bound Collection for type safety
    public static final Collection<? extends Module> modulecollection = module_val.values();

    public void mods() {
        Iterator<Module> iterator = module_val.values().iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            if (module != null) {
                Module.setup(); // Call the setup method inside each module
            } else {
                System.err.println("ModuleManager: Found a null module while registering!");
            }
        }
        this.addModule(new ChatWatermark());
        this.addModule(new Alias());
        this.addModule(new ChatNotifier());
        this.addModule(new ClickGUI());
        this.addModule(new Client());
        this.addModule(new Capes());
        this.addModule(new CustomFont());
        this.addModule(new HUDColorSync());
        this.addModule(new NameChanger());
        this.addModule(new Particles());
        this.addModule(new BubbleGUI());
        this.addModule(new ScreenShaders());
        System.out.println("[(" + "\n\n Loaded Required Modules \n\n" + ")]");

        this.addModule(new VoiceAssistant());
        if (Manager.beta) {
            this.addModule(new WebChat());
        }
        this.addModule(new ChestExploit());
        this.addModule(new AntiHoleCamp());
        this.addModule(new AntiRegear());
        this.addModule(new AutoArmor());
        this.addModule(new AutoBed());
        this.addModule(new AutoCity());
        this.addModule(new AutoCrystal());
        this.addModule(new AutoMinecart());
        this.addModule(new AutoTrap());
        this.addModule(new AutoWeb());
        this.addModule(new BowSpam());
        this.addModule(new Burrow());
        this.addModule(new Criticals());
        this.addModule(new HoleFiller());
        this.addModule(new HoleFillRewrite());
        this.addModule(new KillAura());
        this.addModule(new MultiTask());
        this.addModule(new OffHand());
        if (Manager.beta) {
            this.addModule(new Predict());
        }
        this.addModule(new SelfTrap());
        this.addModule(new Surround());
        if (Manager.beta) {
        }
        this.addModule(new Trigger());
        this.addModule(new WebFill());
        this.addModule(new AutoMountDupe());
        this.addModule(new Blink());
        if (Manager.beta) {
            this.addModule(new ChunkLogger());
        }
        this.addModule(new EntityControl());
        this.addModule(new FrameDupe());
        this.addModule(new HandMine());
        this.addModule(new InstantMine());
        this.addModule(new Jesus());
        this.addModule(new LiquidInteract());
        this.addModule(new MountBypass());
        this.addModule(new NoBreakAnimation());
        this.addModule(new NoEntityTrace());
        this.addModule(new NoFall());
        this.addModule(new NoHandShake());
        this.addModule(new NoHunger());
        this.addModule(new NoSwing());
        this.addModule(new PacketFly());
        if (Manager.beta) {
            this.addModule(new PacketPhase());
        }
        this.addModule(new Phase());
        this.addModule(new PhaseTrap());
        this.addModule(new PortalGodMode());
        this.addModule(new Reach());
        if (Manager.beta) {
            this.addModule(new SuperKnockback());
        }
        if (Manager.beta) {
            this.addModule(new TickShift());
        }
        this.addModule(new ArmourHUD());
        this.addModule(new BindList());
        if (Manager.beta) {
            this.addModule(new CombatInfo());
        }
        this.addModule(new Coordinates());
        this.addModule(new CrystalCounter());
        if (Manager.beta) {
            this.addModule(new CustomIngameGUI());
        }
        this.addModule(new DVDLogo());
        this.addModule(new Greeter());
        this.addModule(new HitCrosshair());
        this.addModule(new InfoList());
        this.addModule(new InvViewer());
        this.addModule(new Logo());
        this.addModule(new ArrayList());
        this.addModule(new Notifications());
        this.addModule(new PlayerViewer());
        this.addModule(new Radar());
        this.addModule(new TargetHUD());
        this.addModule(new TotemCounter());
        this.addModule(new VoiceResult());
        this.addModule(new Watermark());
        this.addModule(new WatermarkCool());
        if (Manager.beta) {
            this.addModule(new WebChatList());
        }
        this.addModule(new AimBot());
        this.addModule(new AntiAFK());
        this.addModule(new AutoClicker());
        this.addModule(new AutoDisconnect());
        this.addModule(new AutoEz());
        this.addModule(new AutoFish());
        this.addModule(new AutoGap());
        this.addModule(new AutoRespawn());
        this.addModule(new BuildHeight());
        this.addModule(new ChatSuffix());
        this.addModule(new ChestInteract());
        this.addModule(new CrackedUtils());
        this.addModule(new DisableFriends());
        this.addModule(new DiscordRPC());
        this.addModule(new FakePlayer());
        this.addModule(new FastPlace());
        this.addModule(new HotbarRefill());
        this.addModule(new MCFriends());
        this.addModule(new MCPearl());
        this.addModule(new NoGlitchBlocks());
        this.addModule(new NoRotate());
        this.addModule(new NoSoundLag());
        this.addModule(new PacketLogger());
        this.addModule(new Spammer());
        this.addModule(new SpeedMine());
        this.addModule(new Timer());
        this.addModule(new TotemPopCount());
        this.addModule(new XCarry());
        this.addModule(new YawLock());
        this.addModule(new Anchor());
        this.addModule(new AntiVoid());
        this.addModule(new AutoWalk());
        this.addModule(new BoatFly());
        this.addModule(new EntitySpeed());
        this.addModule(new FastSwim());
        this.addModule(new Flight());
        this.addModule(new HighJump());
        this.addModule(new HoleTP());
        this.addModule(new LongJump());
        this.addModule(new NoSlowDown());
        this.addModule(new ReverseStep());
        this.addModule(new Scaffold());
        this.addModule(new Speed());
        this.addModule(new Sprint());
        this.addModule(new Step());
        this.addModule(new Velocity());
        this.addModule(new WebBypass());
        this.addModule(new Aspect());
        this.addModule(new BlockHighlight());
        this.addModule(new BreakESP());
        this.addModule(new BurrowESP());
        this.addModule(new Chams());
        this.addModule(new CityESP());
        this.addModule(new EnchantColor());
        this.addModule(new CrystalESP());
        this.addModule(new Freecam());
        this.addModule(new FullBright());
        this.addModule(new HoleESP());
        this.addModule(new ImageESP());
        this.addModule(new ItemPhysics());
        this.addModule(new ItemViewModel());
        this.addModule(new LogoutSpot());
        this.addModule(new NameTags());
        this.addModule(new OldAnimations());
        this.addModule(new NoRender());
        this.addModule(new OffhandSwing());
        this.addModule(new PearlTracker());
        this.addModule(new Skeleton());
        this.addModule(new SkyColor());
        this.addModule(new StorageESP());
        this.addModule(new TabFriends());
        this.addModule(new Tooltips());
        this.addModule(new Tracers());
        this.addModule(new Trajectories());
        this.addModule(new ViewClip());
        this.addModule(new VoidESP());
        this.addModule(new WallHack());
        this.addModule(new WebESP());
    }

    public Module getModuleByClass(Class<? extends Module> clazz) {
        for (Module module : modulecollection) {
            if (module.getClass().equals(clazz)) {
                return module;
            }
        }
        System.err.println("Module not found: " + clazz.getName());
        return null;
    }


    public Module getModuleByName(String name) {
        for (Object module_ : module_val.values()) {
            if (module_ instanceof Module) {
                if (((Module) module_).getName().equalsIgnoreCase(name)) {  // Assuming getName() returns the module's name
                    return (Module) module_;
                }
            }
        }
        return null;
    }


    public ClassToInstanceMap getModules() {
        return module_val;
    }

    public boolean isModuleEnabled(Class clazz) {
        Module module = this.getModuleByClass(clazz);

        // Check if the module exists and is properly assigned
        if (module == null) {
            return false;
        }

        // Ensure it's a valid module class
        if (!Module.class.isAssignableFrom(clazz)) {
            return false;
        }

        // Checking the module's enabled state using the appropriate method
        if (module.isEnabled()) { // Ensure `isEnabled` is defined and works correctly
            return true;
        }
        return false;
    }


    public void addModule(Module module) {
        module_val.put(module.getClass(), module);
    }

    public static void getString1(String string) {

        if (string == null || string.isEmpty() || string.equalsIgnoreCase("NONE")) {
            return;
        }
        module_val.values().stream().filter(arg_0 -> ModuleManager.getString2(string, (Module) arg_0)).forEach(null);
    }

    private static boolean getString2(String string, Module module) {
        return module.Method160().equals(string);
    }
}
