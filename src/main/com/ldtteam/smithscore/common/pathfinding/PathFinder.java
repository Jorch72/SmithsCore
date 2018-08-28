package com.ldtteam.smithscore.common.pathfinding;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Orion Created on 03.07.2015 14:42
 * <p>
 * Copyrighted according to Project specific license
 */
public class PathFinder
{
    @Nullable
    Path iResultedPath = null;

    //Pathfinds using A* Algorithm
    public PathFinder(@Nonnull IPathComponent pStartNode, @Nonnull IPathComponent pEndNode, @Nonnull IPathComponent pSkippableComponents)
    {
        HashMap<IPathComponent, Float> tClosedNodes = new HashMap<IPathComponent, Float>();
        ArrayList<IPathComponent> tOpenNodes = new ArrayList<IPathComponent>();
        HashMap<IPathComponent, IPathComponent> tPreviousNodes = new HashMap<IPathComponent, IPathComponent>();

        HashMap<IPathComponent, Float> GScore = new HashMap<IPathComponent, Float>();
        HashMap<IPathComponent, Float> FScore = new HashMap<IPathComponent, Float>();
        TreeMap<IPathComponent, Float> SortedFScore = new TreeMap<IPathComponent, Float>(new ValueComparator(FScore));

        tOpenNodes.add(pStartNode);
        GScore.put(pStartNode, 0F);
        FScore.put(pStartNode, pStartNode.getLocation().getDistanceTo(pEndNode.getLocation()));

        while (!tOpenNodes.isEmpty())
        {
            SortedFScore.clear();
            SortedFScore.putAll(FScore);

            IPathComponent tCurrentComponent = SortedFScore.firstKey();
            if (tCurrentComponent.getLocation().equals(pEndNode.getLocation()))
            {
                reconstructPath(tPreviousNodes, pStartNode, pEndNode);
                break;
            }

            tOpenNodes.remove(tCurrentComponent);
            tClosedNodes.put(tCurrentComponent, FScore.get(tCurrentComponent));

            FScore.remove(tCurrentComponent);

            for (IPathComponent tNeighborComponent : tCurrentComponent.getValidPathableNeighborComponents())
            {
                if (tClosedNodes.containsKey(tNeighborComponent))
                {
                    continue;
                }

                if (pSkippableComponents.getLocation().equals(tNeighborComponent.getLocation()))
                {
                    continue;
                }

                float tTentative_GScore = GScore.get(tCurrentComponent) + 1;

                if (!tOpenNodes.contains(tNeighborComponent) || (tTentative_GScore < GScore.get(tNeighborComponent)))
                {
                    tPreviousNodes.put(tNeighborComponent, tCurrentComponent);
                    GScore.put(tNeighborComponent, tTentative_GScore);
                    FScore.put(tNeighborComponent, tTentative_GScore + tNeighborComponent.getLocation().getDistanceTo(pEndNode.getLocation()));

                    if (!tOpenNodes.contains(tNeighborComponent))
                    {
                        tOpenNodes.add(tNeighborComponent);
                    }
                }
            }
        }
    }

    private void reconstructPath(@Nonnull HashMap<IPathComponent, IPathComponent> pPreviousNodes, @Nonnull IPathComponent pStartNode, @Nonnull IPathComponent pEndNode)
    {
        iResultedPath = new Path(pStartNode, pEndNode);
        IPathComponent tCurrent = pEndNode;

        iResultedPath.startConstructingReversePath();

        while (pPreviousNodes.containsKey(tCurrent))
        {
            tCurrent = pPreviousNodes.remove(tCurrent);
            iResultedPath.getComponents().add(tCurrent);
        }

        iResultedPath.endConstructingReversePath();
    }

    public boolean isConnected()
    {
        return iResultedPath != null;
    }

    @Nonnull
    public Path getPath()
    {
        return iResultedPath;
    }

    private class ValueComparator implements Comparator<IPathComponent>
    {

        Map<IPathComponent, Float> base;

        public ValueComparator(@Nonnull Map<IPathComponent, Float> base)
        {
            this.base = base;
        }

        public int compare(@Nonnull IPathComponent a, @Nonnull IPathComponent b)
        {
            if (base.get(a) >= base.get(b))
            {
                return -1;
            }
            else
            {
                return 1;
            } // returning 0 would merge keys
        }
    }
}

