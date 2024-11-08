package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
		// On vérifie que le prix affiché correspond au paramètre passé lors de
		// l'initialisation
		// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
		// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void doesNotPrintTicketIfInsufficientBalance() {
		machine.insertMoney(PRICE - 10);
		assertFalse(machine.printTicket(), "Le ticket ne devrait pas être imprimé si le solde est insuffisant");
	}

	@Test
		// S4 : on imprime le ticket si le montant inséré est suffisant
	void printsTicketIfSufficientBalance() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket devrait être imprimé si le solde est suffisant");
	}

	@Test
		// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void balanceDecreasesByTicketPriceAfterPrint() {
		machine.insertMoney(PRICE + 10);
		machine.printTicket();
		assertEquals(10, machine.getBalance(), "La balance devrait être réduite du prix du ticket après impression");
	}

	@Test
		// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalIncreasesAfterPrintTicket() {
		machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(), "Le total ne doit pas changer avant l'impression du ticket");
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le total devrait être mis à jour après l'impression du ticket");
	}

	@Test
		// S7 : refund() rend correctement la monnaie
	void refundReturnsCorrectBalance() {
		machine.insertMoney(30);
		assertEquals(30, machine.refund(), "Le remboursement devrait correspondre au solde actuel");
	}

	@Test
		// S8 : refund() remet la balance à zéro
	void refundResetsBalanceToZero() {
		machine.insertMoney(30);
		machine.refund();
		assertEquals(0, machine.getBalance(), "Le solde devrait être remis à zéro après remboursement");
	}

	@Test
		// S9 : on ne peut pas insérer un montant négatif
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> {
			machine.insertMoney(-10);
		}, "L'insertion d'un montant négatif devrait lancer une exception");
	}

	@Test
		// S10 : on ne peut pas créer de machine avec un prix de ticket négatif
	void cannotCreateMachineWithNegativeTicketPrice() {
		assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-50);
		}, "La création d'une machine avec un prix de ticket négatif devrait lancer une exception");
	}
}
