package com.ldtteam.smithscore.util.common.helper;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3f;

/**
 * Author Marc (Created on: 11.06.2016)
 */
public class MathHelper
{

    @Nonnull
    public static Vector3f fromDegreeToRadian(@Nonnull Vector3f degree)
    {
        return new Vector3f(fromDegreeToRadian(degree.getX()), fromDegreeToRadian(degree.getY()), fromDegreeToRadian(degree.getZ()));
    }

    public static float fromDegreeToRadian(float degree)
    {
        return (float) (degree / 180 * Math.PI);
    }
}
