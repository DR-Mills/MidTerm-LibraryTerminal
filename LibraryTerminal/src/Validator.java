import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validator {
	// validates whole integer within given range
	public int integerWithinRange(String promptMsg, Scanner scnr, int min, int max) {
		int returnValue = 0;
		boolean keepGoing = true;

		System.out.print(promptMsg);
		do {
			try {
				returnValue = scnr.nextInt();

				if (returnValue < min || returnValue > max) {
					System.out.print("Please enter a number between " + min + " and " + max + ": ");
					keepGoing = true;
				} else {
					keepGoing = false;
				}
				scnr.nextLine();
			} catch (InputMismatchException e) {
				System.out
						.print("Invalid input. Please enter a whole number only between " + min + " and " + max + ": ");
				scnr.nextLine();
				keepGoing = true;
			}
		} while (keepGoing);

		return returnValue;
	}

	// validates whole integer within given range
	// returns -1 if user enters given String (exitString) instead of a number
	public int integerInRangeStringToExit(String promptMsg, String exitString, Scanner scnr, int min, int max) {
		int returnValue = 0;
		boolean keepGoing = true;

		System.out.print(promptMsg);
		do {
			String strValue = scnr.next();
			if (strValue.equalsIgnoreCase(exitString)) {
				return -1;
			}
			try {
				returnValue = Integer.parseInt(strValue);
				if (returnValue < min || returnValue > max) {
					System.out.print("Please enter a number between " + min + " and " + max + ": ");
					keepGoing = true;
				} else {
					keepGoing = false;
				}
			} catch (InputMismatchException | NumberFormatException e) {
				System.out
						.print("Invalid input. Please enter a whole number only between " + min + " and " + max + ": ");
				keepGoing = true;
			}
		} while (keepGoing);

		return returnValue;
	}

	// validates whole integer within given range
	// returns -1 if user enters given String (exitString) instead of a number
	public ArrayList<Integer> integerArrayListInRangeExitStringReturnNeg1(String promptMsg, String exitString,
			Scanner scnr, int min, int max) {
		ArrayList<Integer> intArr = new ArrayList<>();
		boolean keepGoing = true;

		System.out.print(promptMsg);
		do {
			String strValue = scnr.nextLine();
			if (strValue.equalsIgnoreCase(exitString)) {
				intArr.add(-1);
				return intArr;
			}
			try {
				String[] strArray = strValue.split(" ");
				for (int i = 0; i < strArray.length; i++) {
					int intValue = Integer.parseInt(strArray[i]);

					if (intValue < min || intValue > max) {
						System.out.print("Please only enter numbers between " + min + " and " + max + ": ");
						keepGoing = true;
						break;
					} else {
						intArr.add(intValue);
						keepGoing = false;
					}
				}
			} catch (InputMismatchException | NumberFormatException e) {
				System.out
						.print("Invalid input. Please enter whole numbers only between " + min + " and " + max + ": ");
				keepGoing = true;
			}
		} while (keepGoing);

		return intArr;
	}

	// overloaded method
	// validates input as "y" (or "yes") or "n" (or "no") and returns boolean y =
	// true, n = false.
	public boolean userContinueYorN(String promptMsg, Scanner scnr) {
		String userInput = null;
		boolean invalidInput = true;
		boolean userContinues = false;

		System.out.print(promptMsg);

		do {
			try {
				userInput = scnr.next();

				if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
					invalidInput = false;
					userContinues = true;
				} else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
					invalidInput = false;
					userContinues = false;
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.print("Invalid input. Please enter \"y\" or \"n\": ");
				invalidInput = true;
			}
		} while (invalidInput);

		return userContinues;
	}

	// overloaded method
	// validates input as "y" (or "yes") or "n" (or "no") and returns boolean y =
	// true, n = false.
	public boolean userContinueYorN(String promptMsg, String goodbyeMsg, Scanner scnr) {
		String userInput = null;
		boolean invalidInput = true;
		boolean userContinues = false;

		System.out.print(promptMsg);

		do {
			try {
				userInput = scnr.next();
				if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
					invalidInput = false;
					userContinues = true;
				} else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
					System.out.println(goodbyeMsg);
					invalidInput = false;
					userContinues = false;
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.print("Invalid input. Please enter \"y\" or \"n\": ");
				invalidInput = true;
			}
		} while (invalidInput);

		return userContinues;
	}

	public String stringLettersOnly(String prompt, Scanner scnr) {
		boolean tryAgain = true;
		String userString;

		do {
			System.out.println(prompt);
			userString = scnr.nextLine();

			String regex = "^[a-zA-Z\s]+$";
			if (userString.matches(regex)) {
				tryAgain = false;
			} else {
				System.out.println("Please enter letters only; numbers and special characters are not allowed: ");
			}
		} while (tryAgain);

		return userString;
	}
}
