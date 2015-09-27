package library.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import library.entities.Loan;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoanMapDAOTest {
	 
	private LoanMapDAO loanMapDAO;
	private ILoan loan;
	private Map loanMap;
	private Date borrowDate, dueDate, currentDate;
	
	private IBook book;
  public IBook id;
	private IMember borrower;
	private Calendar date;
  
  private ILoanHelper helper;

  @Before
  public void setUp() throws Exception {
		book = mock(IBook.class);
		borrower = mock(IMember.class);
		id = mock(IBook.class);
		loanMap = mock(Map.class);
		helper = mock(ILoanHelper.class);
		
		date = Calendar.getInstance();
		borrowDate = new Date();
		date.setTime(borrowDate);

		date.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		dueDate = date.getTime();
		date.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		currentDate = date.getTime();
		
		loanMapDAO = new LoanMapDAO(helper, loanMap);
    loan = new Loan(book, borrower, borrowDate, dueDate);
  }

  @After
  public void tearDown() throws Exception {
  }
  
  @Test(expected=RuntimeException.class)
  public void testHelperIsNull() {
  	// act
  	helper = null;
  	new LoanMapDAO(helper);
  	
  	// asserts
  	assertNull(helper);
  }

  @Test
  public void testLoanMapDAOValues() {
  	// act
  	ILoanHelper actualHelper = helper;
		
		// asserts
		assertEquals(helper, actualHelper);
  }

  @Test
  public void testGetLoanByID() {
  	// act
  	int id = 10;
  	loanMapDAO.getLoanByID(id);
  	
  	// asserts
		assertNotNull(loanMap.containsKey(Integer.valueOf(id)));
  }
  
  @Test(expected=RuntimeException.class)
  public void testGetLoanByBookIfBookIsNull() {
  	// act
  	book = null;
  	loanMapDAO.getLoanByBook(book);
  	
  	// assert
  	assertNull(book);
  }
  
  @Test
  public void testGetLoanByBook() {
  	// act
  	loanMapDAO.getLoanByBook(book);
  	IBook tempBook = loan.getBook();
  	
  	// assert
  	assertEquals(book, tempBook);
  }
  
  @Test(expected=RuntimeException.class)
  public void testFindLoansByBorrowerIfBorrowerIsNull() {
  	// act
  	borrower = null;
  	loanMapDAO.findLoansByBorrower(borrower);
  	
  	// assert
  	assertNull(borrower);
  }
  
  @Test
  public void testFindLoansByBorrower() {
  	// act
  	loanMapDAO.findLoansByBorrower(borrower);
  	
  	// assert
  	assertEquals(loan.getBorrower(), borrower);
  }

  @Test(expected=RuntimeException.class)
  public void testFindLoansByBookTitleIfTitleEmpty() {
  	// act
  	String title = null;
  	loanMapDAO.findLoansByBookTitle(title);
  	
  	// assert
  	assertNull(title);
  }
  
  @Test
  public void testFindLoansByBookTitle() {
  	// act
  	String title = book.getTitle();
  	String tempTitle = loan.getBook().getTitle();
  	
  	// assert
  	assertEquals(title, tempTitle);
  }
  
  @Test
  public void testUpdateOverDueStatus() {
  	// act
  	loan.commit(1);
		loan.isOverDue();
		boolean overDue = loan.checkOverDue(currentDate);
    
  	// assert
  	assertTrue(overDue);
  }
  
  @Test
  public void testGetNextID() {
		// act
		int actualID = loan.getID();
		
		// assert
		assertEquals(0, actualID);
  }

  @Test(expected=RuntimeException.class)
  public void testCreateLoanNullBorrower() {
  	// act
  	borrower = null;
  	IBook book1 = book;
  	loanMapDAO.createLoan(borrower, book);
  	
  	// assert
  	assertNull(borrower);
  	assertNotNull(book1);
  }

  @Test(expected=RuntimeException.class)
  public void testCreateLoanNullBook() {
  	// act
  	IMember borrower1 = borrower;
  	book = null;
  	loanMapDAO.createLoan(borrower, book);
  	
  	// assert
  	assertNotNull(borrower1);
  	assertNull(book);
  }
  
  @Test
  public void testCommitLoan() {
  	// act
  	loanMapDAO.commitLoan(loan);
    int id = 1;
  	
  	// assert
		assertEquals(loan.getID(), id);
  }
}