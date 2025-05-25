package me.ciruu.abyss;

import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@Mod(modid="abyss", name="Abyss", version="1.6.0")
public class AbyssMod {
    
    public static final String MOD_ID = "abyss";
    public static final String MOD_NAME = "Abyss";
    public static final String VERSION = "1.6.0";
    public static final String suffix = "\u1d00\u0299\u028f\ua731\ua731";
    public static EventBus EVENT_BUS;

    @Mod.Instance(value="abyss")
    public static AbyssMod INSTANCE;
    public static Manager manager;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent fMLInitializationEvent) {
        manager.init(fMLInitializationEvent);
    }

    static {
        manager = new Manager();
    }
}

