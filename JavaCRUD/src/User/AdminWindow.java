/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author fbarasa
 */
public class AdminWindow implements ActionListener {

    //Create and initialize containers
    JFrame myAdminFrame = new JFrame("ADMIN PANEL | Logged in as Admin");
    JPanel myAdminPanel = new JPanel();

    //Initialize SWING components/controls
    JButton addUserButton = new JButton("ADD NEW USER");
    JButton viewUserUsersButton = new JButton("VIEW USERS");
    JButton logoutButton = new JButton("LOGOUT");

    AdminWindow() {
        adminForm();
    }

    void adminForm() {

        //Setting the outer JFrame properties
        myAdminFrame.setBounds(400, 200, 600, 400);
        myAdminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set properties for the form controls
        addUserButton.setBounds(10, 50, 150, 80);
        viewUserUsersButton.setBounds(300, 50, 150, 80);
        logoutButton.setBounds(150, 200, 150, 80);

        //Add components to the panel
        myAdminPanel.add(addUserButton);
        myAdminPanel.add(viewUserUsersButton);
        myAdminPanel.add(logoutButton);

        /* Add user action listening capability to the buttons so that
         when a user clicks on the button, an action is performed.
         The actionPerformed(ActionEvent e) */
        addUserButton.addActionListener(this);
        viewUserUsersButton.addActionListener(this);
        logoutButton.addActionListener(this);

        //Set JPanel properties
        myAdminPanel.setLayout(null);
        myAdminPanel.setBounds(5, 5, 550, 350);

        //Add panel to JFrame
        myAdminFrame.add(myAdminPanel);
        myAdminFrame.setResizable(false);
        myAdminFrame.setVisible(true);
    }

    @Override
    //Method invoked when a user click a button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUserButton) {
            //What happens when the addUserButton button is clicked
            AddUser userReg = new AddUser();
            myAdminFrame.dispose();
        } else if (e.getSource() == viewUserUsersButton) {
            //What happens when the add view user button is clicked
            ViewUsers usersList = new ViewUsers();
            myAdminFrame.dispose();
        } else if (e.getSource() == logoutButton) {
            //What happens when the add logout button is clicked
            Login goToLogin = new Login();
            myAdminFrame.dispose();
        } else {

        }
    }

}
