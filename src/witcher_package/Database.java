package witcher_package;
import java.sql.*;

public class Database {
    Connection conectar = null;
    
    // Method to establish the connection with the database.
    public Connection conectar() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/gwent","root","");
        return conectar;
    }
}
