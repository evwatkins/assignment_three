package library.entities;

import java.util.Date;
import java.text.DateFormat;

import library.interfaces.entities.*;

public class Loan
  implements ILoan
{
  private final IBook book;
  private final IMember borrower;
  private Date borrowDate;
  private Date dueDate;
  private int id;
  private ELoanState state;

  // Details of the book to be taken out on loan
  public Loan(IBook book, IMember borrower, Date borrowDate, Date dueDate) {
    boolean loanDetails = (book != null && borrower != null && borrowDate != null && dueDate != null && borrowDate.compareTo(dueDate) <= 0);

    if(!loanDetails) {
      throw new IllegalArgumentException("Error: Incorrect parameters entered. Please try again.");
    } 
    else {
      this.book = book;
      this.borrower = borrower;
      this.borrowDate = borrowDate;
      this.dueDate = dueDate;
      state = ELoanState.PENDING;
      return;
    }
  }

  // Sets the current state of the loan to CURRENT
  public void commit(int loanId)
  {    
    if(state != ELoanState.PENDING) {
      throw new RuntimeException("Error: The loans current state is not PENDING.");
    }
    if(loanId <= 0)
    {
        throw new RuntimeException("Error: Loan ID must be positive.\n");
    }
    else {
      id = loanId;
      state = ELoanState.CURRENT;
      book.borrow(this);
      borrower.addLoan(this);
      return;
    }
  }

  // Sets the current state of the loan to COMPLETE
  public void complete()
  {
    if(state != ELoanState.CURRENT && state != ELoanState.OVERDUE) {
      throw new RuntimeException("Error: The loans current state is not CURRENT or OVERDUE.");
    } 
    else {
      state = ELoanState.COMPLETE;
      return;
    }
  }

  // Returns TRUE if the current state of the loan is OVERDUE
  public boolean isOverDue()
  {
    return state == ELoanState.OVERDUE;
  }

  // Sets the current state of the loan to OVERDUE if currentDate is greater than dueDate
  public boolean checkOverDue(Date currentDate)
  {
    if(state != ELoanState.CURRENT && state != ELoanState.OVERDUE) {
      throw new RuntimeException("Error: The loans current state is not CURRENT or OVERDUE.");
    }
    
    if(currentDate.compareTo(dueDate) > 0) {
      state = ELoanState.OVERDUE;
    }
    return isOverDue();
  }

  // Returns the borrower associated with the loan
  public IMember getBorrower()
  {
    return borrower;
  }

  // Returns the book associated with the loan
  public IBook getBook()
  {
    return book;
  }

  // Returns the book's unique ID
  public int getID()
  {
    return id;
  }

  public ELoanState getCurrentState()
  {
    return state;
  }

  public String toString()
  {
    return String.format("Loan ID:  %d\nAuthor:   %s\nTitle:    %s\nBorrower: %s %s\nBorrowed: %s\nDue Date" + "e: %s", new Object[] {
        Integer.valueOf(id), book.getAuthor(), book.getTitle(), borrower.getFirstName(), borrower.getLastName(), DateFormat.getDateInstance().format(borrowDate), DateFormat.getDateInstance().format(dueDate)
    });
  }
}

