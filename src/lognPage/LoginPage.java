package lognPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;

import RegisterPage.RegisterPage;
import successfulLoginFrame.SuccessfulLogin;



public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    
        

    public LoginPage() {
        // Set up the frame
        setTitle("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Create labels and fields for each input
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Add labels and fields to the main panel
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        // Create the login and register buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");


        // JButton registerButton = new JButton("Register");

// Add an action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of RegisterPage
                RegisterPage registerPage = new RegisterPage();

                // Make the RegisterPage visible
                registerPage.setVisible(true);
            }
        });

            // Add the register button to the main panel
            mainPanel.add(registerButton);

        // Add action listeners to the buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    performLogin();
                } catch (NoSuchAlgorithmException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // 


       

        // Add the buttons to the main panel
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        // Add the main panel to the frame
        add(mainPanel);

        // Show the frame
        setVisible(true);
    }


    // public void performRegistration() throws SQLException {



    //     String fullName = fullNameField.getText();
    //     String email = emailField.getText();
    //     String username = usernameField.getText();
    //     String password = new String(passwordField.getPassword());

    //     Check if the fields are empty
    //     if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
    //         JOptionPane.showMessageDialog(this, "All fields must be filled out.");
    //         return;
    //     }

    //     // Validate the email format
    //     if (!email.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$")) {
    //         JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
    //         return;
    //     }

    //     // Check the length of the password
    //     if (password.length() < 8) {
    //         JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.");
    //         return;
    //     }


    //     // Hash the password
    //     String hashedPassword;
    //     try {
    //         MessageDigest md = MessageDigest.getInstance("SHA-512");
    //         md.update(password.getBytes());
    //         byte[] digest = md.digest();
    //         StringBuilder hexString = new StringBuilder();
    //         for (byte b : digest) {
    //             hexString.append(Integer.toHexString(0xFF & b));
    //         }
    //         hashedPassword = hexString.toString();
    //     } catch (NoSuchAlgorithmException e) {
    //         JOptionPane.showMessageDialog(this, "Error hashing the password.");
    //         return;
    //     }

    //     // Store the user data in the database
    //     String username1 = "root";
    //     String password1 = "root";
    //     Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", username1, password1);
    //     PreparedStatement statement = connection.prepareStatement("INSERT INTO register_user (full_name, email, username, password) VALUES (?, ?, ?, ?)");
    //     statement.setString(1, fullName);
    //     statement.setString(2, email);
    //     statement.setString(3, username);
    //     statement.setString(4, hashedPassword); // Use hashed password here
    //     int rowsAffected = statement.executeUpdate();

    //     if (rowsAffected > 0) {
    //         JOptionPane.showMessageDialog(this, "Registration successful!");
    //     } else {
    //         JOptionPane.showMessageDialog(this, "Registration failed.");
    //     }
    // }


    private void performLogin() throws NoSuchAlgorithmException, SQLException {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password fields cannot be empty.");
            return;
        }

        // Hash the password
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        String hashedPassword = hexString.toString();

        // Check the username and hashed password in the databaseString
        String username1 = "root";
        String password1 = "root";
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", username1, password1);
//            Statement statement
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    PreparedStatement statement = connection.prepareStatement("SELECT * FROM register_user WHERE username = ? AND password = ?");
    statement.setString(1, username);
    statement.setString(2, hashedPassword);

    ResultSet resultSet = statement.executeQuery(); // Declare and initialize the resultSet variable

    if (resultSet.next()) {
        JOptionPane.showMessageDialog(this, "Login successful!");


        SuccessfulLogin obj = new SuccessfulLogin();
        obj.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }
    
    } 



    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}