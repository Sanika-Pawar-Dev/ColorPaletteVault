import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SecurityEngine {
    public static boolean runSecurityPasswordAudit(Component parent, String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "⚠️ Validation Fault: Fields cannot be left blank!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (username.equalsIgnoreCase(password)) {
            JOptionPane.showMessageDialog(parent, "⚠️ Security Fault: Password cannot be identical to your username!", "Policy Violation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(parent, "⚠️ Complexity Check: Password must be at least 6 characters long!", "Weak Password", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*")) {
            JOptionPane.showMessageDialog(parent, "⚠️ Complexity Check: Password must contain at least one Uppercase and one Lowercase letter!", "Weak Password", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static String generateSystemSecureOtp() {
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(10000));
    }

    public static boolean runOtpVerificationChallenge(Component parent, String sentOtp) {
        String userEntry = JOptionPane.showInputDialog(parent,
                "🔒 A secure OTP authentication challenge has been issued.\nEnter the 4-Digit authorization key:",
                "Two-Factor Security Verification", JOptionPane.QUESTION_MESSAGE);

        if (userEntry == null) return false;
        if (userEntry.trim().equals(sentOtp)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(parent, "❌ Security Breach: Token mismatch. Operation rejected.", "Verification Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}