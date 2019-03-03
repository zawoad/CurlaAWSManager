package curlaawsmanager.edu.cis.uab.dal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**	
 * A singleton class to ensure one connection 
 * object through out the application life cycle
 * @author shams
 *
 */

public class DBConnection {
	
	private Connection DB;
	private static DBConnection DBConn;
	private DBConnection(String host, String port, String database, String username, String password)
	{
		try {
			Class.forName("org.postgresql.Driver");
			DB = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+database,
                    username,
                    password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	    
	}
	
	public static Connection DB(String host, String port, String database, String username, String password)
	{
		if(DBConn == null) 
			DBConn = new DBConnection(host, port, database, username, password);
		return DBConn.DB;
	}
}
