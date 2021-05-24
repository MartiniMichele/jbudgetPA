package it.unicam.cs.pa.jbudget.account;

public class AccountException extends Exception {
    /**
     * Messaggio di errore che si verifica quando si tenta di creare un conto con nome vuoto
     */
    public static final String INVALID_NAME = "Il nome deve essere inserito";

    /**
     * Messaggio di errore che si verifica quando si tento di creare un conto con bilancio negativo
     */
    public static final String INVALID_BALANCE = "Il nome deve essere inserito";

    public AccountException(String message) { super(message); }
}
