import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

public class LibraryApp {

	static Validator val = new Validator();
	static Scanner scnr = new Scanner(System.in);
	static Cart cart = new Cart();
	static MediaDatabase db = new MediaDatabase();
	static ArrayList<Movie> movieInventory = db.getMovieList();
	static ArrayList<Book> bookInventory = db.getBookList();
	static Stack<Media> recycledItems = new Stack<Media>();
	static Stack<Media> returnedItems = new Stack<Media>();

	public static void main(String[] args) {

		System.out.println("Welcome to the library");

		boolean userInLibrary = true;
		int userMainMenuChoice;

		while (userInLibrary) {
			System.out.println(
					"\nWhat would you like to do?\n1. Browse\n2. Search\n3. Return\n4. Donate Book\n5. Checkout\n6. Exit");
			userMainMenuChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 6);

			switch (userMainMenuChoice) {

			case 1: // Browse Sub-menu
				browseSubmenu();
				break;

			case 2: // Search Sub-menu
				searchSubmenu();
				break;

			case 3: // Return
				returnItemSubmenu();
				break;

			case 4: // Donate Book
				donateItemSubmenu();
				break;

			case 5:// Checkout
				checkoutSubmenu();
				break;

			case 6: // Exit
				checkReturnsStack();
				emptyRecycleBin();
				userInLibrary = false;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + userMainMenuChoice);

			}
		}
		System.out.println("\nThank you, boodbye!");

	}

	
	private static void donateItemSubmenu() {
		Media donatedItem;

		System.out.println("\nWhat would you like to donate\n1. Book\n2. Movie");
		int userDonateChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 2);

		if (userDonateChoice == 1) {
			donatedItem = manuallyCreateBook();
		} else {
			donatedItem = manuallyCreateMovie();
		}
		
		if (donatedItem.getCondition() < 20) {
			recycledItems.push(donatedItem);
			System.out.println(
					donatedItem.getTitle() + " is too used for our library and has been added to our recycle bin.");
		} else {
			returnedItems.push(donatedItem);
			System.out.println(donatedItem.getTitle() + " accepted. Thank you for your donation! ");
			if (donatedItem.getClass() == Movie.class) {
				movieInventory.add((Movie) donatedItem);
			}
			if (donatedItem.getClass() == Book.class) {
				bookInventory.add((Book) donatedItem);
			}
		}
	}

	
	private static Movie manuallyCreateMovie() {
		System.out.print("\nEnter Movie Title: ");
		String movieTitle = scnr.nextLine();

		System.out.print("\nEnter Movie Director: ");
		String movieDirector = scnr.nextLine();

		System.out.print("\nEnter Movie Condition(1-100): ");
		int movieCondition = val.integerWithinRange("\nEnter number: ", scnr, 1, 100);

		int movieRunTime = val.integerOnly("\nEnter movie runtime in minutes (rounded up to the nearest minute): ",
				scnr);

		return new Movie(movieTitle, movieCondition, Status.INRETURNS, movieDirector, movieRunTime);
	}

	
	private static Book manuallyCreateBook() {
		ArrayList<String> author = new ArrayList<>();

		System.out.print("\nEnter Book Title: ");
		String bookTitle = scnr.nextLine();

		System.out.print("\nEnter Book Author: ");
		String bookAuthor = scnr.nextLine();

		int bookCondition = val.integerWithinRange("\\nEnter Book Condition(1-100): ", scnr, 1, 100);

		String[] authorArr = bookAuthor.split(",");
		for (String a : authorArr) {
			a = a.trim();
			author.add(a);
		}

		return new Book(bookTitle, bookCondition, Status.INRETURNS, author);

	}

	private static void browseSubmenu() {
		System.out.println("1. Browse Books\n2. Browse Movie\n3. Exit To Main Menu");
		int userBrowseMenuChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 3);

		switch (userBrowseMenuChoice) {

		case 1: // --Browse Books
			browse(bookInventory, "\nEnter book number to add to cart (or type \"main\" to return to main menu): ",
					"main");
			break;

		case 2: // --Browse Movies
			browse(movieInventory, "\nEnter movie number to add to cart (or type \"main\" to return to main menu): ",
					"main");
			break;

		case 3: // --Exit back to Main Menu

			break;

		default:

			throw new IllegalArgumentException("Unexpected value: " + userBrowseMenuChoice);

		}
	}

	private static void browse(ArrayList<? extends Media> inventory, String choiceMsg, String breakMsg) {
		boolean stillBrowsing = true;

		do {
			printInventoryList(inventory);
			int browseBookChoice = val.integerInRangeStringToExit(choiceMsg, breakMsg, scnr, 1, inventory.size());

			if (browseBookChoice == -1) { // exit browse submenu
				stillBrowsing = false;
			} else { // add to cart
				addToCart(inventory, browseBookChoice);
				boolean continueBrowse = val.userContinueYorN("\nContinue Browsing? (y/n): ", scnr);

				if (continueBrowse) {
					stillBrowsing = true;
				} else {
					stillBrowsing = false;
				}
			}
		} while (stillBrowsing);
	}

	private static void printInventoryList(ArrayList<? extends Media> inventory) {
		String creator = "";
		System.out.printf("%-5s%-45s%-30s%-10s%n", "No.", "Title", "Author(s) / Director", "Status");
		System.out
				.println("==========================================================================================");
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getClass() == Movie.class) {
				creator = inventory.get(i).getAuthor().toString();
			} else {
				creator = (String) inventory.get(i).getAuthor().toString().subSequence(1,
						inventory.get(i).getAuthor().toString().length() - 1);
			}
			System.out.printf("%-5s%-45s%-30s%-10s%n", i + 1, inventory.get(i).getTitle(), creator,
					inventory.get(i).getMediaStatus());
		}
	}

	private static void addToCart(ArrayList<? extends Media> list, int itemChoice) {
		Media media = list.get(itemChoice - 1);
		if (media.getMediaStatus().equals(Status.ONSHELF)) {
			media.setMediaStatus(Status.INCART);
			cart.addToCart(media);
			System.out.println(media.getTitle() + " added to cart.");

		} else if (media.getMediaStatus().equals(Status.INCART)) {
			System.out.println("Selection is already in your cart.");
		} else {
			System.out.print("Sorry Selection is not available");
		}
	}

	private static void searchSubmenu() {
		boolean searchingCatalog = true;

		do {
			System.out.println("\n1. Search by Author / Director\n2. Search by Title (keyword)\n3. Exit To Main Menu");
			int userSearchMenuChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 3);

			switch (userSearchMenuChoice) {

			case 1: // --Search by Author/Director
				searchingCatalog = search("\nPlease enter Author / Director name: ", "creator");
				break;

			case 2: // --Search by Title keyword
				searchingCatalog = search("\nPlease enter Title keyword(s): ", "title");
				break;

			case 3: // Exit back to Main menu
				searchingCatalog = false;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + userSearchMenuChoice);
			}

		} while (searchingCatalog);
	}

	private static boolean search(String searchMsg, String searchBy) {
		boolean searchingCatalog = true;
		int userMediaChoice;
		System.out.println(searchMsg);
		String userSearchString = scnr.nextLine();

		ArrayList<Media> searchResultArr = searchResults(searchBy, userSearchString);

		if (searchResultArr.size() > 0) {
			printSearchResults(searchResultArr);
			userMediaChoice = val.integerInRangeStringToExit(
					"\nEnter the number of the book or movie to add to cart (or type \"main\" to return to main menu): ",
					"main", scnr, 1, searchResultArr.size());

			if (userMediaChoice == -1) { // exit Search submenu
				searchingCatalog = false;
			} else { // add to cart
				addToCart(searchResultArr, userMediaChoice);
				boolean continueSearch = val.userContinueYorN("\nContinue Searching? (y/n): ", scnr);

				if (continueSearch) {
					searchingCatalog = true;
				} else {
					searchingCatalog = false;
				}
			}
		}
		return searchingCatalog;
	}

	private static void returnItemSubmenu() {
		boolean stillReturning = false;
		ArrayList<Media> checkedOutMedia = new ArrayList<>();

		for (Book book : bookInventory) {
			if (book.getMediaStatus() == Status.CHECKEDOUT) {
				checkedOutMedia.add(book);
			}
		}
		for (Movie movie : movieInventory) {
			if (movie.getMediaStatus() == Status.CHECKEDOUT) {
				checkedOutMedia.add(movie);
			}
		}
		do {
			if (checkedOutMedia.size() == 0) {
				System.out.println("\nThere are no checked out items. You are being returned to the main menu. ");
				break;
			}
			int indexShiftCount = 0;
			printInventoryList(checkedOutMedia);
			ArrayList<Integer> returnBookChoice = val.integerArrayListInRangeExitStringReturnNeg1(
					"\nPlease enter the number of the item you'd like to return.\nFor multiple items, enter each item number, separated by a space.\nTo return to the main menu, type \"main\". ",
					"main", scnr, 1, checkedOutMedia.size());
			if (returnBookChoice.size() == 1 && returnBookChoice.get(0) == -1) { // exit return submenu
				stillReturning = false;
			} else { // return items from checkout
				for (Integer i : returnBookChoice) {
					System.out.println("shiftcount = " + indexShiftCount);
					Media m = checkedOutMedia.get(i - indexShiftCount - 1);
					m.setMediaStatus(Status.INRETURNS);
					returnedItems.add(m);
					checkedOutMedia.remove(m);
					indexShiftCount++;
				}

				boolean continueBrowse = val.userContinueYorN("\nReturn more books? (y/n): ", scnr);

				if (continueBrowse) {
					stillReturning = true;
				} else {
					stillReturning = false;
				}
			}
		} while (stillReturning);
	}

	private static void checkoutSubmenu() {
		boolean cartIsUnverified = true;
		do {
			if (cart.getCart().size() > 0) {
				System.out.println("\nYour cart currently includes: ");
				System.out.printf("%-5s%-45s%-20s%n", "No", "Title", "Media Type");
				System.out.println("============================================================");
				for (int i = 0; i < cart.getCart().size(); i++) {
					System.out.printf("%-5s%-45s%-20s%n", i + 1, cart.getCart().get(i).getTitle(),
							cart.getCart().get(i).getClass().toString().substring(6));
				}

				int checkoutOption = val.integerInRangeStringToExit(
						"\nIf you'd like to remove something from your cart, please enter it's number and press enter.\nOtherwise, please type \"checkout\" to checkout. ",
						"checkout", scnr, 1, cart.getCart().size());
				if (checkoutOption == -1) {
					cartIsUnverified = false;
					checkout();
				} else {
					cartIsUnverified = true;
					cart.getCart().remove(checkoutOption - 1);
				}

			} else {
				cartIsUnverified = false;
				System.out.println("\nYour cart is empty. Please add an item.");
			}
		} while (cartIsUnverified);
	}

	private static void emptyRecycleBin() {
		for (int i = 0; i < recycledItems.size(); i++) {
			System.out.println(recycledItems.pop().getTitle() + " recycled.");
		}
	}

	private static void recycleMovie(Movie movie) {
		recycledItems.add(movie);
		movie.setMediaStatus(Status.INRECYCLED);
		movieInventory.remove(movie);
		System.out.println("\nMovie recycled");
	}

	private static void recycleBook(Book book) {
		recycledItems.add(book);
		book.setMediaStatus(Status.INRECYCLED);
		bookInventory.remove(book);
		System.out.println("\nBook recycled");
	}

	private static void printSearchResults(ArrayList<Media> list) {
		list.sort(Comparator.comparing(Media::getTitle));
		System.out.printf("%-5s%-45s%-30s%-15s%s%n", "No.", "Title", "Author(s) / Director", "Media", "Status");
		System.out.println(
				"======================================================================================================");
		for (int i = 0; i < list.size(); i++) {
			String author = list.get(i).getAuthor().toString();
			if (author.contains("[")) {
				author = (String) list.get(i).getAuthor().toString().subSequence(1,
						list.get(i).getAuthor().toString().length() - 1);
			}
			System.out.printf("%-5s%-45s%-30s%-15s%s%n", i + 1, list.get(i).getTitle(), author,
					list.get(i).getClass().toString().substring(6), list.get(i).getMediaStatus());
		}
	}

	private static ArrayList<Media> searchResults(String searchBy, String userSearchString) {

		boolean continueSearch = true;
		ArrayList<Media> searchResultArr = new ArrayList<>();
		userSearchString = userSearchString.toLowerCase();

		switch (searchBy) {
		case "creator":
			for (Book book : bookInventory) {
				if (book.getAuthor().toString().toLowerCase().contains(userSearchString)) {
					searchResultArr.add(book);
				}
			}
			for (Movie movie : movieInventory) {
				if (movie.getDirector().toString().toLowerCase().contains(userSearchString)) {
					searchResultArr.add(movie);
				}
			}

		case "title":
			for (Book book : bookInventory) {
				if (book.getTitle().toString().toLowerCase().contains(userSearchString)) {
					searchResultArr.add(book);
				}
			}
			for (Movie movie : movieInventory) {
				if (movie.getTitle().toString().toLowerCase().contains(userSearchString)) {
					searchResultArr.add(movie);
				}
			}
		}

		if (searchResultArr.size() < 1) {
			String notFound = (searchBy.equals("creator")
					? "\nSorry, no Authors or Directors with that name were found.\nPlease try a broader search term or a different name. "
					: "\nSorry, no Titles containing that phrase were found.\nPlease try a broader search term or a different name. ");
			System.out.println(notFound);
			continueSearch = true;
		} else {
			continueSearch = false;
		}

		return searchResultArr;
	}

	private static void checkout() {
		System.out.println("\nCheckout Successful, here is your receipt: ");
		System.out.printf("%-40s%-15s%-15s%n", "Title", "Due Date", "Condition");
		System.out.println("================================================================================");
		for (Media media : cart.getCart()) {
			media.setMediaStatus(Status.CHECKEDOUT);
			media.setCondition(media.getCondition() - 1);
			media.setDueDate(LocalDate.now().plusDays(14));
			System.out.printf("%-40s%-15s%-15s%n", media.getTitle(), media.getDueDate(), media.getCondition());
		}
		cart.getCart().clear();
	}

	public static void printCheckedOutItems() {
		System.out.println("");
		for (@SuppressWarnings("unused")
		Book book : bookInventory) {
			System.out.println();
		}
		for (@SuppressWarnings("unused")
		Movie movie : movieInventory) {
		}
	}

	public static void checkReturnsStack() {
		while (!returnedItems.empty()) {
			returnedItems.peek().setMediaStatus(Status.ONSHELF);
			returnedItems.peek().setDueDate(null);
			System.out.println(returnedItems.pop().getTitle() + " returned to shelf");
		}
	}

}
