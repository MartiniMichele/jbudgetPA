package it.unicam.cs.pa.jbudget.budget;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Classe usata per deserializzare i budget
 * @param <T> Parametro generico rappresentante un Budget
 */
public class BudgetInterfaceDeserializer <T extends BudgetInterface> implements JsonDeserializer<T> {


    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, Budget.class);
    }


}
