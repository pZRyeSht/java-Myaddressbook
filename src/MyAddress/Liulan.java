package MyAddress;
/**
 * 数据库的更新
 */
import java.sql.SQLException;

public class Liulan {

	public static void liulan() {
		 MyAddressbook.v.clear();
			try {
				MySqlStatement.MySql();
				String sql = "select * from adressbook ";
				MySqlStatement.rs = MySqlStatement.stmt.executeQuery(sql);
				while(MySqlStatement.rs.next()){
					MyAddressbook.v.addElement(MySqlStatement.rs.getString("name"));
					}
				MyAddressbook.list.setListData(MyAddressbook.v);
			}catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
}
			
