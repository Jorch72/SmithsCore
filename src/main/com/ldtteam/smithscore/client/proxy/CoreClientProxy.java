package com.ldtteam.smithscore.client.proxy;

import com.ldtteam.smithscore.SmithsCore;
import com.ldtteam.smithscore.client.font.MultiColoredFontRenderer;
import com.ldtteam.smithscore.client.handlers.TileEntityDataUpdatedEventHandler;
import com.ldtteam.smithscore.client.handlers.gui.*;
import com.ldtteam.smithscore.client.handlers.network.ClientNetworkableEventHandler;
import com.ldtteam.smithscore.client.model.loader.MultiComponentModelLoader;
import com.ldtteam.smithscore.client.model.loader.SmithsCoreOBJLoader;
import com.ldtteam.smithscore.client.registry.ClientRegistry;
import com.ldtteam.smithscore.client.render.layers.CancelableLayerCustomHead;
import com.ldtteam.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.ldtteam.smithscore.common.handlers.network.CommonNetworkableEventHandler;
import com.ldtteam.smithscore.common.player.handlers.PlayersConnectedUpdatedEventHandler;
import com.ldtteam.smithscore.common.player.handlers.PlayersOnlineUpdatedEventHandler;
import com.ldtteam.smithscore.common.player.management.PlayerManager;
import com.ldtteam.smithscore.common.proxy.CoreCommonProxy;
import com.ldtteam.smithscore.common.structures.StructureRegistry;
import com.ldtteam.smithscore.util.client.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderArmorStand;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Map;

/**
 * Specific proxy class used to initialize client only sides of this Mod
 * It is the common point of entry after the Modclass receives notice of a Init-state update, on the client (and the internal server) side,
 * through one of his eventhandlers.
 * <p>
 * Created by Orion
 * Created on 26.10.2015
 * 12:49
 * <p>
 * Copyrighted according to Project specific license
 */
public class CoreClientProxy extends CoreCommonProxy
{

    @Nonnull
    static MultiColoredFontRenderer multiColoredFontRenderer;
    @Nonnull
    MultiComponentModelLoader multiComponentModelLoader = MultiComponentModelLoader.instance;

    public static ResourceLocation registerMultiComponentItemModel(@Nonnull Item item)
    {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null)
        {
            return null;
        }

        String path = "component/" + itemLocation.getResourcePath() + MultiComponentModelLoader.EXTENSION;

