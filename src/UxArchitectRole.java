import java.awt.Color;
import javax.swing.JLabel;

public class UxArchitectRole {
    public static boolean validate(Color chosen) {
        double luminance = (0.2126 * chosen.getRed() + 0.7152 * chosen.getGreen() + 0.0722 * chosen.getBlue()) / 255.0;
        return !(luminance > 0.82 || luminance < 0.18);
    }

    public static void run(Color chosen, JLabel lblFeedback, String slotsLabel, String hexCode) {
        boolean pass = validate(chosen);
        if (!pass) {
            lblFeedback.setText(slotsLabel + " Fault: " + hexCode + " breaks contrast guidelines!");
            lblFeedback.setForeground(new Color(231, 76, 60));
        } else {
            lblFeedback.setText(slotsLabel + " OK: " + hexCode + " passes WCAG accessibility checks.");
            lblFeedback.setForeground(new Color(46, 204, 113));
        }
    }
}