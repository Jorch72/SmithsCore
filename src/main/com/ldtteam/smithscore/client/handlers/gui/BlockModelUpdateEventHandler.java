package com.ldtteam.smithscore.client.handlers.gui;

import com.ldtteam.smithscore.client.events.models.block.BlockModelUpdateEvent;
import com.ldtteam.smithscore.common.tileentity.IBlockModelUpdatingTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 30.12.2015.
 */
public class BlockModelUpdateEventHandler
{

    @SubscribeEvent
    public void handleUpdataEvent(@Nonnull BlockModelUpdateEvent event)
    {
        TileEntity entity = FMLClientHandler.instance().getClientPlayerEntity().world.getTileEntity(event.getBlockPosition().toBlockPos());

        if (!(entity instanceof IBlockModelUpdatingTileEntity))
        {
            return;
        }

        IBlockState state = entity.getWorld().getBlockState(event.getBlockPosition().toBlockPos());

        entity.getWorld().notifyBlockUpdate(event.getBlockPosition().toBlockPos(), state, state, 3);
    }
}
