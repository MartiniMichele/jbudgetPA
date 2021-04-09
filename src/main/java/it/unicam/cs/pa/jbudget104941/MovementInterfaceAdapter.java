package it.unicam.cs.pa.jbudget104941;

import com.google.gson.*;

import java.lang.reflect.Type;


/**
 * Classe usata per deserializzare i movimenti
 * @param <T> Parametro generico rappresentante un movimento
 */
public class MovementInterfaceAdapter<T extends MovementInterface> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, Movement.class);
    }
}
