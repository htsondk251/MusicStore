package music.data;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import music.business.*;
import music.data.*;

public class InvoiceDB {
	public static int insert(Invoice invoice) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();

		String query = "insert into Invoice values (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
			ps.setLong(1, invoice.getInvoiceNumber());
			ps.setLong(2, invoice.getUser().getUserId());

			java.sql.Date invoiceDate= (java.sql.Date) invoice.getInvoiceDate();
			ps.setDate(3, invoiceDate);
			ps.setFloat(4, (float)invoice.getInvoiceTotal());

			//set isProcessed -- due to table save 'y' or 'n'
			//boolean isProcessedBoolean = invoice.isIsProcessed();
			if (invoice.isIsProcessed()) {
				ps.setString(5, "y");
			} else {
				ps.setString(5, "n");
			}
			

			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}


	public static int update(Invoice invoice) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();

		String query = "update Invoice set UserId=?, InvoiceDate=?, TotalAmount=?, isProcessed=?) where InvoiceId =?";
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
			ps.setLong(1, invoice.getUser().getUserId());

			java.sql.Date invoiceDate= (java.sql.Date) invoice.getInvoiceDate();
			ps.setDate(2, invoiceDate);
			ps.setFloat(3, (float)invoice.getInvoiceTotal());
			ps.setLong(5, invoice.getInvoiceNumber());

			//set isProcessed -- due to table save 'y' or 'n'
			//boolean isProcessedBoolean = invoice.isIsProcessed();
			if (invoice.isIsProcessed()) {
				ps.setString(4, "y");
			} else {
				ps.setString(4, "n");
			}


			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	
	public static List<Invoice> selectUnprocessedInvoices() {
		List<Invoice> unprocessedInvoices = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();

		String query = "select * from Invoice where isProcessed = 'n'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Invoice invoice = null;
		User user = null;
		try {
			ps = connection.prepareStatement(query);
			//ps.setString(1, "n");

			rs = ps.executeQuery();

			while (rs.next()) {
			//get user from userId
			long userID = rs.getLong("UserID");
			user = UserDB.selectUserFromUserId(userID);

			//get lineItems from InvoiceId
			Long invoiceID = rs.getLong("InvoiceID");
			List<LineItem> lineItems = LineItemDB.selectLineItems(invoiceID);

		 	Invoice invoice = new Invoice();
			invoice.setUser(user);
			invoice.setLineItems(lineItems);

			java.sql.Date invoiceDate = (java.sql.Date) invoice.getInvoiceDate();
			invoice.setInvoiceDate(invoiceDate);
			invoice.setInvoiceNumber(invoiceID);
			invoice.setIsProcessed(false);
			unprocessedInvoices.add(invoice);
			}
			return unprocessedInvoices;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}		
	}
}