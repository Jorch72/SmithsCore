package com.ldtteam.smithscore;

import com.google.common.base.Stopwatch;
import com.ldtteam.smithscore.common.events.AutomaticEventBusSubcriptionInjector;
import com.ldtteam.smithscore.common.player.management.PlayerManager;
import com.ldtteam.smithscore.common.proxy.CoreCommonProxy;
import com.ldtteam.smithscore.common.registry.CommonRegistry;
import com.ldtteam.smithscore.util.CoreReferences;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Orion
 * Created on 25.10.2015
 * 21:46
 * <p>
 * Copyrighted according to Project specific license
 */
@Mod(modid = CoreReferences.General.MOD_ID, name = "smithscore", version = CoreReferences.General.VERSION,
  dependencies = "required-after:forge@[13.19,)")
public class SmithsCore
{

    // Instance of this mod use for internal and Forge references
    @Mod.Instance(CoreReferences.General.MOD_ID)
    private static SmithsCore instance;

    // Logger used to output log messages from smithscore
    private static Logger iLogger = LogManager.getLogger(CoreReferences.General.MOD_ID);

    private static boolean isInDevEnvironment = Boolean.parseBoolean(System.getProperties().getProperty("SmithsCore.Dev", "false"));

    private static boolean isInObfuscatedEnvironment = !(boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    // Private variable for the Sided registry
    @SidedProxy(clientSide = "com.ldtteam.smithscore.client.registry.ClientRegistry", serverSide = "com.ldtteam.smithscore.common.registry.CommonRegistry")
    private static CommonRegistry iRegistry;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.ldtteam.smithscore.client.proxy.CoreClientProxy", serverSide = "com.ldtteam.smithscore.common.proxy.CoreCommonProxy")
    private static CoreCommonProxy proxy;

    public static final SmithsCore getInstance() { return instance; }

    public static final CoreCommonProxy getProxy()
    {
        return proxy;
    }

    public static final CommonRegistry getRegistry()
    {
        return iRegistry;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Stopwatch watch = Stopwatch.createStarted();

        if (isInDevEnvironment())
        {
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "SmithsCore starting in Dev mode. Current active Features:");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Additional log output.");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Debug rendering of structures.");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Debug overlay rendering of UI components,");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "");
        }
        else
        {
            getLogger().info(CoreReferences.LogMarkers.PREINIT, "SmithsCore starting in Normal mode.");
        }

        proxy.preInit();

        Loader.instance().getActiveModList().forEach(mod -> {
            AutomaticEventBusSubcriptionInjector.inject(mod, event.getAsmData());
        });

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.PREINIT, "SmithsCore Pre-Init completed after: " + milliseconds + " mS.");
    }

    /**
     * Method to check if SmithsCore should execute highly unstable features or log additional data.
     *
     * @return True when the Development flag is set, false when not.
     */
    public static final boolean isInDevEnvironment() {return isInDevEnvironment; } // || isIsInObfuscatedEnvironment();

    public static final Logger getLogger() { return iLogger; }

    /**
     * Method to check if SmithsCore is running in a deobfuscated environment
     *
     * @return True when SmithsCore is running in a deobfuscated environment.
     */
    public static final boolean isIsInObfuscatedEnvironment()
    {
        return isInObfuscatedEnvironment;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        Stopwatch watch = Stopwatch.createStarted();

        proxy.Init();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.INIT, "SmithsCore Init completed after: " + milliseconds + " ms.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Stopwatch watch = Stopwatch.createStarted();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.POSTINIT, "SmithsCore Post-Init completed after: " + milliseconds + " ms.");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        PlayerManager.getInstance().onServerStart(event);
    }
}
