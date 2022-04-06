/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;
/**
 *
 * @author fbarasa
 */
import java.sql.*;

public class DbConnection {
      
    Connection con;
    Connection getConnection() {
        //Use try {...}catch(Exception e){} to test for any DB connection errors
        try {
            //STEP 1: Load the mysql driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 2: Create the connection
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/simpleLogin", "root", "");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection failed. See error below.");
            System.out.println(e);
        }
        return con;
    }
}
