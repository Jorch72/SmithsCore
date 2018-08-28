package com.ldtteam.smithscore.client.handlers.gui;

import com.ldtteam.smithscore.client.block.ICustomDebugInformationBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 03.01.2016.
 */
public class RenderGameOverlayEventHandler
{

    @SubscribeEvent
    public void handleEvent(@Nonnull RenderGameOverlayEvent.Text event)
    {
        if (Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK
              && Minecraft.getMinecraft().objectMouseOver.getBlockPos() != null)
        {
            BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            IBlockState iblockstate = Minecraft.getMinecraft().world.getBlockState(blockpos);

            if (iblockstate.getBlock() instanceof ICustomDebugInformationBlock)
            {
                ((ICustomDebugInformationBlock) iblockstate.getBlock()).handleDebugInformation(event, Minecraft.getMinecraft().world, blockpos);
            }
        }
    }
}
