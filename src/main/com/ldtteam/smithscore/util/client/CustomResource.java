/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.smithscore.util.client;
/*
/  CustomResource
/  Created by : Orion
/  Created on : 15/06/2014
*/

import com.ldtteam.smithscore.util.client.color.Colors;
import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Class used to manage resource combinations.
 * It is meanly used for a Item/In world Model combination.
 * You can register two texture sheets and a single IIcon to it.
 */
public class CustomResource
{
    private String iInternalName;

    @Nonnull
    private ArrayList<String> iRescourceLocations = new ArrayList<String>();
    private TextureAtlasSprite iIcon;
    private MinecraftColor     iColor;

    private int iLeft;
    private int iTop;
    private int iWidth;
    private int iHeight;

    /**
     * Standard constructor for a single Texture resource
     *
     * @param pInternalName The ID used to identify this Resource
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation)
    {
        this(pInternalName, pIconLocation, "");
    }

    /**
     * Standard constructor for a multi Texture resource
     *
     * @param pInternalName  The ID used to identify this Resource.
     * @param pIconLocation  The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pModelLocation The Texturelocation for the second resource. Usually this is the location of the Modeltexture.
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, @Nonnull String pModelLocation)
    {
        this(pInternalName, pIconLocation, pModelLocation, 255, 255, 255);
    }

    /**
     * Special Constructor for a resource with color
     *
     * @param pInternalName  The ID used to identify this Resource.
     * @param pIconLocation  The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pModelLocation The Texturelocation for the second resource. Usually this is the location of the Modeltexture.
     * @param pRed           The Red Channel for the color (0-255)
     * @param pGreen         The Green Channel for the color (0-255)
     * @param pBlue          The Blue Channel for the color (0-255)
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, @Nonnull String pModelLocation, int pRed, int pGreen, int pBlue)
    {
        this(pInternalName, pIconLocation, pModelLocation, new MinecraftColor(pRed, pGreen, pBlue));
    }

    /**
     * Special Constructor for a resource with color
     *
     * @param pInternalName  The ID used to identify this Resource.
     * @param pIconLocation  The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pModelLocation The Texturelocation for the second resource. Usually this is the location of the Modeltexture.
     * @param pColor         The MinecraftColor instance used as color.
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, @Nonnull String pModelLocation, @Nonnull MinecraftColor pColor)
    {
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iRescourceLocations.add(pModelLocation);
        iColor = pColor;
    }

    /**
     * Special Constructor for a single texture resource with color
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pRed          The Red Channel for the color (0-255)
     * @param pGreen        The Green Channel for the color (0-255)
     * @param pBlue         The Blue Channel for the color (0-255)
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, int pRed, int pGreen, int pBlue)
    {
        this(pInternalName, pIconLocation, new MinecraftColor(pRed, pGreen, pBlue));
    }

    /**
     * Special Constructor for a single texture resource with color
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pColor        The MinecraftColor instance used as color.
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, MinecraftColor pColor)
    {
        this(pInternalName, pIconLocation, "", pColor);
    }

    /**
     * Special Contstuctor used for resources that have a location inside there file.
     * Usually used for textures in the gui
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon, but in this case it would be the gui parts texture location
     * @param pLeft         The U Index inside the texture file
     * @param pTop          The V Index inside the texture file
     * @param pWidth        The componentWidth of the Resource
     * @param pHeight       The Height of the Resource
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, int pLeft, int pTop, int pWidth, int pHeight)
    {
        this(pInternalName, pIconLocation, Colors.DEFAULT, pLeft, pTop, pWidth, pHeight);
    }

    /**
     * Special Constructor used for resources that have a location inside there file and a color.
     * Usually used for textures in the gui
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon, but in this case it would be the gui parts texture location
     * @param pColor        The MinecraftColor instance used as color.
     * @param pLeft         The U Index inside the texture file
     * @param pTop          The V Index inside the texture file
     * @param pWidth        The componentWidth of the Resource
     * @param pHeight       The Height of the Resource
     */
    public CustomResource(@Nonnull String pInternalName, @Nonnull String pIconLocation, @Nonnull MinecraftColor pColor, int pLeft, int pTop, int pWidth, int pHeight)
    {
        iInternalName = pInternalName;
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iColor = pColor;
        iLeft = pLeft;
        iTop = pTop;
        iWidth = pWidth;
        iHeight = pHeight;
    }

    /**
     * Function to get the ID of the Resource
     *
     * @return The ID of the resources
     */
    @Nonnull
    public String getInternalName()
    {
        return this.iInternalName;
    }

    /**
     * Function to get the first (usually the IIcon) texture location of the Resource
     *
     * @return The Texture address location in String
     */
    @Nonnull
    public String getPrimaryLocation()
    {
        return iRescourceLocations.get(0);
    }

    /**
     * Function to add a IIcon to the resource.
     *
     * @param pIcon The IIcon to register.
     */
    @Nonnull
    public void addIcon(TextureAtlasSprite pIcon)
    {
        iIcon = pIcon;
    }

    /**
     * Function to get the IIcon from the resource.
     *
     * @return The registered IIcon.
     */
    @Nonnull
    public TextureAtlasSprite getIcon()
    {
        return iIcon;
    }

    /**
     * Function to get the secondary Texture location.
     * Usually used in models.
     *
     * @return The secondary address location of the resource in String.
     */
    @Nonnull
    public String getSecondaryLocation()
    {
        return iRescourceLocations.get(1);
    }

    /**
     * Function to get the U Index of the primary resource that this CustomResource represents.
     *
     * @return A 0-Based X-index of the first pixel in the Resource
     */
    public int getU()
    {
        return iLeft;
    }

    /**
     * Function to get the V Index of the primary resource that this CustomResource represents.
     *
     * @return A 0-Based Y-index of the first pixel in the Resource
     */
    public int getV()
    {
        return iTop;
    }

    /**
     * Function to get the componentWidth of the primary resource that this CustomResource represents.
     *
     * @return The componentWidth of the resource in the source.
     */
    public int getWidth()
    {
        return iWidth;
    }

    /**
     * Function to get the componentHeight of the primary resource that this CustomResource represents.
     *
     * @return The componentHeight of the resource in the source.
     */
    public int getHeight()
    {
        return iHeight;
    }

    /**
     * Holds the color used to color grayscaled textures.
     *
     * @return The color for rendering grayscaled textures.
     */
    @Nonnull
    public MinecraftColor getColor()
    {
        return iColor;
    }
}
