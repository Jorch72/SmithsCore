package com.ldtteam.smithscore.client.gui.management;

import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import net.minecraft.client.gui.Gui;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 12/3/2015.
 */
public interface IRenderManager
{

    /**
     * Method to get the Gui this StandardRenderManager renders on.
     *
     * @return The current active gui
     */
    @Nonnull
    Gui getRootGuiObject();

    /**
     * Methd to get the root GuiManager this StandardRenderManager belongs to.
     *
     * @return The GuiManager of the Root gui object.
     */
    @Nonnull
    IGUIManager getRootGuiManager();

    /**
     * Method to get this RenderManagers ScissorRegionManager.
     *
     * @return This RenderManagers ScissorRegionManager.
     */
    @Nonnull
    IScissorRegionManager getScissorRegionManager();

    /**
     * Method to render the BackGround of a Component
     *
     * @param component     The Component to render.
     * @param parentEnabled Indicates if the parent is enabled.
     */
    void renderBackgroundComponent(@Nonnull IGUIComponent component, boolean parentEnabled);

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component     The Component to render
     * @param parentEnabled Indicates if the parent is enabled.
     */
    void renderForegroundComponent(@Nonnull IGUIComponent component, boolean parentEnabled);

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     * @param mouseX    The absolute X-Coordinate of the Mouse
     * @param mouseY    The absolute Y-Coordinate of the Mouse
     */
    void renderToolTipComponent(@Nonnull IGUIComponent component, int mouseX, int mouseY);
}
