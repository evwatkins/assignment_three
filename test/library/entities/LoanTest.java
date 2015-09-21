package library.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Evan Watkins
 */
public class LoanTest {
	private IBook book;
	@SuppressWarnings("unused")
  private IBook id;
	private IMember borrower;
	private Date borrowDate, dueDate, currentDate;
	private Loan loan;
	private Calendar date;

	@Before
	public void setUp() throws Exception {
		book = mock(IBook.class);
		borrower = mock(IMember.class);
		id = mock(IBook.class);

		date = Calendar.getInstance();
		borrowDate = new Date();
		date.setTime(borrowDate);

		date.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		dueDate = date.getTime();
		date.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		currentDate = date.getTime();

		loan = new Loan(book, borrower, borrowDate, dueDate);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoanParametersNotNull() {
		// asserts
		assertNotNull(book);
		assertNotNull(borrower);
		assertNotNull(borrowDate);
		assertNotNull(dueDate);
	}

	@Test(expected=AssertionError.class)
	public void testLoanParametersNull() {
		// asserts
		assertNull(book);
		assertNull(borrower);
		assertNull(borrowDate);
		assertNull(dueDate);
	}
	
	@Test
	public void testLoanCurrentStateOfLoan() {
		// act
		ELoanState currentState = loan.getCurrentState();

		// asserts
		assertEquals(ELoanState.PENDING, currentState);
	}

	@Test
	public void testLoanParameterValues() {
		// act
		IBook actualBook = loan.getBook();
		IMember actualBorrower = loan.getBorrower();
		Date actualBorrowDate = borrowDate;
		Date actualDueDate = dueDate;
		
		// asserts
		assertEquals(book, actualBook);
		assertEquals(borrower, actualBorrower);
		assertEquals(borrowDate, actualBorrowDate);
		assertEquals(dueDate, actualDueDate);
	}

	@Test
	public void testCommitWithPositiveID() {
		// act
		ELoanState startState = loan.getCurrentState();
		int id = 5;
		loan.commit(id);
		ELoanState endState = loan.getCurrentState();
		
		// assert
		assertEquals(ELoanState.PENDING, startState);
		int actualId = loan.getID();
		assertEquals(id, actualId);
		assertEquals(ELoanState.CURRENT, endState);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCommitWithNegativeID() {
		// act
		ELoanState startState = loan.getCurrentState();
		int id = -5;
		loan.commit(id);
		ELoanState endState = loan.getCurrentState();
		
		// assert
		assertEquals(ELoanState.PENDING, startState);
		int actualId = loan.getID();
		assertEquals(id, actualId);
		assertEquals(ELoanState.CURRENT, endState);
	}

	@Test
	public void testCompleteState() {
		// act
		loan.commit(5);
		loan.complete();
		
		// assert
		assertEquals(ELoanState.COMPLETE, loan.getCurrentState());
	}

	@Test
	public void testIsOverDue() {
		// act
		loan.commit(5);
		boolean state = loan.isOverDue();
		
		// assert
		assertTrue(state);
	}

	@Test
	public void testCheckOverDue() {
		// act
		loan.commit(5);
		loan.isOverDue();
		
		// assert
		assertEquals(ELoanState.OVERDUE, loan.getCurrentState());
	}

	@Test
	public void testGetBorrower() {
		// act
		IMember actualBorrower = loan.getBorrower();
	
		// assert
		assertEquals(borrower, actualBorrower);
	}

	@Test
	public void testGetBook() {
		// act
		IBook actualBook = loan.getBook();
		
		// assert
		assertEquals(book, actualBook);
	}

	@Test
	public void testGetID() {
		// act
		int actualID = loan.getID();
		
		// assert
		assertEquals(0, actualID);
	}

	@Test
	public void testGetCurrentState() {
		loan.getCurrentState();
		assertEquals(ELoanState.PENDING, loan.getCurrentState());
				
		loan.commit(5);
		loan.getCurrentState();
		assertEquals(ELoanState.CURRENT, loan.getCurrentState());
		
		loan.checkOverDue(currentDate);
		assertEquals(ELoanState.OVERDUE, loan.getCurrentState());
		
		loan.complete();
		assertEquals(ELoanState.COMPLETE, loan.getCurrentState());
	}

	@Test
	public void testToString() {
		// act
		int id = 0;
		String author = "John Doe";
		String title = "Test Book";
		String borrowerFirstName = "Evan";
		String borrowerLastName = "Watkins";
		String bd = DateFormat.getDateInstance().format(borrowDate);
		String dd = DateFormat.getDateInstance().format(dueDate);
		String string = String.format("Loan ID:  %d\nAuthor:   %s\nTitle:    %s\nBorrower: %s %s\nBorrowed: %s\nDue Date: %s", 
				id, author,title,borrowerFirstName, borrowerLastName, bd, dd);
		
		// assert
		assertNotNull(string);
	}
}