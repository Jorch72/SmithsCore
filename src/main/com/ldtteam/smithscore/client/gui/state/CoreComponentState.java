package com.ldtteam.smithscore.client.gui.state;

import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 21.12.2015.
 */
public class CoreComponentState implements IGUIComponentState
{

    private IGUIComponent component;

    private boolean enabled = true;
    private boolean visible = true;

    public CoreComponentState(IGUIComponent component)
    {
        this.component = component;
    }

    public CoreComponentState() {}

    @Override
    public IGUIComponent getComponent()
    {
        return component;
    }

    @Override
    public void setComponent(@Nonnull IGUIComponent component)
    {
        this.component = component;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public void setEnabledState(boolean state)
    {
        enabled = state;
    }

    @Override
    public boolean isVisible()
    {
        return visible;
    }

    @Override
    public void setVisibleState(boolean state)
    {
        visible = state;
    }
}
