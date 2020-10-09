package music.data;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import music.business.*;
import music.data.*;

public class LineItemDB {

	//add one lineItem to the LineItem tables
	public static int insert(long invoiceID, LineItem lineItem) {
		List<LineItem> lineItems = selectLineItems(invoiceID);
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "insert into LineItem (InvoiceID, ProductID, Quantity) values (?, ?, ?)";
		try {
			ps = connection.prepareStatement(query);
			ps.setLong(3, lineItem.getQuantity());
			ps.setLong(2, lineItem.getProduct().getId());
			ps.setLong(1, invoiceID);
			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}


	public static List<LineItem> selectLineItems(long invoiceID) {
		List<LineItem> lineItems = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();		
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "select * from LineItem where InvoiceID = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setLong(1, invoiceID);

			rs = ps.executeQuery();

			while (rs.next()) {
				LineItem lineItem = new LineItem();
				lineItem.setLineItemId(rs.getLong("LineItemID"));

				//get product from ProductID
				long productID = rs.getLong("ProductID");
				Product product = ProductDB.selectProduct(productID);
				lineItem.setProduct(product);
				lineItem.setQuantity(rs.getInt("Quantity"));
				lineItems.add(lineItem);
			}
			return lineItems;
		} catch(SQLException e) {
			System.out.println(e);
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}
}