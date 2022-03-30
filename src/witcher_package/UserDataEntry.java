package witcher_package;
import java.util.Scanner;

public class UserDataEntry {
    
    // Método para pedir al usuario que introduzca el tipo de motor.
    public int pedirTipoBaraja(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduzca tipo de baraja: ");
        int baraja = Integer.parseInt(teclado.nextLine());
        return baraja;
    }
    
    // Método para pedir al usuario que introduzca el año de lanzamiento.
//    public String pedirAnioLanzamiento(){
//        Scanner teclado = new Scanner(System.in);
//        System.out.println("Introduzca año de lanzamiento: ");
//        String anio = teclado.nextLine();
//        return anio;
//    }
    
}
