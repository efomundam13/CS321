import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Lab7 {

	public static Connection conn;
	public static Statement stmt;
	public static PreparedStatement pstmt;
	public static Scanner in = new Scanner(System.in)
			;
	private static void connect(String fileName) {
		// SQLite connection string
		String url = "jdbc:sqlite:" + fileName;
		conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void query1() throws Exception
	{
		String sql = "SELECT Fname, Lname, Address FROM EMPLOYEE WHERE Address LIKE ?";
		System.out.print("Enter city name --> ");
		String city = in.next();
		pstmt  = conn.prepareStatement(sql);
		pstmt.setString(1,"%"+city+"%");  
		ResultSet rs    = pstmt.executeQuery();
		System.out.println("\nResult of Query:");
		while (rs.next()) {
			System.out.printf("%20s : %s\n", rs.getString(1) + " " + rs.getString(2), rs.getString(3));
		}		
	}

	public static void query2() throws Exception
	{
		String sql = "SELECT Lname, SUM(Hours)AS TotalHours, Essn FROM WORKS_ON, EMPLOYEE WHERE Essn=Ssn GROUP BY Essn HAVING SUM(Hours) >= ? ORDER BY Lname";
		System.out.print("Enter number of hours --> ");
		int numHours = in.nextInt();
		pstmt  = conn.prepareStatement(sql);
		pstmt.setInt(1,numHours);  
		ResultSet rs    = pstmt.executeQuery();
		System.out.println("\nResult of Query:");
		while (rs.next()) {
			System.out.println(rs.getString(1) + " " + rs.getDouble("TotalHours"));
			sql = "SELECT Pname, Hours FROM WORKS_ON, PROJECT WHERE Essn = ? AND Pno = Pnumber";
			pstmt  = conn.prepareStatement(sql);
			pstmt.setString(1,rs.getString(3));  
			ResultSet rs2  = pstmt.executeQuery();
			while (rs2.next()) {
				System.out.println("   " + rs2.getString(1) + " " + rs2.getDouble(2));
			}
		}
	}
	public static void main(String [] args)
	{
		String fileName = "company.db";
		try {
			connect(fileName);
			stmt  = conn.createStatement();

			int selection=0;
			do {
				System.out.println("\nMenu");
				System.out.println("  1) Search address by city");
				System.out.println("  2) Get employees by number of hours worked");
				System.out.println("  3) Exit");
				System.out.print("Selection --> ");
				try {
					selection = in.nextInt();
					switch (selection) {
					case 1: query1(); break;
					case 2: query2(); break;
					case 3: break;
					default: System.out.println("Invalid selection");
					}
				} catch (InputMismatchException e) {
					System.out.println("Please enter an integer");
					in.nextLine();
				}
			} while (selection != 3);
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}

	}
}
