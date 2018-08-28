/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.smithscore.util.client;
/*
/  TextureAddressHelper
/  Created by : Orion
/  Created on : 27/06/2014
*/

import javax.annotation.Nonnull;

public class TextureAddressHelper
{
    @Nonnull
    public static String getTextureAddress(@Nonnull String pModID, @Nonnull String pAddress)
    {
        return getTextureAddressWithModID(pModID, pAddress);
    }

    @Nonnull
    public static String getTextureAddressWithModID(@Nonnull String pModID, @Nonnull String pAdress)
    {
        return getTextureAddressWithModIDAndExtension(pModID, pAdress, "");
    }

    @Nonnull
    public static String getTextureAddressWithModIDAndExtension(@Nonnull String pModID, @Nonnull String pAdress, @Nonnull String pExtension)
    {
        return pModID.toLowerCase() + ":" + pAdress + pExtension;
    }

    @Nonnull
    public static String getTextureAddressWithExtension(@Nonnull String pModID, @Nonnull String pAdress, @Nonnull String pExtenstion)
    {
        return getTextureAddressWithModIDAndExtension(pModID, pAdress, pExtenstion);
    }
}
