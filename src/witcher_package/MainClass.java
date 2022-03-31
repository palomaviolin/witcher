package witcher_package;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Gwent Database.
 * @author Paloma Ania
 */
public class MainClass {

    // Main method of the program.
    public static void main(String[] args) {
        
        // Call methods of the class 'UserDataEntry' in order to ask for the cod_deck to the user.
        UserDataEntry datosEntrada = new UserDataEntry();
        int deck = datosEntrada.askForDeckType(); 
        System.out.println("List of cards contained in the chosen deck: " + deck);
        
        // Call method of the class Database to establish the connection and obtain the requested data.
        Database basedatos = new Database();
        try {
            Connection conn = basedatos.conectar();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cards WHERE cod_deck='"+deck+"'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                //Display values.
                System.out.println("");
                System.out.print("Code: " + rs.getInt("cod_card"));
                System.out.print(" //  Name: " + rs.getString("name"));
                System.out.print(" //  Type: " + rs.getString("type"));
                System.out.print(" //  Strength: " + rs.getInt("strength"));
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error in the program (ClassNotFoundException).");
        } catch (SQLException ex) {
            System.err.println("Error in the program (SQLException).");
        }
        
        // Write the data from database (table 'decks') to a file.
        try{
            Connection conn = basedatos.conectar();
            Statement stat = null;
            ResultSet rs = null;
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * from decks");
            FileWriter fw = new FileWriter("D:\\file-1.csv");
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
        
        // Write the data from database (table 'cards') to a file (except images).
        try{
            Connection conn = basedatos.conectar();
            Statement stat = null;
            ResultSet rs = null;
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * from cards");
            FileWriter fw = new FileWriter("D:\\file-2.txt");
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
        
        // Write the data to the database from a file.
        try{
            Connection conn = basedatos.conectar();
            Statement stat = conn.createStatement();
            FileInputStream fstream = new FileInputStream("D:\\new-decks.txt");
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
                    // System.out.println(codDeck[i] + " " + name[i] + " " + skill[i]);
                    // System.out.println(Arrays.toString(codDeck));
                    // System.out.println(Arrays.toString(name));
                    // System.out.println(Arrays.toString(skill));
                    int k = stat.executeUpdate("INSERT INTO decks (cod_deck,name,skill) VALUES ('"+codDeck[i]+"','"+name[i]+"','"+skill[i]+"')");
                }
            }
        } catch(Exception ex){
            System.err.println("An error occurred while transferring the data from the file to the database.");
        }
        
        
    }
    
}
