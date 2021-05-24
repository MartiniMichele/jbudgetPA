package it.unicam.cs.pa.jbudget.movement;

import it.unicam.cs.pa.jbudget.account.AccountInterface;
import it.unicam.cs.pa.jbudget.tag.TagInterface;

import java.time.LocalDate;
import java.util.*;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di rappresentare un {@link Movement}.
 */
public interface MovementInterface {

    String getDescrizione();
    int getId();
    double getImporto();
    String getAccount();
    LocalDate getDate();
    <T extends TagInterface> List<T> getTagList();
    List<String> getStringTag();
    TipoMovimento getDirezione();
    String toString();

    /**
     * Restituisce il bilancio del conto associato
     * @param account Conto associato
     * @return Il bilancio del conto
     */
    double getBilancioConto(AccountInterface account);

    /**
     * Aggiorna il bilancio del conto associato al movimento in entrata
     * @param account Conto associato
     */
    void addMoney(AccountInterface account);

    /**
     * Aggiorna il bilancio del conto associato al movimento in uscita
     * @param account Conto associato
     */
    void removeMoney(AccountInterface account);


}
