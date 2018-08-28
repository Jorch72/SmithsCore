package com.ldtteam.smithscore.client.model.baked;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.smithscore.client.model.overrides.PreBakedItemOverride;
import com.ldtteam.smithscore.client.model.unbaked.DummyModel;
import com.ldtteam.smithscore.util.CoreReferences;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

/**
 * Author Marc (Created on: 29.05.2016)
 */
public class BakedMultiComponentModel implements IBakedModel
{
    final TextureAtlasSprite                                                   particleTexture;
    final ItemCameraTransforms                                                 cameraTransforms;
    final ItemOverrideList                                                     overrides;
    final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> trsrTransforms;

    public BakedMultiComponentModel(
      @Nonnull TextureAtlasSprite particleTexture,
      @Nonnull ItemCameraTransforms transforms,
      @Nonnull ItemOverrideList overrides,
      @Nonnull ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> trsrTransforms)
    {
        this.particleTexture = particleTexture;
        this.cameraTransforms = transforms;
        this.overrides = overrides;
        this.trsrTransforms = trsrTransforms;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nonnull IBlockState state, @Nonnull EnumFacing side, long rand)
    {
        return DummyModel.BAKED_MODEL.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    @Nonnull
    public TextureAtlasSprite getParticleTexture()
    {
        return particleTexture;
    }

    @Override
    @Nonnull
    public ItemCameraTransforms getItemCameraTransforms()
    {
        return cameraTransforms;
    }

    @Override
    @Nonnull
    public ItemOverrideList getOverrides()
    {
        return overrides;
    }

    @Nonnull
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType cameraTransformType)
    {
        return PerspectiveMapWrapper.handlePerspective(this, trsrTransforms, cameraTransformType);
    }

    public static class BakedComponentModelItemOverride extends PreBakedItemOverride
    {
        final String modelType;

        public BakedComponentModelItemOverride(@Nonnull IBakedModel model, @Nonnull String modelType)
        {
            super(model);
            this.modelType = modelType;
        }

        @Override
        public boolean matchedItemStack(@Nonnull ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
        {
            if (!stack.hasTagCompound())
            {
                stack.setTagCompound(new NBTTagCompound());
            }

            stack.getTagCompound().setString(CoreReferences.NBT.IItemProperties.TARGET, modelType);

            return stack.getItem().hasCustomProperties() && stack.getItem().getPropertyGetter(CoreReferences.IItemProperties.MODELTYPE).apply(stack, world, entity) > 0;
        }
    }
}
