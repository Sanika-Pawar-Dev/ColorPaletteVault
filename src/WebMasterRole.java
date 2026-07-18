import java.awt.Color;
import javax.swing.JLabel;

public class WebMasterRole {
    public static boolean validate(Color chosen) {
        return (chosen.getRed() >= 35 && chosen.getGreen() >= 35 && chosen.getBlue() >= 35);
    }

    public static void run(Color chosen, JLabel lblFeedback, String slotsLabel) {
        boolean pass = validate(chosen);
        if (!pass) {
            lblFeedback.setText(slotsLabel + " Fault: RGB codes cannot go below 35 (too dark for screen text).");
            lblFeedback.setForeground(new Color(231, 76, 60));
        } else {
            lblFeedback.setText("Injected CSS: color-variable: rgb(" + chosen.getRed() + "," + chosen.getGreen() + "," + chosen.getBlue() + ");");
            lblFeedback.setForeground(Color.WHITE);
        }
    }
}