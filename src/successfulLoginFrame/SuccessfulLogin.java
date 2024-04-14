package successfulLoginFrame;

// import java.awt.LayoutManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuccessfulLogin extends JFrame {
     protected static final String RegisterPage = null;
    private JTextField website;
    private JTextField username;
    private JPasswordField password;

    public SuccessfulLogin() {
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
        // Create a JTextArea to display the passwords
        JTextArea passwordArea = new JTextArea();
        passwordArea.setEditable(false); // Make the JTextArea non-editable

        // Add the JTextArea to the main panel
        
        // saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String websiteText = website.getText();
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                
                System.out.println("Website: " + websiteText);
                System.out.println("Username: " + usernameText);
                System.out.println("Password: " + passwordText);
                
                passwordArea.append("Website: " + websiteText + "\n");
                passwordArea.append("Username: " + usernameText + "\n");
                passwordArea.append("Password: " + passwordText + "\n\n");
                

            }
        });
        
        
        mainPanel.add(passwordArea);
        mainPanel.add(saveButton);

        add(mainPanel);

        setVisible(true);
    }


     
        public static void main(String[] args) {
            SuccessfulLogin loginFrame = new SuccessfulLogin();
            loginFrame.setVisible(true);
        }
    }