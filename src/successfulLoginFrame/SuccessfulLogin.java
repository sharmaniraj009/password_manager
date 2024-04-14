package successfulLoginFrame;

import lognPage.LoginPage;
import RegisterPage.RegisterPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SuccessfulLogin extends JFrame {

//    LoginPage loginPage = new LoginPage();
//    try {
//        loginPage.retrieveUserInfo("ab");
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }

    // RegisterPage registerPage = new RegisterPage();
    // int mid = registerPage.id;
    
    protected static final String RegisterPage = null;
    private JTextField website;
    private JTextField username;
    private JPasswordField password;
    
    public SuccessfulLogin() {
//        System.out.println("myId: " + myId);
//        System.out.println("myId: " + mid);
        setTitle("Successful Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        

        JLabel websiteLabel = new JLabel("Website:");
        website = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        username = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        password = new JPasswordField();
        
        mainPanel.add(websiteLabel);
        mainPanel.add(website);
        mainPanel.add(usernameLabel);
        mainPanel.add(username);
        mainPanel.add(passwordLabel);
        mainPanel.add(password);
        
        JButton saveButton = new JButton("Save");
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String websiteText = website.getText();
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                
                System.out.println("Website: " + websiteText);
                System.out.println("Username: " + usernameText);
                System.out.println("Password: " + passwordText);

            }
        });


        // Create the show credentials button
        JButton showCredentialsButton = new JButton("Show Credentials");
        
        // Add an action listener to the show credentials button
        showCredentialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Create a new JFrame
            JFrame credentialsFrame = new JFrame("Credentials");
            credentialsFrame.setSize(300, 200);
            credentialsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
            // Create labels to display the credentials
            JLabel websiteLabel = new JLabel("Website: " + website.getText());
            JLabel usernameLabel = new JLabel("Username: " + username.getText());
            JLabel passwordLabel = new JLabel("Password: " + new String(password.getPassword()));
        
            // Create a panel and add the labels to it
            JPanel credentialsPanel = new JPanel();
            credentialsPanel.setLayout(new GridLayout(3, 1));
            credentialsPanel.add(websiteLabel);
            credentialsPanel.add(usernameLabel);
            credentialsPanel.add(passwordLabel);
        
            // Add the panel to the frame
            credentialsFrame.add(credentialsPanel);
        
            // Make the credentialsFrame visible
            credentialsFrame.setVisible(true);
            }
        });
        
        // Add the show credentials button to the main panel
        mainPanel.add(showCredentialsButton);
        
        
        mainPanel.add(saveButton);

        add(mainPanel);

        setVisible(true);
    }

    
        public void performRegistration() throws SQLException {
            String websiteText = website.getText();
            String usernameText = username.getText();
            String passwordText = new String(password.getPassword());

            String username1 = "root";
            String password1 = "root";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", username1, password1);
            String jsonData = String.format("{\"website\": \"%s\", \"username\": \"%s\", \"password\": \"%s\"}", websiteText, usernameText, passwordText);
            PreparedStatement statement = connection.prepareStatement("UPDATE register_users SET data = ? WHERE data->'$.website' = ?");
            statement.setString(1, jsonData);
            statement.setString(2, websiteText);
            statement.executeUpdate();
        }

     
        public static void main(String[] args) {
            SuccessfulLogin loginFrame = new SuccessfulLogin();
            loginFrame.setVisible(true);
        }
    }