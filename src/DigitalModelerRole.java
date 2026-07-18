import java.awt.Color;
import javax.swing.JLabel;

public class DigitalModelerRole {
    public static boolean validate(Color chosen) {
        double computedDensity = (chosen.getRed() * 0.3) + (chosen.getGreen() * 0.5);
        return (computedDensity <= 180.0);
    }

    public static void run(Color chosen, JLabel lblFeedback) {
        boolean pass = validate(chosen);
        double computedDensity = (chosen.getRed() * 0.3) + (chosen.getGreen() * 0.5);
        if (!pass) {
            lblFeedback.setText("Density Fault: " + String.format("%.2f", computedDensity) + " N/mm² exceeds material thresholds!");
            lblFeedback.setForeground(new Color(231, 76, 60));
        } else {
            lblFeedback.setText("Material Map: Physics density verified at " + String.format("%.2f", computedDensity) + " N/mm²");
            lblFeedback.setForeground(Color.WHITE);
        }
    }
}