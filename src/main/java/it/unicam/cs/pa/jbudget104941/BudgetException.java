package it.unicam.cs.pa.jbudget104941;

public class BudgetException extends Exception {
    /**
     * Messaggio di errore che si verifica quando si tenta di creare un budget con nome vuoto
     */
    public static final String INVALID_BNAME = "Il nome inserito è vuoto";

    /**
     * Messaggio di errore che si verifica quando si tenta di creare un budget con lista di tag vuota
     */
    public static final String INVALID_BTAG = "Devono essere presenti dei tag";

    /**
     * Messaggio di errore che si verifica quando si tenta di creare un budget con spesa attesa negativa
     */
    public static final String INVALID_EXPECTED ="Valore di expected non valido";

    public BudgetException(String message) { super(message); }


}
