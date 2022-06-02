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
					"What would you like to do?\n1. Browse\n2. Search\n3. Return\n4. Donate Book\n5. Checkout\n6. Exit");
			userMainMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 6);

			switch (userMainMenuChoice) {

			case 1: // Browse Submenu
				System.out.println("1. Browse Books\n2. Browse Movie\n3. Exit To Main Menu");
				int userBrowseMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 3);

				switch (userBrowseMenuChoice) {

				case 1: // --Browse Books

					boolean browsingBooks = true;

					do {
						printBooks(bookInventory);
						int browseBookChoice = val.integerInRangeStringToExit(
								"Enter book number to add to cart (or type \"main\" to return to main menu): ", "main",
								scnr, 1, bookInventory.size());

						if (browseBookChoice == -1) { // exit browse submenu
							browsingBooks = false;
						} else { // add to cart
							addToCart(bookInventory, browseBookChoice);
							boolean continueBrowse = val.userContinueYorN("Continue Browsing? (y/n): ", scnr);

							if (continueBrowse) {
								browsingBooks = true;
							} else {
								browsingBooks = false;
							}
						}
					} while (browsingBooks);

					break;

				case 2: // --Browse Movies

					boolean browsingMovies = true;

					do {
						printMovies(movieInventory);
						int browseMovieChoice = val.integerInRangeStringToExit(
								"Enter movie number to add to cart (or type \"main\" to return to main menu): ", "main",
								scnr, 1, movieInventory.size());

						if (browseMovieChoice == -1) { // exit browse submenu
							browsingMovies = false;
						} else { // add to cart
							addToCart(movieInventory, browseMovieChoice);
							boolean continueBrowse = val.userContinueYorN("Continue Browsing? (y/n): ", scnr);

							if (continueBrowse) {
								browsingMovies = true;
							} else {
								browsingMovies = false;
							}
						}
					} while (browsingMovies);

					break;

				case 3: // --Exit back to Main Menu

					break;

				default:

					throw new IllegalArgumentException("Unexpected value: " + userBrowseMenuChoice);

				}
				break; // end of Browse Submenu

			case 2: // Search Submenu

				ArrayList<Media> searchResultArr = new ArrayList<>();
				boolean searchingCatalog = true;

				do {
					System.out.println(
							"1. Search by Author / Director\n2. Search by Title (keyword)\n3. Exit To Main Menu");
					int userSearchMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 3);

					switch (userSearchMenuChoice) {

					case 1: // --Search by Author/Director
						int userMediaChoice;
						System.out.println("Please enter Author / Director name: ");
						String authorSearchString = scnr.nextLine();
						searchResultArr = searchAuthorResults(authorSearchString);
						if (searchResultArr.size() > 0) {
							printSearchResults(searchResultArr);
							userMediaChoice = val.integerInRangeStringToExit(
									"Enter the number of the book or movie to add to cart (or type \"main\" to return to main menu): ",
									"main", scnr, 1, searchResultArr.size());
						} else
							break;
						if (userMediaChoice == -1) { // exit Search submenu
							searchingCatalog = false;
						} else { // add to cart
							addToCart(searchResultArr, userMediaChoice);
							boolean continueSearch = val.userContinueYorN("Continue Searching? (y/n): ", scnr);
							if (continueSearch) {
								searchingCatalog = true;
							} else {
								searchingCatalog = false;
							}
						}

						break;

					case 2: // --Search by Title keyword
						System.out.println("Please enter Title keyword(s): ");
						String titleSearchString = scnr.nextLine();

						searchResultArr = searchTitleResults(titleSearchString);

						if (searchResultArr.size() > 0) {
							printSearchResults(searchResultArr);
							userMediaChoice = val.integerInRangeStringToExit(
									"Enter the number of the book or movie to grab or type \"main\" to return to main menu: ",
									"main", scnr, 1, searchResultArr.size());
						} else
							break;

						if (userMediaChoice == -1) { // exit Search submenu
							searchingCatalog = false;
						} else { // add to cart
							addToCart(searchResultArr, userMediaChoice);
							boolean continueSearch = val.userContinueYorN("Continue Searching? (y/n): ", scnr);
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

				System.out.println("What would you like to return\n1. Book\n2. Movie");
				int userReturnChoice = val.integerWithinRange("Enter number: ", scnr, 1, 2);

				if (userReturnChoice == 1) {
					System.out.print("Enter Book Title: ");
					String returnBookTitle = scnr.nextLine();

					for (Book book : bookInventory) {
						if (returnBookTitle.equals(book.getTitle()) && book.getMediaStatus() == Status.CHECKEDOUT) {
							if (book.getCondition() < 1) {
								recycleBook(book);
								break;
							} else {
								book.setMediaStatus(Status.INRETURNS);
								returnedItems.add(book);
								System.out.println("Book returned");
								break;
							}
						} else {
							System.out.println("Book not in our catalog.");

						}
					}

				} else if (userReturnChoice == 2) {
					System.out.print("Enter Movie Title: ");
					String returnMovieTitle = scnr.nextLine();

					for (Movie movie : movieInventory) {
						if (returnMovieTitle.equals(movie.getTitle()) && movie.getMediaStatus() == Status.CHECKEDOUT) {
							if (movie.getCondition() < 1) {
								recycleMovie(movie);
								break;
							} else {
								movie.setMediaStatus(Status.INRETURNS);
								returnedItems.add(movie);
								System.out.println("Movie returned");
								break;
							}

						} else {
							System.out.println("Movie not in our catalog.");
						}
					}
				}

				break;

			case 4: // Donate Book

				System.out.println("What would you like to donate\n1. Book\n2. Movie");
				int userDonateChoice = val.integerWithinRange("Enter number: ", scnr, 1, 2);

				if (userDonateChoice == 1) {
					System.out.print("Enter Book Title: ");
					String donateBookTitle = scnr.nextLine();
					System.out.print("Enter Book Author: ");
					String donateBookAuthor = scnr.nextLine();
					System.out.print("Enter Book Condition(1-100): ");
					int donateBookCondition = val.integerWithinRange("Enter number: ", scnr, 1, 100);

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
					System.out.print("Enter Movie Title: ");
					String donateMovieTitle = scnr.nextLine();
					System.out.print("Enter Movie Director: ");
					String donateMovieDirector = scnr.nextLine();
					System.out.print("Enter Movie Condition(1-100): ");
					int donateMovieCondition = val.integerWithinRange("Enter number: ", scnr, 1, 100);
					System.out.println("Enter Movie Run Time");
					int donateMovieRunTime = scnr.nextInt();

					if (donateMovieCondition < 40) {
						// recycle Movie
						recycledItems.add(new Movie(donateMovieTitle, donateMovieCondition, Status.INRECYCLED,
								donateMovieDirector, donateMovieRunTime));
						System.out.println("Movie Recycled");
					} else {
						movieInventory.add(new Movie(donateMovieTitle, donateMovieCondition, Status.ONSHELF,
								donateMovieDirector, donateMovieRunTime));
						System.out.println("Movie donated");
					}
				}

				break;

			case 5:// Checkout
					// add if statement for an empty cart.
				boolean cartIsUnverified = true;

				do {
					System.out.println("Your cart currently includes: ");
					System.out.printf("%-5s%-20s%-20s%n", "No", "Title", "Media Type");
					for (int i = 0; i < cart.getCart().size(); i++) {
						System.out.printf("%-5s%-20s%-20s%n", i + 1, cart.getCart().get(i).getTitle(),
								cart.getCart().get(i).getClass().toString().substring(6));
					}

					int checkoutOption = val.integerInRangeStringToExit(
							"If you'd like to remove something from your cart, please enter it's number and press enter.\nOtherwise, please type \"checkout\" to checkout. ",
							"checkout", scnr, 1, cart.getCart().size());
					if (checkoutOption == -1) {
						cartIsUnverified = false;
						checkout();
					} else {
						cartIsUnverified = true;
						cart.getCart().remove(checkoutOption - 1);
					}

				} while (cartIsUnverified);

				break;

			case 6:
				// Exit
				// add method to go through return stack and return books to shelf or recycle
				returnsStackCheck(returnedItems);
				userInLibrary = false;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + userMainMenuChoice);

			}
		}
		System.out.println("Thank you, boodbye!");

	}

	private static void recycleMovie(Movie movie) {
		recycledItems.add(movie);
		movie.setMediaStatus(Status.INRECYCLED);
		movieInventory.remove(movie);
		System.out.println("Movie recycled");
	}

	private static void recycleBook(Book book) {
		recycledItems.add(book);
		book.setMediaStatus(Status.INRECYCLED);
		bookInventory.remove(book);
		System.out.println("Book recycled");
	}

	// METHODS
	// need to refactor, this is the same as printMovies with small differences
	private static void printBooks(ArrayList<Book> bookInventory) {
		System.out.printf("%-5s%-15s%s%n", "No.", "Book Title", "Author(s)");
		for (int i = 0; i < bookInventory.size(); i++) {
			String author = (String) bookInventory.get(i).getAuthor().toString().subSequence(1,
					bookInventory.get(i).getAuthor().toString().length() - 1);
			System.out.printf("%-5s%-15s%s%n", i + 1, bookInventory.get(i).getTitle(), author);
		}
	}

	// need to refactor, this is the same as printBooks with small differences
	private static void printMovies(ArrayList<Movie> movieInventory) {
		System.out.printf("%-5s%-15s%s%n", "No.", "Movie Title", "Author(s)");
		for (int i = 0; i < movieInventory.size(); i++) {
			System.out.printf("%-5s%-15s%s%n", i + 1, movieInventory.get(i).getTitle(),
					movieInventory.get(i).getDirector());
		}
	}

	private static void printSearchResults(ArrayList<Media> list) {
		list.sort(Comparator.comparing(Media::getTitle));
		System.out.printf("%-5s%-25s%-30s%-15s%s%n", "No.", "Title", "Author(s) / Director", "Media", "Status");
		System.out.println("=========================================================================");
		for (int i = 0; i < list.size(); i++) {
			String author = list.get(i).getAuthor().toString();
			if (author.contains("[")) {
				author = (String) list.get(i).getAuthor().toString().subSequence(1,
						list.get(i).getAuthor().toString().length() - 1);
			}
			System.out.printf("%-5s%-25s%-30s%-15s%s%n", i + 1, list.get(i).getTitle(), author,
					list.get(i).getClass().toString().substring(6), list.get(i).getMediaStatus());
		}
	}

	private static void addToCart(ArrayList<? extends Media> list, int itemChoice) {
		Media media = list.get(itemChoice - 1);
		if (media.mediaStatus.equals(Status.ONSHELF)) {
			media.setMediaStatus(Status.INCART);
			cart.addToCart(media);
			System.out.println(media.getTitle() + " added to cart.");

		} else {
			System.out.print("Sorry Selection is not available");
		}
	}

	// Need to refactor this method and searchTitleResults. It's the same code, need
	// to pass field variable to search as method parameter. how?
	private static ArrayList<Media> searchAuthorResults(String userSearchString) {

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
					"Sorry, no Authors or Directors with that name were found.\nPlease try a broader search term or a different name. ");
			continueSearch = true;
		} else {
			continueSearch = false;
		}

		return searchResultArr;
	}

	// Need to refactor this method and searchAuthorResults. It's the same code,
	// need
	// to pass field variable to search as method parameter. how?
	private static ArrayList<Media> searchTitleResults(String userSearchString) {

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
					"Sorry, no Authors or Directors with that name were found.\nPlease try a broader search term or a different name. ");
			continueSearch = true;
		} else {
			continueSearch = false;
		}

		return searchResultArr;
	}

	private static void checkout() { // add--automatically leave library...?
		for (Media media : cart.getCart()) {
			media.setMediaStatus(Status.CHECKEDOUT);
			media.setCondition(media.getCondition() - 1);
		}
		cart.getCart().clear();
	}

	public static void printCheckedOutItems() {
		System.out.println("");
		for (Book book : bookInventory) {
			System.out.println();
		}
		for (Movie movie : movieInventory) {
		}
	}
	public static void returnsStackCheck(Stack<Media>returns) {
		for(Media media : returns) {
			if(media.getClass().equals(Book.class)) {
				media.setMediaStatus(Status.ONSHELF);
				System.out.printf("%s returned to shelf", media.getTitle());
				
			}else if (media.getClass().equals(Movie.class)) {
				media.setMediaStatus(Status.ONSHELF);
				System.out.printf("%s returned to shelf", media.getTitle());
			}
		}
		returns.clear();
		System.out.println("Return Stack Clear");
		
	}
}
