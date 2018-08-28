package com.ldtteam.smithscore.client.model.states;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.smithscore.client.model.data.SmithsCoreOBJGroup;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement property for the old OBJProperty in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJState implements IModelState
{
    @Nonnull
    private Map<String, Boolean> visibilityMap = Maps.newHashMap();
    private IModelState parent;
    private Operation operation = Operation.SET_TRUE;

    public SmithsCoreOBJState(@Nonnull List<String> visibleGroups, boolean visibility)
    {
        this(visibleGroups, visibility, TRSRTransformation.identity());
    }

    public SmithsCoreOBJState(@Nonnull List<String> visibleGroups, boolean visibility, @Nullable IModelState parent)
    {
        this.parent = parent;
        for (String s : visibleGroups)
        {
            this.visibilityMap.put(s, visibility);
        }
    }

    @Nullable
    public IModelState getParent()
    {
        return parent;
    }

    @Nonnull
    public Operation getOperation()
    {
        return operation;
    }

    @Nonnull
    public Map<String, Boolean> getVisibilityMap()
    {
        return this.visibilityMap;
    }

    @Nullable
    public IModelState getParent(@Nullable IModelState parent)
    {
        if (parent == null)
        {
            return null;
        }
        else if (parent instanceof SmithsCoreOBJState)
        {
            return ((SmithsCoreOBJState) parent).parent;
        }
        return parent;
    }

    @Nonnull
    public Optional<TRSRTransformation> apply(@Nonnull Optional<? extends IModelPart> part)
    {
        if (parent != null)
        {
            return parent.apply(part);
        }
        return Optional.empty();
    }

    @Nonnull
    public List<String> getGroupsWithVisibility(boolean visibility)
    {
        List<String> ret = Lists.newArrayList();
        for (Map.Entry<String, Boolean> e : this.visibilityMap.entrySet())
        {
            if (e.getValue() == visibility)
            {
                ret.add(e.getKey());
            }
        }
        return ret;
    }

    @Nonnull
    public List<String> getGroupNamesFromMap()
    {
        return Lists.newArrayList(this.visibilityMap.keySet());
    }

    public void changeGroupVisibilities(@Nullable List<String> names, @Nonnull Operation operation)
    {
        if (names == null || names.isEmpty())
        {
            return;
        }
        this.operation = operation;
        if (names.get(0).equals(SmithsCoreOBJGroup.ALL))
        {
            for (String s : this.visibilityMap.keySet())
            {
                this.visibilityMap.put(s, this.operation.performOperation(this.visibilityMap.get(s)));
            }
        }
        else if (names.get(0).equals(SmithsCoreOBJGroup.ALL_EXCEPT))
        {
            for (String s : this.visibilityMap.keySet())
            {
                if (!names.subList(1, names.size()).contains(s))
                {
                    this.visibilityMap.put(s, this.operation.performOperation(this.visibilityMap.get(s)));
                }
            }
        }
        else
        {
            for (String s : names)
            {
                this.visibilityMap.put(s, this.operation.performOperation(this.visibilityMap.get(s)));
            }
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(visibilityMap, parent, operation);
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        SmithsCoreOBJState other = (SmithsCoreOBJState) obj;
        return Objects.equal(visibilityMap, other.visibilityMap) &&
                 Objects.equal(parent, other.parent) &&
                 operation == other.operation;
    }

    @Nonnull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("OBJState: ");
        builder.append(String.format("%n    parent: %s%n", this.parent.toString()));
        builder.append(String.format("    visibility map: %n"));
        for (Map.Entry<String, Boolean> e : this.visibilityMap.entrySet())
        {
            builder.append(String.format("        name: %s visible: %b%n", e.getKey(), e.getValue()));
        }
        return builder.toString();
    }

    public enum Operation
    {
        SET_TRUE,
        SET_FALSE,
        TOGGLE;

        Operation()
        {
        }

        public boolean performOperation(boolean valueToToggle)
        {
            switch (this)
            {
                default:
                case SET_TRUE:
                    return true;
                case SET_FALSE:
                    return false;
                case TOGGLE:
                    return !valueToToggle;
            }
        }
    }
}
