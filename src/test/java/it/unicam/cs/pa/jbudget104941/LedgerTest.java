package it.unicam.cs.pa.jbudget104941;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.accessibility.AccessibleContext;
import javax.annotation.Tainted;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class LedgerTest {
    static Ledger ledger;
    static AccountInterface credito;

    @BeforeAll
    static void initAll(){
        ledger = new Ledger();
        try {
            ledger.addConto("banca", 300, TipoConto.CREDITO);
            credito = new AccountCredito("banca", 100, TipoConto.CREDITO);
        } catch (AccountException e) { e.printStackTrace(); }
    }


    @Test
    void addConto() {
        assertThrows(AccountException.class, () -> ledger.addConto("", 100, TipoConto.CREDITO));
        assertThrows(AccountException.class, () -> ledger.addConto("portafoglio", -10, TipoConto.CREDITO));
        assertThrows(IllegalArgumentException.class, () -> ledger.addConto("banca", 100, TipoConto.CREDITO));
    }

    @Test
    void addMovement() {
        List<TagInterface> listaTag = new ArrayList<>();
        List<TagInterface> listaTagEmpty = new ArrayList<>();
        try {
            Tag tag1 = new Tag("auto");
            Tag tag2 = new Tag("casa");
            Tag tag3 = new Tag("cibo");
            listaTag.add(tag1);
            listaTag.add(tag2);
            listaTag.add(tag3);
        } catch (TagException e) { e.printStackTrace(); }


        assertThrows(MovementException.class, () -> ledger.addMovement("spesa auto", -20, TipoMovimento.ENTRATA, listaTag,
                                                                        credito));

        assertThrows(MovementException.class, () -> ledger.addMovement("spesa idraulico", 20, TipoMovimento.ENTRATA, listaTagEmpty,
                                                                        credito));



    }

    @Test
    void addTag() {
        try {
            ledger.addTag("moto");
        } catch (TagException e) { e.printStackTrace(); }

        assertThrows(TagException.class, () -> ledger.addTag(""));
        assertThrows(IllegalArgumentException.class, () -> ledger.addTag("moto"));
    }

    @Test
    void addBudget() {
        List<TagInterface> listaTag = new ArrayList<>();
        List<TagInterface> listaTag3 = new ArrayList<>();
        try {
            Tag tag1 = new Tag("auto");
            Tag tag2 = new Tag("casa");
            Tag tag3 = new Tag("cibo");
            listaTag.add(tag1);
            listaTag.add(tag2);
            listaTag.add(tag3);
            ledger.addBudget("spesa", listaTag, 120 );
        } catch (TagException e) { e.printStackTrace(); }
        catch (BudgetException b) { b.printStackTrace(); }

        assertThrows(BudgetException.class, () -> ledger.addBudget("", listaTag, 200));
        assertThrows(BudgetException.class, () -> ledger.addBudget("meccanico", listaTag3, 200));
        assertThrows(BudgetException.class, () -> ledger.addBudget("compleanno", listaTag, -200));
        assertThrows(IllegalArgumentException.class, () -> ledger.addBudget("spesa", listaTag, 200));

    }

    @Test
    void movimentiFiltratiPer() {

        List<TagInterface> listaTag = new ArrayList<>();
        try {
            Tag tag1 = new Tag("auto");
            Tag tag2 = new Tag("casa");
            Tag tag3 = new Tag("cibo");
            listaTag.add(tag1);
            listaTag.add(tag2);
            listaTag.add(tag3);
        } catch (TagException e) { e.printStackTrace(); }


        try {
            ledger.addMovement("spesa aereo", 200, TipoMovimento.ENTRATA, listaTag, ledger.getSingleAccount("banca"));
            ledger.addMovement("spesa fanta", 100, TipoMovimento.USCITA, listaTag, ledger.getSingleAccount("banca"));
            ledger.addMovement("spesa telefono", 450, TipoMovimento.USCITA, listaTag, ledger.getSingleAccount("banca"));
        } catch (MovementException e) { e.printStackTrace(); }

        Predicate<MovementInterface> condition = p -> p.getDirezione().equals(TipoMovimento.ENTRATA);
        List<MovementInterface> lista = ledger.movimentiFiltratiPer(condition);

        assertEquals(1, lista.size());
    }

    @Test
    void contiFiltratiPer() {

        try {
             ledger.addConto("cuscino", 300, TipoConto.CREDITO);
             ledger.addConto("portafoglio", 90, TipoConto.CREDITO);
             ledger.addConto("letto", 450, TipoConto.CREDITO);
        } catch (AccountException e) { e.printStackTrace(); }

        Predicate<AccountInterface> condition = p -> p.getBilancio() > 100;
        List<AccountInterface> lista = ledger.contiFiltratiPer(condition);

        assertEquals(2, lista.size());
    }

    @Test
    void tagFiltratiPer() {

        try {
            ledger.addTag("barca");
            ledger.addTag("gelato");
            ledger.addTag("letto");
        } catch (TagException e) { e.printStackTrace(); }

        Predicate<TagInterface> condition = p -> p.getNome().equals("gelato");
        List<TagInterface> lista = ledger.tagFiltratiPer(condition);

        assertEquals(1, lista.size());
    }


    @Test
    void budgetFiltratiPer() {

        List<TagInterface> listaTag = new ArrayList<>();
        try {
            Tag tag1 = new Tag("auto");
            Tag tag2 = new Tag("casa");
            Tag tag3 = new Tag("cibo");
            listaTag.add(tag1);
            listaTag.add(tag2);
            listaTag.add(tag3);
        } catch (TagException e) { e.printStackTrace(); }


        try {
            ledger.addBudget("cuscino", listaTag, 80);
            ledger.addBudget("portafoglio", listaTag, 150);
            ledger.addBudget("letto", listaTag, 170);
        } catch (BudgetException e) { e.printStackTrace(); }

        Predicate<BudgetInterface> condition = p -> p.getExpected() >= 150;
        List<BudgetInterface> lista = ledger.budgetFiltratiPer(condition);

        assertEquals(2, lista.size());
    }
}
