package library.daos;

import java.util.*;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.*;

public class LoanMapDAO
  implements ILoanDAO 
{
  private int nextID;
  private Map<Integer, ILoan> loanMap;
  private ILoanHelper helper;
  private Calendar cal;

  public LoanMapDAO(ILoanHelper helper) {
    if(helper == null) {
      throw new IllegalArgumentException("Error: Helper cannot be null.");
    }
    else {
      nextID = 0;
      this.helper = helper;
      loanMap = new HashMap<Integer, ILoan>();
      cal = Calendar.getInstance();
      return;
    }
  }

  public LoanMapDAO(ILoanHelper helper, Map<Integer, ILoan> loanMap) {
    this(helper);
    
    if(loanMap == null) {
      throw new IllegalArgumentException("Error: LoanMap cannot be null.");
    } 
    else {
      this.loanMap = loanMap;
      return;
    }
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
    
    for(ILoan loan : loanMap.values()) {
      IBook tempBook = loan.getBook();
      if(book.equals(tempBook)) {
        return loan;
      }
    }
    return null;
  }
  
	public List<ILoan> listLoans() {
		List<ILoan> list = new ArrayList<ILoan>(loanMap.values());
		return Collections.unmodifiableList(list);
	}
  
	public List<ILoan> findLoansByBorrower(IMember borrower) {
		if (borrower == null ) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : findLoansByBorrower : borrower cannot be null."));
		}
		
		List<ILoan> list = new ArrayList<ILoan>();
		
		for (ILoan loan : loanMap.values()) {
			if (borrower.equals(loan.getBorrower())) {
				list.add(loan);
			}
		}
		return Collections.unmodifiableList(list);
	}

	public List<ILoan> findLoansByBookTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : findLoansByBookTitle : title cannot be null or blank."));
		}
		
		List<ILoan> list = new ArrayList<ILoan>();
		
		for (ILoan loan : loanMap.values()) {
			String tempTitle = loan.getBook().getTitle();
			if (title.equals(tempTitle)) {
				list.add(loan);
			}
		}
		return Collections.unmodifiableList(list);
	}
  
	public void updateOverDueStatus(Date currentDate) {
		for (ILoan loan : loanMap.values()) {
			loan.checkOverDue(currentDate);
		}
	}

  private int getNextID() {
    return ++nextID;
  }
  
	public List<ILoan> findOverDueLoans() {
		List<ILoan> list = new ArrayList<ILoan>();
		for (ILoan loan : loanMap.values()) {
			if (loan.isOverDue()) {
				list.add(loan);
			}
		}
		return Collections.unmodifiableList(list);
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
    int id = getNextID();
    loan.commit(id);
    loanMap.put(Integer.valueOf(id), loan);
  }
}