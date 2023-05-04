import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {

    public static void main(String[] args) {

        playBullsAndCows(123); // Call the specified method and start the game
    }

    public static boolean contains(int[] x, int y) {

        for (int i = 0; i < x.length; i++) {

            if (x[i] == y){
                return true;
            }
        }
        return false;
    }

    public static int[] generateSecretDigits(int x) {

        Random generator = new Random(x);  //Create new random object

        int[] secretDigits = new int[4];  //Create new array with 4 elements

        for (int i = 0; i < 4; i++) {
            secretDigits[i] = generator.nextInt(10); //Generate random bounded number and assign it to element [i]

            int j = 0;

            //Make sure the assigned number is unique
            while (j <= i) {
                if (j != i && secretDigits[i] == secretDigits[j]) {
                    secretDigits[i] = generator.nextInt(10); ////If repeated, generate new random number to assign
                    j = 0; //Reinitialize j
                }
                else {
                    j++;
                }
            }
        }
        return secretDigits;
    }

    public static int getNumOfBulls(int[] x, int[] y) {

        if (x.length != y.length) { //Check if both arrays have same amount of elements
            throw new IllegalArgumentException("Length error"); //If different amount of elements throw exception
        }
        //Check if every digit in array y is unique
        for (int i = 0; i < y.length; i++) {
            for (int j = 1; j < y.length; j++) {
                if (i != j && y[i] == y[j]){
                    throw new IllegalStateException("Repeat digit"); //Throw exception if repeated digit is found
                }
            }
        }
        int count = 0;
        //Calculate amount of Bulls
        for (int i = 0; i < x.length; i++) {
            if (x[i] == y[i]) {
                count++;
            }
        }
        return count;
    }

    public static int getNumOfCows(int[] x, int[] y) {

        if (x.length != y.length) { //Check if both arrays have same amount of elements
            throw new IllegalArgumentException("Length error"); //If different amount of elements throw exception
        }
        //Check if every digit in array y is unique
        for (int i = 0; i < y.length; i++) {
            for (int j = 1; j < y.length; j++) {
                if (i != j && y[i] == y[j]) {
                    throw new IllegalStateException("Repeat digit"); //Throw exception if repeated digit is found
                }
            }
        }

        int count = 0;
        //Calculate amount of cows
        for (int i = 0; i < x.length; i++) {
            if ((contains(x, y[i])) && (x[i] != y[i])) {
                count++;
            }
        }
        return count;
    }

    public static int[] createGuessArray(int guess) {

        int length = String.valueOf(guess).length(); //Define amount of elements in array

        int[] y = new int[length]; //Create the array with (guess) elements

        //Specify each element based on the player's input (guess)
        for (int i = y.length - 1; i >= 0; i--) {
            y[i] = guess%10;
            guess /= 10;
        }
        return y;
    }

    public static void playBullsAndCows(int x) {

        int[] secretDigits = generateSecretDigits(x); //Call specified method to generate secret array

        System.out.println("Welcome to \"Bulls and Cows\"! Enjoy the game!"); //Welcome message

        boolean quit = false; //Create boolean to be used in condition for loop

        for (int i = 1; !quit; i++) {

            System.out.print("Guess #" + i + ": Please enter a 4-digit number, each digit must be unique: "); //Message with attempts

            Scanner input = new Scanner(System.in); //Allow for player's answer

            int guess = input.nextInt();

            int[] guessArray = createGuessArray(guess); //Call specified method to create array
            //Make sure player's input is positive and 4 digits at most
            if (guess < 0 || guess > 9999) {
                System.out.println("You just wasted one guess, you must enter a 4-digit positive number");
            }
            else {
                //Get number of bulls and cows, or catch the previously defined exceptions
                try {
                    System.out.println("Bulls: " + getNumOfBulls(secretDigits, guessArray));
                    System.out.println("Cows: " + getNumOfCows(secretDigits, guessArray));
                }
                catch (IllegalArgumentException ex) {
                    System.out.println("Length error. The number you enter must have 4 digits!");
                }
                catch (IllegalStateException ex) {
                    System.out.println("Repeated digits, all digits in the number must be unique!");
                }
                }
            if (Arrays.equals(secretDigits, guessArray)){ //Check if all elements are equal (successful guess)

                System.out.println("Congratulations, you guessed the secret number! It took you " + i + " attempts!"); //Success message
                break;
            }
            if (i >= 5){ // Check if it is 5th attempt or higher
                System.out.print("Do you want to quit the game? Answer y/n : "); //Display message to quit
                String answer = input.next(); //Allow player's answer
                //Check if answer is valid
                while (answer.charAt(0) != 'y' && answer.charAt(0) != 'n'){
                    System.out.println("Incorrect answer, please answer with y or n");
                    System.out.print("Do you want to quit the game? Answer y/n : ");
                    answer = input.next();
                }
                if (answer.charAt(0) == 'y') { //Check if player decided to quit
                    System.out.println("You decided to quit after " + i + " attempts. Thank you for playing!"); //Message for quitter
                    quit = true; //Change loop condition
                }
            }
        }
    }
}
