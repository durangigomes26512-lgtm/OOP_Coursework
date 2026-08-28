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

    // Palette & Fonts
    private static final Color BG_MAIN = new Color(242, 250, 246);
    private static final Color WHITE = Color.WHITE;
    private static final Color PRIMARY = new Color(46, 164, 104);
    private static final Color PRIMARY_DARK = new Color(27, 112, 68);
    private static final Color ORANGE = new Color(245, 142, 42);
    private static final Color PURPLE = new Color(132, 94, 194);
    private static final Color PINK = new Color(238, 105, 135);
    private static final Color TEXT_DARK = new Color(45, 52, 54);
    private static final Color TEXT_MUTED = new Color(108, 117, 125);
    private static final Color INPUT_BG = new Color(248, 249, 250);
    private static final Color BORDER_COLOR = new Color(222, 226, 230);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 13);

    // Form Controls
    private final JTextField txtLoginID = createField("", false);
    private final JTextField txtRegName = createField("", false);
    private final JTextField txtRegPhone = createField("", false);
    private final JTextField txtName = createField("", false);
    private final JTextField txtAge = createField("", false);
    private final JTextField txtHealth = createField("Healthy", false);
    private final JTextField txtBreed = createField("", false);

    private final JPasswordField txtLoginPass = (JPasswordField) createField("", true);
    private final JPasswordField txtRegPass = (JPasswordField) createField("", true);

    private final JComboBox<String> cmbRegType = new JComboBox<>(new String[]{"Adopter", "Rescuer"});
    private final JComboBox<String> cmbPayment = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "Cash"});
    private final JComboBox<String> cmbGender = new JComboBox<>(new String[]{"M", "F"});
    private final JComboBox<String> cmbType = new JComboBox<>(new String[]{"Dog", "Cat"});

    private final JCheckBox chkVax = createCheckBox("Vaccinated");
    private final JCheckBox chkLeash = createCheckBox("Leash Trained");
    private final JCheckBox chkIndoor = createCheckBox("Indoor Only");
    private final JCheckBox chkLitter = createCheckBox("Litter Trained");

    private final JLabel lblPetListHeader = new JLabel("Available Pets", SwingConstants.CENTER);
    private final DefaultTableModel petTableModel = new DefaultTableModel(
            new String[]{"Pet ID", "Name", "Age", "Gender", "Health", "Fee (LKR)"}, 0
    );
    private final JLabel[] receiptLabels = new JLabel[6];

    public PetAppGUI(ShelterManager manager) {
        this.manager = manager;

        setTitle("🐾 Pet Adoption Portal");
        setSize(850, 650);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.setBackground(BG_MAIN);
        mainPanel.add(createHomeCard(), "HOME");
        mainPanel.add(createAuthCard(), "AUTH");
        mainPanel.add(createAdoptCategoryCard(), "ADOPT_CAT");
        mainPanel.add(createPetListCard(), "PET_LIST");
        mainPanel.add(wrapCard(createPaymentCard()), "PAYMENT");
        mainPanel.add(wrapCard(createRescueFormCard()), "RESCUE_FORM");
        mainPanel.add(wrapCard(createReceiptCard()), "RECEIPT");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    // Component Factories
    private static JTextField createField(String defaultText, boolean isPassword) {
        JTextField field = isPassword ? new JPasswordField() : new JTextField(defaultText);
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_DARK);
        field.setCaretColor(PRIMARY);
        field.setFont(FONT_PLAIN);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(9, 12, 9, 12)
        ));
        return field;
    }

    private static JCheckBox createCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setBackground(WHITE);
        checkBox.setForeground(TEXT_DARK);
        checkBox.setFont(FONT_PLAIN);
        checkBox.setFocusPainted(false);
        return checkBox;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(WHITE);
        comboBox.setForeground(TEXT_DARK);
        comboBox.setFont(FONT_PLAIN);
        comboBox.setBorder(new LineBorder(BORDER_COLOR, 1, true));
    }

    private JButton createBtn(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.addChangeListener(e -> button.setBackground(button.getModel().isRollover() ? background.brighter() : background));
        return button;
    }

    private JButton createLargeActionButton(String title, String description, Color background) {
        String html = String.format("<html><div style='text-align:center'><div style='font-size:18px'><b>%s</b></div><br><div style='font-size:11px'>%s</div></div></html>", title, description);
        JButton button = new JButton(html);
        button.setFont(FONT_BOLD);
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(25, 25, 25, 25));
        button.addChangeListener(e -> button.setBackground(button.getModel().isRollover() ? background.brighter() : background));
        return button;
    }

    private JPanel wrapCard(JPanel card) {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_MAIN);
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(30, 35, 30, 35)
        ));
        outer.add(card);
        return outer;
    }

    private void addRow(JPanel panel, String labelText, JComponent input) {
        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_MUTED);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(label);
        panel.add(input);
    }

    // Views
    private JPanel createHomeCard() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_MAIN);

        JPanel hero = new JPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBackground(PRIMARY);
        hero.setBorder(new EmptyBorder(35, 20, 35, 20));

        JLabel icon = new JLabel("🐶   🐱   🐾");
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        JLabel title = new JLabel("Pet Adoption Portal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setFont(FONT_TITLE);

        JLabel subtitle = new JLabel("Find a best friend. Change a life!");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(230, 255, 240));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        hero.add(icon);
        hero.add(Box.createVerticalStrut(8));
        hero.add(title);
        hero.add(Box.createVerticalStrut(8));
        hero.add(subtitle);

        JPanel actions = new JPanel(new GridLayout(2, 1, 20, 20));
        actions.setBackground(BG_MAIN);
        actions.setBorder(new EmptyBorder(40, 150, 40, 150));

        JButton adoptButton = createLargeActionButton("🐾 Adopt a Pet", "Find your perfect furry companion", ORANGE);
        JButton rescueButton = createLargeActionButton("🏠 Rehome a Pet", "Give a pet a second chance at happiness", PINK);

        adoptButton.addActionListener(e -> navigateAuth("ADOPT"));
        rescueButton.addActionListener(e -> navigateAuth("RESCUE"));

        actions.add(adoptButton);
        actions.add(rescueButton);

        main.add(hero, BorderLayout.NORTH);
        main.add(actions, BorderLayout.CENTER);
        return main;
    }

    private void navigateAuth(String action) {
        selectedAction = action;
        cardLayout.show(mainPanel, "AUTH");
    }

    private JPanel createAuthCard() {
        styleComboBox(cmbRegType);
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_BOLD);
        tabs.setBackground(WHITE);

        // Login Panel
        JPanel loginCard = new JPanel(new BorderLayout(15, 20));
        loginCard.setBackground(WHITE);

        JLabel loginTitle = new JLabel("Welcome Back!", SwingConstants.CENTER);
        loginTitle.setFont(FONT_SUBTITLE);
        loginTitle.setForeground(PRIMARY_DARK);

        JPanel loginForm = new JPanel(new GridLayout(2, 2, 15, 18));
        loginForm.setBackground(WHITE);
        addRow(loginForm, "User ID:", txtLoginID);
        addRow(loginForm, "Password:", txtLoginPass);

        JButton loginButton = createBtn("Login", PRIMARY);
        loginButton.addActionListener(e -> handleLogin());

        loginCard.add(loginTitle, BorderLayout.NORTH);
        loginCard.add(loginForm, BorderLayout.CENTER);
        loginCard.add(loginButton, BorderLayout.SOUTH);

        // Register Panel
        JPanel registerCard = new JPanel(new BorderLayout(15, 20));
        registerCard.setBackground(WHITE);

        JLabel registerTitle = new JLabel("Create Your Account 🐾", SwingConstants.CENTER);
        registerTitle.setFont(FONT_SUBTITLE);
        registerTitle.setForeground(PURPLE);

        JPanel registerForm = new JPanel(new GridLayout(4, 2, 15, 15));
        registerForm.setBackground(WHITE);
        addRow(registerForm, "Full Name:", txtRegName);
        addRow(registerForm, "Phone:", txtRegPhone);
        addRow(registerForm, "Password:", txtRegPass);
        addRow(registerForm, "User Role:", cmbRegType);

        JButton registerButton = createBtn("Create Account ✨", PURPLE);
        registerButton.addActionListener(e -> handleRegister());

        registerCard.add(registerTitle, BorderLayout.NORTH);
        registerCard.add(registerForm, BorderLayout.CENTER);
        registerCard.add(registerButton, BorderLayout.SOUTH);

        tabs.addTab("Login", wrapCard(loginCard));
        tabs.addTab( "Register", wrapCard(registerCard));

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG_MAIN);
        outer.add(tabs, BorderLayout.CENTER);
        return outer;
    }

    private void handleLogin() {
        currentUser = manager.authenticate(txtLoginID.getText().trim(), new String(txtLoginPass.getPassword()));
        if (currentUser != null) {
            navigatePostAuth();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid User ID or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        if (txtRegName.getText().trim().isEmpty() || txtRegPhone.getText().trim().isEmpty() || txtRegPass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = "U" + String.format("%03d", manager.getUserList().size() + 1);
        currentUser = new User(id, txtRegName.getText().trim(), txtRegPhone.getText().trim(), (String) cmbRegType.getSelectedItem(), new String(txtRegPass.getPassword()));
        manager.addUser(currentUser);

        JOptionPane.showMessageDialog(this, " Registration Successful!\n\nYour User ID is: " + id, "Welcome!", JOptionPane.INFORMATION_MESSAGE);
        navigatePostAuth();
    }

    private void navigatePostAuth() {
        boolean isRescuer = "Rescuer".equalsIgnoreCase(currentUser.getUserType());
        boolean isAdopter = "Adopter".equalsIgnoreCase(currentUser.getUserType());

        if ("ADOPT".equals(selectedAction)) {
            if (isRescuer) showAccessDenied("Rescuers cannot adopt pets.");
            else cardLayout.show(mainPanel, "ADOPT_CAT");
        } else if ("RESCUE".equals(selectedAction)) {
            if (isAdopter) showAccessDenied("Adopters cannot surrender pets.");
            else cardLayout.show(mainPanel, "RESCUE_FORM");
        }
    }

    private void showAccessDenied(String message) {
        JOptionPane.showMessageDialog(this, message, "Access Denied", JOptionPane.WARNING_MESSAGE);
        cardLayout.show(mainPanel, "HOME");
    }

    private JPanel createAdoptCategoryCard() {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(BG_MAIN);
        main.setBorder(new EmptyBorder(70, 100, 70, 100));

        JLabel title = new JLabel("Who would you like to meet? 🐾", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Choose your future furry friend", SwingConstants.CENTER);
        subtitle.setFont(FONT_PLAIN);
        subtitle.setForeground(TEXT_MUTED);

        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.setBackground(BG_MAIN);
        heading.add(title);
        heading.add(subtitle);

        JPanel categories = new JPanel(new GridLayout(1, 2, 30, 0));
        categories.setBackground(BG_MAIN);

        JButton dogButton = createLargeActionButton(" Dogs", "Loyal, playful and full of love", ORANGE);
        JButton catButton = createLargeActionButton(" Cats", "Curious, independent and adorable", PURPLE);

        dogButton.addActionListener(e -> loadPets("Dog"));
        catButton.addActionListener(e -> loadPets("Cat"));

        categories.add(dogButton);
        categories.add(catButton);

        main.add(heading, BorderLayout.NORTH);
        main.add(categories, BorderLayout.CENTER);
        return main;
    }

    private JPanel createPetListCard() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG_MAIN);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        lblPetListHeader.setFont(new Font("Segoe UI", Font.BOLD, 25));

        JTable table = new JTable(petTableModel);
        table.setRowHeight(40);
        table.setFont(FONT_PLAIN);
        table.setBackground(WHITE);
        table.setForeground(TEXT_DARK);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(220, 247, 228));
        table.setSelectionForeground(TEXT_DARK);
        table.setShowVerticalLines(false);

        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setPreferredSize(new Dimension(0, 42));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);

        JButton selectButton = createBtn("🐾 Proceed to Adopt Selected Pet", PRIMARY);
        selectButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedPetToAdopt = manager.findPetByID((String) petTableModel.getValueAt(row, 0));
                cardLayout.show(mainPanel, "PAYMENT");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a pet from the table.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(lblPetListHeader, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(selectButton, BorderLayout.SOUTH);
        return panel;
    }

    private void loadPets(String type) {
        petTableModel.setRowCount(0);
        selectedPetToAdopt = null;

        boolean isDog = "Dog".equalsIgnoreCase(type);
        lblPetListHeader.setText(isDog ? "Available Dogs 🐕" : "Available Cats 🐈");

        for (Pet p : manager.getAvailablePets()) {
            if ((isDog && p instanceof Dog) || (!isDog && p instanceof Cat)) {
                petTableModel.addRow(new Object[]{p.getPetID(), p.getName(), p.getAge(), p.getGender(), p.getHealthStatus(), p.calculateAdoptionFee()});
            }
        }
        cardLayout.show(mainPanel, "PET_LIST");
    }

    private JPanel createPaymentCard() {
        styleComboBox(cmbPayment);
        JPanel card = new JPanel(new GridLayout(4, 1, 15, 15));
        card.setBackground(WHITE);

        JLabel title = new JLabel("Complete Adoption Payment 💳", SwingConstants.CENTER);
        title.setFont(FONT_SUBTITLE);

        JButton payButton = createBtn("Confirm & Pay", PRIMARY);
        payButton.addActionListener(e -> handlePayment());

        card.add(title);
        card.add(new JLabel("Select Payment Method:"));
        card.add(cmbPayment);
        card.add(payButton);
        return card;
    }

    private void handlePayment() {
        try {
            String recID = "REC" + String.format("%03d", manager.getRecordList().size() + 101);
            manager.processAdoption(recID, currentUser, selectedPetToAdopt.getPetID());
            AdoptionRecord record = manager.getRecordList().get(manager.getRecordList().size() - 1);

            receiptLabels[0].setText("Record ID: " + record.getRecordID());
            receiptLabels[1].setText("Pet: " + record.getPet().getName() + " (" + record.getPet().getPetID() + ")");
            receiptLabels[2].setText("Adopter: " + record.getAdopter().getName());
            receiptLabels[3].setText("Fee Paid: LKR " + record.getFeePaid());
            receiptLabels[4].setText("Method: " + cmbPayment.getSelectedItem());
            receiptLabels[5].setText("Date: " + record.getDate());

            cardLayout.show(mainPanel, "RECEIPT");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Payment failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createRescueFormCard() {
        styleComboBox(cmbType);
        styleComboBox(cmbGender);

        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(WHITE);

        JLabel title = new JLabel("Rehome a Pet", SwingConstants.CENTER);
        title.setFont(FONT_SUBTITLE);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBackground(WHITE);

        addRow(form, "Pet Type:", cmbType);
        addRow(form, "Name:", txtName);
        addRow(form, "Age:", txtAge);
        addRow(form, "Gender:", cmbGender);
        addRow(form, "Health:", txtHealth);
        addRow(form, "Vaccinated:", chkVax);
        addRow(form, "Breed (Dogs):", txtBreed);

        JPanel options = new JPanel(new FlowLayout(FlowLayout.LEFT));
        options.setBackground(WHITE);

        Runnable updateOptions = () -> {
            options.removeAll();
            boolean isDog = cmbType.getSelectedItem().toString().equalsIgnoreCase("Dog");
            txtBreed.setEnabled(isDog);
            if (isDog) {
                options.add(chkLeash);
            } else {
                options.add(chkIndoor);
                options.add(chkLitter);
            }
            options.revalidate();
            options.repaint();
        };

        cmbType.addActionListener(e -> updateOptions.run());
        updateOptions.run();

        JButton submitBtn = createBtn("Submit Registration", PRIMARY);
        submitBtn.addActionListener(e -> handlePetSurrender());

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(WHITE);
        center.add(form, BorderLayout.CENTER);
        center.add(options, BorderLayout.SOUTH);

        card.add(title, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        card.add(submitBtn, BorderLayout.SOUTH);
        return card;
    }

    private void handlePetSurrender() {
        try {
            String id = "P" + String.format("%03d", manager.getPetList().size() + 1);
            int age = Integer.parseInt(txtAge.getText().trim());
            char gender = cmbGender.getSelectedItem().toString().charAt(0);

            if (cmbType.getSelectedItem().toString().equalsIgnoreCase("Dog")) {
                manager.addPet(new Dog(id, txtName.getText().trim(), age, gender, txtHealth.getText().trim(), chkVax.isSelected(), txtBreed.getText().trim(), chkLeash.isSelected()));
            } else {
                manager.addPet(new Cat(id, txtName.getText().trim(), age, gender, txtHealth.getText().trim(), chkVax.isSelected(), chkIndoor.isSelected(), chkLitter.isSelected()));
            }

            JOptionPane.showMessageDialog(this, "Pet successfully listed for rehoming!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "HOME");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid details.", "Form Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createReceiptCard() {
        JPanel card = new JPanel(new GridLayout(8, 1, 10, 10));
        card.setBackground(WHITE);

        JLabel title = new JLabel("Adoption Receipt", SwingConstants.CENTER);
        title.setFont(FONT_SUBTITLE);
        card.add(title);

        for (int i = 0; i < receiptLabels.length; i++) {
            receiptLabels[i] = new JLabel("", SwingConstants.CENTER);
            receiptLabels[i].setFont(FONT_PLAIN);
            card.add(receiptLabels[i]);
        }

        JButton homeBtn = createBtn("Back to Home Page", PRIMARY);
        homeBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        card.add(homeBtn);
        return card;
    }

    public static void main(String[] args) {
        ShelterManager manager = new ShelterManager();

        if (manager.getUserList().isEmpty()) {
            manager.addUser(new User("U001", "Jane Doe", "0123456789", "Adopter", "1234"));
        }

        if (manager.getPetList().isEmpty()) {
            manager.addPet(new Dog("P001", "Buddy", 3, 'M', "Healthy", true, "Golden Retriever", true));
            manager.addPet(new Dog("P002", "Rex", 4, 'M', "Healthy", true, "German Shepherd", true));
            manager.addPet(new Dog("P003", "Max", 2, 'M', "Healthy", true, "Labrador", true));
            manager.addPet(new Dog("P004", "Bella", 5, 'F', "Healthy", true, "Beagle", true));
            manager.addPet(new Dog("P007", "Rocky", 3, 'M', "Healthy", true, "Poodle", false));

            manager.addPet(new Cat("P005", "Whiskers", 2, 'F', "Healthy", true, true, true));
            manager.addPet(new Cat("P008", "Luna", 2, 'F', "Healthy", true, true, true));
            manager.addPet(new Cat("P009", "Simba", 4, 'M', "Healthy", true, true, false));
            manager.addPet(new Cat("P010", "Cleo", 1, 'F', "Healthy", true, false, true));
            manager.addPet(new Cat("P006", "Milo", 1, 'M', "Healthy", true, true, true));
        }

        SwingUtilities.invokeLater(() -> new PetAppGUI(manager).setVisible(true));
    }
}
