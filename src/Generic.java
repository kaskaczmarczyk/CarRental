import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Generic<T> {
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    Class<T> type;

    public T stringToT(String regex, T t) throws IOException {
        T myInput = null;
        while (true) {
            String myString = bufferedReader.readLine();
            if (myString.matches(regex)) {
                try {
                    if (Integer.class.isInstance(myInput)) {
                        return (T) myInput;
                    } else if (Double.class.isInstance(myInput)) {
                        return (T) myInput;
                    }
                    break;
                } catch (NumberFormatException exc) {
                    System.out.print("Enter value in correct format: ");
                }
            } else {
                System.out.print("Enter value in correct format: ");
            }
        }
        return myInput;
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
