package com.ldtteam.smithscore.client.gui.animation;

import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 09.01.2016.
 */
public interface IAnimatibleGuiComponent extends IGUIComponent
{

    /**
     * Method called by the rendering system to perform animations. It is called before the component is rendered but
     * after the component has been updated.
     *
     * @param partialTickTime The current partial tick time.
     */
    void performAnimation(@Nonnull float partialTickTime);
}
