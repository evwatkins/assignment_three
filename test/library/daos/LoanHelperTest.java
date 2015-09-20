package library.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Date;

import library.interfaces.entities.IBook;
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
  private Date borrowDate;
  private Date dueDate;

  @Before
  public void setUp() throws Exception {
    book = mock(IBook.class);
    borrower = mock(IMember.class);
    borrowDate = mock(Date.class);
    dueDate = mock(Date.class);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void checkParameterForValueTrue() {
    // asserts
    assertNotNull(book);
    assertNotNull(borrower);
    assertNotNull(borrowDate);
    assertNotNull(dueDate);
  }
}