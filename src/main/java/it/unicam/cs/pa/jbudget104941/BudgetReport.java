package it.unicam.cs.pa.jbudget104941;

import java.util.List;
import java.util.function.Predicate;

/**
 * Classe che ha il compito di creare un report di spesa di un budget
 * l'accesso e la modifica delle informazioni associate avviene tramite i rispettivi getter e setter
 * Implementa {@link BudgetReportInterface}
 */
public class BudgetReport implements BudgetReportInterface {
    BudgetInterface budget;
    double rimanenza;
    double spesaEffettiva;

    /**
     * Costruttore del Budget Report
     * @param budget Budget relativo
     */
    public BudgetReport(BudgetInterface budget){
        this.budget = budget;
        setRimanenza(budget.getExpected());
        setSpesaEffettiva(0);
    }


    public double getSpesaEffettiva() {
        return spesaEffettiva;
    }

    public void setSpesaEffettiva(double spesaEffettiva) {
        this.spesaEffettiva = spesaEffettiva;
    }

    public BudgetInterface getBudget() {
        return budget;
    }

    public void setBudget(BudgetInterface budget) {
        this.budget = budget;
    }

    public double getRimanenza() {
        return rimanenza;
    }

    public void setRimanenza(double variazione) {
        this.rimanenza = variazione;
    }



    /**
     * Metodo che calcola la spesa effettuata per un budget
     * @param importo Importo dei movimenti relativi al budget
     * @return La spesa effettuata
     */
    @Override
    public double report(double importo) {
        setRimanenza(this.rimanenza - importo);
        setSpesaEffettiva(this.spesaEffettiva + importo);
        budget.setDisponibile(this.rimanenza);
        return getSpesaEffettiva();
    }
}
