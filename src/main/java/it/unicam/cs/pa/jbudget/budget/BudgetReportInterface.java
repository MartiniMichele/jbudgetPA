package it.unicam.cs.pa.jbudget.budget;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di rappresentare un {@link BudgetReport}.
 */
public interface BudgetReportInterface {
    /**
     * Metodo che calcola la spesa effettuata per un budget
     * @param importo Importo dei movimenti relativi al budget
     * @return La spesa effettuata
     */
    double report(double importo);
}
