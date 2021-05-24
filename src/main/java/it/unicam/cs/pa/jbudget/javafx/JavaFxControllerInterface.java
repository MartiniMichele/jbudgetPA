package it.unicam.cs.pa.jbudget.javafx;

import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di controllare una view.
 */
public interface JavaFxControllerInterface {

    String SAME_ELEMENT = "Oggetto gia' esistente";
    String INVALID_ELEMENT = "Uno dei campi inseriti non e' valido";
    String VOID_ELEMENT = "Uno dei campi e' nullo";

    void launchError(String str);
}
