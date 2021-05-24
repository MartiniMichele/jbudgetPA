package it.unicam.cs.pa.jbudget.tag;

import com.google.gson.*;

import java.lang.reflect.Type;


/**
 * Classe usata per deserializzare i tag
 * @param <T> Parametro generico rappresentante un tag
 */
public class TagInterfaceDeserializer<T extends TagInterface> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, Tag.class);
    }
}
