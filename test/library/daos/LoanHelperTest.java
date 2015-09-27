package library.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Calendar;
import java.util.Date;

import library.entities.Loan;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Evan Watkins
 */
public class LoanHelperTest {

	private IBook book;
  private IMember borrower;
  private Date borrowDate, dueDate;
  private Loan loan;
  private Calendar cal;

  @Before
  public void setUp() throws Exception {
    book = mock(IBook.class);
    borrower = mock(IMember.class);
 
    cal = Calendar.getInstance();
    borrowDate = new Date();
    cal.setTime(borrowDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    dueDate = cal.getTime();

    loan = new Loan(book, borrower, borrowDate, dueDate);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testParametersNoNull() {
    // asserts
    assertNotNull(book);
    assertNotNull(borrower);
    assertNotNull(borrowDate);
    assertNotNull(dueDate);
  }
  
  @Test
  public void testValueOfParameters() {
    // act
    IBook actualBook = loan.getBook();
    IMember actualBorrower = loan.getBorrower();
    
    // asserts
    assertEquals(book, actualBook);
    assertEquals(borrower, actualBorrower);
  }
}