import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class DashboardView extends JPanel {
    private ColorVaultApp app;
    private JTextField txtPalName;
    private JLabel lblFeatureTitle, lblFeatureFeedback;
    private JPanel pnlPaletteGalleryGrid;
    private Color cA = Color.LIGHT_GRAY, cB = Color.LIGHT_GRAY, cC = Color.LIGHT_GRAY;
    private boolean vA = true, vB = true, vC = true;

    // ✨ Search Component added to maintain persistence across UI redraw loops
    private JTextField txtSearchQuery;

    public DashboardView(ColorVaultApp app) {
        this.app = app; setLayout(new BorderLayout()); setBackground(new Color(30, 32, 45));
        JPanel nav = new JPanel(new BorderLayout()); nav.setBackground(new Color(20, 22, 30)); nav.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel lblUserBadge = new JLabel("👑 WORKSPACE HUB // USER ID: " + app.getUser().toUpperCase() + " • [" + app.getRole().toUpperCase() + "]");
        lblUserBadge.setForeground(Color.WHITE); nav.add(lblUserBadge, BorderLayout.WEST);

        JButton btnLogout = new JButton("Log Out"); btnLogout.setBackground(new Color(192, 57, 43)); btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(app, "Are you sure you want to log out of your session?", "Log Out Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) app.showPage("PAGE_LOGIN");
        });
        nav.add(btnLogout, BorderLayout.EAST); add(nav, BorderLayout.NORTH);

        JPanel contentSplitFrame = new JPanel(new GridLayout(1, 2, 30, 0)); contentSplitFrame.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); contentSplitFrame.setOpaque(false);
        JPanel leftFormConsole = new JPanel(new GridBagLayout()); leftFormConsole.setOpaque(false); GridBagConstraints fGbc = new GridBagConstraints(); fGbc.fill = GridBagConstraints.HORIZONTAL; fGbc.insets = new Insets(12,8,12,8);

        String labelA = "Primary Slot:", labelB = "Secondary Slot:", labelC = "Accent Slot:";
        if (app.getRole().equalsIgnoreCase("UI/UX Architect")) { labelA = "Canvas Background Alpha:"; labelB = "Component Midtone Beta:"; labelC = "Foreground Typography Gamma:"; }
        else if (app.getRole().equalsIgnoreCase("Web Master")) { labelA = "CSS Color Token A:"; labelB = "CSS Color Token B:"; labelC = "CSS Color Token C:"; }
        else if (app.getRole().equalsIgnoreCase("Digital Modeler")) { labelA = "Material Mesh Element Alpha:"; labelB = "Material Mesh Element Beta:"; labelC = "Material Mesh Element Gamma:"; }

        fGbc.gridx = 0; fGbc.gridy = 0;
        JLabel lblPalName = new JLabel("Palette Vector Label:");
        lblPalName.setForeground(new Color(220, 225, 235));
        leftFormConsole.add(lblPalName, fGbc);

        txtPalName = new JTextField("My Premium Vector Set", 12); fGbc.gridx = 1; leftFormConsole.add(txtPalName, fGbc);

        fGbc.gridx = 0; fGbc.gridy = 1;
        JLabel lblAlpha = new JLabel(labelA);
        lblAlpha.setForeground(new Color(220, 225, 235));
        leftFormConsole.add(lblAlpha, fGbc);

        JButton btnPickA = new JButton("Tap to Pick Shade"); btnPickA.setBackground(cA); fGbc.gridx = 1; leftFormConsole.add(btnPickA, fGbc);

        fGbc.gridx = 0; fGbc.gridy = 2;
        JLabel lblBeta = new JLabel(labelB);
        lblBeta.setForeground(new Color(220, 225, 235));
        leftFormConsole.add(lblBeta, fGbc);

        JButton btnPickB = new JButton("Tap to Pick Shade"); btnPickB.setBackground(cB); fGbc.gridx = 1; leftFormConsole.add(btnPickB, fGbc);

        fGbc.gridx = 0; fGbc.gridy = 3;
        JLabel lblGamma = new JLabel(labelC);
        lblGamma.setForeground(new Color(220, 225, 235));
        leftFormConsole.add(lblGamma, fGbc);

        JButton btnPickC = new JButton("Tap to Pick Shade"); btnPickC.setBackground(cC); fGbc.gridx = 1; leftFormConsole.add(btnPickC, fGbc);

        lblFeatureTitle = new JLabel("✨ Active Role Feature Desk:"); lblFeatureTitle.setForeground(new Color(241, 196, 15));
        lblFeatureFeedback = new JLabel("Awaiting workspace color token selection map inputs..."); lblFeatureFeedback.setForeground(Color.LIGHT_GRAY);
        fGbc.gridy = 4; fGbc.gridx = 0; leftFormConsole.add(lblFeatureTitle, fGbc); fGbc.gridx = 1; leftFormConsole.add(lblFeatureFeedback, fGbc);

        if (app.getRole().equalsIgnoreCase("UI/UX Architect")) lblFeatureTitle.setText("🛠️ UX Contrast Guard:");
        else if (app.getRole().equalsIgnoreCase("Web Master")) lblFeatureTitle.setText("💻 CSS Code Exporter:");
        else if (app.getRole().equalsIgnoreCase("Digital Modeler")) lblFeatureTitle.setText("🧱 Material Structural Engine:");
        else lblFeatureTitle.setText("🌍 Creator Core Suite:");

        JButton btnCreateRecord = new JButton("✨ Save New Palette to Vault");
        btnCreateRecord.setBackground(new Color(46, 204, 113)); btnCreateRecord.setForeground(Color.WHITE); fGbc.gridy = 5; fGbc.gridx = 0; fGbc.gridwidth = 2; leftFormConsole.add(btnCreateRecord, fGbc);
        contentSplitFrame.add(leftFormConsole);

        JPanel rightGalleryWrapper = new JPanel(new BorderLayout()); rightGalleryWrapper.setBackground(new Color(38, 41, 56));
        JPanel pnlGalleryHeader = new JPanel(new BorderLayout()); pnlGalleryHeader.setBackground(new Color(48, 51, 68)); pnlGalleryHeader.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblGalleryHeading = new JLabel("📁 SECURE PALETTE VAULT FILE RETRIEVALS");
        lblGalleryHeading.setForeground(new Color(116, 185, 255));
        pnlGalleryHeader.add(lblGalleryHeading, BorderLayout.WEST);

        // ✨ Add Interactive Live Search Bar Component inside Header Panel Layout
        txtSearchQuery = new JTextField(10);
        txtSearchQuery.setToolTipText("Search items by name...");
        JPanel searchBoxContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchBoxContainer.setOpaque(false);
        JLabel lblSearchIcon = new JLabel("🔍 ");
        lblSearchIcon.setForeground(Color.WHITE);
        searchBoxContainer.add(lblSearchIcon);
        searchBoxContainer.add(txtSearchQuery);
        pnlGalleryHeader.add(searchBoxContainer, BorderLayout.EAST);

        rightGalleryWrapper.add(pnlGalleryHeader, BorderLayout.NORTH);

        pnlPaletteGalleryGrid = new JPanel(); pnlPaletteGalleryGrid.setLayout(new BoxLayout(pnlPaletteGalleryGrid, BoxLayout.Y_AXIS)); pnlPaletteGalleryGrid.setBackground(new Color(38, 41, 56)); pnlPaletteGalleryGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollBoxFrame = new JScrollPane(pnlPaletteGalleryGrid); scrollBoxFrame.setBorder(null); scrollBoxFrame.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); scrollBoxFrame.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); scrollBoxFrame.getVerticalScrollBar().setUnitIncrement(14); rightGalleryWrapper.add(scrollBoxFrame, BorderLayout.CENTER);
        contentSplitFrame.add(rightGalleryWrapper); add(contentSplitFrame, BorderLayout.CENTER);

        Runnable refreshLocalGalleryView = new Runnable() {
            public void run() {
                pnlPaletteGalleryGrid.removeAll(); MongoCollection<Document> palettes = DatabaseConfig.getPalettesCollection();

                // ✨ Build dynamic query structure to support filtered lookups
                String filterText = txtSearchQuery.getText().trim();
                Document queryConditions = new Document("createdBy", app.getUser());
                if (!filterText.isEmpty()) {
                    // Uses regular expressions for fuzzy, case-insensitive keyword checking
                    queryConditions.append("paletteName", new Document("$regex", filterText).append("$options", "i"));
                }

                if (palettes.countDocuments(queryConditions) == 0) {
                    JPanel emptyPanel = new JPanel(new GridBagLayout()); emptyPanel.setOpaque(false);

                    // ✨ Dynamic notice modification depending on matching outputs
                    String noticeMsg = filterText.isEmpty()
                            ? "<html><center>📪 <b>No Saved Palettes Found inside Vault</b><br><font color='#bdc3c7'>Generate colors on the left panel to populate database records.</font></center></html>"
                            : "<html><center>🔍 <b>No Matching Results Found</b><br><font color='#bdc3c7'>We couldn't find records matching \"" + filterText + "\".</font></center></html>";

                    JLabel emptyLabel = new JLabel(noticeMsg); emptyLabel.setForeground(new Color(189, 195, 199)); emptyPanel.add(emptyLabel); pnlPaletteGalleryGrid.add(emptyPanel);
                } else {
                    for (Document doc : palettes.find(queryConditions)) {
                        String nameStr = doc.getString("paletteName"); ArrayList<String> colorsHexList = (ArrayList<String>) doc.get("colors");
                        JPanel rowCardStripItem = new JPanel(); rowCardStripItem.setLayout(new BoxLayout(rowCardStripItem, BoxLayout.Y_AXIS)); rowCardStripItem.setBackground(new Color(48, 51, 68)); rowCardStripItem.setPreferredSize(new Dimension(420, 115)); rowCardStripItem.setMaximumSize(new Dimension(440, 115)); rowCardStripItem.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(60, 64, 85), 1), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
                        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2)); titleRow.setOpaque(false); JLabel lblDisplayTitle = new JLabel("🎨 " + nameStr.toUpperCase()); lblDisplayTitle.setForeground(Color.WHITE); titleRow.add(lblDisplayTitle); rowCardStripItem.add(titleRow);
                        JPanel chipsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4)); chipsRow.setOpaque(false);
                        for (String hex : colorsHexList) { JLabel lblColorBlock = new JLabel(" " + hex + " "); lblColorBlock.setOpaque(true); lblColorBlock.setBackground(Color.decode(hex)); lblColorBlock.setForeground(Color.WHITE); chipsRow.add(lblColorBlock); chipsRow.add(Box.createRigidArea(new Dimension(6, 0))); }
                        rowCardStripItem.add(chipsRow);
                        JPanel fixedActionControlDock = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2)); fixedActionControlDock.setOpaque(false);
                        JButton btnEdit = new JButton("Edit"); JButton btnDelete = new JButton("Delete"); fixedActionControlDock.add(btnEdit); fixedActionControlDock.add(Box.createRigidArea(new Dimension(4, 0))); fixedActionControlDock.add(btnDelete); rowCardStripItem.add(fixedActionControlDock);

                        btnEdit.addActionListener(click -> {
                            JPanel editModalLayout = new JPanel(new GridLayout(5, 2, 8, 8)); JTextField txtNewTitle = new JTextField(nameStr, 12);
                            JButton btnNewColorA = new JButton("Slot 1"); btnNewColorA.setBackground(Color.decode(colorsHexList.get(0)));
                            JButton btnNewColorB = new JButton("Slot 2"); btnNewColorB.setBackground(Color.decode(colorsHexList.get(1)));
                            JButton btnNewColorC = new JButton("Slot 3"); btnNewColorC.setBackground(Color.decode(colorsHexList.get(2)));
                            final Color[] colorTracker = { Color.decode(colorsHexList.get(0)), Color.decode(colorsHexList.get(1)), Color.decode(colorsHexList.get(2)) };
                            final boolean[] internalValidationFlags = { true, true, true };

                            btnNewColorA.addActionListener(ev -> { Color c = JColorChooser.showDialog(app, "Edit Slot 1", colorTracker[0]); if(c != null) { colorTracker[0] = c; btnNewColorA.setBackground(c); internalValidationFlags[0] = app.validate(c); } });
                            btnNewColorB.addActionListener(ev -> { Color c = JColorChooser.showDialog(app, "Edit Slot 2", colorTracker[1]); if(c != null) { colorTracker[1] = c; btnNewColorB.setBackground(c); internalValidationFlags[1] = app.validate(c); } });
                            btnNewColorC.addActionListener(ev -> { Color c = JColorChooser.showDialog(app, "Edit Slot 3", colorTracker[2]); if(c != null) { colorTracker[2] = c; btnNewColorC.setBackground(c); internalValidationFlags[2] = app.validate(c); } });

                            editModalLayout.add(new JLabel("Modify Title:")); editModalLayout.add(txtNewTitle); editModalLayout.add(new JLabel("Slot 1:")); editModalLayout.add(btnNewColorA); editModalLayout.add(new JLabel("Slot 2:")); editModalLayout.add(btnNewColorB); editModalLayout.add(new JLabel("Slot 3:")); editModalLayout.add(btnNewColorC);

                            if (JOptionPane.showConfirmDialog(app, editModalLayout, "Advanced Palette Update Engine", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION && !txtNewTitle.getText().trim().isEmpty()) {

                                StringBuilder errorReport = new StringBuilder();

                                // 🛠️ MULTI-SLOT EDIT CONSOLE RULES VALIDATOR
                                if (app.getRole().equalsIgnoreCase("UI/UX Architect")) {
                                    if (!internalValidationFlags[0]) errorReport.append("• Canvas Background Alpha\n");
                                    if (!internalValidationFlags[1]) errorReport.append("• Component Midtone Beta\n");
                                    if (!internalValidationFlags[2]) errorReport.append("• Foreground Typography Gamma\n");

                                    if (errorReport.length() > 0) {
                                        JOptionPane.showMessageDialog(app, "❌ UI/UX Accessibility Guard Alert:\nThe following modified slots failed contrast validation ratios:\n" + errorReport.toString() + "Please select readable tints.", "Contrast Ratio Violation", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }

                                if (app.getRole().equalsIgnoreCase("Web Master")) {
                                    if (!internalValidationFlags[0]) errorReport.append("• CSS Color Token A\n");
                                    if (!internalValidationFlags[1]) errorReport.append("• CSS Color Token B\n");
                                    if (!internalValidationFlags[2]) errorReport.append("• CSS Color Token C\n");

                                    if (errorReport.length() > 0) {
                                        JOptionPane.showMessageDialog(app, "❌ Web Master Development Lockout:\nThe following entries have pixel structures below 35 RGB elements:\n" + errorReport.toString() + "This is too dark to render text legibly in CSS scripts!", "CSS Code Token Error", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }

                                if (app.getRole().equalsIgnoreCase("Digital Modeler")) {
                                    if (!internalValidationFlags[0]) errorReport.append("• Material Mesh Element Alpha\n");
                                    if (!internalValidationFlags[1]) errorReport.append("• Material Mesh Element Beta\n");
                                    if (!internalValidationFlags[2]) errorReport.append("• Material Mesh Element Gamma\n");

                                    if (errorReport.length() > 0) {
                                        JOptionPane.showMessageDialog(app, "❌ Digital Modeler Physics Guard Alert:\nThe following slots calculated above the 180 N/mm² density threshold:\n" + errorReport.toString() + "Render process aborted.", "Structural Engine Failure", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }

                                if (app.getRole().equalsIgnoreCase("General Purpose")) {
                                    if (colorTracker[0].equals(colorTracker[1]) || colorTracker[0].equals(colorTracker[2]) || colorTracker[1].equals(colorTracker[2])) {
                                        JOptionPane.showMessageDialog(app, "❌ General Rule Fault: Duplicate colors detected! Please pick three unique shades.", "Diversity Violation", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }

                                ArrayList<String> updatedHexCodes = new ArrayList<>(Arrays.asList(app.toWebHexCode(colorTracker[0]), app.toWebHexCode(colorTracker[1]), app.toWebHexCode(colorTracker[2])));
                                palettes.updateOne(new Document("_id", doc.getObjectId("_id")), new Document("$set", new Document("paletteName", txtNewTitle.getText().trim()).append("colors", updatedHexCodes)));
                                JOptionPane.showMessageDialog(app, "🎉 Success! Palette modifications saved to secure vault.");
                                run();
                            }
                        });

                        btnDelete.addActionListener(click -> { if (JOptionPane.showConfirmDialog(app, "Are you sure you want to delete this color palette?", "Delete Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { palettes.deleteOne(new Document("_id", doc.getObjectId("_id"))); run(); } });
                        pnlPaletteGalleryGrid.add(rowCardStripItem); pnlPaletteGalleryGrid.add(Box.createRigidArea(new Dimension(0, 10)));
                    }
                }
                pnlPaletteGalleryGrid.revalidate(); pnlPaletteGalleryGrid.repaint();
            }
        };

        // ✨ Attach DocumentListener onto text tracking matrix to enable seamless live-updates
        txtSearchQuery.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { refreshLocalGalleryView.run(); }
            public void removeUpdate(DocumentEvent e) { refreshLocalGalleryView.run(); }
            public void changedUpdate(DocumentEvent e) { refreshLocalGalleryView.run(); }
        });

        btnPickA.addActionListener(e -> { Color c = JColorChooser.showDialog(app, "Select Color Alpha", cA); if (c != null) { cA = c; btnPickA.setBackground(c); btnPickA.setText(app.toWebHexCode(c)); vA = app.validate(c); app.runFeedback(c, lblFeatureFeedback, "Slot A"); } });
        btnPickB.addActionListener(e -> { Color c = JColorChooser.showDialog(app, "Select Color Beta", cB); if (c != null) { cB = c; btnPickB.setBackground(c); btnPickB.setText(app.toWebHexCode(c)); vB = app.validate(c); app.runFeedback(c, lblFeatureFeedback, "Slot B"); } });
        btnPickC.addActionListener(e -> { Color c = JColorChooser.showDialog(app, "Select Color Gamma", cC); if (c != null) { cC = c; btnPickC.setBackground(c); btnPickC.setText(app.toWebHexCode(c)); vC = app.validate(c); app.runFeedback(c, lblFeatureFeedback, "Slot C"); } });

        btnCreateRecord.addActionListener(e -> {
            StringBuilder errorReport = new StringBuilder();

            // 🛠️ MULTI-SLOT MAIN DASHBOARD SAVE RULES VALIDATOR
            if (app.getRole().equalsIgnoreCase("UI/UX Architect")) {
                if (!vA) errorReport.append("• Canvas Background Alpha\n");
                if (!vB) errorReport.append("• Component Midtone Beta\n");
                if (!vC) errorReport.append("• Foreground Typography Gamma\n");

                if (errorReport.length() > 0) {
                    JOptionPane.showMessageDialog(this, "❌ UI/UX Accessibility Guard Alert:\nThe following chosen color parameters failed contrast validation ratios:\n" + errorReport.toString() + "Clear layout and pick a readable tint.", "Contrast Ratio Violation", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (app.getRole().equalsIgnoreCase("Web Master")) {
                if (!vA) errorReport.append("• CSS Color Token A\n");
                if (!vB) errorReport.append("• CSS Color Token B\n");
                if (!vC) errorReport.append("• CSS Color Token C\n");

                if (errorReport.length() > 0) {
                    JOptionPane.showMessageDialog(this, "❌ Web Master Development Lockout:\nThe following entries have pixel structures below 35 RGB elements:\n" + errorReport.toString() + "This is too dark to render text legibly in CSS scripts!", "CSS Code Token Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (app.getRole().equalsIgnoreCase("Digital Modeler")) {
                if (!vA) errorReport.append("• Material Mesh Element Alpha\n");
                if (!vB) errorReport.append("• Material Mesh Element Beta\n");
                if (!vC) errorReport.append("• Material Mesh Element Gamma\n");

                if (errorReport.length() > 0) {
                    JOptionPane.showMessageDialog(this, "❌ Digital Modeler Physics Guard Alert:\nThe computed pigment mass factors inside the following slots calculated above the 180 N/mm² density threshold:\n" + errorReport.toString() + "Render process aborted.", "Structural Engine Failure", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (app.getRole().equalsIgnoreCase("General Purpose") && (cA.equals(cB) || cA.equals(cC) || cB.equals(cC))) {
                JOptionPane.showMessageDialog(this, "❌ General Rule Fault: Duplicate colors detected! Please pick three unique shades.", "Diversity Violation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String title = txtPalName.getText().trim(); if (title.isEmpty() || cA.equals(Color.LIGHT_GRAY) || cB.equals(Color.LIGHT_GRAY) || cC.equals(Color.LIGHT_GRAY)) return;
            ArrayList<String> hexPackage = new ArrayList<>(Arrays.asList(String.format("#%02X%02X%02X", cA.getRed(), cA.getGreen(), cA.getBlue()), String.format("#%02X%02X%02X", cB.getRed(), cB.getGreen(), cB.getBlue()), String.format("#%02X%02X%02X", cC.getRed(), cC.getGreen(), cC.getBlue())));
            DatabaseConfig.getPalettesCollection().insertOne(new Document("paletteName", title).append("colors", hexPackage).append("createdBy", app.getUser()));

            cA = Color.LIGHT_GRAY; cB = Color.LIGHT_GRAY; cC = Color.LIGHT_GRAY; vA = true; vB = true; vC = true;
            btnPickA.setBackground(cA); btnPickA.setText("Tap to Pick Shade"); btnPickB.setBackground(cB); btnPickB.setText("Tap to Pick Shade"); btnPickC.setBackground(cC); btnPickC.setText("Tap to Pick Shade"); txtPalName.setText("My Premium Vector Set"); lblFeatureFeedback.setText("Awaiting workspace color token selection map inputs..."); lblFeatureFeedback.setForeground(Color.LIGHT_GRAY);
            refreshLocalGalleryView.run();
        });
        refreshLocalGalleryView.run();
    }
}