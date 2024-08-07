import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDB {

	private static Connection connect(String fileName) {
		// SQLite connection string
		String url = "jdbc:sqlite:" + fileName;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
 		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static void main(String [] args)
	{
		String fileName = "company.db";
		try (Connection conn = connect(fileName)) {
			Statement stmt  = conn.createStatement();

			String sql = "SELECT Lname FROM EMPLOYEE";
			ResultSet rs    = stmt.executeQuery(sql);
			System.out.println("Result of Query 1:");
			while (rs.next()) {
				System.out.println(rs.getString("Lname"));
			}

			sql = "SELECT Fname, Lname, Address FROM EMPLOYEE, DEPARTMENT WHERE Dname='Research' AND Dnumber=Dno";
			rs    = stmt.executeQuery(sql);
			System.out.println("\nResult of Query 2:");
			while (rs.next()) {
				System.out.println(rs.getString("Fname") + " " + rs.getString("Lname") + ": " + rs.getString("Address"));
			}

			sql = "SELECT Dno, Fname, Lname, Address FROM EMPLOYEE, DEPARTMENT WHERE Dname=? AND Dnumber=Dno";
			PreparedStatement pstmt  = conn.prepareStatement(sql);
			pstmt.setString(1,"Administration");           
			rs    = pstmt.executeQuery();
			System.out.println("\nResult of Query 3:");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + ": " + rs.getString(4));
			}

			sql = "SELECT Fname, Lname, Ssn FROM EMPLOYEE WHERE NOT EXISTS (SELECT Pnumber FROM PROJECT WHERE Dnum = 4 EXCEPT SELECT Pno FROM WORKS_ON WHERE Ssn = Essn)";
			rs    = stmt.executeQuery(sql);
			System.out.println("\nResult of Query 4:");
			while (rs.next()) {
				System.out.println(rs.getString("Fname") + " " + rs.getString("Lname") + " " + rs.getInt(3));
			}

		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}

	}
}