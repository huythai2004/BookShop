
package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DBUtils implements Serializable{
    public static Connection makeConnection() throws ClassNotFoundException, SQLException{
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        
        String url = "jdbc:sqlserver://localhost:1433;databaseName=BookShop";
        
        Connection con = DriverManager.getConnection(url, "sa", "123");
       
        return con;
    }
}
