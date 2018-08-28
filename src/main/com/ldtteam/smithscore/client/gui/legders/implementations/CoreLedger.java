package com.ldtteam.smithscore.client.gui.legders.implementations;

import com.ldtteam.smithscore.client.gui.animation.IAnimatibleGuiComponent;
import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentBorder;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentImage;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentLabel;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.ldtteam.smithscore.client.gui.legders.core.IGUILedger;
import com.ldtteam.smithscore.client.gui.legders.core.LedgerConnectionSide;
import com.ldtteam.smithscore.client.gui.management.IGUIManager;
import com.ldtteam.smithscore.client.gui.management.IRenderManager;
import com.ldtteam.smithscore.client.gui.state.CoreComponentState;
import com.ldtteam.smithscore.client.gui.state.IGUIComponentState;
import com.ldtteam.smithscore.client.gui.state.LedgerComponentState;
import com.ldtteam.smithscore.util.client.CustomResource;
import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import com.ldtteam.smithscore.util.common.positioning.Plane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by marcf on 12/28/2015.
 */
public abstract class CoreLedger implements IGUILedger, IAnimatibleGuiComponent
{

    protected         CustomResource       ledgerIcon;
    protected         String               translatedLedgerHeader;
    protected         int                  closedLedgerHeight;
    protected         int                  closedLedgerWidth;
    private           String               uniqueID;
    private           LedgerComponentState state;
    private transient IGUIBasedLedgerHost  root;
    private           LedgerConnectionSide side;
    @Nonnull
    private LinkedHashMap<String, IGUIComponent> components = new LinkedHashMap<String, IGUIComponent>();
    private MinecraftColor color;

