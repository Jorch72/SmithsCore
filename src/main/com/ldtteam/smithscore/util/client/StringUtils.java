/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.smithscore.util.client;
/*
/  StringUtils
/  Created by : Orion
/  Created on : 23/01/2015
*/

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import javax.annotation.Nonnull;

public class StringUtils
{

    /**
     * Calculates the minimum width needed to display the elements of the given Array vertically (under each other)
     *
     * @param pStrings     The strings you want to display
     * @param pCurrentFont The fontrenderer used to calculate the width of each string.
     * @return A int telling you the minimal width in pixels needed to render all the strings properly
     */
    public static int GetMininumWidth(@Nonnull String[] pStrings, @Nonnull FontRenderer pCurrentFont)
    {
        int tCurrentMaximum = 0;

        for (int tRule = 0; tRule < pStrings.length; tRule++)
        {
            String tCurrentLine = pStrings[tRule];

            if (pCurrentFont.getStringWidth(tCurrentLine) > tCurrentMaximum)
            {
                tCurrentMaximum = pCurrentFont.getStringWidth(tCurrentLine);
            }
        }

        return tCurrentMaximum;
    }

    /**
     * Splits a given string so that it fits inside the given Width
     *
     * @param pToSplit  String to split.
     * @param pMaxWidth Maxwidth of each part of the string.
     * @return An Array containing the splitted string. Uses the default fontrenderer to calculate.
     * <p>
     * TODO: Update to use custom fontrenderer.
     */
    @Nonnull
    public static String[] SplitString(@Nonnull String pToSplit, int pMaxWidth)
    {
        return (String[]) Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(pToSplit, pMaxWidth).toArray();
    }
}
