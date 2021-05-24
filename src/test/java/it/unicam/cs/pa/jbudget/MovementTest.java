package it.unicam.cs.pa.jbudget;

import it.unicam.cs.pa.jbudget.account.AccountCredito;
import it.unicam.cs.pa.jbudget.account.AccountException;
import it.unicam.cs.pa.jbudget.account.AccountInterface;
import it.unicam.cs.pa.jbudget.account.TipoConto;
import it.unicam.cs.pa.jbudget.movement.Movement;
import it.unicam.cs.pa.jbudget.movement.MovementException;
import it.unicam.cs.pa.jbudget.movement.TipoMovimento;
import it.unicam.cs.pa.jbudget.tag.Tag;
import it.unicam.cs.pa.jbudget.tag.TagException;
import it.unicam.cs.pa.jbudget.tag.TagInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
    Movement movimento;
    AccountInterface credito;
    List<TagInterface> listaTag = new ArrayList<>();

    @BeforeEach
    void init(){

        try {
            Tag tag1 = new Tag("auto");
            Tag tag2 = new Tag("casa");
            Tag tag3 = new Tag("cibo");
            listaTag.add(tag1);
            listaTag.add(tag2);
            listaTag.add(tag3);
            credito = new AccountCredito("portafoglio", 300, TipoConto.CREDITO);
            movimento = new Movement("descr", 50, TipoMovimento.USCITA, listaTag, "portafoglio");
        } catch (AccountException e) { e.printStackTrace(); }
          catch (MovementException e) { e.printStackTrace(); }
        catch (TagException e) { e.printStackTrace(); }

    }

    @Test
    void addMoney() {
        movimento.addMoney(credito);
        int bilancioAttuale = 350;
        assertEquals(credito.getBilancio(), bilancioAttuale);
    }

    @Test
    void removeMoney() {
        movimento.removeMoney(credito);
        int bilancioAttuale = 250;
        assertEquals(credito.getBilancio(), bilancioAttuale);
    }
}