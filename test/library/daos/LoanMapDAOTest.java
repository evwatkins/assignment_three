package library.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Map;

import library.interfaces.daos.ILoanHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoanMapDAOTest {
  private ILoanHelper helper = mock(ILoanHelper.class);

  private Map loanMap = mock(Map.class);
  private LoanMapDAO loanMapDAO;

  @Before
  public void createLoanMapDAO() throws Exception {
    loanMapDAO = new LoanMapDAO(helper, loanMap);
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testLoanMapDAOILoanHelper() {

  }

  @Test
  public void testLoanMapDAOILoanHelperMap() {

  }

  @Test
  public void testCreateLoan() {

  }

  @Test
  public void testCommitLoan() {

  }

  @Test
  public void testGetLoanByID() {

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
  public void testFindOverDueLoans() {

  }
}