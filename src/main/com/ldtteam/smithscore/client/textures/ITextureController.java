package com.ldtteam.smithscore.client.textures;

import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Author Marc (Created on: 09.06.2016)
 */
public interface ITextureController
{

    /**
     * Function to get teh Texture based of the baseTexture and the location.
     * Loads the location and generates a Sprite.
     *
     * @param baseTexture The base texture.
     * @param location    The location of the new texture.
     * @return A modified verion of the base texture.
     */
    @Nonnull
    TextureAtlasSprite getTexture(@Nonnull TextureAtlasSprite baseTexture, @Nonnull String location);

    /**
     * Indicates if the texture has been stitched or not. The Texture creator will stitch it if false is returned.
     *
     * @return True when the texture is already stitched.
     */
    boolean isStitched();

    /**
     * Indicates if vertex coloring is used during the creation of the modified texture´.
     *
     * @return True when VertexColoring is used, false when not.
     */
    boolean useVertexColoring();

    /**
     * The color in which the material should be rendered.
     *
     * @return A MinecraftColor instance that shows which color the material has.
     */
    @Nonnull
    MinecraftColor getVertexColor();

    /**
     * Method used by the rendering system to get the Vertex color for liquids.
     *
     * @return The color for the molten metal if armories default system should be used.
     */
    @Nonnull
    MinecraftColor getLiquidColor();

    /**
     * A special suffix for the texture.
     *
     * @return "" When no suffix exists or a suffix.
     */
    @Nullable
    String getTextureSuffix();

    /**
     * Function used to set the suffix. Returns the instance the method was called on.
     *
     * @param suffix The new Suffix.
     * @return The instance this method was called on, used for method chaining.
     */
    @Nonnull
    ITextureController setTextureSuffix(@Nullable final String suffix);

    /**
     * Gets the identifier for the Texturevariations that this Controller produces.
     *
     * @return A string to identify the textures created by this Controller.
     */
    @Nonnull
    String getCreationIdentifier();

    /**
     * Method to set the Identifier for textures created by this Controller.
     *
     * @param identifier The new identifier for Textures created by this Controller.
     * @return A Controller with the Identifier set. Allows for chain setting for variables.
     */
    @Nonnull
    ITextureController setCreationIdentifier(String identifier);
}
