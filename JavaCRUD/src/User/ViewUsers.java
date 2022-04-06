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
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewUsers implements ActionListener {
                String userName, doB;
                int role;
    //Create JFrame
    JFrame usersListFrame = new JFrame("USERS LIST | Logged in as Admin");
    JPanel panel = new JPanel();

    //Initialize SWING components/controls
    JButton editButton = new JButton("EDIT SELECTED USER");
    JButton deleteButton = new JButton("DELETE SELECTED");
    JButton addButton = new JButton("ADD NEW USER");
    JLabel actionsuccessful = new JLabel();
    DefaultTableModel model;
    JTable table;

    //The constructor
    ViewUsers() {
        doViewUsers();
    }

    //Method that gets the data and prepare the JTable
    final void doViewUsers() {
        try {
            //Instantiate DbConnection class to use the connection
            DbConnection stayconnected = new DbConnection();
            stayconnected.getConnection();
            //Create the statement object for executing queries
            Statement stmt = stayconnected.con.createStatement();
            //To help us determine the number of rows on the JTable
            ResultSet res2 = stmt.executeQuery("SELECT COUNT(*) AS recordCount FROM logins");
            //Create two arrays to store data that will be retrieved from DB and displayed in JTable
            res2.next();
            int count = res2.getInt("recordCount");
            String columns[] = {"User Name", "DoB", "Role"};
            String data[][] = new String[count][3];
            
                        //Execute the statement
            ResultSet res = stmt.executeQuery("SELECT * FROM logins");
            //This while loop will ensure that all database rows are collecteed
            int i = 0;
            while (res.next()) {
                String name = res.getString("userName");
                String dob = res.getString("doB");
                String role = res.getString("roleId");
                data[i][0] = name;
                data[i][1] = dob;
                data[i][2] = role;
                i++;
            }

            //Create a datamodel for storing the data received from the database
            model = new DefaultTableModel(data, columns);

            //Add the datamodel to JTable
            table = new JTable(model);

            //Create a scroll bar to hold the table. Helps when there is a lot of data in the database
            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(5, 5, 400, 200);
            //Set the table properties of ShowGrid and ShowVerticalLines
            table.setShowGrid(true);
            table.setShowVerticalLines(true);
            table.setBounds(10, 50, 400, 200);

            //Set JPanel properties
            panel.setBounds(5, 5, 580, 380);
            panel.setLayout(null);

            //Set button properties
            editButton.setBounds(10, 250, 160, 50);
            deleteButton.setBounds(200, 250, 150, 50);
            addButton.setBounds(370, 250, 130, 50);
            actionsuccessful.setBounds(10, 310, 200, 50);

            //Add actionListener to the buttons
            editButton.addActionListener(this);
            deleteButton.addActionListener(this);
            addButton.addActionListener(this);

            //Add scrollpane to the panel
            panel.add(pane);
            panel.add(editButton);
            panel.add(deleteButton);
            panel.add(addButton);
            panel.add(actionsuccessful);

            usersListFrame.add(panel);
            usersListFrame.setBounds(400, 200, 600, 400);
            usersListFrame.setResizable(false);
            usersListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Show the frame
            usersListFrame.setVisible(true);

            //Add action listeners
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    //Method invoked when a user click a button
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == editButton) {
            if (table.getSelectedRow() >= 0) {

                //The next three lines below have been added to help us get the username from the selected row
                int column = 0;
                int column2 = 1;
                int column3 = 2;
                int row = table.getSelectedRow();
                try {
                    userName = model.getValueAt(row, column).toString();
                    doB = model.getValueAt(row, column2).toString();
                    role = Integer.parseInt(model.getValueAt(row, column3).toString());
                    /* Now that we have a username assigned to the String userName above.
                     We can go ahead and use it to delete a record from the database. But first...
                     */
                    //Jump to the EditUser.java and carry with you the userName, doB and role extracted from the JTable
                    EditUser edituser = new EditUser();
                    edituser.editingForm(userName, doB, role);
                } catch (Exception e) {
                    System.out.println("Details are ="+userName+","+doB+","+role);
                    JOptionPane.showMessageDialog(null, "You must select a user to edit");
                    System.out.println("Error! See below details \n");
                    System.out.println(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must select a user to edit");
            }
        } else if (event.getSource() == deleteButton) {

            if (table.getSelectedRow() >= 0) {
                String userName = "";
                //The next three lines below have been added to help get the username from the selected row
                int column = 0;
                int row = table.getSelectedRow();
                try {
                    userName = model.getValueAt(row, column).toString();
                    /* Now that we have a username assigned to the String userName above.
                     We can go ahead and delete it from the database. But first
                     */
                    //Instantiate DbConnection class to use the connection
                    DbConnection stayconnected = new DbConnection();
                    stayconnected.getConnection();
                    //Create the statement object for executing queries
                    Statement stmt = stayconnected.con.createStatement();

                    //Confirm the delete
                    JOptionPane.showMessageDialog(null, "Delete user " + userName + "? This action can not be reversed");
                    //Execute the delete statement and assigned number of affected rows to numRows
                    int numRows = stmt.executeUpdate("DELETE FROM logins WHERE userName='" + userName + "'");
                    if (numRows > 0) {
                        //Close the connection
                        stayconnected.con.close();
                        model.removeRow(row);

                        //Set the label text
                        actionsuccessful.setText("User " + userName + " deleted successfully");
                        actionsuccessful.setForeground(Color.green);
                        System.out.println("User " + userName + " deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Delete failed");
                        System.out.println("User " + userName + " was not found. Delete failed");
                    }
                    //doViewUsers();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "You must select a user to delete");
                    System.out.println("Error! See below details \n");
                    System.out.println(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must select a user to delete");
            }

        } else if(event.getSource() == addButton) {
            new AddUser();
            usersListFrame.dispose();
        } else {
        }
    }
}
