package it.unicam.cs.pa.jbudget.javafx;


import it.unicam.cs.pa.jbudget.controller.Ledger;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di interagire con l'utente.
 */
public interface JavaFxInputControllerInterface extends JavaFxControllerInterface {

    void initElements(Ledger ledger);
}
