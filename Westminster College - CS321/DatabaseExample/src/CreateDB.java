
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
public class CreateDB {
 
	public static Connection conn = null;
	public static Statement stmt = null;
	
    public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:" + fileName;
//        String url = "jdbc:sqlite:C:/users/bonomojp/documents/courses/cs321/" + fileName;
 
        try  {
        	conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                stmt = conn.createStatement();
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public static void createNewTable(String sql) {
        // SQL statement for creating a new table
        try {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void insert(String table, String tuple) {
        String sql = "INSERT INTO " + table + " VALUES (" + tuple + ");";
 
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        createNewDatabase("company.db");
        createNewTable("CREATE TABLE EMPLOYEE(" +
        		"Fname text NOT NULL," +
        		"Minit text," +
        		"Lname text NOT NULL," +
        		"Ssn char(9) NOT NULL," +
        		"Bdate Date," +
        		"Address text," +
        		"Sex char," +
        		"Salary money," +
        		"Super_ssn char(9)," +
        		"Dno int NOT NULL," +
        		"PRIMARY KEY (Ssn));");
        createNewTable("CREATE TABLE DEPARTMENT(" +
        		"Dname text NOT NULL," +
        		"Dnumber int NOT NULL," +
        		"Mgr_ssn char(9) NOT NULL," +
        		"Mgr_start_date date," +
        		"PRIMARY KEY (Dnumber)," +
        		"UNIQUE (Dname)," +
        		"FOREIGN KEY (Mgr_ssn) REFERENCES EMPLOYEE(Ssn) );");
        createNewTable("CREATE TABLE DEPT_LOCATIONS(" +
        		"Dnumber int NOT NULL," +
        		"Dlocation text NOT NULL," +
        		"PRIMARY KEY (Dnumber, Dlocation)," +
        		"FOREIGN KEY (Dnumber) REFERENCES DEPARTMENT(Dnumber) );");
        createNewTable("CREATE TABLE PROJECT(" +
        		"Pname text NOT NULL," +
        		"Pnumber int NOT NULL," +
        		"Plocation text," +
        		"Dnum int NOT NULL," +
        		"PRIMARY KEY (Pnumber)," +
        		"UNIQUE (Pname)," +
        		"FOREIGN KEY (Dnum) REFERENCES DEPARTMENT(Dnumber) );");
        createNewTable("CREATE TABLE WORKS_ON(" +
        		"Essn char(9) NOT NULL," +
        		"Pno int NOT NULL," +
        		"Hours decimal(3,1) NOT NULL," +
        		"PRIMARY KEY (Essn, Pno)," +
        		"FOREIGN KEY (Essn) REFERENCES EMPLOYEE(Ssn)," +
        		"FOREIGN KEY (Pno) REFERENCES PROJECT(Pnumber) );");
        createNewTable("CREATE TABLE DEPENDENT(" +
        		"Essn char(9) NOT NULL," +
        		"Dependent_name test NOT NULL," +
        		"Sex char," +
        		"Bdate date," +
        		"Relationship text," +
        		"PRIMARY KEY (Essn, Dependent_name)," +
        		"FOREIGN KEY (Essn) REFERENCES EMPLOYEE(Ssn) );");
        insert("EMPLOYEE", "'John',     'B', 'Smith', '123456789', '1962-01-09', '731 Fondren, Houston, TX', 'M', 37000, '333445555', 5");
        insert("EMPLOYEE", "'Franklin', 'T', 'Wong', '333445555', '1955-12-08', '638 Voss, Houston, TX', 'M', 40000, '888665555', 5");
        insert("EMPLOYEE", "'Alicia',   'J', 'Zeleya', '999887777', '1968-01-19', '3321 Castle, Spring, TX', 'F', 25000, '987654321', 4");
        insert("EMPLOYEE", "'Jennifer', 'S', 'Wallace', '987654321', '1941-06-20', '291 Berry, Bellaire, TX', 'F', 43000, '888665555', 4");
        insert("EMPLOYEE", "'Ramesh',   'K', 'Narayan', '666884444', '1962-09-18', '975 Fire Oak, Humble, TX', 'M', 38000, '333445555', 5");
        insert("EMPLOYEE", "'Joyce',    'A', 'Enlish', '453453453', '1972-07-31', '5631 Rice, Houston, TX', 'F', 25000, '333445555', 5");
        insert("EMPLOYEE", "'Ahmad',    'V', 'Jabbar', '987987987', '1969-03-29', '980 Dallas, Houston, TX', 'M', 25000, '987654321', 4");
        insert("EMPLOYEE", "'Jamex',    'E', 'Borg', '888665555', '1937-11-10', '450 Stone, Houston, TX', 'M', 55000, 'NULL', 1");
        insert("DEPARTMENT", "'Research', 5, '333445555', '1988-05-22'");
        insert("DEPARTMENT", "'Administration', 4, '987654321', '1995-01-01'");
        insert("DEPARTMENT", "'Headquarters', 1, '888665555', '1981-06-19'");
        insert("DEPT_LOCATIONS", "1, 'Houston'");
        insert("DEPT_LOCATIONS", "4, ''");
        insert("DEPT_LOCATIONS", "5, 'Bellaire'");
        insert("DEPT_LOCATIONS", "5, 'Sugerland'");
        insert("DEPT_LOCATIONS", "5, 'Houston'");
        insert("PROJECT", "'ProductX', 1, 'Bellaire', 5");
        insert("PROJECT", "'ProductY', 2, 'Sugerland', 5");
        insert("PROJECT", "'ProductZ', 3, 'Houston', 5");
        insert("PROJECT", "'Computerization', 10, 'Stafford', 4");
        insert("PROJECT", "'Reorganization', 20, 'Houston', 1");
        insert("PROJECT", "'Newbenefits', 30, 'Stafford', 4");
        insert("WORKS_ON", "'123456789', 1,  32.5");
        insert("WORKS_ON", "'123456789', 2,   7.5");
        insert("WORKS_ON", "'666884444', 3,  40.0");
        insert("WORKS_ON", "'453453453', 1,  20.0");
        insert("WORKS_ON", "'453453453', 2,  20.0");
        insert("WORKS_ON", "'333445555', 2,  10.0");
        insert("WORKS_ON", "'333445555', 3,  10.0");
        insert("WORKS_ON", "'333445555', 10, 10.0");
        insert("WORKS_ON", "'333445555', 20, 10.0");
        insert("WORKS_ON", "'999887777', 30, 30.0");
        insert("WORKS_ON", "'999887777', 10, 10.0");
        insert("WORKS_ON", "'987987987', 10, 35.0");
        insert("WORKS_ON", "'987987987', 30,  5.0");
        insert("WORKS_ON", "'987654321', 30, 20.0");
        insert("WORKS_ON", "'987654321', 20, 15.0");
        insert("WORKS_ON", "'888665555', 20, 20.5");
        insert("DEPENDENT", "'333445555', 'Alice', 'F', '1986-04-05', 'Daughter'");
        insert("DEPENDENT", "'333445555', 'Theodore', 'M', '1983-10-25', 'Son'");
        insert("DEPENDENT", "'333445555', 'Joy', 'F', '1958-05-03', 'Spouse'");
        insert("DEPENDENT", "'987654321', 'Abner', 'M', '1942-02-28', 'Spouse'");
        insert("DEPENDENT", "'123456789', 'Michael', 'M', '1988-01-04', 'Son'");
        insert("DEPENDENT", "'123456789', 'Alice', 'F', '1988-12-30', 'Daughter'");
        insert("DEPENDENT", "'123456789', 'Elizabeth', 'F', '1937-05-05', 'Spouse'");
    }
}