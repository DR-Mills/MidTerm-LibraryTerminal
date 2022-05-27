import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LibraryApp {
	static Validator val = new Validator();
	static Scanner scnr = new Scanner(System.in);
	static ArrayList<Movie> movieInventory = new ArrayList<>();
	static ArrayList<Book> bookInventory = new ArrayList<>();
	static Cart cart = new Cart();

	public static void main(String[] args) {

		movieInventory.add(new Movie("Move1", 100, Status.ONSHELF, "Joe Blow", 120)); // test data
		movieInventory.add(new Movie("Move2", 100, Status.ONSHELF, "jim Brown", 100)); // test data
		bookInventory.add(new Book("Book1", 100, Status.ONSHELF, new ArrayList<String>(Arrays.asList("Mike Jone", "Kyle Johns")))); // test data																											
		bookInventory.add(new Book("Book2", 100, Status.ONSHELF, new ArrayList<String>(Arrays.asList("Billy Mandy")))); // test data

		System.out.println("Welcome to the library");

		boolean userInLibrary = true;
		int userMainMenuChoice;
		int userBrowseMenuChoice;

		while (userInLibrary) {
			System.out.println(
					"What would you like to do?\n1. Browse\n2. Search\n3. Return\n4. Donate Book\n5. Checkout\n6. Exit");
			userMainMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 6);

			switch (userMainMenuChoice) {
			case 1:
				// Browse
				System.out.println(
						"1. Browse Books\n2. Browse Movie\n3. Browse Author\n4. Browse Director\n5. Exit To Main Menu");
				userBrowseMenuChoice = val.integerWithinRange("Enter number: ", scnr, 1, 5);

				switch (userBrowseMenuChoice) {
				case 1:
					// Browse Books
					boolean browsingBooks = true;
					do {
					printBooks(bookInventory);
					int userBrowseBookChoice = val.integerInRangeStringToExit(
							"Enter book number to grab book or type \"main\" to return to main menu: ", "main", scnr, 1,
							bookInventory.size());
					if (userBrowseBookChoice == -1) { //exit browse submenu
						browsingBooks = false;
					} else { //add to cart
						if (bookInventory.get(userBrowseBookChoice -1).mediaStatus.equals(Status.ONSHELF) ) {
						System.out.println(bookInventory.get(userBrowseBookChoice-1).getTitle() + " added to cart.");
						addToCart(bookInventory.get(userBrowseBookChoice -1));}
					else { System.out.print("Sorry Selection is not available");}
						boolean continueBrowse = val.userContinueYorN("Continue Browsing? (y/n): ", scnr); //asking to continue browsing or return to main
						if (continueBrowse) {
							browsingBooks = true;
						} else {
							browsingBooks = false;
						}
					}
					} while (browsingBooks);
					break;
				case 2:
					//Browse Movies
					boolean browsingMovies = true;
					do {
					printMovies(movieInventory);
					int userBrowseMovieChoice = val.integerInRangeStringToExit(
							"Enter movie number to grab movie or type \"main\" to return to main menu: ", "main", scnr, 1,
							movieInventory.size());
					if (userBrowseMovieChoice == -1) { //exit browse submenu
						browsingMovies = false;
					} else { //add to cart
						if (movieInventory.get(userBrowseMovieChoice -1).mediaStatus.equals(Status.ONSHELF) ) {
						System.out.println(movieInventory.get(userBrowseMovieChoice-1).getTitle() + " added to cart.");
						addToCart(movieInventory.get(userBrowseMovieChoice -1));}
					else { System.out.print("Sorry Selection is not available");}
						boolean continueBrowse = val.userContinueYorN("Continue Browsing? (y/n): ", scnr); //asking to continue browsing or return to main
						if (continueBrowse) {
							browsingMovies = true;
						} else {
							browsingMovies = false;
						}
					}
					} while (browsingMovies);					break;
				case 3:
					// Browse Author
					break;
				case 4:
					// Browse Director
				case 5:
					// Exit Back to Main Menu
				default:
					throw new IllegalArgumentException("Unexpected value: " + userBrowseMenuChoice);

				}

				break;
			case 2:
				// Search
				break;
			case 3:
				// Return
				break;
			case 4:
				// Donate Book
				break;
			case 5:
				// Checkout
				break;
			case 6:
				// Exit
			default:
				throw new IllegalArgumentException("Unexpected value: " + userMainMenuChoice);

			}

			// int userCartChoice = menuChoice();
			// cartMenu(userCartChoice);
		}
		System.out.println("Thankyou Bye!");

		/*
		 * Welcome user
		 * 
		 * Menu - Return - Search - Check-out - Donate Book - Exit - Read/watch on-prem
		 * 
		 * Print Menu (Function) Prints return user choice.
		 * 
		 * 
		 * Return Users returns book and changes status in inventory. Add book to stack
		 * Decide what needs to be done with books Return: due date = null; status =
		 * on-shelf
		 * 
		 * 
		 * Search Browse by: class By title, author, director By All (Keyword) Search
		 * for keywords from classes. If found return selection. Browse function Switch
		 * method for browsing -Sub menu(title, author...)selecting by number. print sub
		 * menu if x print x for each book prints get x user inputs selection or return
		 * to main menu option to lead to Check-Out
		 * 
		 * -Search function print sub menu if x print prompt user search database for
		 * selection for each book prints get x user inputs selection or return to main
		 * menu option to lead to Check-Out
		 *
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Check-Out If already checked out let them know Due date = 2 weeks later
		 * Condition degregation
		 * 
		 * Donate Book Prompt title, author, condition Add to inventory
		 * 
		 */
		Validator validate = new Validator();
		Scanner scnr = new Scanner(System.in);

		int userMenuChoice = validate.integerWithinRange("Please enter your menu item", scnr, 1, 5);

	}

	private static void printBooks(ArrayList<Book> bookInventory) {
		System.out.printf("%-5s%-15s%s%n", "No.", "Book Title", "Author(s)");
		for (int i = 0; i < bookInventory.size(); i++) {
			System.out.printf("%-5s%-15s%s%n", i + 1, bookInventory.get(i).getTitle(),
					bookInventory.get(i).getAuthor());
		}
	}
	private static void printMovies(ArrayList<Movie> movieInventory) {
		System.out.printf("%-5s%-15s%s%n", "No.", "Book Title", "Author(s)");
		for (int i = 0; i < movieInventory.size(); i++) {
			System.out.printf("%-5s%-15s%s%n", i + 1, movieInventory.get(i).getTitle(),
					movieInventory.get(i).getDirector());
		}
	}

	private static void addToCart(Book book) {
		cart.addToCart(book);
		book.setMediaStatus(Status.INCART);
	}
	private static void addToCart(Movie movie) {
		cart.addToCart(movie);
		movie.setMediaStatus(Status.INCART);
	}

}
