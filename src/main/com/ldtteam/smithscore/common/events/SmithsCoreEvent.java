/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.smithscore.common.events;

import com.ldtteam.smithscore.SmithsCore;
import net.minecraft.network.INetHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

/**
 * Root class for all smithscore events.
 */
public class SmithsCoreEvent extends Event
{
    /**
     * Convenient function to post this event on the common event bus within smithscore
     */
    public boolean PostCommon()
    {
        return SmithsCore.getRegistry().getCommonBus().post(this);
    }

    /**
     * Convenient function to post this event on the client event bus within smithscore
     */
    public boolean PostClient()
    {
        return SmithsCore.getRegistry().getClientBus().post(this);
    }

    /**
     * Function used to get the sided NetworkingHandler.
     *
     * @param pContext The Context of the Message for which the handlers has to be retrieved.
     * @return The ClientNetHandler on the ClientSide and the ServerNetHandler on the server side.
     */
    @Nonnull
    public INetHandler getSidedPlayerHandlerFromContext(@Nonnull MessageContext pContext)
    {
        if (pContext.side == Side.SERVER)
        {
            return pContext.getServerHandler();
        }

        return pContext.getClientHandler();
    }
}
