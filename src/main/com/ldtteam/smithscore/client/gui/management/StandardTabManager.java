package com.ldtteam.smithscore.client.gui.management;

import com.ldtteam.smithscore.SmithsCore;
import com.ldtteam.smithscore.client.events.gui.GuiInputEvent;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedTabHost;
import com.ldtteam.smithscore.client.gui.tabs.core.IGUITab;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Marc on 18.01.2016.
 */
public class StandardTabManager implements ITabManager
{

    transient IGUIBasedTabHost host;
    String activeTabId;
    @Nonnull
    LinkedHashMap<String, IGUITab> tabs = new LinkedHashMap<String, IGUITab>();

    public StandardTabManager(IGUIBasedTabHost host)
    {
        this.host = host;
    }

    /**
     * Method to get this TabManagers host.
     *
     * @return The host of this TabManager.
     */
    @Nonnull
    @Override
    public IGUIBasedTabHost getHost()
    {
        return host;
    }

    @Override
    public void setHost(@Nonnull final IGUIBasedTabHost host)
    {
        this.host = host;
    }

    /**
     * Method called by the Host.
     * <p>
     * To notify the Manager that all tabs have been registered.
     */
    @Override
    public void onTabRegistrationComplete()
    {
        for (IGUITab tab : tabs.values())
        {
            host.getManager().onTabChanged(tab.getID());
            tab.registerComponents(tab);
        }

        host.onTabChanged(activeTabId);
        host.getManager().onTabChanged(activeTabId);
        SmithsCore.getRegistry().getClientBus().post(new GuiInputEvent(GuiInputEvent.InputTypes.TABCHANGED, host.getID(), activeTabId));
    }

    /**
     * Method used to get the currently displayed Tab.
     *
     * @return The currently displayed Tab.
     */
    @Override
    public IGUITab getCurrentTab()
    {
        return tabs.get(activeTabId);
    }

    /**
     * Method used to get the current tabs Index inside the Tab list.
     *
     * @return The index of the currently displayed Index.
     */
    @Override
    public int getCurrentTabIndex()
    {
        return (new ArrayList<String>(tabs.keySet())).indexOf(activeTabId);
    }

    /**
     * Method used to get the tab from a Selector index.
     *
     * @param selectorIndex The index you want the tab for.
     * @return The tab for the requested selector index.
     *
     * @throws IllegalArgumentException is the given selectorIndex is not Valid.
     */
    @Nonnull
    @Override
    public IGUITab getTabFromSelectorIndex(int selectorIndex) throws IllegalArgumentException
    {
        if (selectorIndex < 0 || selectorIndex > getTabSelectorCount())
        {
            throw new IllegalArgumentException("The given selectorCount is not within a valid range.");
        }

        int tabIndex = getCurrentTabIndex();
        int selectedSelectorIndex = tabIndex % getTabSelectorCount();

        tabIndex -= selectedSelectorIndex;

        ArrayList<IGUITab> tabList = new ArrayList<>(tabs.values());

        if (tabList.size() < tabIndex + selectorIndex || tabIndex + selectorIndex < 0)
        {
            throw new IllegalArgumentException("The given selectorIndex is not available");
        }

        return tabList.get(tabIndex + selectorIndex);
    }

    /**
     * Method used to register a new Tab
     *
     * @param newTab The new tab.
     */
    @Override
    public void registerNewTab(@Nonnull IGUITab newTab)
    {
        if (tabs.size() == 0)
        {
            activeTabId = newTab.getID();
        }

        tabs.put(newTab.getID(), newTab);
    }

    /**
     * Method used to retrieve all the possible tabs for this TabManager.
     *
     * @return A LinkedHashMap that holds all the possible tabs sorted on registration order with their
     * ID as keys.
     */
    @Nonnull
    @Override
    public LinkedHashMap<String, IGUITab> getTabs()
    {
        return tabs;
    }

    /**
     * Method used to get to total tab selector count.
     *
     * @return The total amount of tab selectors that could fit on the host.
     */
    @Override
    public int getTabSelectorCount()
    {
        int maxCount = (host.getSize().getWidth() - 2 * getSelectorsHorizontalOffset()) / getTabSelectorWidth();

        if (maxCount > getTabs().size())
        {
            return getTabs().size();
        }

        return maxCount;
    }

    /**
     * Method to get the componentWidth of a Tab Selector
     *
     * @return The componentWidth of a Tab Selector. Vanilla standard is 28.
     */
    @Override
    public int getTabSelectorWidth()
    {
        return 24;
    }

    /**
     * Method to get the componentHeight of a Tab Selector.
     *
     * @return The componentHeight of a Tab Selector. Vanilla standard is 30. Due to border mechanics default is 33.
     */
    @Override
    public int getTabSelectorHeight()
    {
        return 27;
    }

    /**
     * Method used to grab the vertical offset for a Tab Selector when he is InActive.
     *
     * @return The amount of pixels a InActive Tab Selector is placed lower then a Active one.
     */
    @Override
    public int getInActiveSelectorVerticalOffset()
    {
        return 4;
    }

    /**
     * Method used to get the horizontal offset of the Tab Selectors from the top left corner of the UI.
     *
     * @return The horizontal offset of the Selectors.
     */
    @Override
    public int getSelectorsHorizontalOffset()
    {
        return 4;
    }

    /**
     * Method used to get the vertical offset of the Display area of a TabManager
     *
     * @return The vertical offset of the DisplayArea
     */
    @Override
    public int getDisplayAreaVerticalOffset()
    {
        if (this.getTabs().size() < 2)
        {
            return 0;
        }

        return getTabSelectorHeight() - 3;
    }

    /**
     * Method to set the currently displayed tab.
     *
     * @param tab The tab to be displayed.
     */
    @Override
    public void setActiveTab(@Nonnull IGUITab tab)
    {
        activeTabId = tab.getID();

        host.onTabChanged(tab.getID());
        host.getManager().onTabChanged(tab.getID());
        SmithsCore.getRegistry().getClientBus().post(new GuiInputEvent(GuiInputEvent.InputTypes.TABCHANGED, host.getID(), tab.getID()));
    }
}
