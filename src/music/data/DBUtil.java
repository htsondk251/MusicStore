package music.data;

import java.sql.*;

public class DBUtil {
	public static void closePreparedStatement(PreparedStatement ps) {
		try {
			ps.close();
		} catch(SQLException e) {
			System.out.println(e);
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}

