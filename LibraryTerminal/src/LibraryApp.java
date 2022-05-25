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

Return
	Goes into stack/queue
	Checks condition, recycles if needed
Return: due date = null; status = on-shelf

Search
	Browse by: class
By title, author, director
	By All (Keyword)

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
