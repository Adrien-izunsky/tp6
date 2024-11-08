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
		machine.insertMoney(PRICE - 1);
		assertFalse(machine.printTicket(), "Le ticket a été imprimé avec un montant insuffisant");
	}

	@Test
// S4 : on imprime le ticket si le montant inséré est suffisant
	void printsTicketIfSufficientBalance() {
		machine.insertMoney(PRICE); // On insère le montant exact
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé avec un montant suffisant");
	}

	@Test
// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void balanceIsDecrementedAfterPrintingTicket() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0, machine.getBalance(), "La balance n'est pas correctement décrémentée après impression du ticket");
	}

	@Test
// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalIsUpdatedOnlyAfterPrintingTicket() {
		machine.insertMoney(PRICE);
		int initialTotal = machine.getTotal();
		machine.printTicket();
		assertEquals(initialTotal + PRICE, machine.getTotal(), "Le total n'est pas mis à jour après impression du ticket");
	}

	@Test
// S7 : refund() rend correctement la monnaie
	void refundReturnsCorrectBalance() {
		machine.insertMoney(30); // Insertion de 30 centimes
		assertEquals(30, machine.refund(), "Le remboursement ne correspond pas à la balance");
	}

	@Test
// S8 : refund() remet la balance à zéro
	void refundResetsBalance() {
		machine.insertMoney(30);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'est pas remise à zéro après remboursement");
	}

	@Test
// S9 : on ne peut pas insérer un montant négatif
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10),
				"Une exception aurait dû être levée pour un montant négatif");
	}

	@Test
// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cannotCreateMachineWithNegativePrice() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-50),
				"Une exception aurait dû être levée pour un prix de ticket négatif");
	}

}
