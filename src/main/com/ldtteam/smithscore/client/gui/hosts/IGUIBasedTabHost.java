package com.ldtteam.smithscore.client.gui.hosts;

import com.ldtteam.smithscore.client.gui.management.ITabManager;
import com.ldtteam.smithscore.client.gui.tabs.core.IGUITab;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/15/2016.
 */
public interface IGUIBasedTabHost extends IGUIBasedComponentHost
{

    /**
     * Method called by the gui system to initialize this tab host.
     *
     * @param host The host for the tabs.
     */
    void registerTabs(@Nonnull IGUIBasedTabHost host);

    /**
     * Method used to register a new Tab to this host.
     * Should be called from the registerTabs method to handle sub component init properly.
     *
     * @param tab The new Tab to register.
     */
    void registerNewTab(@Nonnull IGUITab tab);

    /**
     * Method to get the TabManager to handle Tab Interactions.
     *
     * @return The current TabManager for this host.
     */
    @Nonnull
    ITabManager getTabManager();

    /**
     * Method called by a TabManager to indicate that the active tab has been changed.
     *
     * @param newActiveTabID The ID of the new active tab.
     */
    void onTabChanged(@Nonnull String newActiveTabID);
}
