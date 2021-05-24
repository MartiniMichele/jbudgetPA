package it.unicam.cs.pa.jbudget.account;

import it.unicam.cs.pa.jbudget.movement.MovementInterface;

/**
 * Classe rappresentante un conto di tipo debito. Estende {@link Account}
 * @param <T> Parametro generico rappresentante un movimento
 */
public class AccountDebito<T extends MovementInterface> extends Account implements AccountInterface {
    private double restituito;      //Indica il denaro restituito
    private boolean estinto;       //Indica se il debito è estinto


    /**
     * Costruttore per un account di tipo debito
     */
    public AccountDebito(String nome, double bilancio, TipoConto tipo) throws AccountException {
        super(nome, bilancio, tipo);
        setEstinto(false);
    }


    public double getRestituito() {
        return restituito;
    }

    public boolean isEstinto() {
        return estinto;
    }

    public void setRestituito(double restituito) {
        this.restituito = restituito;
    }

    public void setEstinto(boolean estinto) {
        this.estinto = estinto;
    }


    /**
     * Aggiorna la lista dei movimenti con il movimento attuale preso in input
     * e in caso di movimento in uscita(si sta saldando il debito) aggiorna la variabile restituito
     * @param movimento Movimento che opera sul conto
     */
    public void update(MovementInterface movimento){

        switch (movimento.getDirezione()){
            case ENTRATA:
                movementlist.add(movimento);
                break;
            case USCITA:
                setRestituito(this.restituito + movimento.getImporto());
                if (this.bilancio == 0) setEstinto(true);
                movementlist.add(movimento);
                break;

        }
    }

    @Override
    public String toString() {
        return "ContoDebito{" +
                "nome='" + nome + '\'' +
                ", restituito=" + restituito +
                ", estinto=" + estinto +
                ", bilancio=" + bilancio +
                ", tipo=" + tipo +
                ", movementlist=" + movementlist +
                '}';
    }
}
