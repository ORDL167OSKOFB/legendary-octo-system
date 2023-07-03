import java.util.Scanner;


/**
 * Class which is used to get and check user inputs for validity Created in
 * order to facilitate code reuse and have DRY code, also consolidates user 
 * input into a seperate class
 * 
 * @author Andrew Ellis
 * @version V1.0
 */
public class GetInput {
    private static Scanner uInput = new Scanner(System.in);;

    /**
     * Method to get user input for an integer and ensures it is a valid integer
     * 
     * @return the user input integer, else -1 if they did not enter an integer
     */
    public static int getIntInput() {
        int intToTest = -1;

        // The reason it reads the scanner for a String rather than double is because
        // double inputs ignore whitespace, allowing the user to press enter constantly
        String input = uInput.nextLine();
        try {
            intToTest = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
        return intToTest;
    }

    /**
     * Method to get user input for a double and ensures it is a valid double 
     * 
     * @return the user input double, else -1.0 if they did not enter an double
     */
    public static double getDoubleInput() {
        double doubleToTest = -1.0;

        // The reason it reads the scanner for a String rather than double is because
        // double inputs ignore whitespace, allowing the user to press enter constantly
        String input = uInput.nextLine();
        try {
            doubleToTest = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1.0;
        }
        return doubleToTest;
    }

    /**
     * Method to get user input for yes or no values input as 'Y' or 'N'
     * 
     * @return true if user input 'Y', false if they input 'N'
     */
    public static boolean getYesNo() {

        // The default value for char datatypes
        char choice = '\u0000';
        while (Character.toUpperCase(choice) != 'N') {
            try {
                choice = uInput.nextLine().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Please enter Y for yes or N for no.");
            }

            // Converts character to uppercase so it can be entered either
            // in uppercase or lowercase
            switch (Character.toUpperCase(choice)) {
                case 'Y':
                    return true;
                case 'N':
                    return false;
                default:
                    System.out.println("Please enter Y for yes or N for no.");
                    break;
            }
        }
        return false;
    }

    /**
     * Method to get user input for a String
     * 
     * @return the user input
     */
    public static String getString() {
        String choice = uInput.nextLine();
        return choice;
    }

}