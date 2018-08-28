package com.ldtteam.smithscore.util.client;

import com.ldtteam.smithscore.client.events.texture.TextureStitchCollectedEvent;
import com.ldtteam.smithscore.common.events.AutomaticEventBusSubscriber;
import com.ldtteam.smithscore.util.CoreReferences;
import com.ldtteam.smithscore.util.client.color.Colors;
import com.ldtteam.smithscore.util.client.gui.MultiComponentTexture;
import com.ldtteam.smithscore.util.client.gui.TextureComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 06.12.2015.
 */
@AutomaticEventBusSubscriber(modid = CoreReferences.General.MOD_ID, types = AutomaticEventBusSubscriber.BusType.CLIENT)
@Mod.EventBusSubscriber(modid = CoreReferences.General.MOD_ID, value = Side.CLIENT)
public class Textures
{

    /**
     * Actual construction method is called from the ForgeEvent system. This method kicks the creation of the textures
     * of and provided a map to put the textures in.
     *
     * @param event The events fired before the TextureSheet is stitched. TextureStitchEvent.Pre instance.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void loadTextures(@Nonnull TextureStitchCollectedEvent event)
    {
        Gui.Basic.INFOICON.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Gui.Basic.INFOICON.getPrimaryLocation())));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void loadTexturesAfterCreation(TextureStitchEvent.Post event)
    {
    }

    public static class Gui
    {
        @Nonnull
        private static String GUITEXTUREPATH       = "smithscore:textures/gui/";
        @Nonnull
        private static String COMPONENTTEXTUREPATH = GUITEXTUREPATH + "components/";

        public static class Basic
        {
            @Nonnull
            public static  CustomResource INFOICON         = new CustomResource("Gui.Basic.ledgers.InfoIon", "smithscore:gui/icons/16x info icon", Colors.DEFAULT, 0, 0, 16, 16);
            @Nonnull
            private static String         BASICTEXTUREPATH = GUITEXTUREPATH + "basic/";
            @Nonnull
            public static  CustomResource LEDGERLEFT       = new CustomResource("Gui.Basic.ledgers.Left", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT);

            public static class Slots
            {
                @Nonnull
                public static CustomResource DEFAULT = new CustomResource("Gui.Basic.Slots.Default", BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 0, 0, 18, 18);
            }

            public static class Border
            {
                @Nonnull
                public static CustomResource CENTER                     =
                  new CustomResource("Gui.Basic.Border.Center", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT, 4, 4, 248, 248);
                @Nonnull
                public static CustomResource STRAIGHTBORDERLIGHT        =
                  new CustomResource("Gui.Basic.Border.Border.Light", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT, 3, 0, 250, 3);
                @Nonnull
                public static CustomResource STRAIGHTBORDERDARK         =
                  new CustomResource("Gui.Basic.Border.Border.Dark", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT, 3, 253, 250, 3);
                @Nonnull
                public static CustomResource INWARTSCORNERLIGHT         =
                  new CustomResource("Gui.Basic.Border.Corner.Inwards.Light", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT, 0, 0, 3, 3);
                @Nonnull
                public static CustomResource INWARTSCORNERLIGHTINVERTED =
                  new CustomResource("Gui.Basic.Border.Corner.Inwards.Light", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT, 0, 253, 3, 3);
                @Nonnull
                public static CustomResource INWARTSCORNERDARK          =
                  new CustomResource("Gui.Basic.Border.Corner.Inwards.Dark", BASICTEXTUREPATH + "ledger/ledger.png", Colors.DEFAULT, 252, 252, 4, 4);

                @Nonnull
                private static String         BORDERTEXTUREPATH        = BASICTEXTUREPATH + "border/";
                @Nonnull
                public static  CustomResource OUTWARTSCORNERDARKDARK   =
                  new CustomResource("Gui.Basic.Border.Corner.Outwards.Dark", BORDERTEXTUREPATH + "outwartscornerbig.png", Colors.DEFAULT, 0, 0, 3, 3);
                @Nonnull
                public static  CustomResource OUTWARTSCORNERDARKLIGHT  =
                  new CustomResource("Gui.Basic.Border.Corner.Outwards.Light", BORDERTEXTUREPATH + "outwartscornerbig.png", Colors.DEFAULT, 3, 0, 3, 3);
                @Nonnull
                public static  CustomResource OUTWARTSCORNERLIGHTDARK  =
                  new CustomResource("Gui.Basic.Border.Corner.Outwards.Light", BORDERTEXTUREPATH + "outwartscornerbig.png", Colors.DEFAULT, 6, 0, 3, 3);
                @Nonnull
                public static  CustomResource OUTWARTSCORNERLIGHTLIGHT =
                  new CustomResource("Gui.Basic.Border.Corner.Outwards.Light", BORDERTEXTUREPATH + "outwartscornerbig.png", Colors.DEFAULT, 9, 0, 3, 3);
            }

            public static class Components
            {
                @Nonnull
                public static CustomResource ARROWEMPTY =
                  new CustomResource("Gui.Basic.components.Arrow.Empty", COMPONENTTEXTUREPATH + "progressbars.png", Colors.DEFAULT, 0, 0, 22, 16);
                @Nonnull
                public static CustomResource ARROWFULL  =
                  new CustomResource("Gui.Basic.components.Arrow.Full", COMPONENTTEXTUREPATH + "progressbars.png", Colors.DEFAULT, 22, 0, 22, 16);
                @Nonnull
                public static CustomResource FLAMEEMPTY =
                  new CustomResource("Gui.Basic.components.Flame.Empty", COMPONENTTEXTUREPATH + "progressbars.png", Colors.DEFAULT, 44, 0, 16, 16);
                @Nonnull
                public static CustomResource FLAMEFULL  =
                  new CustomResource("Gui.Basic.components.Flame.Full", COMPONENTTEXTUREPATH + "progressbars.png", Colors.DEFAULT, 60, 0, 16, 16);

                public static class Button
                {
                    public static final CustomResource DOWNARROW      =
                      new CustomResource("Gui.Basic.components.Button.DownArrow", COMPONENTTEXTUREPATH + "components.png", Colors.DEFAULT, 39, 0, 7, 10);
                    public static final CustomResource UPARROW        =
                      new CustomResource("Gui.Basic.components.Button.UpArrow", COMPONENTTEXTUREPATH + "components.png", Colors.DEFAULT, 46, 0, 7, 10);
                    public static final CustomResource SCROLLBAR      =
                      new CustomResource("Gui.Basic.components.Button.ScrollButton", COMPONENTTEXTUREPATH + "components.png", Colors.DEFAULT, 32, 0, 7, 10);
                    @Nonnull
                    protected static    String         WIDGETFILEPATH = BASICTEXTUREPATH + "buttons.png";

                    public static class Disabled
                    {
                        @Nonnull
                        public static CustomResource CORNERLEFTTOP     = new CustomResource("Gui.Basic.components.Button.Disabled.Corners.LeftTop", WIDGETFILEPATH, 1, 61, 1, 1);
                        @Nonnull
                        public static CustomResource CORNERRIGHTTOP    = new CustomResource("Gui.Basic.components.Button.Disabled.Corners.RightTop", WIDGETFILEPATH, 198, 61, 1, 1);
                        @Nonnull
                        public static CustomResource CORNERLEFTBOTTOM  = new CustomResource("Gui.Basic.components.Button.Disabled.Corners.LeftBottom", WIDGETFILEPATH, 1, 77, 1, 2);
                        @Nonnull
                        public static CustomResource CORNERRIGHTBOTTOM =
                          new CustomResource("Gui.Basic.components.Button.Disabled.Corners.RightBottom", WIDGETFILEPATH, 198, 77, 1, 2);

                        @Nonnull
                        public static CustomResource SIDETOP    = new CustomResource("Gui.Basic.components.Button.Disabled.Side.Top", WIDGETFILEPATH, 1, 61, 196, 1);
                        @Nonnull
                        public static CustomResource SIDELEFT   = new CustomResource("Gui.Basic.components.Button.Disabled.Side.Left", WIDGETFILEPATH, 1, 62, 1, 15);
                        @Nonnull
                        public static CustomResource SIDERIGHT  = new CustomResource("Gui.Basic.components.Button.Disabled.Side.Right", WIDGETFILEPATH, 198, 62, 1, 15);
                        @Nonnull
                        public static CustomResource SIDEBOTTOM = new CustomResource("Gui.Basic.components.Button.Disabled.Side.Bottom", WIDGETFILEPATH, 1, 77, 196, 2);

                        @Nonnull
                        public static CustomResource CENTER = new CustomResource("Gui.Basic.components.Button.Disabled.Center", WIDGETFILEPATH, 2, 62, 196, 15);

                        @Nonnull
                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER),
                          new TextureComponent[] {new TextureComponent(CORNERLEFTTOP),
                            new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM),
                            new TextureComponent(CORNERLEFTBOTTOM)},
                          new TextureComponent[] {new TextureComponent(SIDETOP),
                            new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM),
                            new TextureComponent(SIDELEFT)});
                    }

                    public static class Standard
                    {
                        @Nonnull
                        public static CustomResource CORNERLEFTTOP     = new CustomResource("Gui.Basic.components.Button.Standard.Corners.LeftTop", WIDGETFILEPATH, 1, 21, 1, 1);
                        @Nonnull
                        public static CustomResource CORNERRIGHTTOP    = new CustomResource("Gui.Basic.components.Button.Standard.Corners.RightTop", WIDGETFILEPATH, 198, 21, 1, 1);
                        @Nonnull
                        public static CustomResource CORNERLEFTBOTTOM  = new CustomResource("Gui.Basic.components.Button.Standard.Corners.LeftBottom", WIDGETFILEPATH, 1, 37, 1, 2);
                        @Nonnull
                        public static CustomResource CORNERRIGHTBOTTOM =
                          new CustomResource("Gui.Basic.components.Button.Standard.Corners.RightBottom", WIDGETFILEPATH, 198, 37, 1, 2);

                        @Nonnull
                        public static CustomResource SIDETOP    = new CustomResource("Gui.Basic.components.Button.Standard.Side.Top", WIDGETFILEPATH, 1, 21, 196, 1);
                        @Nonnull
                        public static CustomResource SIDELEFT   = new CustomResource("Gui.Basic.components.Button.Standard.Side.Left", WIDGETFILEPATH, 1, 22, 1, 15);
                        @Nonnull
                        public static CustomResource SIDERIGHT  = new CustomResource("Gui.Basic.components.Button.Standard.Side.Right", WIDGETFILEPATH, 198, 22, 1, 15);
                        @Nonnull
                        public static CustomResource SIDEBOTTOM = new CustomResource("Gui.Basic.components.Button.Standard.Side.Bottom", WIDGETFILEPATH, 1, 37, 196, 2);

                        @Nonnull
                        public static CustomResource CENTER = new CustomResource("Gui.Basic.components.Button.Standard.Center", WIDGETFILEPATH, 2, 22, 196, 15);

                        @Nonnull
                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER),
                          new TextureComponent[] {new TextureComponent(CORNERLEFTTOP),
                            new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM),
                            new TextureComponent(CORNERLEFTBOTTOM)},
                          new TextureComponent[] {new TextureComponent(SIDETOP),
                            new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM),
                            new TextureComponent(SIDELEFT)});
                    }

                    public static class Clicked
                    {
                        @Nonnull
                        public static CustomResource CORNERLEFTTOP     = new CustomResource("Gui.Basic.components.Button.Clicked.Corners.LeftTop", WIDGETFILEPATH, 1, 41, 1, 1);
                        @Nonnull
                        public static CustomResource CORNERRIGHTTOP    = new CustomResource("Gui.Basic.components.Button.Clicked.Corners.RightTop", WIDGETFILEPATH, 198, 41, 1, 1);
                        @Nonnull
                        public static CustomResource CORNERLEFTBOTTOM  = new CustomResource("Gui.Basic.components.Button.Clicked.Corners.LeftBottom", WIDGETFILEPATH, 1, 57, 1, 2);
                        @Nonnull
                        public static CustomResource CORNERRIGHTBOTTOM =
                          new CustomResource("Gui.Basic.components.Button.Clicked.Corners.RightBottom", WIDGETFILEPATH, 198, 57, 1, 2);

                        @Nonnull
                        public static CustomResource SIDETOP    = new CustomResource("Gui.Basic.components.Button.Clicked.Side.Top", WIDGETFILEPATH, 1, 41, 196, 1);
                        @Nonnull
                        public static CustomResource SIDELEFT   = new CustomResource("Gui.Basic.components.Button.Clicked.Side.Left", WIDGETFILEPATH, 1, 42, 1, 15);
                        @Nonnull
                        public static CustomResource SIDERIGHT  = new CustomResource("Gui.Basic.components.Button.Clicked.Side.Right", WIDGETFILEPATH, 198, 42, 1, 15);
                        @Nonnull
                        public static CustomResource SIDEBOTTOM = new CustomResource("Gui.Basic.components.Button.Clicked.Side.Bottom", WIDGETFILEPATH, 1, 57, 196, 2);

                        @Nonnull
                        public static CustomResource CENTER = new CustomResource("Gui.Basic.components.Button.Clicked.Center", WIDGETFILEPATH, 2, 42, 196, 15);

                        @Nonnull
                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER),
                          new TextureComponent[] {new TextureComponent(CORNERLEFTTOP),
                            new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM),
                            new TextureComponent(CORNERLEFTBOTTOM)},
                          new TextureComponent[] {new TextureComponent(SIDETOP),
                            new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM),
                            new TextureComponent(SIDELEFT)});
                    }
                }
            }
        }
    }
}
