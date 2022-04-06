/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author fbarasa
 */
public class HomeWindow implements ActionListener {

    JFrame myHomeFrame = new JFrame("LUNCH MANAGEMENT SYSTEM");
    JPanel myPanel = new JPanel();

    //Initialize SWING components/controls
    JLabel titleLabel = new JLabel("LUNCH MANAGEMENT SYSTEM HOME PAGE");
    JButton loginButton = new JButton("CLICK HERE TO LOGIN ");
    JButton closeButton = new JButton("CLOSE");

    Image icon = Toolkit.getDefaultToolkit().getImage("D:\\login.jpg");
    HomeWindow() {
        loadHomeForm();
    }

    //Set size and location

    public void loadHomeForm() {
        // Prepare the main home frame
        myHomeFrame.setBounds(160, 10, 900, 600);
        myHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myHomeFrame.setIconImage(icon);
         
        myPanel.setBounds(1, 1, 890, 590);
        myPanel.setLayout(null);
        myPanel.setBackground(Color.GRAY);
        
        titleLabel.setBounds(50, 5, 500, 50);
        titleLabel.setFont(new Font("Serif",Font.PLAIN,22));
        loginButton.setBounds(50, 100, 200, 80);
        closeButton.setBounds(400, 100, 200, 80);
        myPanel.add(titleLabel);
        myPanel.add(loginButton);
        myPanel.add(closeButton);
        loginButton.addActionListener(this);
        closeButton.addActionListener(this);
        myHomeFrame.add(myPanel);
        
        //show the home screen
        myHomeFrame.setResizable(false);
        myHomeFrame.setVisible(true);
    }

    @Override
    //Method invoked when a user click a button
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loginButton) {
//Call the constructor in the Login class
            myHomeFrame.dispose();
            Login login = new Login();
        } else if (event.getSource() == closeButton) {
            myHomeFrame.dispose();
        } else {
        }
    }
}
