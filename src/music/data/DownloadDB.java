package music.data;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import music.business.*;
import music.data.*;

public class DownloadDB {
	public static int insert(Download download) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();

		String query = "insert into Download (UserID, DownloadDate, ProductCode) values (?, ?, ?)";
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
			ps.setLong(1, download.getUser().getUserId());			

			java.sql.Date downloadDate= new java.sql.Date(download.getDownloadDate().getTime());
			ps.setDate(2, downloadDate);
			ps.setString(3, download.getProductCode());	

			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}		
	}
}