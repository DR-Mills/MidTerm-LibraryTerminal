import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LibraryApp {
	static Validator val = new Validator();
	static Scanner scnr = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		ArrayList<Movie> movieInventory = new ArrayList<>();
		ArrayList<Book> bookInventory = new ArrayList<>();
		movieInventory.add(new Movie("Move1", 100, "Joe Blow", 120));
		movieInventory.add(new Movie("Move2", 100, "jim Brown", 100));
		bookInventory.add(new Book("Book1", 100, new ArrayList<String>(Arrays.asList("Mike Jone", "Kyle Johns"))));
		bookInventory.add(new Book("Book2", 100, new ArrayList<String>(Arrays.asList("Billy Mandy"))));
		System.out.println("Welcome to the library");
		System.out.println(bookInventory);
		System.out.println(movieInventory);
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
					for(int i = 0; i < bookInventory.size(); i++) {
						System.out.printf("%-3s%-15s%s%n",i + 1, bookInventory.get(i).getTitle(), bookInventory.get(i).getAuthor());
					}
					break;
				case 2:
					// Browse Movies
					break;
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

			//int userCartChoice = menuChoice();
			//cartMenu(userCartChoice);
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

}
