package library.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Evan Watkins
 */
public class LoanTest {
  private IBook book;
  private IMember borrower;
  private Date borrowDate;
  private Date dueDate;
  private Loan loan;

  @Before
  public void setUp() throws Exception {
    book = mock(IBook.class);
    borrower = mock(IMember.class);
    borrowDate = mock(Date.class);
    dueDate = mock(Date.class);
    
    loan = new Loan(book, borrower, borrowDate, dueDate);
  }
  
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testLoan() {
    // arrange
    
    
    // act
    ELoanState result = loan.getCurrentState();
    
    // asserts
    IBook actualBook = loan.getBook();
    IMember actualBorrower = loan.getBorrower();
    assertEquals(ELoanState.PENDING, result);
    assertEquals(book, actualBook);
    assertEquals(borrower, actualBorrower);
  }

  @Test
  public void testCommit() {

  }

  @Test
  public void testComplete() {

  }

  @Test
  public void testIsOverDue() {

  }

  @Test
  public void testCheckOverDue() {

  }

  @Test
  public void testGetBorrower() {

  }

  @Test
  public void testGetBook() {

  }

  @Test
  public void testGetID() {

  }

  @Test
  public void testGetCurrentState() {

  }

  @Test
  public void testToString() {

  }

}
