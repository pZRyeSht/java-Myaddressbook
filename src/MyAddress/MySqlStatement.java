package MyAddress;
/**
 * 数据库的连接
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlStatement {
	public static ResultSet rs =null;
	public static Statement stmt=null;
	
	public static void MySql() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:MySQL://localhost:3306/myadressbook?useSSL"
				+ "=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
		Connection conn = DriverManager.getConnection(url,"root","123456");
		stmt = conn.createStatement();
		
	}
}
