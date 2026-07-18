import javax.swing.*;
import java.awt.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class LoginView extends JPanel {
    public LoginView(ColorVaultApp app) {
        setLayout(new GridBagLayout()); setBackground(new Color(24, 26, 36));
        JPanel card = new JPanel(new GridBagLayout()); card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1), BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        GridBagConstraints g = new GridBagConstraints(); g.insets = new Insets(10,10,10,10); g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("SECURE STUDIO GATEWAY", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblTitle.setForeground(new Color(41, 128, 185));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; card.add(lblTitle, g);

        JLabel lblSub = new JLabel("Sign in to your asset portfolios", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblSub.setForeground(Color.GRAY);
        g.gridy = 1; card.add(lblSub, g);

        g.gridwidth = 1; g.gridy = 2; g.gridx = 0;
        card.add(new JLabel("Username Handle:"), g);
        JTextField txtLoginUser = new JTextField(15); g.gridx = 1; card.add(txtLoginUser, g);

        g.gridx = 0; g.gridy = 3;
        card.add(new JLabel("Account Password:"), g);
        JPasswordField txtLoginPass = new JPasswordField(15); g.gridx = 1; card.add(txtLoginPass, g);

        JButton btnLogin = new JButton("Authorize & Sign In");
        btnLogin.setBackground(new Color(41, 128, 185)); btnLogin.setForeground(Color.WHITE); g.gridx = 0; g.gridy = 4; card.add(btnLogin, g);

        JButton btnGoToReg = new JButton("Create Account");
        btnGoToReg.setBackground(new Color(52, 73, 94)); btnGoToReg.setForeground(Color.WHITE); g.gridx = 1; card.add(btnGoToReg, g);

        JButton btnForgot = new JButton("Forgot Password?");
        btnForgot.setBorderPainted(false); btnForgot.setContentAreaFilled(false); btnForgot.setForeground(new Color(127, 140, 141));
        g.gridy = 5; g.gridx = 0; g.gridwidth = 2; card.add(btnForgot, g);

        btnGoToReg.addActionListener(e -> { txtLoginUser.setText(""); txtLoginPass.setText(""); app.showPage("PAGE_REGISTER"); });

        btnLogin.addActionListener(e -> {
            String u = txtLoginUser.getText().trim().toLowerCase();
            String pStr = new String(txtLoginPass.getPassword());
            if (u.isEmpty() || pStr.isEmpty()) { JOptionPane.showMessageDialog(app, "⚠️ Identity Error: Entry fields cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE); return; }

            Document userDoc = DatabaseConfig.getUsersCollection().find(new Document("username", u)).first();
            if (userDoc != null && userDoc.getString("password").equals(pStr)) {
                String loginOtp = SecurityEngine.generateSystemSecureOtp();
                System.out.println("[2FA OVERLAY MONITOR] System Login OTP Generated -> " + loginOtp);
                if (!SecurityEngine.runOtpVerificationChallenge(app, loginOtp)) return;

                app.setSessionContext(u, userDoc.getString("role"));
                txtLoginUser.setText(""); txtLoginPass.setText("");
                app.initDashboardView();
            } else { JOptionPane.showMessageDialog(app, "❌ Error: Invalid credentials configuration matching records.", "Access Denied", JOptionPane.ERROR_MESSAGE); }
        });

        btnForgot.addActionListener(e -> {
            String targetUser = JOptionPane.showInputDialog(app, "Verify your system username handle first:");
            if (targetUser == null || targetUser.trim().isEmpty()) return;

            MongoCollection<Document> coll = DatabaseConfig.getUsersCollection();
            Document doc = coll.find(new Document("username", targetUser.trim().toLowerCase())).first();
            if (doc != null) {
                String resetOtp = SecurityEngine.generateSystemSecureOtp();
                System.out.println("[2FA OVERLAY MONITOR] System Reset Password OTP Generated -> " + resetOtp);
                if (!SecurityEngine.runOtpVerificationChallenge(app, resetOtp)) return;

                String oldPass = doc.getString("password");
                String newPassword = JOptionPane.showInputDialog(app, "Identity Verified! Input your new desired password:");
                if (newPassword != null) {
                    String cleanPass = newPassword.trim();
                    if (cleanPass.equals(oldPass)) { JOptionPane.showMessageDialog(app, "❌ Policy Breach: You cannot reuse your previous password profile template!", "Reset Error", JOptionPane.ERROR_MESSAGE); return; }
                    if (SecurityEngine.runSecurityPasswordAudit(app, targetUser, cleanPass)) {
                        coll.updateOne(new Document("username", targetUser.trim().toLowerCase()), new Document("$set", new Document("password", cleanPass)));
                        JOptionPane.showMessageDialog(app, "🔄 Success! Password cluster record updated. You can log in now.", "Vault Sync Done", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else { JOptionPane.showMessageDialog(app, "❌ Error: Username match not found inside database records.", "Identity Fault", JOptionPane.ERROR_MESSAGE); }
        });
        add(card);
    }
}