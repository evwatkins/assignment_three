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
  public Loan(IBook book, IMember borrower, Date borrowDate, Date returnDate) {
    if(!sane(book, borrower, borrowDate, returnDate)) {
      throw new IllegalArgumentException("Error: Parameters are empty.");
    } 
    else {
      this.book = book;
      this.borrower = borrower;
      this.borrowDate = borrowDate;
      this.dueDate = returnDate;
      this.state = ELoanState.PENDING;
      return;
    }
  }
  
  // Return book
  private boolean sane(IBook book, IMember borrower, Date borrowDate, Date returnDate)
  {
      return book != null && borrower != null && borrowDate != null && returnDate != null && borrowDate.compareTo(returnDate) <= 0;
  }

  // Sets the current state of the loan to CURRENT
  public void commit(int loanId) {    
    if(!(state == ELoanState.PENDING)) {
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
  public void complete() {
    if(!(state == ELoanState.CURRENT || state == ELoanState.OVERDUE)) {
      throw new RuntimeException("Error: The loans current state is not CURRENT or OVERDUE.");
    } 
    else {
      state = ELoanState.COMPLETE;
      return;
    }
  }

  // Returns TRUE if the current state of the loan is OVERDUE
  public boolean isOverDue() {
  	state = ELoanState.OVERDUE;
    return state == ELoanState.OVERDUE;
  }

  // Sets the current state of the loan to OVERDUE if currentDate is greater than dueDate
  public boolean checkOverDue(Date currentDate) {
    if(!(state == ELoanState.CURRENT || state == ELoanState.OVERDUE)) {
      throw new RuntimeException("Error: The loans current state is not CURRENT or OVERDUE.");
    }
    
    else if(currentDate.compareTo(dueDate) > 0) {
      state = ELoanState.OVERDUE;
    }
    return isOverDue();
  }

  // Returns the borrower associated with the loan
  public IMember getBorrower() {
    return borrower;
  }

  // Returns the book associated with the loan
  public IBook getBook() {
    return book;
  }

  // Returns the book's unique ID
  public int getID() {
    return id;
  }

  //Returns the book's current state
  public ELoanState getCurrentState() {
    return state;
  }

  //Returns the details of the book
  public String toString() {
    return String.format("Loan ID:  %d\n"
    		+ "Author:   %s\n"
    		+ "Title of Book:    %s\n"
    		+ "Borrower's Name: %s %s\n"
    		+ "Date Borrowed: %s\n"
    		+ "Due Date" + "e: %s", new Object[] {
        Integer.valueOf(id), book.getAuthor(), book.getTitle(), borrower.getFirstName(), borrower.getLastName(), 
        DateFormat.getDateInstance().format(borrowDate), DateFormat.getDateInstance().format(dueDate)
    });
  }
}

