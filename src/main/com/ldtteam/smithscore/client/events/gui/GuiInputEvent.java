package com.ldtteam.smithscore.client.events.gui;

import com.ldtteam.smithscore.common.events.network.StandardNetworkableEvent;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 26.01.2016.
 */
@Cancelable
public class GuiInputEvent extends StandardNetworkableEvent
{
    InputTypes type;

    String componentID;
    String input;

    public GuiInputEvent(@Nonnull InputTypes types, @Nonnull String componentID, @Nonnull String input)
    {
        this.type = types;
        this.componentID = componentID;
        this.input = input;
    }

    public GuiInputEvent()
    {
    }

    @Nonnull
    public InputTypes getTypes()
    {
        return type;
    }

    @Nonnull
    public String getComponentID()
    {
        return componentID;
    }

    @Nonnull
    public String getInput()
    {
        return input;
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored by it
     * in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing events.
     */
    @Override
    public void readFromMessageBuffer(@Nonnull ByteBuf pMessageBuffer)
    {
        type = InputTypes.values()[pMessageBuffer.readInt()];
        componentID = ByteBufUtils.readUTF8String(pMessageBuffer);
        input = ByteBufUtils.readUTF8String(pMessageBuffer);
    }

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    @Override
    public void writeToMessageBuffer(@Nonnull ByteBuf pMessageBuffer)
    {
        pMessageBuffer.writeInt(type.ordinal());
        ByteBufUtils.writeUTF8String(pMessageBuffer, componentID);
        ByteBufUtils.writeUTF8String(pMessageBuffer, input);
    }

    public enum InputTypes
    {
        TABCHANGED,
        BUTTONCLICKED,
        SCROLLED,
        FLUIDPRIORITIZED,
        TEXTCHANGED,
    }
}