    public CoreLedger(
      @Nonnull String uniqueID,
      @Nonnull LedgerComponentState state,
      @Nonnull IGUIBasedLedgerHost root,
      LedgerConnectionSide side,
      @Nonnull CustomResource ledgerIcon,
      @Nonnull String translatedLedgerHeader,
      @Nonnull MinecraftColor color)
    {
        this.uniqueID = uniqueID;
        this.state = state;
        this.state.setComponent(this);
        this.root = root;
        this.side = side;
        this.ledgerIcon = ledgerIcon;
        this.translatedLedgerHeader = translatedLedgerHeader;
        this.color = color;

        closedLedgerHeight = ledgerIcon.getHeight() + 10;
        closedLedgerWidth = ledgerIcon.getWidth() + 10;
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host)
    {
        ComponentImage componentImage = new ComponentImage(getID() + ".header.icon", new CoreComponentState(null), this, new Coordinate2D(5, 5), ledgerIcon)
        {
            @Nonnull
            @Override
            public ArrayList<String> getToolTipContent()
            {
                return getIconToolTipText();
            }
        };
        ComponentLabel
          componentLabel = new ComponentLabel(getID() + ".header.label",
          this,
          new CoreComponentState(null),
          new Coordinate2D(closedLedgerWidth, 5 + (ledgerIcon.getHeight() - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2),
          new MinecraftColor(MinecraftColor.WHITE),
          Minecraft.getMinecraft().fontRenderer,
          translatedLedgerHeader);

        registerNewComponent(componentImage);
        registerNewComponent(componentLabel);
    }

    /**
     * Function used to get the ID of the Component.
     *
     * @return The ID of this Component.
     */
    @Nonnull
    @Override
    public String getID()
    {
        return uniqueID;
    }

    /**
     * Function used to get the StateObject of this Component. A good example of what to find in the state Object is the
     * Visibility of it. But also things like the Displayed Text of a label or TextBox are stored in the components
     * state Object.
     *
     * @return This components state Object.
     */
    @Nonnull
    @Override
    public IGUIComponentState getState()
    {
        return state;
    }

    /**
     * Function to get this components host.
     *
     * @return This components host.
     */
    @Nonnull
    @Override
    public IGUIBasedComponentHost getComponentHost()
    {
        return root;
    }

    @Override
    public void setComponentHost(@Nonnull final IGUIBasedComponentHost host)
    {
        if (host instanceof IGUIBasedLedgerHost)
        {
            this.root = (IGUIBasedLedgerHost) host;
        }
    }

    /**
     * Method to get the rootAnchorPixel location as seen globally by the render system of OpenGL.
     *
     * @return The location of the top left pixel of this component
     */
    @Nonnull
    @Override
    public Coordinate2D getGlobalCoordinate()
    {
        return root.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    /**
     * Returns to location of the most top left rendered Pixel of this Component relative to its parent.
     *
     * @return A Coordinate representing the Location of the most top left Pixel relative to its parent.
     */
    @Nonnull
    @Override
    public Coordinate2D getLocalCoordinate()
    {
        Coordinate2D primaryCorner = getLedgerHost().getLedgerManager().getLedgerLocalCoordinate(getPrimarySide(), getID());

        if (getPrimarySide() == LedgerConnectionSide.LEFT)
        {
            return primaryCorner.getTranslatedCoordinate(new Coordinate2D(-1 * (getSize().getWidth() - 4), 0));
        }
        else
        {
            return primaryCorner.getTranslatedCoordinate(new Coordinate2D(-4, 0));
        }
    }

    @Override
    public void setLocalCoordinate(@Nonnull final Coordinate2D coordinate)
    {
        //Noop local coords of ledgers are dynamically calculated.
    }

    /**
     * Gets the Area Occupied by this Component, is locally oriented.
     *
     * @return A Plane detailing the the position and size of this Component.
     */
    @Nonnull
    @Override
    public Plane getAreaOccupiedByComponent()
    {
        return new Plane(getGlobalCoordinate(), getSize().getWidth(), getSize().getHeigth());
    }

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Nonnull
    @Override
    public Plane getSize()
    {
        return new Plane(0,
          0,
          (int) Math.ceil(closedLedgerWidth + (getMaxWidth() - closedLedgerWidth) * state.getOpenProgress()),
          (int) Math.ceil(closedLedgerHeight + (getMaxHeight() - closedLedgerHeight) * state.getOpenProgress()));
    }

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update(int mouseX, int mouseY, float partialTickTime)
    {
        //The ledger it self has no update only an animation.
    }

    /**
     * Function used to draw this components background. Usually this will incorporate all of the components visual
     * objects. A good example of a Component that only uses the drawBackground function is the BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawBackground(int mouseX, int mouseY)
    {
        ComponentBorder componentBorder = new ComponentBorder(getID() + ".background",
          this,
          new Coordinate2D(0, 0),
          getSize().getWidth(),
          getSize().getHeigth(),
          color,
          ComponentBorder.CornerTypes.Inwards,
          ComponentBorder.CornerTypes.Inwards,
          ComponentBorder.CornerTypes.Inwards,
          ComponentBorder.CornerTypes.Inwards);

        getRootGuiObject().getRenderManager().renderBackgroundComponent(componentBorder, false);
    }

    /**
     * Function used to draw this components foreground. Usually this will incorporate very few of teh components visual
     * Objects. A good example of a Component that only uses the drawForeground function is the GUIDescriptionLabel (The
     * labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        //NOOP
    }

    /**
     * Function called when the mouse was clicked inside of this component. Either it should pass this function to its
     * SubComponents (making sure that it recalculates the location and checks if it is inside before hand, handle the
     * Click them self or both.
     * <p>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the
     *                       mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the
     *                       mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedInside(int relativeMouseX, int relativeMouseY, int mouseButton)
    {
        for (IGUIComponent component : components.values())
        {
            Coordinate2D location = component.getLocalCoordinate();
            Plane localOccupiedArea = component.getSize().Move(location.getXComponent(), location.getYComponent());

            if (!localOccupiedArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
            {
                continue;
            }

            if (component.handleMouseClickedInside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton))
            {
                return true;
            }
        }

        getLedgerHost().getLedgerManager().onLedgerClickedInside(this);

        return true;
    }

    /**
     * Function to get this components host.
     *
     * @return This components host.
     */
    @Nonnull
    @Override
    public IGUIBasedLedgerHost getLedgerHost()
    {
        return root;
    }

    @Override
    public void setLedgerHost(@Nonnull final IGUIBasedLedgerHost host)
    {
        this.root = host;
    }

    /**
     * Method to get the primary rendered side of the Ledger.
     *
     * @return Left when the Ledger is rendered on the left side, right when rendered on the right side.
     */
    @Nonnull
    @Override
    public LedgerConnectionSide getPrimarySide()
    {
        return side;
    }

    /**
     * Method used by the ToolTip system to dynamically get the ToolTip that is displayed when hovered over the Icon.
     *
     * @return The Icons ToolTip.
     */
    @Nonnull
    @Override
    public ArrayList<String> getIconToolTipText()
    {
        ArrayList<String> result = new ArrayList<String>();

        result.add(translatedLedgerHeader);

        return result;
    }

    /**
     * Function called when the mouse was clicked outside of this component. It is only called when the function
     * requiresForcedMouseInput() return true Either it should pass this function to its SubComponents (making sure that
     * it recalculates the location and checks if it is inside before hand, handle the Click them self or both.
     * <p>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the
     *                       mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the
     *                       mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedOutside(int relativeMouseX, int relativeMouseY, int mouseButton)
    {
        for (IGUIComponent component : components.values())
        {
            if (component.requiresForcedMouseInput())
            {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        return false;
    }

    /**
     * Method to check if this function should capture all of the buttons pressed on the mouse regardless of the press
     * location was inside or outside of the Component.
     *
     * @return True when all the mouse clicks should be captured by this component.
     */
    @Override
    public boolean requiresForcedMouseInput()
    {
        for (IGUIComponent component : components.values())
        {
            if (component.requiresForcedMouseInput())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Function called when a Key is typed.
     *
     * @param key The key that was typed.
     */
    @Override
    public boolean handleKeyTyped(char key, int keyCode)
    {
        for (IGUIComponent component : components.values())
        {
            if (component.handleKeyTyped(key, keyCode))
            {
                return true;
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public boolean handleMouseWheel(final int relativeMouseX, @Nonnull final int relativeMouseY, @Nonnull final int deltaWheel)
    {
        for (IGUIComponent component : components.values())
        {
            final Plane plane = component.getSize().Move(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent());

            if (plane.ContainsCoordinate(relativeMouseX, relativeMouseY))
            {
                if (component.handleMouseWheel(relativeMouseX - component.getLocalCoordinate().getXComponent(),
                  relativeMouseY - component.getLocalCoordinate().getYComponent(),
                  deltaWheel))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public ArrayList<String> getToolTipContent()
    {
        return new ArrayList<String>();
    }

    /**
     * Method used by the rendering and animation system to determine the max size of the Ledger.
     *
     * @return An int bigger then 16 plus the icon componentWidth that describes the maximum componentWidth of the Ledger when expanded.
     */
    public abstract int getMaxWidth();

    /**
     * Method used by the rendering and animation system to determine the max size of the Ledger.
     *
     * @return An int bigger then 16 plus the icon componentHeight that describes the maximum componentHeight of the Ledger when expanded.
     */
    public abstract int getMaxHeight();

    /**
     * Method used to register a new Component to this host.
     *
     * @param component The new component.
     */
    @Override
    public void registerNewComponent(@Nonnull IGUIComponent component)
    {
        components.put(component.getID(), component);

        if (component instanceof IGUIBasedComponentHost)
        {
            ((IGUIBasedComponentHost) component).registerComponents((IGUIBasedComponentHost) component);
        }
    }

    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    @Nonnull
    @Override
    public IGUIBasedComponentHost getRootGuiObject()
    {
        return getComponentHost().getRootGuiObject();
    }

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    @Nonnull
    @Override
    public IGUIManager getRootManager()
    {
        return root.getRootManager();
    }

    /**
     * Function to get all the components registered to this host.
     *
     * @return A ID to Component map that holds all the components (but not their SubComponents) of this host.
     */
    @Nonnull
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents()
    {
        return components;
    }

    @Nullable
    public IGUIComponent getComponentByID(String uniqueUIID)
    {
        if (getID().equals(uniqueUIID))
        {
            return this;
        }

        if (getAllComponents().get(uniqueUIID) != null)
        {
            return getAllComponents().get(uniqueUIID);
        }

        for (IGUIComponent childComponent : getAllComponents().values())
        {
            if (childComponent instanceof IGUIBasedComponentHost)
            {
                IGUIComponent foundComponent = ((IGUIBasedComponentHost) childComponent).getComponentByID(uniqueUIID);

                if (foundComponent != null)
                {
                    return foundComponent;
                }
            }
        }

        return null;
    }

    @Override
    public void drawHoveringText(@Nullable List<String> textLines, int x, int y, FontRenderer font)
    {
        getComponentHost().drawHoveringText(textLines, x, y, font);
    }

    @Nonnull
    @Override
    public IRenderManager getRenderManager()
    {
        return getComponentHost().getRenderManager();
    }

    @Override
    public int getDefaultDisplayVerticalOffset()
    {
        return getComponentHost().getDefaultDisplayVerticalOffset();
    }

    /**
     * Method called by the rendering system to perform animations. It is called before the component is rendered but
     * after the component has been updated.
     *
     * @param partialTickTime The current partial tick time.
     */
    @Override
    public void performAnimation(float partialTickTime)
    {
        if (state.getOpenState() && state.getOpenProgress() < 1F)
        {
            float newTotalAnimationTicks = (state.getOpenProgress() * getAnimationTime()) + partialTickTime;

            if (newTotalAnimationTicks > getAnimationTime())
            {
                newTotalAnimationTicks = getAnimationTime();
            }

            state.setOpenProgress(newTotalAnimationTicks / getAnimationTime());
        }
        else if (!state.getOpenState() && state.getOpenProgress() > 0F)
        {
            float newTotalAnimationTicks = (state.getOpenProgress() * getAnimationTime()) - partialTickTime;

            if (newTotalAnimationTicks < 0F)
            {
                newTotalAnimationTicks = 0f;
            }

            state.setOpenProgress(newTotalAnimationTicks / getAnimationTime());
        }
    }

    /**
     * Method used by the animation system of the Ledger to determine how fast the animation should go. Measured in game
     * update ticks, 20 Ticks is 1 Second. The animation system will take the partial tick time into account when it
     * calculates the update of pixels.
     *
     * @return The amount of game ticks it should take to complete the opening and closing animation.
     */
    public int getAnimationTime()
    {
        if (getMaxWidth() >= getMaxHeight())
        {
            return getMaxWidth() / 4 + 1;
        }

        return getMaxHeight() / 4 + 1;
    }

    @Override
    public boolean shouldScissor()
    {
        return true;
    }

    @Nonnull
    @Override
    public Plane getGlobalScissorLocation()
    {
        return new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(4, 4)), getSize().getWidth() - 8, getSize().getHeigth() - 8);
    }

    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    @Override
    @Nonnull
    public IGUIManager getManager()
    {
        return root.getManager();
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager(@Nonnull IGUIManager newManager)
    {
        root.setManager(newManager);
    }
}
