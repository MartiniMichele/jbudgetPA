package it.unicam.cs.pa.jbudget.account;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe di supporto usata per la deserializzazione dei conti da file Json
 */
public class AccountSupportClass {
    private String tipo;

    public AccountSupportClass(){}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
