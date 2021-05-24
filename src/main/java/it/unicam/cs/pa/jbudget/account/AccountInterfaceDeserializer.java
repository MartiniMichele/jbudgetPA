package it.unicam.cs.pa.jbudget.account;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Classe usata per deserializzare i conti dal file Json
 * @param <T> Parametro generico rappresentante un conto
 */
public class AccountInterfaceDeserializer <T extends AccountInterface> implements JsonDeserializer {

    /**
     * Metodo che in base al tipo sceglie come instanziare il conto
     */
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonElement flag = json.getAsJsonObject().get("tipo");
        AccountSupportClass asc = new AccountSupportClass();
        asc.setTipo(gson.fromJson(flag, String.class));
        if (asc.getTipo().equals("CREDITO")) {
            return context.deserialize(json, AccountCredito.class);
        }
        else { return context.deserialize(json, AccountDebito.class); }
    }
   }
