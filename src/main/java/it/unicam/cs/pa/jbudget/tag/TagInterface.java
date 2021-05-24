package it.unicam.cs.pa.jbudget.tag;


/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di rappresentare un {@link Tag}.
 */
public interface TagInterface {

    String getNome();
    int getId();
    void setNome(String nome);
    String toString();
}
