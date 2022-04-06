/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author fbarasa
 */
public class AddUser implements ActionListener {

    //Declare two String variable to hold the username and the password.
    String userNameInput, passwordInput, doB;
    int userRole;
    String usersList[] = {"User", "Admin"};

    //Create and initialize containers
    JFrame myFrame = new JFrame("User Registration");
    JPanel myPanel = new JPanel();

    //Initialize SWING components/controls
    JLabel titleLabel = new JLabel("ADD A NEW USER");
    JLabel usernameLabel = new JLabel("Username* ");
    JLabel passwordLabel = new JLabel("Password* ");
    JLabel dateOfBirthLabel = new JLabel("DoB: ");
    JLabel userRoleLabel = new JLabel("Role: ");
    JLabel userAddedLabel = new JLabel();
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JTextField dateOfBirthField = new JTextField();
    JComboBox userRoleComboBox = new JComboBox(usersList);
    JButton submitButton = new JButton("SUBMIT");
    JButton cancelButton = new JButton("CLEAR ENTRIES");
    JButton homeButton = new JButton("MAIN SCREEN");

    //The constructor to load the registration form whenever this class (AddUser) is instantiated
    AddUser() {
        registrationForm();
    }

    void registrationForm() {

        //Setting the outer JFrame properties
        myFrame.setBounds(400, 200, 600, 400);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set properties for the controls
        titleLabel.setBounds(50, 20, 100, 30);
        usernameLabel.setBounds(10, 60, 80, 30);
        passwordLabel.setBounds(10, 120, 80, 30);
        dateOfBirthLabel.setBounds(10, 180, 80, 30);
        userRoleLabel.setBounds(10, 240, 80, 30);
        userAddedLabel.setBounds(100, 270, 200, 20);
        usernameField.setBounds(100, 60, 200, 30);
        passwordField.setBounds(100, 120, 200, 30);
        dateOfBirthField.setBounds(100, 180, 200, 30);
        userRoleComboBox.setBounds(100, 240, 200, 30);
        submitButton.setBounds(50, 300, 80, 30);
        cancelButton.setBounds(150, 300, 130, 30);
        homeButton.setBounds(320, 300, 120, 30);

        //Add atomic components to the panel
        myPanel.add(titleLabel);
        myPanel.add(usernameLabel);
        myPanel.add(passwordLabel);
        myPanel.add(dateOfBirthLabel);
        myPanel.add(userRoleLabel);
        myPanel.add(userAddedLabel);
        myPanel.add(usernameField);
        myPanel.add(passwordField);
        myPanel.add(dateOfBirthField);
        myPanel.add(userRoleComboBox);
        myPanel.add(submitButton);
        myPanel.add(cancelButton);
        myPanel.add(homeButton);

        //Add evenyt listening capability to the buttons
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        homeButton.addActionListener(this);

        //Set JPanel properties
        myPanel.setLayout(null);
        myPanel.setBounds(5, 5, 550, 350);

        //Add panel to JFrame
        myFrame.add(myPanel);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            //What happens when the addUserButton button is clicked
            userNameInput = usernameField.getText();
            passwordInput = passwordField.getText();
            doB = dateOfBirthField.getText();
            userRole = userRoleComboBox.getSelectedIndex();
            //Check if username or password supplied
            if (userNameInput.equalsIgnoreCase("") || userNameInput.equalsIgnoreCase(" ") || userNameInput.equalsIgnoreCase("   ")) {
                JOptionPane.showMessageDialog(myFrame, "Username can not be blank");
            } else if (passwordInput.equalsIgnoreCase("") || passwordInput.equalsIgnoreCase(" ")) {
                JOptionPane.showMessageDialog(myFrame, "Password can not be blank");
            } else {
                try {
                    //Instantiate DbConnection class to use the connection
                    DbConnection stayconnected = new DbConnection();
                    stayconnected.getConnection();
                    //Create the statement object for executing queries
                    Statement stmt = stayconnected.con.createStatement();
                    //Execute the statement
                    stmt.executeUpdate("INSERT INTO logins VALUES('" + userNameInput + "','" + passwordInput + "','" + doB + "','" + userRole + "')");
                    System.out.println("User details added successfully \n");
                    userAddedLabel.setText("New user added successfully");
                    userAddedLabel.setForeground(Color.GREEN);
                    clearFormData();
                    //Close the connection
                    stayconnected.con.close();
                } catch (SQLException sqe) {
                    System.out.println("Error! See below details \n");
                    JOptionPane.showMessageDialog(myFrame, sqe);
                    System.out.println(sqe);
                }
            }
        } else if (e.getSource() == cancelButton) {
            //What happens when the add view user button is clicked
            clearFormData();
            userAddedLabel.setText("");
        } else if (e.getSource() == homeButton) {
            //What happens when the home button is clicked
            //Move to a system admin screen
            myFrame.dispose();
            AdminWindow adminMainForm = new AdminWindow();
        } else {
            AdminWindow adminMainForm = new AdminWindow();
        }
    }

    void clearFormData() {
        usernameField.setText("");
        passwordField.setText("");
        dateOfBirthField.setText("");
    }
}
