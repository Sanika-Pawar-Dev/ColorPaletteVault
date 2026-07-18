import javax.swing.*;
import java.awt.*;

public class ColorVaultApp extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    private String currentSessionUser = "";
    private String userProfileRole = "General Purpose";

    public ColorVaultApp() {
        setTitle("Colorex:Color Palette Vault Studio");
        setSize(1020, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainContainer.add(new LoginView(this), "PAGE_LOGIN");
        mainContainer.add(new RegisterView(this), "PAGE_REGISTER");

        add(mainContainer);
        cardLayout.show(mainContainer, "PAGE_LOGIN");
    }

    public void setSessionContext(String user, String role) {
        this.currentSessionUser = user;
        this.userProfileRole = role;
    }

    public String getUser() {
        return this.currentSessionUser;
    }

    public String getRole() {
        return this.userProfileRole;
    }

    public void showPage(String pageName) {
        cardLayout.show(mainContainer, pageName);
    }

    public void initDashboardView() {
        mainContainer.add(new DashboardView(this), "PAGE_DASHBOARD");
        cardLayout.show(mainContainer, "PAGE_DASHBOARD");
    }

    public String toWebHexCode(Color c) {
        if (c.equals(Color.LIGHT_GRAY)) return "BLANK";
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    public boolean validate(Color c) {
        if (userProfileRole.equalsIgnoreCase("UI/UX Architect")) return UxArchitectRole.validate(c);
        if (userProfileRole.equalsIgnoreCase("Web Master")) return WebMasterRole.validate(c);
        if (userProfileRole.equalsIgnoreCase("Digital Modeler")) return DigitalModelerRole.validate(c);
        return true;
    }

    public void runFeedback(Color c, JLabel f, String s) {
        if (userProfileRole.equalsIgnoreCase("UI/UX Architect")) {
            UxArchitectRole.run(c, f, s, toWebHexCode(c));
        } else if (userProfileRole.equalsIgnoreCase("Web Master")) {
            WebMasterRole.run(c, f, s);
        } else if (userProfileRole.equalsIgnoreCase("Digital Modeler")) {
            DigitalModelerRole.run(c, f);
        } else {
            f.setText("Core Studio: Transformed shade code register handle is " + toWebHexCode(c));
            f.setForeground(Color.WHITE);
        }
    }

    public static void main(String[] args) {
        // 1. Place the evaluator notice right here at the start of execution
        System.out.println("====================================================");
        System.out.println("🔐 SECURITY ENGINE SIMULATION ACTIVE");
        System.out.println("👉 EVALUATOR NOTE: Dynamic registration OTPs will print");
        System.out.println("   HERE in this console to maintain UI security protocols.");
        System.out.println("====================================================");

        // 2. Your existing application launch code stays right below it:
        SwingUtilities.invokeLater(() -> new ColorVaultApp().setVisible(true));
    }
}