        return registerMultiComponentItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    @Nonnull
    public static ResourceLocation registerMultiComponentItemModel(@Nonnull Item item, @Nonnull final ResourceLocation location)
    {
        if (!location.getResourcePath().endsWith(MultiComponentModelLoader.EXTENSION))
        {
            SmithsCore.getLogger().error("The MultiComponent-model " + location.toString() + " does not end with '"
                                           + MultiComponentModelLoader.EXTENSION
                                           + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MultiComponentModelLoader.EXTENSION);
    }

    @Nonnull
    public static ResourceLocation registerItemModelDefinition(@Nonnull Item item, @Nonnull final ResourceLocation location, @Nonnull String requiredExtension)
    {
        if (!location.getResourcePath().endsWith(requiredExtension))
        {
            SmithsCore.getLogger().error("The item-model " + location.toString() + " does not end with '"
                                           + requiredExtension
                                           + "' and will therefore not be loaded by the custom model loader!");
        }

        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
        {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        // We have to read the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelBakery.registerItemVariants(item, location);

        SmithsCore.getLogger()
          .info("Added model definition for: " + item.getUnlocalizedName() + " add: " + location.getResourcePath() + " in the Domain: " + location.getResourceDomain());

        return location;
    }

    @Nonnull
    public static MultiColoredFontRenderer getMultiColoredFontRenderer()
    {
        return multiColoredFontRenderer;
    }

    /**
     * Function used to prepare mods and plugins for the Init-Phase
     * Also initializes most of the network code for the client- and Serverside.
     * <p>
     * The configuration handler is initialized by a different function.
     */
    @Override
    public void preInit()
    {
        super.preInit();

        ModelLoaderRegistry.registerLoader(multiComponentModelLoader);
        ModelLoaderRegistry.registerLoader(SmithsCoreOBJLoader.INSTANCE);
    }

    /**
     * Function called from preInit() to register all of the Eventhandlers used by common code.
     */
    @Override
    protected void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(((ClientRegistry) SmithsCore.getRegistry()).getTextureCreator());
        MinecraftForge.EVENT_BUS.register(((ClientRegistry) SmithsCore.getRegistry()).getMouseManager());
        MinecraftForge.EVENT_BUS.register(new ClientTickEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());
        MinecraftForge.EVENT_BUS.register(PlayerManager.getInstance());
        MinecraftForge.EVENT_BUS.register(StructureRegistry.getClientInstance());
        MinecraftForge.EVENT_BUS.register(StructureRegistry.getServerInstance());

        SmithsCoreCapabilityDispatcher.initialize();

        SmithsCore.getRegistry().getClientBus().register(new ClientNetworkableEventHandler());
        SmithsCore.getRegistry().getCommonBus().register(new ClientNetworkableEventHandler());
        SmithsCore.getRegistry().getCommonBus().register(new CommonNetworkableEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new GuiInputEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new ContainerGUIOpenedEventHandler());
        SmithsCore.getRegistry().getNetworkBus().register(new ContainerGUIClosedEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new PlayersOnlineUpdatedEventHandler());
        SmithsCore.getRegistry().getNetworkBus().register(new PlayersConnectedUpdatedEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new BlockModelUpdateEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new TileEntityDataUpdatedEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(StructureRegistry.getInstance());
        SmithsCore.getRegistry().getCommonBus().register(StructureRegistry.getServerInstance());

        SmithsCore.getRegistry().getClientBus().register(new ButtonInputEventHandler());
        SmithsCore.getRegistry().getClientBus().register(((ClientRegistry) SmithsCore.getRegistry()).getTextureCreator());
    }

    /**
     * Function used to initialize this mod.
     * It sets parameters used in most of its functions as common mod for ldtteam mods.
     */
    @Override
    public void Init()
    {
        super.Init();

        multiColoredFontRenderer =
          new MultiColoredFontRenderer(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().renderEngine);
        if (Minecraft.getMinecraft().gameSettings.language != null)
        {
            multiColoredFontRenderer.setUnicodeFlag(
              Minecraft.getMinecraft().getLanguageManager().isCurrentLocaleUnicode() || Minecraft.getMinecraft().gameSettings.forceUnicodeFont);
            multiColoredFontRenderer.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
        }
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(multiColoredFontRenderer);

        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

        RenderPlayer renderPlayer = skinMap.get("default");
        renderPlayer.layerRenderers.removeIf(l -> l.getClass().equals(LayerCustomHead.class));
        renderPlayer.addLayer(new CancelableLayerCustomHead(renderPlayer.getMainModel().bipedHead));

        renderPlayer = skinMap.get("slim");
        renderPlayer.layerRenderers.removeIf(l -> l.getClass().equals(LayerCustomHead.class));
        renderPlayer.addLayer(new CancelableLayerCustomHead(renderPlayer.getMainModel().bipedHead));

        RenderZombie renderZombie = (RenderZombie) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityZombie.class);
        renderZombie.layerRenderers.removeIf(l -> l.getClass().equals(LayerCustomHead.class));
        renderZombie.addLayer(new CancelableLayerCustomHead(((ModelZombie) renderZombie.getMainModel()).bipedHead));

        RenderSkeleton renderSkeleton = (RenderSkeleton) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntitySkeleton.class);
        renderSkeleton.layerRenderers.removeIf(l -> l.getClass().equals(LayerCustomHead.class));
        renderSkeleton.addLayer(new CancelableLayerCustomHead(((ModelSkeleton) renderSkeleton.getMainModel()).bipedHead));

        RenderArmorStand renderArmorStand = (RenderArmorStand) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityArmorStand.class);
        renderArmorStand.layerRenderers.removeIf(l -> l.getClass().equals(LayerCustomHead.class));
        renderArmorStand.addLayer(new CancelableLayerCustomHead(renderArmorStand.getMainModel().bipedHead));
    }

    /**
     * Function used to change behaviour of this mod based on loaded mods.
     */
    @Override
    public void postInit()
    {
        super.postInit();
    }

    /**
     * Function used to initialize the configuration classes that are common between client and server
     *
     * @param pSuggestedConfigFile The file (or directory given that Java makes no difference between the two) that is suggested to contain configuration options for this mod.
     *                             This parameter is in normal situations populated with the suggested configuration File from the PreInit event.
     * @see File
     * @see FMLPreInitializationEvent
     */
    @Override
    public void configInit(@Nonnull File pSuggestedConfigFile)
    {
        super.configInit(pSuggestedConfigFile);
    }

    /**
     * Function used to get the effective running side.
     * Basically indicates if elements marked with SideOnly(Side.client) or SideOnly(Side.SERVER) are available to the JVM
     * As the client side of this proxy inherits from this common one it overrides this function and returns Side.client instead of value returned from here.
     * <p>
     * The value returned here does not indicate if the user is running a dedicated or a internal server. It only indicated that the instance of minecraft has gui-Capabilities or
     * not.
     *
     * @return The effective running Side of this proxy
     *
     * @see SideOnly
     * @see CoreCommonProxy
     */
    @Nonnull
    @Override
    public Side getEffectiveSide()
    {
        return Side.CLIENT;
    }

    @Override
    @Nonnull
    public EntityPlayer getPlayerForSide(@Nonnull MessageContext context)
    {
        if (context.side == Side.SERVER)
        {
            return context.getServerHandler().player;
        }
        else
        {
            return FMLClientHandler.instance().getClientPlayerEntity();
        }
    }
}
