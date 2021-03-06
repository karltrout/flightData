/**
 * Created by karlt on 11/15/17.
 */

import java.sql.*;

public class JDBCExample {
  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/";

  //  Database credentials
  static final String USER = "root";
  static final String PASS = "";

  public static void main(String[] args) {

    try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)){

      System.out.println("Connecting to database...");

      //STEP 4: Execute a query
      System.out.println("Creating database...");
      Statement stmt = conn.createStatement();

      String sql = "CREATE DATABASE if NOT EXISTS tfms";
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");

      System.out.println("Creating user...");
      stmt = conn.createStatement();
      //stmt.executeUpdate("CREATE USER 'tgf'@'localhost'");
      //stmt.executeUpdate("UPDATE mysql.user SET Password=PASSWORD('tgfUser') WHERE User='tgf' AND Host='locahost'");
      stmt.execute("GRANT ALL ON tfms.* TO 'tgf'@'localhost' IDENTIFIED BY 'tgfUser'");
      stmt.executeUpdate("FLUSH PRIVILEGES ;");

    }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
    }
    System.out.println("Goodbye!");
  }//end main
}//end JDBCExample