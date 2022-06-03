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

			case 1: // Browse Submenu
				browseSubmenu();
				break;

			case 2: // Search Submenu

				ArrayList<Media> searchResultArr = new ArrayList<>();
				boolean searchingCatalog = true;

				do {
					System.out.println(
							"\n1. Search by Author / Director\n2. Search by Title (keyword)\n3. Exit To Main Menu");
					int userSearchMenuChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 3);

					switch (userSearchMenuChoice) {

					case 1: // --Search by Author/Director
						int userMediaChoice;
						System.out.println("\nPlease enter Author / Director name: ");
						String authorSearchString = scnr.nextLine();
						searchResultArr = searchAuthorResults(authorSearchString);
						if (searchResultArr.size() > 0) {
							printSearchResults(searchResultArr);
							userMediaChoice = val.integerInRangeStringToExit(
									"\nEnter the number of the book or movie to add to cart (or type \"main\" to return to main menu): ",
									"main", scnr, 1, searchResultArr.size());
						} else
							break;
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

						break;

					case 2: // --Search by Title keyword
						System.out.println("\nPlease enter Title keyword(s): ");
						String titleSearchString = scnr.nextLine();

						searchResultArr = searchTitleResults(titleSearchString);

						if (searchResultArr.size() > 0) {
							printSearchResults(searchResultArr);
							userMediaChoice = val.integerInRangeStringToExit(
									"\nEnter the number of the book or movie to grab or type \"main\" to return to main menu: ",
									"main", scnr, 1, searchResultArr.size());
						} else
							break;

						if (userMediaChoice == -1) { // exit Search submenu
							searchingCatalog = false;
						} else {
							addToCart(searchResultArr, userMediaChoice);
							boolean continueSearch = val.userContinueYorN("\nContinue Searching? (y/n): ", scnr);
							if (continueSearch) {
								searchingCatalog = true;
							} else {
								searchingCatalog = false;
							}
						}

						break;

					case 3: // Exit back to Main menu
						searchingCatalog = false;
						break;

					default:
						throw new IllegalArgumentException("Unexpected value: " + userSearchMenuChoice);
					}

				} while (searchingCatalog);

				break;

			case 3: // Return

				System.out.println("\nWhat would you like to return\n1. Book\n2. Movie");
				int userReturnChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 2);
				int itemNotFound = 0;

				if (userReturnChoice == 1) {
					System.out.print("\nEnter Book Title: ");
					String returnBookTitle = scnr.nextLine();

					for (Book book : bookInventory) {
						if (!returnBookTitle.equalsIgnoreCase(book.getTitle())) {
							itemNotFound++;
						}
						if (returnBookTitle.equalsIgnoreCase(book.getTitle())
								&& book.getMediaStatus() != Status.CHECKEDOUT) {
							System.out.println(
									"That title isn't checked out yet. It's currently " + book.getMediaStatus() + ". ");
							break;
						}
						if (returnBookTitle.equalsIgnoreCase(book.getTitle())
								&& book.getMediaStatus() == Status.CHECKEDOUT) {
							if (book.getCondition() < 1) {
								recycleBook(book);
								break;
							} else {
								book.setMediaStatus(Status.INRETURNS);
								returnedItems.add(book);
								System.out.println("\nBook returned");
								break;
							}
						}
						if (itemNotFound == bookInventory.size()) {
							System.out.println("\nBook not in our catalog.");
						}
					}

				} else if (userReturnChoice == 2) {
					System.out.print("\nEnter Movie Title: ");
					String returnMovieTitle = scnr.nextLine();

					for (Movie movie : movieInventory) {
						if (!returnMovieTitle.equalsIgnoreCase(movie.getTitle())) {
							itemNotFound++;
						}
						if (returnMovieTitle.equalsIgnoreCase(movie.getTitle())
								&& movie.getMediaStatus() != Status.CHECKEDOUT) {
							System.out.println("That title isn't checked out yet. It's currently "
									+ movie.getMediaStatus() + ". ");
							break;
						}
						if (returnMovieTitle.equalsIgnoreCase(movie.getTitle())
								&& movie.getMediaStatus() == Status.CHECKEDOUT) {
							if (movie.getCondition() < 1) {
								recycleMovie(movie);
								break;
							} else {
								movie.setMediaStatus(Status.INRETURNS);
								returnedItems.add(movie);
								System.out.println("\nMovie returned");
								break;
							}
						}
						if (itemNotFound == movieInventory.size()) {
							System.out.println("\nMovie not in our catalog.");
						}
					}
				}

				break;

			case 4: // Donate Book

				System.out.println("\nWhat would you like to donate\n1. Book\n2. Movie");
				int userDonateChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 2);

				if (userDonateChoice == 1) {
					System.out.print("\nEnter Book Title: ");
					String donateBookTitle = scnr.nextLine();

					System.out.print("\nEnter Book Author: ");
					String donateBookAuthor = scnr.nextLine();

					System.out.print("\nEnter Book Condition(1-100): ");
					int donateBookCondition = val.integerWithinRange("\nEnter number: ", scnr, 1, 100);

					if (donateBookCondition < 40) {
						recycledItems.add(new Book(donateBookTitle, donateBookCondition, Status.INRECYCLED,
								new ArrayList<String>(Arrays.asList(donateBookAuthor))));
						System.out.println("Book recycled");
					} else {
						bookInventory.add(new Book(donateBookTitle, donateBookCondition, Status.ONSHELF,
								new ArrayList<String>(Arrays.asList(donateBookAuthor))));
						System.out.println("Book donated");
					}

				} else if (userDonateChoice == 2) {
					System.out.print("\nEnter Movie Title: ");
					String donateMovieTitle = scnr.nextLine();

					System.out.print("\nEnter Movie Director: ");
					String donateMovieDirector = scnr.nextLine();

					System.out.print("\nEnter Movie Condition(1-100): ");
					int donateMovieCondition = val.integerWithinRange("\nEnter number: ", scnr, 1, 100);

					System.out.println("\nEnter Movie Run Time");
					int donateMovieRunTime = scnr.nextInt();

					if (donateMovieCondition < 1) {
						recycledItems.add(new Movie(donateMovieTitle, donateMovieCondition, Status.INRECYCLED,
								donateMovieDirector, donateMovieRunTime));
						System.out.println("\nMovie Recycled");
					} else {
						movieInventory.add(new Movie(donateMovieTitle, donateMovieCondition, Status.ONSHELF,
								donateMovieDirector, donateMovieRunTime));
						System.out.println("\nMovie donated");
					}
				}

				break;

			case 5:// Checkout
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
				break;

			case 6: // Exit
				returnsStackCheck(returnedItems);
				throwMediaInRecycleBin();
				userInLibrary = false;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + userMainMenuChoice);

			}
		}
		System.out.println("\nThank you, boodbye!");

	}

	
	private static void browseSubmenu() {
		System.out.println("1. Browse Books\n2. Browse Movie\n3. Exit To Main Menu");
		int userBrowseMenuChoice = val.integerWithinRange("\nEnter number: ", scnr, 1, 3);

		switch (userBrowseMenuChoice) {

		case 1: // --Browse Books
			browse(bookInventory,
					"\nEnter book number to add to cart (or type \"main\" to return to main menu): ", "main");
			break;

		case 2: // --Browse Movies
			browse(movieInventory,
					"\nEnter movie number to add to cart (or type \"main\" to return to main menu): ", "main");
			break;

		case 3: // --Exit back to Main Menu

			break;

		default:

			throw new IllegalArgumentException("Unexpected value: " + userBrowseMenuChoice);

		}
	}

	
	private static void browse(ArrayList<? extends Media> inventory, String choiceMsg, String breakMsg) {
		boolean browsingBooks = true;

		do {
			printInventoryList(inventory);
			int browseBookChoice = val.integerInRangeStringToExit(choiceMsg, breakMsg, scnr, 1, inventory.size());

			if (browseBookChoice == -1) { // exit browse submenu
				browsingBooks = false;
			} else { // add to cart
				addToCart(inventory, browseBookChoice);
				boolean continueBrowse = val.userContinueYorN("\nContinue Browsing? (y/n): ", scnr);

				if (continueBrowse) {
					browsingBooks = true;
				} else {
					browsingBooks = false;
				}
			}
		} while (browsingBooks);
	}

	
	private static void printInventoryList(ArrayList<? extends Media> inventory) {
		System.out.printf("%-5s%-45s%-30s%-10s%n", "No.", "Title", "Author(s) / Director", "Status");
		System.out
		.println("==========================================================================================");
		if (inventory.get(0).getClass() == Book.class) {
			for (int i = 0; i < inventory.size(); i++) {
				String author = (String) inventory.get(i).getAuthor().toString().subSequence(1,
						inventory.get(i).getAuthor().toString().length() - 1);
				System.out.printf("%-5s%-45s%-30s%-10s%n", i + 1, inventory.get(i).getTitle(), author,
						inventory.get(i).getMediaStatus());
			}
		}
		if (inventory.get(0).getClass() == Movie.class) {
			for (int i = 0; i < inventory.size(); i++) {
				System.out.printf("%-5s%-45s%-30s%-10s%n", i + 1, inventory.get(i).getTitle(),
						inventory.get(i).getDirector(), inventory.get(i).getMediaStatus());
			}
		}
	}
	
	
	private static void addToCart(ArrayList<? extends Media> list, int itemChoice) {
		Media media = list.get(itemChoice - 1);
		if (media.getMediaStatus().equals(Status.ONSHELF)) {
			media.setMediaStatus(Status.INCART);
			cart.addToCart(media);
			System.out.println(media.getTitle() + " added to cart.");

		} else {
			System.out.print("Sorry Selection is not available");
		}
	}
	
	
	private static void throwMediaInRecycleBin() {
		for (int i = 0; i < recycledItems.size(); i++) {
			recycledItems.pop();
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


	private static ArrayList<Media> searchAuthorResults(String userSearchString) {

		@SuppressWarnings("unused")
		boolean continueSearch = true;
		ArrayList<Media> searchResultArr = new ArrayList<>();
		userSearchString = userSearchString.toLowerCase();

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

		if (searchResultArr.size() < 1) {
			System.out.println(
					"\nSorry, no Authors or Directors with that name were found.\nPlease try a broader search term or a different name. ");
			continueSearch = true;
		} else {
			continueSearch = false;
		}

		return searchResultArr;
	}

	private static ArrayList<Media> searchTitleResults(String userSearchString) {

		@SuppressWarnings("unused")
		boolean continueSearch = true;
		ArrayList<Media> searchResultArr = new ArrayList<>();
		userSearchString = userSearchString.toLowerCase();

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

		if (searchResultArr.size() < 1) {
			System.out.println(
					"\nSorry, no Titles containing that phrase were found.\nPlease try a broader search term or a different name. ");
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

	public static void returnsStackCheck(Stack<Media> returns) {
		for (Media media : returns) {
			if (media.getClass().equals(Book.class)) {
				media.setMediaStatus(Status.ONSHELF);
				media.setDueDate(null);
				System.out.printf("%s returned to shelf\n", media.getTitle());

			} else if (media.getClass().equals(Movie.class)) {
				media.setMediaStatus(Status.ONSHELF);
				media.setDueDate(null);
				System.out.printf("%s returned to shelf\n", media.getTitle());
			}
		}
		returns.clear();

	}
}
