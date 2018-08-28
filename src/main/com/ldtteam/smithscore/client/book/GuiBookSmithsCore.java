package com.ldtteam.smithscore.client.book;

import com.ldtteam.smithscore.client.book.data.IBook;
import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.ldtteam.smithscore.client.gui.management.IGUIManager;
import com.ldtteam.smithscore.client.gui.management.IRenderManager;
import com.ldtteam.smithscore.client.gui.management.StandardRenderManager;
import com.ldtteam.smithscore.client.gui.state.CoreComponentState;
import com.ldtteam.smithscore.client.gui.state.IGUIComponentState;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import com.ldtteam.smithscore.util.common.positioning.Plane;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by marcf on 7/30/2016.
 */
public class GuiBookSmithsCore extends GuiScreen implements IGUIBasedComponentHost
{

    boolean isInitialized = false;

    @Nonnull
    IRenderManager renderer = new StandardRenderManager(this);
    @Nonnull
    IGUIManager guiManager;
    @Nonnull
    IGUIComponentState                   state      = new CoreComponentState(this);
    @Nonnull
    LinkedHashMap<String, IGUIComponent> components = new LinkedHashMap<>();

    IBook book;

    Plane contents;

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     *
     * @param typedChar The char pressed
     * @param keyCode   The corresponding keycode.
     */
    @Override
    protected void keyTyped(@Nonnull char typedChar, @Nonnull int keyCode) throws IOException
    {
        if (this.handleKeyTyped(typedChar, keyCode))
        {
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawHoveringText(@Nullable List<String> textLines, @Nonnull int x, @Nonnull int y, @Nonnull FontRenderer font)
    {
        if (textLines == null || textLines.size() == 0)
        {
            return;
        }

        super.drawHoveringText(textLines, x, y, font);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     *
     * @param mouseX      The absolute X-Coordinate of the mouse click
     * @param mouseY      The absolute Y-Coordinate of the mouse click
     * @param mouseButton The mouse button that was used to perform the click
     */
    @Override
    protected void mouseClicked(@Nonnull int mouseX, @Nonnull int mouseY, @Nonnull int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (requiresForcedMouseInput())
        {
            this.handleMouseClickedOutside(mouseX - getLocalCoordinate().getXComponent(), mouseY - getLocalCoordinate().getYComponent(), mouseButton);
        }

        this.handleMouseClickedInside(mouseX - getLocalCoordinate().getXComponent(), mouseY - getLocalCoordinate().getYComponent(), mouseButton);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the gui is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        if (!isInitialized())
        {
            registerComponents(this);
        }

        Plane areaWithComponents = getSize();
        int xOffSet = (this.width - areaWithComponents.getWidth()) / 2;
        int yOffSet = (this.height - areaWithComponents.getHeigth()) / 2;

        this.contents = new Plane(new Coordinate2D(xOffSet, yOffSet), areaWithComponents.getWidth(), areaWithComponents.getHeigth());

        super.initGui();

        setIsInitialized(true);

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();

        Keyboard.enableRepeatEvents(false);
    }

    @Nonnull
    public boolean isInitialized()
    {
        return isInitialized;
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host)
    {
        for (IGUIComponent component : book.getOpenPage().getComponents().values())
        {
            registerNewComponent(component);
        }
    }

    private void setIsInitialized(@Nonnull boolean isInitialized)
    {
        this.isInitialized = isInitialized;
    }

    @Override
    public void registerNewComponent(@Nonnull IGUIComponent component)
    {
        this.components.put(component.getID(), component);
    }

    @Nonnull
    @Override
    public IGUIBasedComponentHost getRootGuiObject()
    {
        return this;
    }

    @Override
    @Nonnull
    public IGUIManager getRootManager()
    {
        return getManager();
    }

    @Override
    @Nonnull
    public IGUIManager getManager()
    {
        return guiManager;
    }

    @Override
    public void setManager(@Nonnull IGUIManager newManager)
    {
        this.guiManager = newManager;
    }

    @Nonnull
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents()
    {
        return components;
    }

    @Nullable
    public IGUIComponent getComponentByID(@Nonnull String uniqueUIID)
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

    @Nonnull
    @Override
    public String getID()
    {
        return book.getBookLocation().toString();
    }

    @Nonnull
    @Override
    public IGUIComponentState getState()
    {
        return state;
    }

    @Nonnull
    @Override
    public IGUIBasedComponentHost getComponentHost()
    {
        return this;
    }

    @Override
    public void setComponentHost(@Nonnull final IGUIBasedComponentHost host)
    {
        //NOOP, This is the root of the component tree.
    }

    @Override
    @Nonnull
    public Coordinate2D getGlobalCoordinate()
    {
        return getLocalCoordinate();
    }

    @Override
    @Nonnull
    public Coordinate2D getLocalCoordinate()
    {
        if (contents == null)
        {
            return new Coordinate2D(0, 0);
        }

        return contents.TopLeftCoord();
    }

    @Override
    public void setLocalCoordinate(@Nonnull final Coordinate2D coordinate)
    {
        //Noop guis can not be moved.
    }

    @Override
    @Nonnull
    public Plane getAreaOccupiedByComponent()
    {
        if (contents == null)
        {
            return new Plane();
        }

        return contents;
    }

    @Nonnull
    @Override
    public Plane getSize()
    {
        Plane area = new Plane(0, 0, 0, 0);

        for (IGUIComponent component : getAllComponents().values())
        {
            area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
        }

        return area;
    }

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update(@Nonnull int mouseX, @Nonnull int mouseY, @Nonnull float partialTickTime)
    {
        //NOOP
    }

    @Override
    public void drawBackground(@Nonnull int mouseX, @Nonnull int mouseY)
    {
        renderer.renderBackgroundComponent(this, false);
    }

    @Override
    public void drawForeground(@Nonnull int mouseX, @Nonnull int mouseY)
    {
        renderer.renderForegroundComponent(this, false);
        renderer.renderToolTipComponent(this, mouseX - getLocalCoordinate().getXComponent(), mouseY - getLocalCoordinate().getYComponent());
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
    public boolean handleMouseClickedInside(@Nonnull int relativeMouseX, @Nonnull int relativeMouseY, @Nonnull int mouseButton)
    {
        for (IGUIComponent component : getAllComponents().values())
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

        return true;
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
    public boolean handleMouseClickedOutside(@Nonnull int relativeMouseX, @Nonnull int relativeMouseY, @Nonnull int mouseButton)
    {
        for (IGUIComponent component : getAllComponents().values())
        {
            if (component.requiresForcedMouseInput())
            {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        return false;
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        int delta = Mouse.getEventDWheel();
        if (delta != 0)
        {
            final Plane plane = getSize().Move(getLocalCoordinate().getXComponent(), getLocalCoordinate().getYComponent());
            if (plane.ContainsCoordinate(x, y))
            {
                x -= getLocalCoordinate().getXComponent();
                y -= getLocalCoordinate().getYComponent();

                handleMouseWheel(x, y, delta);
            }
        }
        super.handleMouseInput();
    }

    @Nonnull
    @Override
    public boolean handleMouseWheel(final int relativeMouseX, @Nonnull final int relativeMouseY, @Nonnull final int deltaWheel)
    {
        //TODO!
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
        for (IGUIComponent component : getAllComponents().values())
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
    public boolean handleKeyTyped(@Nonnull char key, @Nonnull int keyCode)
    {
        for (IGUIComponent component : getAllComponents().values())
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
    public ArrayList<String> getToolTipContent()
    {
        return new ArrayList<String>();
    }

    /**
     * Method used to retrieve the rendermanager of this gui,
     *
     * @return The currently used StandardRenderManager.
     */
    @Nonnull
    public IRenderManager getRenderManager()
    {
        return renderer;
    }

    @Override
    @Nonnull
    public int getDefaultDisplayVerticalOffset()
    {
        return 0;
    }
}
