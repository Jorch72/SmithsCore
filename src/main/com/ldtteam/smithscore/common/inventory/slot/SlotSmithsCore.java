package com.ldtteam.smithscore.common.inventory.slot;

import com.ldtteam.smithscore.common.inventory.IItemStorage;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 20.06.2016)
 */
public class SlotSmithsCore extends Slot
{
    public SlotSmithsCore(@Nonnull IItemStorage inventoryIn, int index, int xPosition, int yPosition)
    {
        super(new IItemStorage.IInventoryWrapper(inventoryIn), index, xPosition, yPosition);
    }
}
