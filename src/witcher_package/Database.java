package witcher_package;
import java.sql.*;

public class Database {
    Connection connect = null;
    
    // Method to establish the connection with the database.
    public Connection connect() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/gwent","root","");
        return connect;
    }
}
