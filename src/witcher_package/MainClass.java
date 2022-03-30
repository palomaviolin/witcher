package witcher_package;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.sql.SQLException;
/**
 * Gwent Database.
 * @author Paloma Ania
 */
public class MainClass {

    // Main method of the program.
    public static void main(String[] args) {
        
        // Llamo a los métodos de la clase 'UserDataEntry' para pedir motor y año al usuario.
        UserDataEntry datosEntrada = new UserDataEntry();
        int baraja = datosEntrada.pedirTipoBaraja(); 
        // String anio = datosEntrada.pedirAnioLanzamiento();
        System.out.println("Listado de cartas que contiene la baraja: " + baraja);
        
        // Llamo al método de la clase Database para establecer la conexión con la base de datos y obtener los datos pedidos.
        Database basedatos = new Database();
        try {
            Connection conn = basedatos.conectar();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cards WHERE cod_deck='"+baraja+"'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                //Display values
                System.out.println("");
                System.out.print("Código carta: " + rs.getInt("cod_card"));
                System.out.print(", Nombre carta: " + rs.getString("name"));
                System.out.print(", Tipo: " + rs.getString("type"));
                System.out.print(", Poder: " + rs.getInt("strength"));
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error en el programa (ClassNotFoundException).");
        } catch (SQLException ex) {
            System.err.println("Error en el programa (SQLException).");
        }
        
        // Escribir en fichero datos de la tabla 'decks'
        try{
            Connection conn = basedatos.conectar();
            Statement stat = null;
            ResultSet rs = null;
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * from decks");
            FileWriter fw = new FileWriter("D:\\fichero.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            String line = "";
            while(rs.next()){
                line = rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3);
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        // Escribir en fichero datos de la tabla 'cards', excepto imágenes
        try{
            Connection conn = basedatos.conectar();
            Statement stat = null;
            ResultSet rs = null;
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * from cards");
            FileWriter fw = new FileWriter("D:\\fichero3.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            String line = "";
            bw.write("cod_card, name, type, strength, cod_deck \n \n");
            while(rs.next()){
                line = rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5);
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        
    }
    
}
