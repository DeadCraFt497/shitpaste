package me.ciruu.abyss.mixin;

import java.util.Map;
import javax.annotation.Nullable;
import me.ciruu.abyss.DRM;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@IFMLLoadingPlugin.MCVersion(value="1.12.2")
@IFMLLoadingPlugin.Name(value="Abyss client")
@IFMLLoadingPlugin.SortingIndex(value=1002)
@IFMLLoadingPlugin.TransformerExclusions(value={"me.ciruu.abyss.mixin", "me.ciruu.abyss.mixin.client", "me.ciruu.abyss.accessor"})
public class MixinLoaderForge
implements IFMLLoadingPlugin {
    public static final Logger log = LogManager.getLogger((String)"Abyss mixins");

    public MixinLoaderForge() {
        log.info("Initializing Abyss mixins");
        DRM.checkDRM();
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.abyss.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        log.info("Abyss mixins initialized");
    }

    public String[] getASMTransformerClass() {
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    @Nullable
    public String getSetupClass() {
        return null;
    }

    public void injectData(Map map) {
    }

    public String getAccessTransformerClass() {
        return null;
    }
}
