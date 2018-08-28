package com.ldtteam.smithscore.client.properties;

import com.ldtteam.smithscore.client.model.states.SmithsCoreOBJState;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement property for the old OBJProperty in MC Forge used until the ModelGroup hiding works.
 */
public enum SmithsCoreOBJProperty implements IUnlistedProperty<SmithsCoreOBJState>
{
    INSTANCE;

    @Nonnull
    public String getName()
    {
        return "OBJProperty";
    }

    @Override
    public boolean isValid(@Nonnull SmithsCoreOBJState value)
    {
        return value instanceof SmithsCoreOBJState;
    }

    @Nonnull
    @Override
    public Class<SmithsCoreOBJState> getType()
    {
        return SmithsCoreOBJState.class;
    }

    @Nonnull
    @Override
    public String valueToString(@Nonnull SmithsCoreOBJState value)
    {
        return value.toString();
    }
}
