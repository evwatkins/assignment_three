package library.daos;

import java.util.*;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.*;

public class LoanMapDAO
  implements ILoanDAO 
{
  private int nextID;
  private Map loanMap;
  private ILoanHelper helper;
  private Calendar cal;

  public LoanMapDAO(ILoanHelper helper) {
    if(helper == null) {
      throw new IllegalArgumentException("Error: Helper cannot be null.");
    }
    else {
      nextID = 0;
      this.helper = helper;
      loanMap = new HashMap();
      cal = Calendar.getInstance();
      return;
    }
  }

  public LoanMapDAO(ILoanHelper helper, Map loanMap) {
    this(helper);
    if(loanMap == null) {
      throw new IllegalArgumentException("Error: LoanMap cannot be null.");
    } 
    else {
      this.loanMap = loanMap;
      return;
    }
  }

  public ILoan createLoan(IMember borrower, IBook book) {
    if(borrower == null || book == null) {
      throw new IllegalArgumentException("Error: Borrower and book cannot be null.");
    } 
    else {
      Date borrowDate = new Date();
      cal.setTime(borrowDate);
      cal.add(5, 14);
      Date dueDate = cal.getTime();

      ILoan loan = helper.makeLoan(book, borrower, borrowDate, dueDate);
      return loan;
    }
  }

  public void commitLoan(ILoan loan) {
    int id = getNextId();
    loan.commit(id);
    loanMap.put(Integer.valueOf(id), loan);
  }

  private int getNextId() {
    return ++nextID;
  }
 
  public ILoan getLoanByID(int id) {
    if(loanMap.containsKey(Integer.valueOf(id))) {
      return (ILoan)loanMap.get(Integer.valueOf(id));
    } 
    else {
      return null;
    }
  }

  public ILoan getLoanByBook(IBook book) {
    if(book == null) {
      throw new IllegalArgumentException("Error: Book cannot be null.");
    }
    for(Iterator iterator = loanMap.values().iterator(); iterator.hasNext();) {
      ILoan loan = (ILoan)iterator.next();
      IBook tempBook = loan.getBook();
      if(book.equals(tempBook)) {
        return loan;
      }
    }

    return null;
  }

  public List listLoans() {
    List list = new ArrayList(loanMap.values());
    return Collections.unmodifiableList(list);
  }

  public List findLoansByBorrower(IMember borrower) {
    if(borrower == null) {
      throw new IllegalArgumentException("Error: borrower cannot be null.");
    }
    
    List list = new ArrayList();
    
    for(Iterator iterator = loanMap.values().iterator(); iterator.hasNext();) {
      ILoan loan = (ILoan)iterator.next();
      if(borrower.equals(loan.getBorrower())) {
        list.add(loan);
      }
    }

    return Collections.unmodifiableList(list);
  }

  public List findLoansByBookTitle(String title) {
    if(title == null || title.isEmpty()) {
      throw new IllegalArgumentException("Error: Title cannot be null or blank.");
    }
    List list = new ArrayList();
    for(Iterator iterator = loanMap.values().iterator(); iterator.hasNext();) {
      ILoan loan = (ILoan)iterator.next();
      String tempTitle = loan.getBook().getTitle();
      if(title.equals(tempTitle)) {
        list.add(loan);
      }
    }

    return Collections.unmodifiableList(list);
  }

  public void updateOverDueStatus(Date currentDate) {
    ILoan loan;
    for(Iterator iterator = loanMap.values().iterator(); iterator.hasNext(); loan.checkOverDue(currentDate)) {
      loan = (ILoan)iterator.next();
    }

  }

  public List findOverDueLoans() {
    List list = new ArrayList();
    for(Iterator iterator = loanMap.values().iterator(); iterator.hasNext();) {
      ILoan loan = (ILoan)iterator.next();
      if(loan.isOverDue()) {
        list.add(loan);
      }
    }

    return Collections.unmodifiableList(list);
  }


}