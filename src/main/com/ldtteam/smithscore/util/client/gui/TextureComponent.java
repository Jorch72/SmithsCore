/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.smithscore.util.client.gui;

import com.ldtteam.smithscore.util.client.CustomResource;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.renderer.texture.TextureMap;

import javax.annotation.Nonnull;

/**
 * A Part of a Texture.
 */
public class TextureComponent
{

    public int iU      = 0;
    public int iV      = 0;
    public int iWidth  = 0;
    public int iHeight = 0;

    //The Translation that has to be done after the Rotation.
    public Coordinate2D iRelativeTranslation = new Coordinate2D(0, 0);

    //The rotation that has to be done to make this Component fit.
    public UIRotation iRotation = new UIRotation(false, false, false, 0F);

    //The texture address of this Component.
    public String iAddress = TextureMap.LOCATION_BLOCKS_TEXTURE.getResourcePath();

    /**
     * Standard constructor for a TextureComponent
     *
     * @param pAddress             The ResourceLocation of this TextureComponent.
     * @param pU                   The X Coordinate in the Image of this Component.
     * @param pV                   The Y Coordinate in the Image of this Component
     * @param pWidth               The Width of this Component
     * @param pHeight              The Height of this Component
     * @param pRotation            The UIRotation that has to be done to this Component fit.
     * @param pRelativeTranslation The Translation that has to be done before the Render but after the rotation to make this Component fit.
     */
    public TextureComponent(@Nonnull String pAddress, int pU, int pV, int pWidth, int pHeight, @Nonnull UIRotation pRotation, @Nonnull Coordinate2D pRelativeTranslation)
    {
        iAddress = pAddress;
        iU = pU;
        iV = pV;
        iWidth = pWidth;
        iHeight = pHeight;
        iRotation = pRotation;
        iRelativeTranslation = pRelativeTranslation;
    }

    /**
     * A Special small Constructor used if no rotation or translation is needed.
     *
     * @param pResource The CustomResource describing this Component.
     */
    public TextureComponent(@Nonnull CustomResource pResource)
    {
        this(pResource, new UIRotation(false, false, false, 0F), new Coordinate2D(0, 0));
    }

    /**
     * Standard constructor for a TextureComponent
     *
     * @param pResource            CustomResource describing this Component.
     * @param pRotation            The UIRotation that has to be done to this Component fit.
     * @param pRelativeTranslation The Translation that has to be done before the Render but after the rotation to make this Component fit.
     */
    public TextureComponent(@Nonnull CustomResource pResource, @Nonnull UIRotation pRotation, @Nonnull Coordinate2D pRelativeTranslation)
    {
        iAddress = pResource.getPrimaryLocation();
        iU = pResource.getU();
        iV = pResource.getV();
        iWidth = pResource.getWidth();
        iHeight = pResource.getHeight();
        iRotation = pRotation;
        iRelativeTranslation = pRelativeTranslation;
    }
}
