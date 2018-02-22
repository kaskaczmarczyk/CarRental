import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckInput {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    int stringToInt() throws IOException {
        int myInt;
        while (true) {
            String myString = bufferedReader.readLine();
            if (myString.matches("^[0-9]+$")) {
                try {
                    myInt = Integer.parseInt(myString);
                    break;
                } catch (NumberFormatException exc) {
                    System.out.print("Enter number: ");
                }
            } else {
                System.out.print("Enter number: ");
            }
        }
        return myInt;
    }

    public String notTakeEmptyString() throws IOException {
        String myString;
        while (true) {
            String inputString = bufferedReader.readLine();
            if (inputString.matches("^[A-z]+$")) {
                try {
                    myString = inputString;
                    break;
                } catch (NumberFormatException exc) {
                    System.out.println("Enter the character or word. ");
                }
            } else {
                System.out.println("Enter the character or word. ");
            }
        }
        return myString;
    }
}
