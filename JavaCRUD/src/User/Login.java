/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author fbarasa
 */
public class Login implements ActionListener {

    //Declare two String variable to hold the username and the password.
    String userNameInput, passwordInput;

    //Create and initialize containers
    JFrame myFrame = new JFrame("LUNCH MANAGEMENT SYSTEM");
    JPanel myPanel = new JPanel();

    //Initialize SWING components/controls
    JLabel titleLabel = new JLabel("USER LOGIN");
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton cancelButton = new JButton("CANCEL");

    Login() {
        loginForm();
    }

    public void setUserDetails(String userName, String password) {
        this.userNameInput = userName;
        this.passwordInput = password;
    }

    void loginForm() {

        //Setting the outer JFrame properties
        myFrame.setBounds(400, 200, 600, 400);
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set properties for the controls
        titleLabel.setBounds(200, 10, 100, 30);
        usernameLabel.setBounds(10, 60, 80, 30);
        passwordLabel.setBounds(10, 120, 80, 30);
        usernameField.setBounds(100, 60, 200, 30);
        passwordField.setBounds(100, 120, 200, 30);
        loginButton.setBounds(100, 200, 80, 30);
        cancelButton.setBounds(215, 200, 80, 30);

        //Add atomic components to the panel
        myPanel.add(titleLabel);
        myPanel.add(usernameLabel);
        myPanel.add(passwordLabel);
        myPanel.add(usernameField);
        myPanel.add(passwordField);
        myPanel.add(loginButton);
        myPanel.add(cancelButton);

        //Add evenyt listening capability to the buttons
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        //Set JPanel properties
        myPanel.setLayout(null);
        myPanel.setBounds(5, 5, 550, 350);

        //Add panel to JFrame
        myFrame.add(myPanel);
        myFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            userNameInput = usernameField.getText();
            passwordInput = passwordField.getText();
            //Check if username or password supplied
            if (userNameInput.equalsIgnoreCase("") || userNameInput.equalsIgnoreCase(" ") || userNameInput.equalsIgnoreCase("   ")) {
                JOptionPane.showMessageDialog(myFrame, "Username can not be blank");
            } else if (passwordInput.equalsIgnoreCase("") || passwordInput.equalsIgnoreCase(" ")) {
                JOptionPane.showMessageDialog(myFrame, "Password can not be blank");
            } else {
                //Do the login
                try {
                    //Instantiate DbConnection class to use the connection
                    DbConnection stayconnected = new DbConnection();
                    stayconnected.getConnection();
                    //Create the statement object for executing queries
                    Statement stmt = stayconnected.con.createStatement();
                    //Execute the statement
                    ResultSet rs = stmt.executeQuery("SELECT * FROM logins WHERE userName ='" + userNameInput + "' AND password ='" + passwordInput + "'");
                    //Handle the results in the Resultset here if needed
                    if (rs.next()) {

                        //Move to a system admin screen if login is successful
                        AdminWindow adminMainForm = new AdminWindow();

                        //Close login form
                        myFrame.dispose();

                        //Print something on the console. This is not necessary
                        System.out.println("Login successful");
                        //beginAfterLogin();
                    } else {
                        JOptionPane.showMessageDialog(myFrame, "Incorrect username or password");
                        System.out.println("Incorrect username or password \n");
                        //main(null);
                    }
                    //Close the connection
                    stayconnected.con.close();
                } catch (SQLException error) {
                    System.out.println("Error! See below details \n");
                    System.out.println(e);
                }
        }
        } else if (e.getSource() == cancelButton) {
            myFrame.dispose();
        } else {

        }
    }
}
