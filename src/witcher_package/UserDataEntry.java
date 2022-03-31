package witcher_package;
import java.util.Scanner;

public class UserDataEntry {
    
    // Method to ask the user to enter the type of deck of which he wants to see its data.
    public int askForDeckType(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the 'cod_deck' of the type of deck you want to see its data: ");
        int deck = Integer.parseInt(keyboard.nextLine());
        return deck;
    }
    
}
