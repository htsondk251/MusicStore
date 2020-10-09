package music.data;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import music.business.Product;
import music.data.ConnectionPool;
import music.data.DBUtil;

public class ProductDB {
	public static Product selectProduct(String code) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		String query = "select * from Product where ProductCode = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Product product = new Product();
		try {

			ps = connection.prepareStatement(query);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs.next()) {
//				System.out.println("before " + rs.getString("ProductCode"));
				product.setId(rs.getLong("ProductId"));
				product.setCode(rs.getString("ProductCode"));
				product.setDescription(rs.getString("ProductDescription"));
				product.setPrice(rs.getDouble("ProductPrice"));				
			}
//			System.out.println("after " + product.getCode());
			return product;			
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}


	public static Product selectProduct(long id) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		String query = "select * from Product where ProductId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Product product = null;
		try {
			ps = connection.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				product.setId(rs.getLong("ProductId"));
				product.setCode(rs.getString("ProductCode"));
				product.setDescription(rs.getString("ProductDescription"));
				product.setPrice(rs.getDouble("ProductPrice"));
			}
			return product;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}


	public static ArrayList<Product> selectProducts() {
		ArrayList<Product> products = new ArrayList<>();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		String query = "select * from Product";
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Product product = new Product();		//check khi chi co 1 object -> sai bet
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("ProductId"));
				product.setCode(rs.getString("ProductCode"));
				product.setDescription(rs.getString("ProductDescription"));
				product.setPrice(rs.getDouble("ProductPrice"));
//				System.out.println(product.getDescription());
				products.add(product);
			}
//			for (Product p : products) {
//				System.out.println(p.getDescription());
//			}
			return products;
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