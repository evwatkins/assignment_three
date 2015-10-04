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
  public IBook id;
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
		// setup
		IBook actualBook = loan.getBook();
		IMember actualBorrower = loan.getBorrower();
		
		// asserts
		assertNotNull(actualBook);
		assertNotNull(actualBorrower);
		assertNotNull(borrowDate);
		assertNotNull(dueDate);
	}

	@Test(expected=AssertionError.class)
	public void testLoanParametersAreNull() {
		// asserts
		assertNull(book);
		assertNull(borrower);
		assertNull(borrowDate);
		assertNull(dueDate);
	}
	
	@Test
	public void testLoanCurrentStateOfLoan() {
		// setup
		ELoanState currentState = loan.getCurrentState();

		// asserts
		assertEquals(ELoanState.PENDING, currentState);
	}

	@Test
	public void testLoanParameterValues() {
		// setup
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
		// setup
		ELoanState startState = loan.getCurrentState();
		int id = 5;
		loan.commit(id);
		int actualId = loan.getID();
		ELoanState endState = loan.getCurrentState();
		
		// assert
		assertEquals(ELoanState.PENDING, startState);
		assertEquals(ELoanState.CURRENT, endState);
		assertEquals(id, actualId);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCommitWithNegativeID() {
		// setup
		int id = -5;
		loan.commit(id);
		int actualId = loan.getID();
		
		// assert
		assertEquals(id, actualId);
	}

	@Test
	public void testCompleteState() {
		// setup
		loan.commit(5);
		loan.complete();
		
		// assert
		assertEquals(ELoanState.COMPLETE, loan.getCurrentState());
	}

	@Test
	public void testIsOverDue() {
		// setup
		boolean state = loan.isOverDue();
		
		// assert
		assertTrue(state);
	}

	@Test(expected=RuntimeException.class)
	public void testCheckOverDueError() {
		// setup
		loan.complete();
		loan.checkOverDue(currentDate);
		
		// assert
		assertEquals(ELoanState.COMPLETE, loan.getCurrentState());
	}
	
	@Test
	public void testCheckOverDue() {
		// setup
		ELoanState state = loan.getCurrentState();
		int id = 5;
		loan.commit(id);
		loan.checkOverDue(currentDate);
		
		// assert
		assertNotEquals(dueDate, currentDate);
		assertNotEquals(ELoanState.OVERDUE, state);
	}

	@Test
	public void testGetBorrower() {
		// setup
		IMember actualBorrower = loan.getBorrower();
	
		// assert
		assertEquals(borrower, actualBorrower);
	}

	@Test
	public void testGetBook() {
		// setup
		IBook actualBook = loan.getBook();
		
		// assert
		assertEquals(book, actualBook);
	}

	@Test
	public void testGetID() {
		// setup
		int actualID = loan.getID();
		
		// assert
		assertEquals(0, actualID);
	}

	@Test
	public void testGetCurrentState() {
		// setup and assert
		assertEquals(ELoanState.PENDING, loan.getCurrentState());
				
		loan.commit(5);
		assertEquals(ELoanState.CURRENT, loan.getCurrentState());
		
		loan.checkOverDue(currentDate);
		assertEquals(ELoanState.OVERDUE, loan.getCurrentState());
		
		loan.complete();
		assertEquals(ELoanState.COMPLETE, loan.getCurrentState());
	}

	@Test
	public void testToStringNotNull() {
		// setup
		int id = 0;
		String author = "John Doe";
		String title = "Test Book";
		String borrowerFirstName = "Evan";
		String borrowerLastName = "Watkins";
		String bDate = DateFormat.getDateInstance().format(borrowDate);
		String dDate = DateFormat.getDateInstance().format(dueDate);
		String string = String.format("Loan ID:  %d\n"
				+ "Author:   %s\n"
				+ "Title of Book:    %s\n"
				+ "Borrower's Name: %s %s\n"
				+ "Date Borrowed: %s\n"
				+ "Due Date: %s", 
				id, author,title,borrowerFirstName, borrowerLastName, bDate, dDate);
		
		// assert
		assertNotNull(string);
	}
}