package com.ldtteam.smithscore.common.structures;

import com.ldtteam.smithscore.SmithsCore;
import com.ldtteam.smithscore.common.events.structure.StructureEvent;
import com.ldtteam.smithscore.common.pathfinding.IPathComponent;
import com.ldtteam.smithscore.common.pathfinding.PathFinder;
import com.ldtteam.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructureController<S extends IStructure, P extends IStructurePart<S>>
{

    default void onPartPlaced(@Nonnull P part)
    {
        S joinedStructure = null;

        for (EnumFacing facing : getPossibleConnectionSides())
        {
            Coordinate3D facingLocation = part.getLocation().moveCoordinate(facing, 1);
            TileEntity tileEntity = part.getEnvironment().getTileEntity(facingLocation.toBlockPos());

            if (!(tileEntity instanceof IStructurePart))
            {
                continue;
            }

            if (!((IStructurePart) tileEntity).getStructure().canPartJoin(part))
            {
                continue;
            }

            if (joinedStructure == null)
            {
                joinedStructure = ((P) tileEntity).getStructure();

                new StructureEvent.Destroyed(part.getStructure(), part.getEnvironment().provider.getDimension()).PostCommon();

                part.setStructure(joinedStructure);
                joinedStructure.registerPart(part);

                new StructureEvent.Updated(joinedStructure, part.getEnvironment().provider.getDimension()).PostCommon();

                continue;
            }

            if (!((IStructurePart) tileEntity).getStructure().getMasterLocation().equals(joinedStructure.getMasterLocation()))
            {
                IStructure oldStructure = ((IStructurePart) tileEntity).getStructure();

                joinedStructure.getController().onStructureMerge(part, oldStructure);
                new StructureEvent.Destroyed(oldStructure, tileEntity.getWorld().provider.getDimension()).PostCommon();
                continue;
            }
        }

        if (joinedStructure == null)
        {
            part.getStructure().registerPart(part);
            part.getStructure().setMasterLocation(part.getLocation());
            new StructureEvent.Create(part.getStructure(), part.getEnvironment().provider.getDimension()).PostCommon();
        }
    }

    @Nonnull
    EnumFacing[] getPossibleConnectionSides();

    default void onStructureMerge(@Nonnull P connectingPart, @Nonnull S otherStructure)
    {
        S structure = connectingPart.getStructure();
        Iterator<Coordinate3D> iterator = otherStructure.getPartLocations().iterator();

        structure.getData().onDataMergeInto(otherStructure.getData());

        while (iterator.hasNext())
        {
            P slavePart = (P) connectingPart.getEnvironment().getTileEntity(iterator.next().toBlockPos());
            iterator.remove();

            otherStructure.removePart(slavePart);
            structure.registerPart(slavePart);

            slavePart.setStructure(structure);
        }

        new StructureEvent.Updated(structure, connectingPart.getEnvironment().provider.getDimension()).PostCommon();
    }

    default void onPartDestroyed(@Nonnull P part)
    {
        S structure = part.getStructure();

        if (structure.getMasterLocation().equals(part.getLocation()))
        {
            if (structure.getPartLocations().size() == 1)
            {
                new StructureEvent.Destroyed(structure, part.getEnvironment().provider.getDimension()).PostCommon();
                return;
            }
            else
            {
                Iterator<Coordinate3D> iterator = structure.getPartLocations().iterator();
                iterator.next();

                Coordinate3D newMasterLocation = iterator.next();
                structure.setMasterLocation(newMasterLocation);

                structure.removePart(part);

                for (Object obj : structure.getPartLocations())
                {
                    Coordinate3D coordinate3D = (Coordinate3D) obj;
                    ((P) part.getEnvironment().getTileEntity(coordinate3D.toBlockPos())).setStructure(structure);
                }

                new StructureEvent.MasterBlockChanged(structure, part.getLocation(), part.getEnvironment().provider.getDimension()).PostCommon();
            }
        }
        else
        {
            structure.removePart(part);
            new StructureEvent.Updated(structure, part.getEnvironment().provider.getDimension()).PostCommon();
        }

        LinkedHashSet<IStructurePart> notConnectedComponents = validateStructureIntegrity(structure, part);
        structure.removePart(part);

        IStructure newStructure = structure;
        while (!notConnectedComponents.isEmpty())
        {
            newStructure = splitStructure(newStructure, notConnectedComponents);
            notConnectedComponents = validateStructureIntegrity(newStructure, part);
        }
    }

    @Nonnull
    static LinkedHashSet<IStructurePart> validateStructureIntegrity(@Nonnull IStructure structure, @Nonnull IStructurePart splitter)
    {
        LinkedHashSet<IStructurePart> notConnectedComponents = new LinkedHashSet<>();

        for (Object obj : structure.getPartLocations())
        {
            IStructurePart part = (IStructurePart) splitter.getEnvironment().getTileEntity(((Coordinate3D) obj).toBlockPos());

            if (!checkIfComponentStillConnected(structure, part, splitter))
            {
                notConnectedComponents.add(part);
            }
        }

        return notConnectedComponents;
    }

    @Nonnull
    static IStructure splitStructure(@Nonnull IStructure oldStructure, @Nonnull LinkedHashSet<IStructurePart> parts)
    {
        //Create the new structures master Entity
        Iterator<IStructurePart> iterator = parts.iterator();
        IStructurePart newMaster = iterator.next();
        iterator.remove();

        IStructure newStructure = StructureRegistry.getInstance().getFactory(oldStructure).generateNewStructure(newMaster);
        oldStructure.removePart(newMaster);
        newStructure.registerPart(newMaster);
        newMaster.setStructure(newStructure);


        //Let all the Slaves join the new handlers
        while (iterator.hasNext())
        {
            IStructurePart slavePart = iterator.next();
            iterator.remove();

            oldStructure.removePart(slavePart);
            newStructure.registerPart(slavePart);

            slavePart.setStructure(newStructure);
        }

        new StructureEvent.Create(newStructure, newMaster.getEnvironment().provider.getDimension()).PostCommon();
        new StructureEvent.Updated(oldStructure, newMaster.getEnvironment().provider.getDimension()).PostCommon();

        return newStructure;
    }

    static boolean checkIfComponentStillConnected(@Nonnull IStructure structure, @Nonnull IStructurePart target, @Nonnull IStructurePart splitter)
    {
        if (SmithsCore.isInDevEnvironment())
        {
            SmithsCore.getLogger().info("Starting connection search between: " + structure.getMasterLocation().toString() + " to " + target.getLocation().toString());
        }

        PathFinder tConnectionChecker = new PathFinder((IPathComponent) target.getEnvironment().getTileEntity(structure.getMasterLocation().toBlockPos()), target, splitter);
        return tConnectionChecker.isConnected();
    }
}
