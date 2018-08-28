package com.ldtteam.smithscore.util.common.helper;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ldtteam.smithscore.SmithsCore;
import com.ldtteam.smithscore.util.CoreReferences;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

/**
 * Created by marcf on 8/3/2016.
 */
public class SerializationHelper
{

    private static final Type ITEMSTACKTYPE      = new TypeToken<ItemStack>() {}.getType();
    private static final Type ITEMSTACKARRAYTYPE = new TypeToken<ItemStack[]>() {}.getType();

    private static final Gson GSON = new GsonBuilder()
                                       .registerTypeAdapter(ITEMSTACKTYPE, new ItemStackDeserializer())
                                       .registerTypeAdapter(ITEMSTACKARRAYTYPE, new ItemStackArrayDeserializer())
                                       .create();

    public static ItemStack deserializeItemStack(JsonElement element)
    {
        return GSON.fromJson(element, ITEMSTACKTYPE);
    }

    public static ItemStack[] deserializeItemStackArray(JsonElement element)
    {
        return GSON.fromJson(element, ITEMSTACKARRAYTYPE);
    }

    private static class ItemStackDeserializer implements JsonDeserializer<ItemStack>
    {

        @Nonnull
        @Override
        public ItemStack deserialize(@Nonnull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject stackData = json.getAsJsonObject();

            Item item = Item.REGISTRY.getObject(new ResourceLocation(stackData.get("item").getAsString()));
            int meta = stackData.get("meta").getAsInt();
            int count = stackData.get("count").getAsInt();

            NBTTagCompound compound;
            try
            {
                compound = JsonToNBT.getTagFromJson(stackData.get("data").getAsString());
            }
            catch (NBTException e)
            {
                SmithsCore.getLogger().error(CoreReferences.LogMarkers.SERIALIZATION, (Object) new Exception("Failed to deserialize the NBTTag from JSON", e));
                compound = new NBTTagCompound();
            }

            return new ItemStack(item, count, meta, compound);
        }
    }

    private static class ItemStackArrayDeserializer implements JsonDeserializer<ItemStack[]>
    {

        @Nonnull
        @Override
        public ItemStack[] deserialize(@Nonnull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonArray stackArray = json.getAsJsonArray();
            ItemStack[] deserializedArray = new ItemStack[stackArray.size()];

            for (int i = 0; i < stackArray.size(); i++)
            {
                JsonElement stackElement = stackArray.get(i);

                deserializedArray[i] = GSON.fromJson(stackElement, ITEMSTACKTYPE);
            }

            return deserializedArray;
        }
    }
}

