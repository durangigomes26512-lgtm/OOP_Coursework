package coursework;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PetAppGUI extends JFrame {
    private final ShelterManager manager;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private User currentUser;
    private String selectedAction = "";
    private Pet selectedPetToAdopt;

    // Theme Palette
    private static final Color BG_DARK = new Color(248, 249, 250), CARD_BG = Color.WHITE;
    private static final Color PRIMARY_GREEN = new Color(45, 90, 39), ACCENT_GREEN = new Color(74, 124, 89);
    private static final Color ACCENT_CORAL = new Color(224, 122, 95), ACCENT_DOG = new Color(204, 122, 45), ACCENT_CAT = new Color(42, 122, 138);
    private static final Color TEXT_DARK = new Color(43, 45, 66), TEXT_MUTED = new Color(108, 117, 125);
    private static final Color INPUT_BG = new Color(241, 243, 245), BORDER_COLOR = new Color(222, 226, 230);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13), FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 13);

    // Dynamic Form Controls
    private final JTextField txtLoginID = createField(""), txtRegName = createField(""), txtRegPhone = createField("");
    private final JTextField txtName = createField(""), txtAge = createField(""), txtHealth = createField("Healthy"), txtBreed = createField("");
    private final JPasswordField txtLoginPass = (JPasswordField) createField("", true), txtRegPass = (JPasswordField) createField("", true);
    private final JComboBox<String> cmbRegType = new JComboBox<>(new String[]{"Adopter", "Rescuer"}), cmbPayment = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "Cash"});
    private final JComboBox<String> cmbGender = new JComboBox<>(new String[]{"M", "F"}), cmbType = new JComboBox<>(new String[]{"Dog", "Cat"});
    private final JCheckBox chkVax = createCheckBox("Vaccinated"), chkLeash = createCheckBox("Leash Trained"), chkIndoor = createCheckBox("Indoor Only"), chkLitter = createCheckBox("Litter Trained");

    private final JLabel lblPetListHeader = new JLabel("Available Pets", SwingConstants.CENTER);
    private final DefaultTableModel petTableModel = new DefaultTableModel(new String[]{"Pet ID", "Name", "Age", "Gender", "Health", "Fee (LKR)"}, 0);
    private final JLabel[] receiptLabels = new JLabel[6];

    public PetAppGUI(ShelterManager manager) {
        this.manager = manager;
        setTitle("🐾 Pet Adoption Portal");
        setSize(650, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.setBackground(BG_DARK);
        mainPanel.add(createHomeCard(), "HOME");
        mainPanel.add(createAuthCard(), "AUTH");
        mainPanel.add(createAdoptCategoryCard(), "ADOPT_CAT");
        mainPanel.add(createPetListCard(), "PET_LIST");
        mainPanel.add(createPaymentCard(), "PAYMENT");
        mainPanel.add(createRescueFormCard(), "RESCUE_FORM");
        mainPanel.add(createReceiptCard(), "RECEIPT");

        add(mainPanel);
    }

    // Modern Unified Factory Helpers
    private static JTextField createField(String defaultText, boolean isPassword) {
        JTextField tf = isPassword ? new JPasswordField() : new JTextField(defaultText);
        tf.setBackground(INPUT_BG);
        tf.setForeground(TEXT_DARK);
        tf.setCaretColor(PRIMARY_GREEN);
        tf.setFont(FONT_PLAIN);
        tf.setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDER_COLOR, 1, true), new EmptyBorder(6, 10, 6, 10)));
        return tf;
    }

    private static JTextField createField(String defaultText) {
        return createField(defaultText, false);
    }

    private static JCheckBox createCheckBox(String text) {
        JCheckBox chk = new JCheckBox(text);
        chk.setBackground(CARD_BG);
        chk.setForeground(TEXT_DARK);
        chk.setFont(FONT_PLAIN);
        return chk;
    }

    private JButton createBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        btn.addChangeListener(e -> btn.setBackground(btn.getModel().isRollover() ? bg.brighter() : bg));
        return btn;
    }

    private JPanel wrapCard(JPanel card) {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_DARK);
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDER_COLOR, 1, true), new EmptyBorder(25, 30, 25, 30)));
        outer.add(card);
        return outer;
    }

    private void addRow(JPanel p, String labelText, JComponent input) {
        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(TEXT_MUTED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        p.add(lbl);
        p.add(input);
    }

    // View Components
    private JPanel createHomeCard() {
        JPanel card = new JPanel(new GridLayout(4, 1, 12, 12));
        JLabel icon = new JLabel("🐶 🐱", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));

        JLabel title = new JLabel("Pet Adoption Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(PRIMARY_GREEN);

        JButton btnA = createBtn("Adopt a Pet", ACCENT_GREEN);
        JButton btnR = createBtn("Rescue a Pet", ACCENT_CORAL);

        btnA.addActionListener(e -> navigateAuth("ADOPT"));
        btnR.addActionListener(e -> navigateAuth("RESCUE"));

        card.add(icon); card.add(title); card.add(btnA); card.add(btnR);
        return wrapCard(card);
    }

    private void navigateAuth(String action) {
        selectedAction = action;
        cardLayout.show(mainPanel, "AUTH");
    }

    private JPanel createAuthCard() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_DARK);

        JPanel lForm = new JPanel(new GridLayout(2, 2, 8, 12)); lForm.setBackground(CARD_BG);
        addRow(lForm, "User ID:", txtLoginID); addRow(lForm, "Password:", txtLoginPass);
        JButton btnL = createBtn("Login", ACCENT_GREEN);
        btnL.addActionListener(e -> handleLogin());
        
        JPanel login = new JPanel(new BorderLayout(10, 15)); login.setBackground(CARD_BG);
        login.add(lForm, BorderLayout.CENTER); login.add(btnL, BorderLayout.SOUTH);

        JPanel rForm = new JPanel(new GridLayout(4, 2, 8, 8)); rForm.setBackground(CARD_BG);
        addRow(rForm, "Full Name:", txtRegName); addRow(rForm, "Phone:", txtRegPhone);
        addRow(rForm, "Password:", txtRegPass); addRow(rForm, "User Role:", cmbRegType);
        JButton btnR = createBtn("Register Account", ACCENT_GREEN);
        btnR.addActionListener(e -> handleRegister());

        JPanel reg = new JPanel(new BorderLayout(10, 15)); reg.setBackground(CARD_BG);
        reg.add(rForm, BorderLayout.CENTER); reg.add(btnR, BorderLayout.SOUTH);

        tabs.addTab("🔑 Login", wrapCard(login)); tabs.addTab("📝 Register", wrapCard(reg));
        JPanel outer = new JPanel(new BorderLayout()); outer.setBackground(BG_DARK); outer.add(tabs); 
        return outer;
    }

    private void handleLogin() {
        currentUser = manager.authenticate(txtLoginID.getText().trim(), new String(txtLoginPass.getPassword()));
        if (currentUser != null) navigatePostAuth();
        else JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleRegister() {
        String id = "U" + String.format("%03d", manager.getUserList().size() + 1);
        currentUser = new User(id, txtRegName.getText(), txtRegPhone.getText(), (String) cmbRegType.getSelectedItem(), new String(txtRegPass.getPassword()));
        manager.addUser(currentUser);
        JOptionPane.showMessageDialog(this, "Registered! Your ID: " + id);
        navigatePostAuth();
    }

    private void navigatePostAuth() {
        boolean isRescuer = "Rescuer".equalsIgnoreCase(currentUser.getUserType());
        boolean isAdopter = "Adopter".equalsIgnoreCase(currentUser.getUserType());

        if ("ADOPT".equals(selectedAction)) {
            if (isRescuer) showAccessDenied("Rescuers cannot adopt pets.");
            else cardLayout.show(mainPanel, "ADOPT_CAT");
        } else if ("RESCUE".equals(selectedAction)) {
            if (isAdopter) showAccessDenied("Adopters cannot rescue/surrender pets.");
            else cardLayout.show(mainPanel, "RESCUE_FORM");
        }
    }

    private void showAccessDenied(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Access Denied", JOptionPane.WARNING_MESSAGE);
        cardLayout.show(mainPanel, "HOME");
    }

    private JPanel createAdoptCategoryCard() {
        JPanel card = new JPanel(new GridLayout(3, 1, 12, 12));
        JLabel title = new JLabel("Choose Category", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_GREEN);

        JButton btnD = createBtn("🐕 Adopt a Dog", ACCENT_DOG);
        JButton btnC = createBtn("🐈 Adopt a Cat", ACCENT_CAT);

        btnD.addActionListener(e -> loadPets("Dog"));
        btnC.addActionListener(e -> loadPets("Cat"));

        card.add(title); card.add(btnD); card.add(btnC);
        return wrapCard(card);
    }

    private JPanel createPetListCard() {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(20, 20, 20, 20));

        lblPetListHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JTable table = new JTable(petTableModel);
        table.setRowHeight(28);
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_DARK);
        table.setGridColor(BORDER_COLOR);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setBackground(INPUT_BG);
        table.getTableHeader().setForeground(PRIMARY_GREEN);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);

        JButton btnSelect = createBtn("Proceed to Adopt Selected Pet", ACCENT_GREEN);
        btnSelect.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedPetToAdopt = manager.findPetByID((String) petTableModel.getValueAt(row, 0));
                cardLayout.show(mainPanel, "PAYMENT");
            } else JOptionPane.showMessageDialog(this, "Select a pet first.");
        });

        JScrollPane pane = new JScrollPane(table);
        pane.getViewport().setBackground(BG_DARK);
        pane.setBorder(new LineBorder(BORDER_COLOR, 1));

        p.add(lblPetListHeader, BorderLayout.NORTH);
        p.add(pane, BorderLayout.CENTER);
        p.add(btnSelect, BorderLayout.SOUTH);
        return p;
    }

    private void loadPets(String type) {
        petTableModel.setRowCount(0);
        boolean isDog = "Dog".equals(type);
        lblPetListHeader.setText(isDog ? "🐕 Available Dogs" : "🐈 Available Cats");
        lblPetListHeader.setForeground(isDog ? ACCENT_DOG : ACCENT_CAT);

        for (Pet p : manager.getAvailablePets()) {
            if ((isDog && p instanceof Dog) || (!isDog && p instanceof Cat))
                petTableModel.addRow(new Object[]{p.getPetID(), p.getName(), p.getAge(), p.getGender(), p.getHealthStatus(), "LKR " + p.calculateAdoptionFee()});
        }
        cardLayout.show(mainPanel, "PET_LIST");
    }

    private JPanel createPaymentCard() {
        JPanel card = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel title = new JLabel("💳 Checkout", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_GREEN);

        JButton btnPay = createBtn("Confirm & Pay", ACCENT_GREEN);
        btnPay.addActionListener(e -> handlePayment());

        JLabel label = new JLabel("Select Payment Method:");
        label.setForeground(TEXT_DARK);
        card.add(title); card.add(label); card.add(cmbPayment); card.add(btnPay);
        return wrapCard(card);
    }

    private void handlePayment() {
        try {
            String recID = "REC" + String.format("%03d", manager.getRecordList().size() + 101);
            manager.processAdoption(recID, currentUser, selectedPetToAdopt.getPetID());
            AdoptionRecord r = manager.getRecordList().get(manager.getRecordList().size() - 1);
            
            receiptLabels[0].setText("Record ID: " + r.getRecordID());
            receiptLabels[1].setText("Pet: " + r.getPet().getName() + " [" + r.getPet().getPetID() + "]");
            receiptLabels[2].setText("Adopter: " + r.getAdopter().getName());
            receiptLabels[3].setText("Fee Paid: LKR " + r.getFeePaid());
            receiptLabels[4].setText("Method: " + cmbPayment.getSelectedItem());
            receiptLabels[5].setText("Date: " + r.getDate());
            
            cardLayout.show(mainPanel, "RECEIPT");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }

    private JPanel createRescueFormCard() {
        JPanel card = new JPanel(new BorderLayout(10, 15)); 
        card.setBackground(CARD_BG);

        JPanel form = new JPanel(new GridLayout(7, 2, 8, 8)); 
        form.setBackground(CARD_BG);

        JPanel dynamicOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        dynamicOptionsPanel.setBackground(CARD_BG);

        Runnable updateFormFields = () -> {
            boolean isDog = "Dog".equals(cmbType.getSelectedItem());
            txtBreed.setEnabled(isDog);
            if (!isDog) txtBreed.setText("");

            dynamicOptionsPanel.removeAll();
            if (isDog) {
                dynamicOptionsPanel.add(chkLeash);
            } else {
                dynamicOptionsPanel.add(chkIndoor);
                dynamicOptionsPanel.add(chkLitter);
            }
            dynamicOptionsPanel.revalidate();
            dynamicOptionsPanel.repaint();
        };

        cmbType.addActionListener(e -> updateFormFields.run());

        addRow(form, "Type:", cmbType); 
        addRow(form, "Name:", txtName);
        addRow(form, "Age:", txtAge); 
        addRow(form, "Gender:", cmbGender);
        addRow(form, "Health:", txtHealth); 
        addRow(form, "Vaccinated:", chkVax);
        addRow(form, "Breed (Dog only):", txtBreed);

        updateFormFields.run();

        JPanel centerContainer = new JPanel(new BorderLayout(5, 10));
        centerContainer.setBackground(CARD_BG);
        centerContainer.add(form, BorderLayout.CENTER);
        centerContainer.add(dynamicOptionsPanel, BorderLayout.SOUTH);

        JButton btnSub = createBtn("Surrender Pet", ACCENT_CORAL);
        btnSub.addActionListener(e -> handlePetSurrender());

        card.add(centerContainer, BorderLayout.CENTER); 
        card.add(btnSub, BorderLayout.SOUTH);
        return wrapCard(card);
    }

    private void handlePetSurrender() {
        try {
            String id = "P" + String.format("%03d", manager.getPetList().size() + 1);
            int age = Integer.parseInt(txtAge.getText().trim());
            char g = cmbGender.getSelectedItem().toString().charAt(0);

            // Updated parameter order: (petID, name, age, gender, healthStatus, isVaccinated, ...)
            if ("Dog".equals(cmbType.getSelectedItem())) {
                manager.addPet(new Dog(id, txtName.getText(), age, g, txtHealth.getText(), chkVax.isSelected(), txtBreed.getText(), chkLeash.isSelected()));
            } else {
                manager.addPet(new Cat(id, txtName.getText(), age, g, txtHealth.getText(), chkVax.isSelected(), chkIndoor.isSelected(), chkLitter.isSelected()));
            }

            JOptionPane.showMessageDialog(this, "Pet registered successfully!");
            cardLayout.show(mainPanel, "HOME");
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Invalid inputs. Check age or empty fields.", "Error", JOptionPane.ERROR_MESSAGE); 
        }
    }

    private JPanel createReceiptCard() {
        JPanel card = new JPanel(new GridLayout(8, 1, 6, 6));
        JLabel title = new JLabel("🎉 Adoption Complete!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY_GREEN);
        card.add(title);

        for (int i = 0; i < receiptLabels.length; i++) {
            receiptLabels[i] = new JLabel();
            receiptLabels[i].setForeground(TEXT_DARK);
            receiptLabels[i].setFont(FONT_PLAIN);
            card.add(receiptLabels[i]);
        }

        JButton btnHome = createBtn("Back to Home", ACCENT_GREEN);
        btnHome.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        card.add(btnHome);

        return wrapCard(card);
    }

    public static void main(String[] args) {
        ShelterManager manager = new ShelterManager();
        manager.addUser(new User("U001", "Jane Doe", "0123456789", "Adopter", "1234"));

        // Updated pet instantiations to match new constructor signature order:
        // Dog(petID, name, age, gender, healthStatus, isVaccinated, breed, leashTrained)
        manager.addPet(new Dog("P001", "Buddy", 3, 'M', "Healthy", true, "Golden Retriever", true));
        manager.addPet(new Dog("P002", "Rex", 4, 'M', "Healthy", true, "German Shepherd", true));
        manager.addPet(new Dog("P003", "Bella", 1, 'F', "Healthy", false, "Poodle", false));
        manager.addPet(new Dog("P004", "Charlie", 2, 'M', "Healthy", true, "Beagle", true));

        // Cat(petID, name, age, gender, healthStatus, isVaccinated, indoorOnly, litterTrained)
        manager.addPet(new Cat("P005", "Whiskers", 2, 'F', "Healthy", true, true, true));
        manager.addPet(new Cat("P006", "Milo", 1, 'M', "Healthy", true, true, true));
        manager.addPet(new Cat("P007", "Luna", 3, 'F', "Healthy", false, false, true));
        manager.addPet(new Cat("P008", "Oliver", 2, 'M', "Healthy", true, true, false));

        SwingUtilities.invokeLater(() -> new PetAppGUI(manager).setVisible(true));
    }
}
