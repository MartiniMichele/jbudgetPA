package it.unicam.cs.pa.jbudget104941;

import java.util.*;
import java.time.*;

/**
 * La classe implementa l'interfaccia {@link MovementInterface} ed ha il compito di gestire un movimento
 * l'accesso e la modifica delle informazioni associate ad esso avviene tramite i rispettivi getter e setter
 * un movimento deve essere associato ad un conto.
 * @param <T> Parametro generico rappresentate un tag
 */
public class Movement <T extends TagInterface> implements MovementInterface {

    private String descrizione;
    private double importo;
    private TipoMovimento direzione;
    private List<T> tagList = new ArrayList<>();
    private String account;
    private LocalDate date;
    private static int count;
    private int id;


    /**
     * Costruttore del movimento.
     * @param descrizione Descrizione del movimento
     * @param importo Importo del movimento
     * @param direzione Tipo di movimento, se entrata o uscita
     * @param categorie Lista dei tag associati al movimento
     * @param account Conto associato al movimento
     * @throws MovementException Lanciata in caso di parametri non validi
     */
    public Movement(String descrizione, double importo, TipoMovimento direzione, List<T> categorie, String account) throws MovementException {
        checkAccount(account);
        checkImporto(importo);
        checkTag(categorie);
        count++;
        this.descrizione = descrizione;
        this.importo = importo;
        this.direzione = direzione;
        this.tagList = categorie;
        this.account = account;
        this.date = LocalDate.now();
        this.id = count;

    }

    /**
     * Controlla se l'importo inserito è valido
     * @param importo Importo del movimento
     * @throws MovementException Se esso non è valido
     */
    private void checkImporto(double importo) throws MovementException {
        if (importo <= 0) { throw new MovementException(MovementException.INVALID_VALUE); }
    }

    /**
     * Controlla se il conto inserito è vuoto
     * @param account Conto del movimento
     * @throws MovementException Se esso non è valido
     */
    private void checkAccount(String account) throws MovementException {
        if (account.isEmpty()) { throw new MovementException(MovementException.INVALID_ACCOUNT); }
    }

    /**
     * Controlla se la lista di tag inserita è valida
     * @param tags Lista di tag
     * @throws MovementException Se essa non è valida
     */
    private void checkTag(List<T> tags) throws MovementException {
        if (tags.isEmpty()) { throw new MovementException(MovementException.INVALID_TAG); }
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public void setDirezione(TipoMovimento direzione) {
        this.direzione = direzione;
    }

    public void setAccountInterface(AccountInterface accountInterface) {
        this.account = account;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public TipoMovimento getDirezione() {
        return direzione;
    }

    public LocalDate getDate(){return date;}

    public int getId() {
        return id;
    }

    @Override
    public double getImporto() {
        return importo;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public double getBilancioConto(AccountInterface account) {
        return account.getBilancio();
    }

    @Override
    public List<T> getTagList() {
        return tagList;
    }


    /**
     * Restituisce i tag come lista di stringhe
     * @return Lista dei nomi dei tag
     * @throws NullPointerException Se lista vuota
     */
    @Override
    public List<String> getStringTag() throws NullPointerException {
        ArrayList<String> nomi = new ArrayList<>();
        this.tagList.forEach(p -> nomi.add(p.getNome()));
        return nomi;
    }

    private String stringDate(LocalDate date){
        return date.toString();
    }



    /**
     * Aggiorna il bilancio del conto associato al movimento in entrata
     * @param account Conto associato
     */
    @Override
    public void addMoney(AccountInterface account) {
        double bilancio = getBilancioConto(account);
        double risultato = bilancio + this.importo;
        account.setBilancio(risultato);
    }



    /**
     * Aggiorna il bilancio del conto associato al movimento in uscita
     * @param account Conto associato
     */
    @Override
    public void removeMoney(AccountInterface account) {
        double bilancio = getBilancioConto(account);
        double risultato = bilancio - this.importo;
        account.setBilancio(risultato);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "descrizione='" + descrizione + '\'' +
                ", importo=" + importo +
                ", direzione=" + direzione +
                ", tag=" + tagList +
                ", conto=" + account +
                ", data=" + date +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return getId() == movement.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

