package it.unicam.cs.pa.jbudget104941;

import java.util.List;
import java.util.function.Predicate;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di
 * controllare i dati passati dalla View al Model. Hanno inoltre la responsabilita' di controllare,
 * ripristinare e salvare gli oggetti con le informazioni prese da File.
 */

public interface LedgerInterface {

    List<AccountInterface> getConti();
    List<MovementInterface> getMovements();
    List<TagInterface> getTags();
    List<BudgetInterface> getBudgets();


    /**
     * Crea un movimento e lo aggiunge alla lista dei movimenti creati
     */
    void addMovement(String nome, double importo, TipoMovimento direzione, List<TagInterface> categoria, AccountInterface conto) throws IllegalArgumentException, IndexOutOfBoundsException, MovementException;



    /**
     * Crea un conto e lo aggiunge alla lista dei conti
     */
    void addConto(String nome, double denaro, TipoConto tipo) throws IllegalArgumentException, AccountException;



    /**
     * elimina un conto in base ad un predicato fornito al metodo
     */
    void removeConto(Predicate<AccountInterface> condition);



    /**
     * elimina un movimento in base ad un predicato fornito al metodo
     */
    void removeMovement(Predicate<MovementInterface> condition);



    /**
     * elimina un tag in base ad un predicato fornito al metodo
     */
    void removeTag(Predicate<TagInterface> condition);



    /**
     * Crea un tag e  lo aggiunge alla lista dei tag
     */
    void addTag(String nome) throws IllegalArgumentException, TagException;



    /**
     * crea un budget e lo aggiunge alla lista dei budget
     */
    void addBudget(String nome, List<TagInterface> tags, double expected) throws IllegalArgumentException, BudgetException;



    /**
     * elimina un budget in base al predicato fornito
     */
    void removeBudget(Predicate<BudgetInterface> condition);



    /**
     * Prende in input un predicate e lo usa per filtrare i movimenti
     */
    List<MovementInterface> movimentiFiltratiPer(Predicate<MovementInterface> predicate);



    /**
     * Prende in input un predicate e lo usa per filtrare i conti
     */
    List<AccountInterface> contiFiltratiPer(Predicate<AccountInterface> predicate);



    /**
     * Prende in input un predicate e lo usa per filtrare i tag
     */
    List<TagInterface> tagFiltratiPer(Predicate<TagInterface> predicate);



    /**
     * Prende in input un predicate e lo usa per filtrare i budget
     */
    List<BudgetInterface> budgetFiltratiPer(Predicate<BudgetInterface> predicate);

}
