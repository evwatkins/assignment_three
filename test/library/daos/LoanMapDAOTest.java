package library.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import library.entities.Loan;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.IMember;
import library.interfaces.entities.ELoanState;
import library.interfaces.entities.ILoan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoanMapDAOTest {
	 
	private LoanMapDAO loanMapDAO;
	private LoanMapDAO nextID;
  public IBook id;
	private IBook book;
  
	private ILoanHelper helper = mock(ILoanHelper.class);
  private Map loanMap = mock(Map.class);

  @Before
  public void setUp() throws Exception {
    loanMapDAO = new LoanMapDAO(helper, loanMap);
  }

  @After
  public void tearDown() throws Exception {
  }
  
  @Test(expected=AssertionError.class)
  public void testHelperIsNotNull() {
  	// asserts
  	assertNotNull(helper = null);
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
  
  @Test
  public void testGetLoanByBook() {

  }

  @Test
  public void testListLoans() {

  }

  @Test
  public void testFindLoansByBorrower() {

  }

  @Test
  public void testFindLoansByBookTitle() {

  }
  
  @Test
  public void testUpdateOverDueStatus() {

  }
  
  @Test
  public void testGetNextID() {

  }

  @Test
  public void testFindOverDueLoans() {

  }

  @Test
  public void testCreateLoan() {

  }

  @Test
  public void testCommitLoan() {

  }
}