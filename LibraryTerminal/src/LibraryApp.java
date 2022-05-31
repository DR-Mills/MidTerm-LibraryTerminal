import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class LibraryApp {
	static Validator val = new Validator();
	static Scanner scnr = new Scanner(System.in);
	static ArrayList<Movie> movieInventory = new ArrayList<>();
	static ArrayList<Book> bookInventory = new ArrayList<>();
	static Cart cart = new Cart();

	public static void main(String[] args) {

		movieInventory.add(new Movie("Move1", 100, Status.ONSHELF, "Joe Brown", 120)); // test data
		movieInventory.add(new Movie("Move2", 100, Status.ONSHELF, "jim Brown", 100)); // test data
		bookInventory.add(new Book("Book1", 100, Status.ONSHELF,
				new ArrayList<String>(Arrays.asList("Mike Brown", "Kyle Johns")))); // test data
		bookInventory.add(new Book("Book2", 100, Status.ONSHELF, new ArrayList<String>(Arrays.asList("Billy Brown")))); // test
																														// data

		System.out.println("Welcome to the library");

		boolean userInLibrary = true;
		int userMainMenuChoice;
		int userBrowseMenuChoice;
		String donateBookAuthor;
		int donateBookCondition;
		String donateBookTitle;
		String donateMovieDirector;
		int donateMovieCondition;
		String donateMovieTitle;
		int donateMovieRunTime;
		int userDonateChoice;

		while (userInLibrary) {
			System.out.println(
					"What would you like to do?\n1. Browse\n2. Search\n3. Return\n4. Donate Book\n5. Checkout\n6. Exit");
			userMainMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 6);

			switch (userMainMenuChoice) {
			case 1: // Browse Submenu
				System.out.println("1. Browse Books\n2. Browse Movie\n3. Exit To Main Menu");
				userBrowseMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 3);

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
							if (movieInventory.get(browseMovieChoice - 1).mediaStatus.equals(Status.ONSHELF)) {
								System.out.println(
										movieInventory.get(browseMovieChoice - 1).getTitle() + " added to cart.");
								addToCart(movieInventory, browseMovieChoice);
							} else {
								System.out.print("Sorry Selection is not available");
							}
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

				int userSearchMenuChoice;
				ArrayList<Media> searchResultArr = new ArrayList<>();
				boolean searchingCatalog = true;

				do {
					System.out.println(
							"1. Search by Author / Director\n2. Search by Title (keyword)\n3. Exit To Main Menu");
					userSearchMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 3);

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

			case 3:
				// Return
				System.out.println();
				break;
			case 4:
				// Donate Book
				// Build method in validator class to validate Input
				System.out.println("What would you like to donate\n1. Book\n2. Movie");
				userDonateChoice = val.integerWithinRange("Enter number: ", scnr, 1, 2);
				if (userDonateChoice == 1) {
					System.out.print("Enter Book Title: ");
					donateBookTitle = scnr.next();
					System.out.print("Enter Book Author: ");
					donateBookAuthor = scnr.next();
					System.out.print("Enter Book Condition(1-100): ");
					donateBookCondition = val.integerWithinRange("Enter number: ", scnr, 1, 100);
					if (donateBookCondition < 40) {
						// recycle book
					} else {
						bookInventory.add(new Book(donateBookTitle, donateBookCondition, Status.ONSHELF,
								new ArrayList<String>(Arrays.asList(donateBookAuthor))));
						System.out.println("Book donated");
					}

				} else if (userDonateChoice == 2) {
					System.out.print("Enter Movie Title: ");
					donateMovieTitle = scnr.next();
					System.out.print("Enter Movie Director: ");
					donateMovieDirector = scnr.next();
					System.out.print("Enter Movie Condition(1-100): ");
					donateMovieCondition = val.integerWithinRange("Enter number: ", scnr, 1, 100);
					System.out.println("Enter Movie Run Time");
					donateMovieRunTime = scnr.nextInt();
					if (donateMovieCondition < 40) {
						// recycle Movie
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
				userInLibrary = false;
			default:
				throw new IllegalArgumentException("Unexpected value: " + userMainMenuChoice);

			}
		}
		System.out.println("Thank you, boodbye!");
	}

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

//		System.out.println("MOVIES");
//		System.out.printf("%-5s%-15s%-15s%s-%n", "No.", "Status", "Title", "Director");
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getClass() == Movie.class) {				
//			System.out.printf("%-5s%-15s%-15s%s-%n", i + 1, list.get(i).getMediaStatus(), list.get(i).getTitle(),
//					list.get(i).getDirector());
//			}
//		}
//	}

	
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

	private static void checkout() {
		for (Media media : cart.getCart()) {
			media.setMediaStatus(Status.CHECKEDOUT);
			media.setCondition(media.getCondition() - 1);
		}

		// TESTING CODE
//		System.out.println("movieInventory " + movieInventory);
//		System.out.println("bookInventory " + bookInventory);
//		System.out.println("cart " + cart.getCart());

	}

}
