package com.ldtteam.smithscore.client.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ldtteam.smithscore.client.model.deserializers.definition.MultiComponentModelDefinition;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelDeserializer implements JsonDeserializer<Map<String, ResourceLocation>>
{
    public static final MultiComponentModelDeserializer instance = new MultiComponentModelDeserializer();

    private static final Type mapType = new TypeToken<HashMap<String, ResourceLocation>>()
    {
    }.getType();
    private static final Gson gson    = new GsonBuilder().registerTypeAdapter(mapType, instance).create();

    private MultiComponentModelDeserializer()
    {
    }

    /**
     * Method deserializes the given ModelLocation  into a MultiComponentModel.
     * The returned definition will hold all the SubModels in a Map.
     *
     * @param modelLocation The location to load the Definition From.
     * @return A ModelDefinition for a MultiComponentModel.
     *
     * @throws IOException Thrown when the given ModelLocation points to nothing or not to a ModelFile.
     */
    @Nonnull
    public MultiComponentModelDefinition deserialize(@Nonnull ResourceLocation modelLocation) throws IOException
    {
        return new MultiComponentModelDefinition(gson.fromJson(ModelHelper.getReaderForResource(modelLocation), mapType), ModelHelper.loadTransformFromJson(modelLocation));
    }

    @Nonnull
    @Override
    public Map<String, ResourceLocation> deserialize(@Nonnull JsonElement json, @Nonnull Type typeOfT, @Nonnull JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject textureObject = jsonObject.get("components").getAsJsonObject();

        HashMap<String, ResourceLocation> textureLocations = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : textureObject.entrySet())
        {
            textureLocations.put(entry.getKey(), new ResourceLocation(entry.getValue().getAsString()));
        }

        return textureLocations;
    }
}
