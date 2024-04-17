package successfulLoginFrame;

import lognPage.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SuccessfulLogin extends JFrame {

    

    
    protected static final String RegisterPage = null;
    private JTextField loginUsername;
    private JTextField website;
    private JTextField username;
    private JPasswordField password;
    
    
    /**
     * 
     */
    public SuccessfulLogin() {
        
        LoginPage loginPage = new LoginPage();
        // String usernameinput = loginPage.usernameField.getText();
        //       System.out.println("myId: " + Id);
        setTitle("Successful Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));
        
        
        JLabel loginUsernameLabel = new JLabel("Login username :");
        loginUsername = new JTextField();
        JLabel websiteLabel = new JLabel("Website:");
        website = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        username = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        password = new JPasswordField();
        
        mainPanel.add(loginUsernameLabel);
        mainPanel.add(loginUsername);
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
                String loginUsernameField = loginUsername.getText();
                try {
                    int id = loginPage.retrieveIdFromUsername(loginUsernameField);
                    // int id = loginPage.retrieveIdFromUsername(usernameinput);
                    System.out.println("id : " + id);
                    performRegistration(id);
                    retrieveCredentials(id);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                String websiteText = website.getText();
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());

                System.out.println("loginUsername: " + loginUsernameField);
                System.out.println("Website: " + websiteText);
                System.out.println("Username: " + usernameText);
                System.out.println("Password: " + passwordText);
                // try {
                //     int id = retrieveIdFromUsername(loginUsernameField);
                //     performRegistration();
                //     System.out.println("Registration successful! and the id is : " + id);
                // } catch (SQLException ex) {
                //     System.out.println("Error performing registration: " + ex.getMessage());
                // }
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

    
        

        /**
         * Performs the registration process by updating the user's data in the database.
         * This method retrieves the website, username, and password from the input fields,
         * establishes a connection to the database, and updates the user's data with the
         * provided information.
         *
         * @throws SQLException if a database access error occurs
         */
        public void performRegistration(int id) throws SQLException {
            String websiteText = website.getText();
            String usernameText = username.getText();
            String passwordText = new String(password.getPassword());

            String username1 = "root";
            String password1 = "root";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", username1, password1);
            String jsonData = String.format("{\"website\": \"%s\", \"username\": \"%s\", \"password\": \"%s\"}", websiteText, usernameText, passwordText);
            PreparedStatement statement = connection.prepareStatement("UPDATE register_user SET credentials = ? WHERE id = ?");
            statement.setString(1, jsonData);
            statement.setLong(2, id);
            statement.executeUpdate();
        }

        /**
         * Retrieves the credential row from the SQL table based on the provided ID.
         * The credential row is in JSON format and the method displays the results.
         *
         * @param id the ID of the credential row to retrieve
         * @throws SQLException if a database access error occurs
         */
        public void retrieveCredentials(int id) throws SQLException {
            String username1 = "root";
            String password1 = "root";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_database", username1, password1);
            PreparedStatement statement = connection.prepareStatement("SELECT credentials FROM register_user WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String credentialsJson = resultSet.getString("credentials");
                System.out.println("Credentials: " + credentialsJson);
            } else {
                System.out.println("No credentials found for ID: " + id);
            }
        }

     
        public static void main(String[] args) {
            SuccessfulLogin loginFrame = new SuccessfulLogin();
            loginFrame.setVisible(true);
        }
    }