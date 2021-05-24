package it.unicam.cs.pa.jbudget.account;
import it.unicam.cs.pa.jbudget.movement.MovementInterface;

import java.util.*;


/**
 * Classe astratta utilizzata per raggruppare le funzioni comuni a tutti i conti
 * l'accesso e la modifica delle informazioni associate avviene tramite i rispettivi getter e setter
 * non possono essere creati due conti con lo stesso nome
 * Implementa {@link AccountInterface}
 */
public abstract class Account <T extends MovementInterface> implements AccountInterface {
    protected String nome;
    protected double bilancio;
    protected TipoConto tipo;
    protected List<T> movementlist = new ArrayList<>();
    protected static int count;  //Usata per contare il numero di oggetti creati ed assegnare loro l'id
    protected int id;

    /**
     * Costruttore del conto
     * @param nome Nome del conto
     * @param bilancio Bilancio del conto
     * @param tipo Tipo del conto
     * @throws AccountException Lanciata in caso di parametri non validi
     */
    public Account(String nome, double bilancio, TipoConto tipo) throws AccountException {
        checkNome(nome);
        checkbilancio(bilancio);
        count++;
        this.nome = nome;
        this.bilancio = bilancio;
        this.tipo = tipo;
        this.id = count;
    }

    /**
     * Verifica se il nome del conto è vuoto
     * @param nome Nome inserito
     * @throws AccountException Se esso è vuoto
     */
    private void checkNome(String nome) throws AccountException {
        if (nome.isEmpty()) { throw new AccountException(AccountException.INVALID_NAME); }
    }

    /**
     * Verifica se il bilancio è negativo
     * @param bilancio Bilancio inserito
     * @throws AccountException Se esso è vuoto
     */
    private void checkbilancio(double bilancio) throws AccountException {
        if (bilancio < 0) { throw new AccountException(AccountException.INVALID_BALANCE); }
    }



    public String getNome() {
        return nome;
    }
    public  double  getBilancio() {return bilancio;}
    public int getId() {
        return id;
    }
    public List<T> getMovementlist() {
        return movementlist;
    }
    public TipoConto getTipo() {
        return tipo;
    }
    public List<Integer> getIntegerMovimenti() {
        ArrayList<Integer> nomi = new ArrayList<>();
        this.movementlist.forEach(p -> nomi.add(p.getId()));
        return nomi;
    }


    @Override
    public void setBilancio(double bilancio) {
        this.bilancio = bilancio;
    }
    public void setMovementlist(ArrayList<T> movementlist) {
        this.movementlist = movementlist;
    }
    public void setTipo(TipoConto tipo) {
        this.tipo = tipo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }



    @Override
    public abstract void update(MovementInterface movementInterface);


    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account<?> account = (Account<?>) o;
        return getNome().equals(account.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }
}
