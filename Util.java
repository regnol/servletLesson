import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Util {
	
	public static void closeConn(Statement stmt,Connection conn){
		if(stmt != null){
				try {
					stmt.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				stmt = null;
			}
			
			if(conn != null){
				try{
					conn.close();
				} catch(SQLException se){
					se.printStackTrace();
				}
				conn = null;
			}
	}
}