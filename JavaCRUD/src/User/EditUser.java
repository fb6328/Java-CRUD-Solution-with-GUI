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
public class EditUser implements ActionListener {

    //Declare two String variable to hold the username and the password.
    String userNameInput, passwordInput, doB;
    //An integer variable for the user role for inserting into DB
    int userRole;
    //An array list for various user roles to be displayed in JCombobox (drop-down menu)
    String usersList[] = {"User", "Admin"};

    //Create and initialize containers
    JFrame myFrame = new JFrame("Update User Details");
    JPanel myPanel = new JPanel();

    //Initialize SWING form components/controls
    JLabel titleLabel = new JLabel();
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel usernameLabel2 = new JLabel();
    JLabel passwordLabel = new JLabel("Password* ");
    JLabel dateOfBirthLabel = new JLabel("DoB: ");
    JLabel userRoleLabel = new JLabel("Role: ");
    JLabel userAddedLabel = new JLabel();
    JPasswordField passwordField = new JPasswordField();
    JTextField dateOfBirthField = new JTextField();
    JComboBox userRoleComboBox = new JComboBox(usersList);
    JButton submitButton = new JButton("SUBMIT");
    JButton cancelButton = new JButton("CLEAR ENTRIES");
    JButton homeButton = new JButton("VIEW USERS");

    //This method will load the form and do the editing for us. It has been called from ViewUser.java
    public void editingForm(String userN, String dateOfBirth, int role) {
        userNameInput = userN;
        userRole = role;
        doB = dateOfBirth;
        try {
    //Instantiate DbConnection class to use the connection
            DbConnection stayconnected = new DbConnection();
            stayconnected.getConnection();
            //Create the statement object for executing queries
            Statement stmt = stayconnected.con.createStatement();

            //Execute the statement
            ResultSet res = stmt.executeQuery("SELECT * FROM logins WHERE userName = '"+userNameInput+"'");
            //This while loop will ensure that all database rows are collecteed
            res.next();
            passwordInput = res.getString("password");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! " + e);
        }
        //Setting the outer JFrame properties
        myFrame.setBounds(400, 200, 600, 400);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set properties for the controls
        titleLabel.setBounds(50, 20, 300, 30);
        titleLabel.setText("UPDATE DETAILS FOR USER "+userNameInput);
        usernameLabel.setBounds(10, 60, 80, 30);
        passwordLabel.setBounds(10, 120, 80, 30);
        dateOfBirthLabel.setBounds(10, 180, 80, 30);
        userRoleLabel.setBounds(10, 240, 80, 30);
        userAddedLabel.setBounds(100, 270, 200, 20);
        usernameLabel2.setBounds(100, 60, 200, 30);
        usernameLabel2.setText(userNameInput);
        usernameLabel2.setToolTipText("This can not be edited");
        passwordField.setBounds(100, 120, 200, 30);
        passwordField.setText(passwordInput);
        dateOfBirthField.setBounds(100, 180, 200, 30);
        dateOfBirthField.setText(doB);
        userRoleComboBox.setBounds(100, 240, 200, 30);
        userRoleComboBox.setSelectedItem(userRole);
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
        myPanel.add(usernameLabel2);
        myPanel.add(passwordField);
        myPanel.add(dateOfBirthField);
        myPanel.add(userRoleComboBox);
        myPanel.add(submitButton);
        myPanel.add(cancelButton);
        myPanel.add(homeButton);

        //Add event listening capability to the buttons
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

    //For handling user actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            //What happens when the submitButton button is clicked
            userNameInput = usernameLabel2.getText();
            passwordInput = passwordField.getText();
            doB = dateOfBirthField.getText();
            userRole = userRoleComboBox.getSelectedIndex();
            //Check if username or password supplied
            if (userNameInput.equalsIgnoreCase("") || userNameInput.equalsIgnoreCase(" ") || userNameInput.equalsIgnoreCase("   ")) {
                JOptionPane.showMessageDialog(myFrame, "Please select user to edit");
            } else if (passwordInput.equalsIgnoreCase("") || passwordInput.equalsIgnoreCase(" ")) {
                JOptionPane.showMessageDialog(myFrame, "Please select user to edit");
            } else {
                try {
                    //Instantiate DbConnection class to use the connection
                    DbConnection stayconnected = new DbConnection();
                    stayconnected.getConnection();
                    //Create the statement object for executing queries
                    Statement stmt = stayconnected.con.createStatement();
                    //Execute the statement
                    stmt.executeUpdate("UPDATE logins SET password ='" + passwordInput + "',doB ='" + doB + "',roleId ='" + userRole + "' WHERE userName ='"+userNameInput+"'");
                    System.out.println("User details updated successfully \n");
                    userAddedLabel.setText("User details updated successfully");
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
        titleLabel.setText("UPDATE USER DETAILS");
        usernameLabel2.setText("");
        passwordField.setText("");
        dateOfBirthField.setText("");
    }
}
