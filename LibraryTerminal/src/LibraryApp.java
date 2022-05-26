import java.util.Scanner;

public class LibraryApp {

	public static void main(String[] args) {
 
/*
Welcome user
				
Menu
-	Return
-	Search
-	Check-out
-	Donate Book
-	Exit
-	Read/watch on-prem

*Print Menu (Function)
*Prints return user choice.


Return
*Users returns book and changes status in inventory.
*Add book to stack
*Decide what needs to be done with books
*Return: due date = null; status = on-shelf


Search
	Browse by: class
By title, author, director
	By All (Keyword)
*Search for keywords from classes. If found return selection.
*Browse function
*Switch method for browsing
*-Sub menu(title, author...)selecting by number.
*print sub menu
*if x  print x
*for each book prints get x
*user inputs selection or return to main menu
*option to lead to Check-Out

*-Search function
*print sub menu
*if x  print prompt
*user search database for selection
*for each book prints get x
*user inputs selection or return to main menu
*option to lead to Check-Out
*





Check-Out
	If already checked out let them know
	Due date = 2 weeks later
	Condition degregation

Donate Book
	Prompt title, author, condition
	Add to inventory
 *		
*/
		Validator validate = new Validator();
		Scanner scnr = new Scanner(System.in);
		
		int userMenuChoice = validate.integerWithinRange("Please enter your menu item", scnr, 1, 5);
		
	}

}
