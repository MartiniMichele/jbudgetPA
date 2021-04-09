package it.unicam.cs.pa.jbudget104941;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe rappresentante un budget di spesa prevista per determinati tag Implementa {@link BudgetInterface}
 * l'accesso e la modifica delle informazioni associate avviene tramite i rispettivi getter e setter
 * non possono essere creati due budget con lo stesso nome
 * @param <T> Parametro generico rappresentante un tag
 */
public class Budget <T extends TagInterface> implements BudgetInterface {
    protected String nome;
    protected List<T> tags;
    protected double expected;
    protected double disponibile;

    /**
     * Costruttore Budget
     * @param nome Nome del budget
     * @param tag Lista di tag del budget
     * @param expected Valore di spesa prevista per il budget
     * @throws BudgetException Lanciata se alcuni parametri non sono validi
     */
    public Budget(String nome, List<T> tag, double expected) throws BudgetException {
        checkExpected(expected);
        checkNome(nome);
        checktag(tag);
        this.nome = nome;
        this.expected = expected;
        this.tags = tag;
        setDisponibile(expected);
    }

    /**
     * Controlla se il nome è nullo
     * @param nome Nome del budget
     * @throws BudgetException se esso è nullo
     */
    private void checkNome(String nome) throws BudgetException {
        if (nome.isEmpty()) { throw new BudgetException(BudgetException.INVALID_BNAME); }
    }

    /**
     * Controlla se la lista dei tag è vuota
     * @param tags Lista dei tag del budget
     * @throws BudgetException Se essa è nulla
     */
    private void checktag(List<T> tags) throws BudgetException {
        if (tags.isEmpty()) { throw new BudgetException(BudgetException.INVALID_BTAG); }
    }

    /**
     * Controlla se il valore di spesa prevista è negativa
     * @param expected Spesa prevista
     * @throws BudgetException Se essa è nulla
     */
    private void checkExpected(double expected) throws BudgetException {
        if (expected < 0) { throw new BudgetException(BudgetException.INVALID_EXPECTED); }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String getNome() {
        return nome;
    }

    public void setDescrizione(String descrizione) {
        this.nome = descrizione;
    }

    public void setTags(List<T> tags) {
        this.tags = tags;
    }

    @Override
    public double getExpected() {
        return expected;
    }

    public void setExpected(double expected) {
        this.expected = expected;
    }

    public double getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(double disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public List<T> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Double.compare(budget.getExpected(), getExpected()) == 0 &&
                getNome().equals(budget.getNome()) &&
                getTags().equals(budget.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getTags(), getExpected());
    }

    @Override
    public String toString() {
        return "Budget{" +
                "nome='" + nome + '\'' +
                ", tags=" + tags +
                ", expected=" + expected +
                ", disponibile=" + disponibile +
                '}';
    }
}
