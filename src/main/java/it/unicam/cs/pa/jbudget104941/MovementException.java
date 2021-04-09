package it.unicam.cs.pa.jbudget104941;

public class MovementException extends Exception {

    /**
     * Messaggio di errore che si verifica quando si tenta di creare un movimento
     * con importo negativo o pari a zero
     */
    public static final String INVALID_VALUE = "Il movimento non può avere importo nullo o negativo";

    /**
     * Messaggio di errore che si verifica quando si tenta di creare un movimento
     * senza inserire il conto
     */
    public static final String INVALID_ACCOUNT = "Il conto deve essere inserito";

    /**
     * Messaggio di errore che si verifica quando si tenta di creare un movimento
     * con la lista di tag vuota
     */
    public static final String INVALID_TAG = "Devono essere presenti dei tag";

    public MovementException(String message) {super(message);}

}
