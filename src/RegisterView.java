import javax.swing.*;
import java.awt.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class RegisterView extends JPanel {
    public RegisterView(ColorVaultApp app) {
        setLayout(new GridBagLayout()); setBackground(new Color(24, 26, 36));
        JPanel card = new JPanel(new GridBagLayout()); card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1), BorderFactory.createEmptyBorder(25, 40, 25, 40)));
        GridBagConstraints g = new GridBagConstraints(); g.insets = new Insets(8,8,8,8); g.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("CREATE FRESH PROFILE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20)); lblTitle.setForeground(new Color(39, 174, 96));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; card.add(lblTitle, g);

        g.gridwidth = 1; g.gridy = 1; g.gridx = 0; card.add(new JLabel("Full Identity Name:"), g);
        JTextField txtRegName = new JTextField(15); g.gridx = 1; card.add(txtRegName, g);

        g.gridx = 0; g.gridy = 2; card.add(new JLabel("Choose Username:"), g);
        JTextField txtRegUser = new JTextField(15); g.gridx = 1; card.add(txtRegUser, g);

        g.gridx = 0; g.gridy = 3; card.add(new JLabel("Choose Password:"), g);
        JPasswordField txtRegPass = new JPasswordField(15); g.gridx = 1; card.add(txtRegPass, g);

        g.gridx = 0; g.gridy = 4; card.add(new JLabel("Design Profile Role:"), g);
        JComboBox<String> cbRoleSelector = new JComboBox<>(new String[]{"General Purpose", "UI/UX Architect", "Web Master", "Digital Modeler"}); g.gridx = 1; card.add(cbRoleSelector, g);

        JButton btnRegister = new JButton("Instantiate Account");
        btnRegister.setBackground(new Color(39, 174, 96)); btnRegister.setForeground(Color.WHITE); g.gridx = 0; g.gridy = 5; card.add(btnRegister, g);

        // FIXED: Shifted background execution color strictly to green matching instantiate submission configurations
        JButton btnCancel = new JButton("Back to Login");
        btnCancel.setBackground(new Color(39, 174, 96)); btnCancel.setForeground(Color.WHITE); g.gridx = 1; card.add(btnCancel, g);

        btnCancel.addActionListener(e -> { txtRegName.setText(""); txtRegUser.setText(""); txtRegPass.setText(""); app.showPage("PAGE_LOGIN"); });

        btnRegister.addActionListener(e -> {
            String name = txtRegName.getText().trim(); String user = txtRegUser.getText().trim().toLowerCase();
            String pass = new String(txtRegPass.getPassword()); String role = (String) cbRoleSelector.getSelectedItem();

            if (!SecurityEngine.runSecurityPasswordAudit(app, user, pass) || name.isEmpty()) return;
            MongoCollection<Document> coll = DatabaseConfig.getUsersCollection();
            if (coll.find(new Document("username", user)).first() != null) { JOptionPane.showMessageDialog(card, "❌ Error: Username handle is already taken!", "Identity Conflict", JOptionPane.ERROR_MESSAGE); return; }

            coll.insertOne(new Document("username", user).append("password", pass).append("name", name).append("role", role));
            JOptionPane.showMessageDialog(card, "🎉 Success! Profile securely provisioned to database.", "Account Unlocked", JOptionPane.INFORMATION_MESSAGE);
            txtRegName.setText(""); txtRegUser.setText(""); txtRegPass.setText(""); app.showPage("PAGE_LOGIN");
        });
        add(card);
    }
}