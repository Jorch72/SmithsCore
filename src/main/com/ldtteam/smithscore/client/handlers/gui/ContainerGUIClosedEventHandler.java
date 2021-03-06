/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.smithscore.client.handlers.gui;

import com.ldtteam.smithscore.client.events.gui.ContainerGuiClosedEvent;
import com.ldtteam.smithscore.common.inventory.ContainerSmithsCore;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerGUIClosedEventHandler
{

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerOpenenedContainerGUIClientSide(@Nonnull ContainerGuiClosedEvent event)
    {
        if (!(FMLClientHandler.instance().getClientPlayerEntity().openContainer instanceof ContainerSmithsCore))
        {
            return;
        }

        if (!event.getContainerID().equals(((ContainerSmithsCore) FMLClientHandler.instance().getClientPlayerEntity().openContainer).getContainerID()))
        {
            return;
        }

        ContainerSmithsCore container = (ContainerSmithsCore) event.getClosingPlayer().openContainer;
        container.getManager().onGuiOpened(event.getPlayerID());
    }
}
