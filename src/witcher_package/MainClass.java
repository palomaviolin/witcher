package witcher_package;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
            FileWriter fw = new FileWriter("D:\\fichero5.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            String line = "";
            while(rs.next()){
                line = rs.getInt(1)+";"+rs.getString(2)+";"+rs.getString(3);
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

        // Escribir en base de datos, datos desde un fichero.
        try{
            Connection conn = basedatos.conectar();
            Statement stat = conn.createStatement();
            FileInputStream fstream = new FileInputStream("D:\\nuevas-cartas.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String[] codDeck = new String[2];
            String[] name = new String[2];
            String[] skill = new String[2];
            
            while(br.readLine() != null){
                for(int i=0; i<codDeck.length; i++){
                    codDeck[i] = br.readLine();
                    name[i] = br.readLine();
                    skill[i] = br.readLine();
                    System.out.println(codDeck[i] + "" + name[i] + "" + skill[i]);
                    System.out.println(Arrays.toString(codDeck));
                    System.out.println(Arrays.toString(name));
                    System.out.println(Arrays.toString(skill));
                    int k = stat.executeUpdate("INSERT INTO decks (cod_deck,name,skill) VALUES ('"+codDeck[i]+"','"+name[i]+"','"+skill[i]+"')");
                }
            }
        } catch(Exception ex){
         System.err.println("Se ha producido un error al pasar los datos del fichero a la base de datos.");
        }
        
        
    }
    
}
