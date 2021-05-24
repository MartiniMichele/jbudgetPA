package it.unicam.cs.pa.jbudget.budget;

import it.unicam.cs.pa.jbudget.tag.TagInterface;

import java.util.List;


/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di rappresentare un {@link Budget}.
 */
public interface BudgetInterface <T extends TagInterface> {
    List<T> getTags();
    double getExpected();
    String getNome();
    void setExpected(double expected);
    void setDisponibile(double disponibile);
  }
