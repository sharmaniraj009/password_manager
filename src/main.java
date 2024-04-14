import javax.swing.*;

import lognPage.LoginPage;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}