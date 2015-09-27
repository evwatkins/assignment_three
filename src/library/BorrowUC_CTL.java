package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class BorrowUC_CTL implements ICardReaderListener, 
IScannerListener, 
IBorrowUIListener {

	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;

	//private String state;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;

	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;

	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		scanCount = 0;

		this.reader = reader;
		this.scanner = scanner;
		this.printer = printer;
		this.display = display;
		this.bookDAO = bookDAO;
		this.loanDAO = loanDAO;
		this.memberDAO = memberDAO;

		reader.addListener(this);
		scanner.addListener(this);
		ui = new BorrowUC_UI(this);
		state = EBorrowState.CREATED;
	}

	public void initialise() {
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");	
		setState(EBorrowState.INITIALIZED);
	}

	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	public void cardSwiped(int memberID) {
		System.out.println((new StringBuilder("cardSwiped: got ")).append(memberID).toString());

		if(!state.equals(EBorrowState.INITIALIZED)) {
			throw new RuntimeException(String.format("BorrowUC_CTL : cardSwiped : illegal operation in state: %s", new Object[] {
					state
			}));
		}

		borrower = memberDAO.getMemberByID(memberID);

		if(borrower == null) {
			ui.displayErrorMessage(String.format("Member ID %d not found", new Object[] {
					Integer.valueOf(memberID)
			}));
			return;
		}

		boolean overdue = borrower.hasOverDueLoans();
		boolean atLoanLimit = borrower.hasReachedLoanLimit();
		boolean hasFines = borrower.hasFinesPayable();
		boolean overFineLimit = borrower.hasReachedFineLimit();
		boolean borrowing_restricted = overdue || atLoanLimit || overFineLimit;

		if(borrowing_restricted) {
			setState(EBorrowState.BORROWING_RESTRICTED);
		} 
		else {
			setState(EBorrowState.SCANNING_BOOKS);
		}

		int mID = borrower.getID();
		String mName = (new StringBuilder(String.valueOf(borrower.getFirstName()))).append(" ").append(borrower.getLastName()).toString();
		String mContact = borrower.getContactPhone();
		ui.displayMemberDetails(mID, mName, mContact);

		if(hasFines) {
			float amountOwing = borrower.getFineAmount();
			ui.displayOutstandingFineMessage(amountOwing);
		}
		if(overdue) {
			ui.displayOverDueMessage();
		}
		if(atLoanLimit) {
			ui.displayAtLoanLimitMessage();
		}
		if(overFineLimit) {
			System.out.println((new StringBuilder("State: ")).append(state).toString());
			float amountOwing = borrower.getFineAmount();
			ui.displayOverFineLimitMessage(amountOwing);
		}

		String loanString = buildLoanListDisplay(borrower.getLoans());
		ui.displayExistingLoan(loanString);
	}

	@Override
	public void bookScanned(int barcode) {
		throw new RuntimeException("Not implemented yet");
	}


	private void setState(EBorrowState state) {
		throw new RuntimeException("Not implemented yet");
	}

	public void cancelled() {
		setState(EBorrowState.CANCELLED);
	}

	public void scansCompleted() {
		setState(EBorrowState.CONFIRMING_LOANS);
	}

	public void loansConfirmed() {
		setState(EBorrowState.COMPLETED);
	}

	public void loansRejected() {
		System.out.println("Loans Rejected");
		setState(EBorrowState.SCANNING_BOOKS);
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
