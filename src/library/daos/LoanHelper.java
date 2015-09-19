package library.daos;

import java.util.*;

import library.entities.Loan;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.*;

public class LoanHelper
  implements ILoanHelper 
{
  public ILoan makeLoan(IBook book, IMember borrower, Date borrowDate, Date dueDate, int loanID) {
    return new Loan(book, borrower, borrowDate, dueDate, loanID);
  }
}