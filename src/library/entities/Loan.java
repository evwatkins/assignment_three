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
  private int loanID;
  private ELoanState currentState;

  // Details of the book to be taken out on loan
  public Loan(IBook book, IMember borrower, Date borrowDate, Date dueDate, int loanID) {
    boolean loanDetails = (book != null && borrower != null && borrowDate != null && dueDate != null && borrowDate.compareTo(dueDate) <= 0 && loanID <= 0);

    if(!loanDetails) {
      throw new IllegalArgumentException("Error: Incorrect parameters entered. Please try again.");
    } 
    else {
      this.book = book;
      this.borrower = borrower;
      this.borrowDate = borrowDate;
      this.dueDate = dueDate;
      this.loanID = loanID;
      currentState = ELoanState.PENDING;
      return;
    }
  }

  // Sets the current state of the loan to CURRENT
  public void commit()
  {    
    if(currentState != ELoanState.PENDING) {
      throw new RuntimeException("Error: The loans current state is not PENDING.");
    }
    else {
      currentState = ELoanState.CURRENT;
      book.borrow(this);
      borrower.addLoan(this);
      return;
    }
  }

  // Sets the current state of the loan to COMPLETE
  public void complete()
  {
    if(currentState != ELoanState.CURRENT && currentState != ELoanState.OVERDUE) {
      throw new RuntimeException("Error: The loans current state is not CURRENT or OVERDUE.");
    } 
    else {
      currentState = ELoanState.COMPLETE;
      return;
    }
  }

  // Returns TRUE if the current state of the loan is OVERDUE
  public boolean isOverDue()
  {
    return currentState == ELoanState.OVERDUE;
  }

  // Sets the current state of the loan to OVERDUE if currentDate is greater than dueDate
  public boolean checkOverDue(Date currentDate)
  {
    if(currentState != ELoanState.CURRENT && currentState != ELoanState.OVERDUE) {
      throw new RuntimeException("Error: The loans current state is not CURRENT or OVERDUE.");
    }
    
    if(currentDate.compareTo(dueDate) > 0) {
      currentState = ELoanState.OVERDUE;
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
    return loanID;
  }

  public ELoanState getCurrentState()
  {
    return currentState;
  }

  public String toString()
  {
    return String.format("Loan ID:  %d\nAuthor:   %s\nTitle:    %s\nBorrower: %s %s\nBorrowed: %s\nDue Date" + "e: %s", new Object[] {
        Integer.valueOf(loanID), book.getAuthor(), book.getTitle(), borrower.getFirstName(), borrower.getLastName(), DateFormat.getDateInstance().format(borrowDate), DateFormat.getDateInstance().format(dueDate)
    });
  }
}

