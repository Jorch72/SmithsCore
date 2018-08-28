package com.ldtteam.smithscore.client.gui.management;

import com.ldtteam.smithscore.client.events.gui.GuiInputEvent;
import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import com.ldtteam.smithscore.common.inventory.ContainerSmithsCore;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:13
 * <p>
 * Copyrighted according to Project specific license
 */
public class RelayBasedGUIManager implements IGUIManager
{

    IGUIManagerProvider host;
    ContainerSmithsCore containerSmithsCore;

    public RelayBasedGUIManager(@Nonnull IGUIManagerProvider host, @Nonnull ContainerSmithsCore containerSmithsCore)
    {
        this.host = host;
        this.containerSmithsCore = containerSmithsCore;
    }

    /**
     * Method called when a player closed the linked UI.
     *
     * @param playerId The unique ID of the player that opens the UI.
     */
    @Override
    public void onGuiOpened(@Nonnull UUID playerId)
    {
        host.getManager().onGuiOpened(playerId);
    }

    /**
     * Method called when a player closes the linked UI.
     *
     * @param playerID The unique ID of the player that closed the UI.
     */
    @Override
    public void onGUIClosed(@Nonnull UUID playerID)
    {
        host.getManager().onGUIClosed(playerID);
    }

    /**
     * Method used by components that support overriding tooltips to grab the new tooltip string.
     *
     * @param component The component requesting the override.
     * @return A string displayed as tooltip for the IGUIComponent during rendering.
     */
    @Override
    @Nonnull
    public String getCustomToolTipDisplayString(@Nonnull IGUIComponent component)
    {
        return host.getManager().getCustomToolTipDisplayString(component);
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue(@Nonnull IGUIComponent component)
    {
        return host.getManager().getProgressBarValue(component);
    }

    /**
     * Method used by the rendering system to dynamically update a Label.
     *
     * @param component The component requesting the content.
     * @return THe string that should be displayed.
     */
    @Override
    @Nullable
    public String getLabelContents(@Nonnull IGUIComponent component)
    {
        return host.getManager().getLabelContents(component);
    }

    @Override
    @Nullable
    public ArrayList<FluidStack> getTankContents(@Nonnull IGUIComponent component)
    {
        return host.getManager().getTankContents(component);
    }

    @Override
    public int getTotalTankContents(@Nonnull IGUIComponent component)
    {
        return host.getManager().getTotalTankContents(component);
    }

    /**
     * Method called by GUI's that are tab based when the active Tab changed.
     *
     * @param newActiveTabId The new active Tabs ID.
     */
    @Override
    public void onTabChanged(@Nonnull String newActiveTabId)
    {
        containerSmithsCore.onTabChanged(newActiveTabId);
    }

    @Override
    public void onInput(GuiInputEvent.InputTypes types, @Nonnull String componentId, @Nonnull String input)
    {
        host.getManager().onInput(types, componentId, input);
    }
}
