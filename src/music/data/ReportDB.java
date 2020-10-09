package music.data;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import music.business.*;
import music.data.*;

public class ReportDB {
	public static Workbook getUserEmail() {
		//create the workbook, its worksheet and its title row
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("User Email Report");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("The User Email Report");

		//create the header row
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("LastName");
		row.createCell(1).setCellValue("FirstName");
		row.createCell(2).setCellValue("Email");
		row.createCell(3).setCellValue("CompanyName");
		row.createCell(4).setCellValue("Address1");
		row.createCell(5).setCellValue("Address2");
		row.createCell(6).setCellValue("City");
		row.createCell(7).setCellValue("State");
		row.createCell(8).setCellValue("Zip");
		row.createCell(9).setCellValue("Country");
		row.createCell(10).setCellValue("UserID");

		//create data row
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "select * from User order by LastName";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			int i = 3;
			while (rs.next()) {
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(rs.getString("LastName"));
				row.createCell(1).setCellValue(rs.getString("FirstName"));
				row.createCell(2).setCellValue(rs.getString("Email"));
				row.createCell(3).setCellValue(rs.getString("CompanyName"));
				row.createCell(4).setCellValue(rs.getString("Address1"));
				row.createCell(5).setCellValue(rs.getString("Address2"));
				row.createCell(6).setCellValue(rs.getString("City"));
				row.createCell(7).setCellValue(rs.getString("State"));
				row.createCell(8).setCellValue(rs.getString("Zip"));
				row.createCell(9).setCellValue(rs.getString("Country"));
				row.createCell(10).setCellValue(rs.getString("UserID"));
				i++;
			}
			return workbook;	
		} catch (SQLException e) {
			System.out.println(e);
			return null;	
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	
	public static Workbook getDownloadDetail(String startDate, String endDate) {
		//create the workbook, sheet and its tilte row
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Download Report");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("The Download Report");

		//create header rows
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("Start Date: " + startDate);
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("End Date: " + endDate);
		row = sheet.createRow(5);
		row.createCell(0).setCellValue("DownloadDate");
		row.createCell(1).setCellValue("ProductCode");
		row.createCell(2).setCellValue("Email");
		row.createCell(3).setCellValue("FirstName");
		row.createCell(4).setCellValue("LastName");

		//create data rows
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "select DownloadDate, ProductCode, Email, FirstName, LastName " +
				"from Download inner join User on User.UserID = Download.UserID " +
				"where DownloadDate >= '" + startDate + "' and DownloadDate <= '" + endDate + 
				"' order by DownloadDate desc";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			int total = 0;
			int i = 6;
			while (rs.next()) {
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(rs.getString("DownloadDate"));
				row.createCell(1).setCellValue(rs.getString("ProductCode"));
				row.createCell(2).setCellValue(rs.getString("Email"));
				row.createCell(3).setCellValue(rs.getString("FirstName"));
				row.createCell(4).setCellValue(rs.getString("LastName"));
				total++;
				i++;
			}
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue("Total Number of Downloads: " + total);
			return workbook;
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