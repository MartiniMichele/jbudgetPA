package it.unicam.cs.pa.jbudget.tag;

import java.util.Objects;

/**
 * Classe che ha il compito di rappresentare un tag. implementa {@link TagInterface}
 * l'accesso e la modifica delle informazioni associate avviene tramite i rispettivi getter e setter
 * non possono essere creati due tag con lo stesso nome
 */
public class Tag implements TagInterface {
    private String nome;
    private static int count;
    private int id;

    /**
     * Costruttore del tag
     * @param nome Nome del tag
     * @throws TagException Lanciata se il nome inserito non è valido
     */
    public Tag(String nome) throws TagException {
        checkNome(nome);
        count++;
        this.nome = nome;
        this.id = count;
    }

    /**
     * Verifica se il nome del tag è vuoto
     * @param nome Nome del tag
     * @throws TagException Se esso è vuoto
     */
    private void checkNome(String nome) throws TagException {
        if (nome.isEmpty()) { throw new TagException(TagException.INVALID_TNAME); }
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getNome().equals(tag.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }

    @Override
    public String toString() {
        return nome;
    }
}
