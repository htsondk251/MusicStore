package music.data;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import music.business.User;
import music.data.*;

public class UserDB {
	public static int insert(User user) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;

		String query = "insert into User (firstName, lastName, email, companyName, address1, address2, city, st, zip, country, creditCardType, creditCardNumber, creditCartExpiration) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?)";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getCompanyName());
			ps.setString(5, user.getAddress1());
			ps.setString(6, user.getAddress2());
			ps.setString(7, user.getCity());
			ps.setString(8, user.getState());
			ps.setString(9, user.getZip());
			ps.setString(10, user.getCountry());
			ps.setString(11, user.getCreditCardType());
			ps.setString(12, user.getCreditCardNumber());
			ps.setString(13, user.getCreditCardExpirationDate());
			return ps.executeUpdate();
/*
			//get userId from the last INSERT statement
			String identityQuery = "select @@IDENTITY as IDENTITY";
			Statement identityStatement = connection.createStatement();
			ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
			identittyResultSet.next();
			long userId = identityResutlSet.getLong("IDENTITY");
			identityResultSet.close();
			identityStatement.close();
		
			user.setId(userId);
*/
		} catch(SQLException e) {
			System.out.println(0);
			return 0;	
		} finally {
			//ps.close() must in a try - catch
			//ps.close();
			//DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	
	public static int update(User user) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;

		String query = "update User "
			+ "set firstName=?, lastName=?, companyName=?, address1=?, address2=?, city?, st=?, zip=?, country=?, creditCardType=?, creditCardNumber=?, creditCartExpiration=? "
			+ "where email = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());

			ps.setString(3, user.getCompanyName());
			ps.setString(4, user.getAddress1());
			ps.setString(5, user.getAddress2());
			ps.setString(6, user.getCity());
			ps.setString(7, user.getState());
			ps.setString(8, user.getZip());
			ps.setString(9, user.getCountry());
			ps.setString(10, user.getCreditCardType());
			ps.setString(11, user.getCreditCardNumber());
			ps.setString(12, user.getCreditCardExpirationDate());
			ps.setString(13, user.getEmail());

			return ps.executeUpdate();
		} catch(SQLException e) {
			System.out.println(0);
			return 0;	
		} finally {
			//DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}	
	}


	public static boolean emailExists(String email) {
		User user = selectUser(email);
		return user != null;
	}

/*
    public static boolean emailExists(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT Email FROM User "
                + "WHERE Email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    } 
*/
	public static User selectUser(String email) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from User "
			+ "where email = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setUserId(rs.getLong(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				user.setAddress1(rs.getString(6));
				user.setAddress2(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				user.setCountry(rs.getString(11));
				user.setCreditCardType(rs.getString(12));
				user.setCreditCardNumber(rs.getString(13));
				user.setCreditCardExpirationDate(rs.getString(14));
				return user;
			} else {
				return null;
			}			
		} catch(SQLException e) {
			System.out.println(0);
			return null;	
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}


	public static User selectUserFromUserId(long userID) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		String query = "select * from User "
			+ "where userID = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setLong(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getLong(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				user.setAddress1(rs.getString(6));
				user.setAddress2(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				user.setCountry(rs.getString(11));
				user.setCreditCardType(rs.getString(12));
				user.setCreditCardNumber(rs.getString(13));
				user.setCreditCardExpirationDate(rs.getString(14));
			}
			return user;
		} catch(SQLException e) {
			System.out.println(0);
			return null;	
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

/*
	public static int delete(User user) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;

		String query = "delete from User "
			+ "where email = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getEmail());
			return ps.executeUpdate();
		} catch(SQLException e) {
			System.out.println(0);
			return 0;	
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	
	public static List<User> selectUsers() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<>();
		String query = "select * from User ";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				user.setAddress1(rs.getString(6));
				user.setAddress2(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				user.setCountry(rs.getString(11));
				user.setCreditCardType(rs.getString(12));
				user.setCreditCardNumber(rs.getString(13));
				user.setCreditCardExpirationDate(rs.getString(14));
				users.add(user);
			}
			return users;
		} catch(SQLException e) {
			System.out.println(0);
			return null;	
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}
*/
}
