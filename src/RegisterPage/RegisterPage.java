package RegisterPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("unused")
public class RegisterPage extends JFrame {
    protected static final String RegisterPage = null;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterPage() {
        // Set up the frame
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        // Create labels and fields for each input
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Add labels and fields to the main panel
        mainPanel.add(fullNameLabel);
        mainPanel.add(fullNameField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        // Create the register button
        JButton registerButton = new JButton("Register");
        // Create the register button
        
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    performRegistration();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Create the back button
        JButton backButton = new JButton("Back");

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose the current window
                dispose();
            }
        });

        mainPanel.add(registerButton);
        mainPanel.add(backButton);


        // Add the main panel to the frame
        add(mainPanel);

        // Show the frame
        setVisible(true);


    }

    public void performRegistration() throws SQLException {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the fields are empty
        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return;
        }

        // Validate the email format
        if (!email.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        // Check the length of the password
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.");
            return;
        }


        // Hash the password
        String hashedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            hashedPassword = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Error hashing the password.");
            return;
        }

        // Store the user data in the database
        String username1 = "root";
        String password1 = "root";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", username1, password1);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO register_user (full_name, email, username, password) VALUES (?, ?, ?, ?)");
        statement.setString(1, fullName);
        statement.setString(2, email);
        statement.setString(3, username);
        statement.setString(4, hashedPassword); // Use hashed password here
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.");
        }
    }
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new RegisterPage());
}
}

