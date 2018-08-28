package com.ldtteam.smithscore.client.gui.components.implementations;

import com.ldtteam.smithscore.client.gui.components.core.ComponentOrientation;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.ldtteam.smithscore.client.gui.state.IGUIComponentState;
import com.ldtteam.smithscore.util.client.CustomResource;
import com.ldtteam.smithscore.util.client.TranslationKeys;
import com.ldtteam.smithscore.util.client.gui.GuiHelper;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import com.ldtteam.smithscore.util.common.positioning.Plane;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by Marc on 25.12.2015.
 */
public class ComponentProgressBar extends CoreComponent
{

    private ComponentOrientation renderDirection;

    private CustomResource emptyResource;
    private CustomResource fullResource;

    public ComponentProgressBar(
      @Nonnull String uniqueID,
      @Nonnull IGUIBasedComponentHost root,
      @Nonnull IGUIComponentState state,
      @Nonnull Coordinate2D localCoordinate,
      ComponentOrientation componentDirection,
      CustomResource emptyResource,
      CustomResource fullResource)
    {
        super(uniqueID, root, state, localCoordinate, 0, 0);

        this.renderDirection = componentDirection;
        this.emptyResource = emptyResource;
        this.fullResource = fullResource;
    }

    @Nonnull
    @Override
    public Plane getAreaOccupiedByComponent()
    {
        return new Plane(getGlobalCoordinate(), getWidth(), getHeight());
    }

    @Nonnull
    @Override
    public Plane getSize()
    {
        return new Plane(0, 0, getWidth(), getHeight());
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
        //NOOP
    }

    @Override
    public void drawBackground(int mouseX, int mouseY)
    {
        GuiHelper.drawResource(emptyResource, 0, 0);

        switch (renderDirection)
        {
            case HORIZONTALLEFTTORIGHT:
                drawTopLayerFromLeftToRight();
                break;
            case HORIZONTALRIGHTTOLEFT:
                drawTopLayerFromRightToLeft();
                break;
            case VERTICALBOTTOMTOTOP:
                drawTopLayerBottomToTop();
                break;
            case VERTICALTOPTOBOTTOM:
                drawTopLayerTopToBottom();
                break;
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        //NOOP
    }

    @Nonnull
    @Override
    public ArrayList<String> getToolTipContent()
    {
        ArrayList<String> tips = new ArrayList<String>();

        String displayString = getComponentHost().getRootManager().getCustomToolTipDisplayString(this);
        if (displayString == null || displayString == "" || displayString.length() == 0)
        {
            displayString = I18n.format(TranslationKeys.GUI.PROGRESS) + ": " + Math.round(getComponentHost().getRootManager().getProgressBarValue(this) * 100) + "%";
        }

        tips.add(displayString);

        return tips;
    }

    public void drawTopLayerFromLeftToRight()
    {
        Plane renderBox = new Plane(getGlobalCoordinate(), (int) (getWidth() * getComponentHost().getRootManager().getProgressBarValue(this)), getHeight());

        if (renderBox.getWidth() == 0)
        {
            return;
        }

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(renderBox);

        GuiHelper.drawResource(fullResource, 0, 0);

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();
    }

    public void drawTopLayerFromRightToLeft()
    {
        int fullWidth = (int) (getWidth() * getComponentHost().getRootManager().getProgressBarValue(this));

        if (fullWidth == 0)
        {
            return;
        }

        Plane renderBox = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(getWidth() - fullWidth, 0)), fullWidth, getHeight());

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(renderBox);

        GuiHelper.drawResource(fullResource, 0, 0);

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();
    }

    public void drawTopLayerBottomToTop()
    {
        int fullHeight = (int) (getHeight() * getComponentHost().getRootManager().getProgressBarValue(this));

        if (fullHeight == 0)
        {
            return;
        }

        Plane renderBox = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(0, getHeight() - fullHeight)), getWidth(), fullHeight);

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(renderBox);

        GuiHelper.drawResource(fullResource, 0, 0);

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();
    }

    public void drawTopLayerTopToBottom()
    {
        Plane renderBox = new Plane(getGlobalCoordinate(), getWidth(), (int) (getHeight() * getComponentHost().getRootManager().getProgressBarValue(this)));

        if (renderBox.getHeigth() == 0)
        {
            return;
        }

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(renderBox);

        GuiHelper.drawResource(fullResource, 0, 0);

        getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();
    }

    private int getWidth()
    {
        if (emptyResource.getWidth() > fullResource.getWidth())
        {
            return emptyResource.getWidth();
        }

        return fullResource.getWidth();
    }

    private int getHeight()
    {
        if (emptyResource.getHeight() > fullResource.getHeight())
        {
            return emptyResource.getHeight();
        }

        return fullResource.getHeight();
    }
}
