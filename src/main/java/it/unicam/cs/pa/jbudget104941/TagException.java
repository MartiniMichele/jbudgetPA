package it.unicam.cs.pa.jbudget104941;

public class TagException extends Exception {
    /**
     * Messaggio di errore che si verifica quando si tenta di creare un tag con nome vuoto
     */
    public static final String INVALID_TNAME = "Il nome inserito è vuoto";

    public TagException(String message) { super (message); }
}
