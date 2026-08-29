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
    private Pet selectedPetToAdopt, selectedPetToManage;

    // Colours
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

    // Fonts
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 13);

    // Login/Register
    private final JTextField txtLoginID = createField("");
    private final JPasswordField txtLoginPass = createPasswordField();
    private final JTextField txtRegName = createField("");
    private final JTextField txtRegPhone = createField("");
    private final JPasswordField txtRegPass = createPasswordField();

    // Rehome
    private final JTextField txtName = createField("");
    private final JTextField txtAge = createField("");
    private final JTextField txtHealth = createField("Healthy");
    private final JTextField txtBreed = createField("");

    // Update
    private final JTextField txtUpdateAge = createField("");
    private final JTextField txtUpdateHealth = createField("");
    private final JCheckBox chkUpdateVaccinated = createCheckBox("Vaccinated");

    // Combo boxes
    private final JComboBox<String> cmbRegType = new JComboBox<>(new String[]{"Adopter", "Rescuer"});
    private final JComboBox<String> cmbPayment = new JComboBox<>(new String[]{"Credit Card", "Debit Card", "Cash"});
    private final JComboBox<String> cmbGender = new JComboBox<>(new String[]{"M", "F"});
    private final JComboBox<String> cmbType = new JComboBox<>(new String[]{"Dog", "Cat"});

    // Check boxes
    private final JCheckBox chkVax = createCheckBox("Vaccinated");
    private final JCheckBox chkLeash = createCheckBox("Leash Trained");
    private final JCheckBox chkIndoor = createCheckBox("Indoor Only");
    private final JCheckBox chkLitter = createCheckBox("Litter Trained");

    // Tables
    private final JLabel lblPetListHeader = new JLabel("Available Pets", SwingConstants.CENTER);

    private final DefaultTableModel petTableModel = new DefaultTableModel(
        new String[]{"Pet ID", "Name", "Age", "Gender", "Health", "Fee (LKR)"}, 0
    );

    private final DefaultTableModel managePetTableModel = new DefaultTableModel(
        new String[]{"Pet ID", "Type", "Name", "Age", "Gender", "Health", "Vaccinated", "Status"}, 0
    );

    private JTable managePetTable;
    private final JLabel[] receiptLabels = new JLabel[6];

    public PetAppGUI(ShelterManager manager) {
        this.manager = manager;

        setTitle("Pet Adoption Portal");
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
        mainPanel.add(createManagePetsCard(), "MANAGE_PETS");
        mainPanel.add(wrapCard(createReceiptCard()), "RECEIPT");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    // COMPONENT METHODS

    private static JTextField createField(String text) {
        JTextField field = new JTextField(text);
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

    private static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
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
        JCheckBox box = new JCheckBox(text);
        box.setBackground(WHITE);
        box.setForeground(TEXT_DARK);
        box.setFont(FONT_PLAIN);
        box.setFocusPainted(false);
        return box;
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setBackground(WHITE);
        box.setForeground(TEXT_DARK);
        box.setFont(FONT_PLAIN);
        box.setBorder(new LineBorder(BORDER_COLOR, 1, true));
    }

    private JButton createBtn(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.addChangeListener(e ->
            button.setBackground(button.getModel().isRollover() ? color.brighter() : color)
        );
        return button;
    }

  private JButton createLargeActionButton(String title, String description, Color color) { 
    String text = "<html><div style='text-align:center'>" +  "<span style='font-size:22px'><b>" +
     title + "</b></span><br><br>" + "<span style='font-size:11px'>" 
    + description + "</span>" + "</div></html>";

        JButton button = createBtn(text, color);
        button.setBorder(new EmptyBorder(25, 25, 25, 25));
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

    private void addRow(JPanel panel, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_MUTED);
        label.setFont(FONT_BOLD);
        panel.add(label);
        panel.add(component);
    }

    // HOME PAGE

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
        title.setForeground(WHITE);
        title.setFont(FONT_TITLE);

        JLabel subtitle = new JLabel("Find a best friend. Change a life!");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(230, 255, 240));
        subtitle.setFont(FONT_PLAIN);

        hero.add(icon);
        hero.add(Box.createVerticalStrut(8));
        hero.add(title);
        hero.add(Box.createVerticalStrut(8));
        hero.add(subtitle);

        JPanel actions = new JPanel(new GridLayout(2, 1, 20, 20));
        actions.setBackground(BG_MAIN);
        actions.setBorder(new EmptyBorder(40, 150, 40, 150));

        JButton adopt = createLargeActionButton(
            "🐾 Adopt a Pet",
            "Find your perfect furry companion",
            ORANGE
        );

        JButton rescue = createLargeActionButton(
            "🏠 Rehome a Pet",
            "Give a pet a second chance",
            PINK
        );

        adopt.addActionListener(e -> navigateAuth("ADOPT"));
        rescue.addActionListener(e -> navigateAuth("RESCUE"));

        actions.add(adopt);
        actions.add(rescue);

        main.add(hero, BorderLayout.NORTH);
        main.add(actions, BorderLayout.CENTER);
        return main;
    }

    private void navigateAuth(String action) {
        selectedAction = action;
        cardLayout.show(mainPanel, "AUTH");
    }

    // LOGIN & REGISTER

    private JPanel createAuthCard() {
        styleComboBox(cmbRegType);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_BOLD);

        JPanel login = new JPanel(new BorderLayout(15, 20));
        login.setBackground(WHITE);

        JLabel loginTitle = new JLabel("Welcome Back!", SwingConstants.CENTER);
        loginTitle.setFont(FONT_SUBTITLE);
        loginTitle.setForeground(PRIMARY_DARK);

        JPanel loginForm = new JPanel(new GridLayout(2, 2, 15, 18));
        loginForm.setBackground(WHITE);

        addRow(loginForm, "User ID:", txtLoginID);
        addRow(loginForm, "Password:", txtLoginPass);

        JButton loginBtn = createBtn("Login", PRIMARY);
        loginBtn.addActionListener(e -> handleLogin());

        login.add(loginTitle, BorderLayout.NORTH);
        login.add(loginForm, BorderLayout.CENTER);
        login.add(loginBtn, BorderLayout.SOUTH);

        JPanel register = new JPanel(new BorderLayout(15, 20));
        register.setBackground(WHITE);

        JLabel registerTitle = new JLabel("Create Your Account!", SwingConstants.CENTER);
        registerTitle.setFont(FONT_SUBTITLE);
        registerTitle.setForeground(PURPLE);

        JPanel registerForm = new JPanel(new GridLayout(4, 2, 15, 15));
        registerForm.setBackground(WHITE);

        addRow(registerForm, "Full Name:", txtRegName);
        addRow(registerForm, "Phone:", txtRegPhone);
        addRow(registerForm, "Password:", txtRegPass);
        addRow(registerForm, "User Role:", cmbRegType);

        JButton registerBtn = createBtn("Create Account", PURPLE);
        registerBtn.addActionListener(e -> handleRegister());

        register.add(registerTitle, BorderLayout.NORTH);
        register.add(registerForm, BorderLayout.CENTER);
        register.add(registerBtn, BorderLayout.SOUTH);

        tabs.addTab("Login", wrapCard(login));
        tabs.addTab("Register", wrapCard(register));

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG_MAIN);
        outer.add(tabs);
        return outer;
    }

    private void handleLogin() {

    String userID = txtLoginID.getText().trim();
    String password = new String(txtLoginPass.getPassword());

    // Check empty fields
    if (userID.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Please enter your User ID and password.",
            "Missing Information",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // Authenticate user
    currentUser = manager.authenticate(userID, password);

    if (currentUser != null) {

        JOptionPane.showMessageDialog(
            this,
            "Login successful. Welcome " + currentUser.getName() + "!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        navigatePostAuth();

    } else {

        JOptionPane.showMessageDialog(
            this,
            "Incorrect User ID or password.",
            "Login Failed",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

    private void handleRegister() {

    String name = txtRegName.getText().trim();
    String phone = txtRegPhone.getText().trim();
    String password = new String(txtRegPass.getPassword());
    String userType = cmbRegType.getSelectedItem().toString();

    // Check empty fields
    if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Please fill in all fields.",
            "Missing Information",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // Validate name
    if (!name.matches("[a-zA-Z ]+")) {
        JOptionPane.showMessageDialog(
            this,
            "Name should contain letters only.",
            "Invalid Name",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // Validate phone number
    if (!phone.matches("\\d{10}")) {
        JOptionPane.showMessageDialog(
            this,
            "Please enter a valid 10-digit phone number.",
            "Invalid Phone Number",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // Validate password length
    if (password.length() < 4) {
        JOptionPane.showMessageDialog(
            this,
            "Password must contain at least 4 characters.",
            "Weak Password",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // Generate User ID
    String id = "U" + String.format("%03d",
            manager.getUserList().size() + 1);

    // Create user
    currentUser = new User(
        id,
        name,
        phone,
        userType,
        password
    );

    manager.addUser(currentUser);

    JOptionPane.showMessageDialog(
        this,
        "Registration Successful!\n\nYour User ID is: " + id,
        "Welcome",
        JOptionPane.INFORMATION_MESSAGE
    );

    navigatePostAuth();
}

    private void navigatePostAuth() {
        boolean rescuer = currentUser.getUserType().equalsIgnoreCase("Rescuer");
        boolean adopter = currentUser.getUserType().equalsIgnoreCase("Adopter");

        if (selectedAction.equals("ADOPT")) {
            if (rescuer) showAccessDenied("Rescuers cannot adopt pets.");
            else cardLayout.show(mainPanel, "ADOPT_CAT");
        }

        if (selectedAction.equals("RESCUE")) {
            if (adopter) showAccessDenied("Adopters cannot surrender pets.");
            else cardLayout.show(mainPanel, "RESCUE_FORM");
        }
    }

    private void showAccessDenied(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Access Denied",
            JOptionPane.WARNING_MESSAGE
        );
        cardLayout.show(mainPanel, "HOME");
    }

    // ADOPT CATEGORY

    private JPanel createAdoptCategoryCard() {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(BG_MAIN);
        main.setBorder(new EmptyBorder(70, 100, 70, 100));

        JLabel title = new JLabel("Who would you like to meet?", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel(
            "Choose your future furry friend",
            SwingConstants.CENTER
        );
        subtitle.setFont(FONT_PLAIN);
        subtitle.setForeground(TEXT_MUTED);

        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.setBackground(BG_MAIN);
        heading.add(title);
        heading.add(subtitle);

        JPanel categories = new JPanel(new GridLayout(1, 2, 30, 0));
        categories.setBackground(BG_MAIN);

        JButton dogs = createLargeActionButton(
            "🐕 Dogs",
            "Loyal, playful and full of love",
            ORANGE
        );

        JButton cats = createLargeActionButton(
            "🐈 Cats",
            "Curious, independent and adorable",
            PURPLE
        );

        dogs.addActionListener(e -> loadPets("Dog"));
        cats.addActionListener(e -> loadPets("Cat"));

        categories.add(dogs);
        categories.add(cats);

        main.add(heading, BorderLayout.NORTH);
        main.add(categories, BorderLayout.CENTER);
        return main;
    }

    // PET LIST

    private JPanel createPetListCard() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG_MAIN);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        lblPetListHeader.setFont(FONT_TITLE);

        JTable table = new JTable(petTableModel);
        styleTable(table);

       JButton selectButton = createBtn("Proceed to Adopt Selected Pet", PRIMARY);

selectButton.addActionListener(e -> {
    int row = table.getSelectedRow();

    if (row != -1) {
        selectedPetToAdopt = manager.findPetByID(
            (String) petTableModel.getValueAt(row, 0)
        );
        cardLayout.show(mainPanel, "PAYMENT");
    } else {
        JOptionPane.showMessageDialog(
            this,
            "Please select a pet from the table.",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE
        );
    }
});

JButton backButton = createBtn(
    "Back to Dog/Cat Selection",
    PRIMARY_DARK
);

backButton.addActionListener(
    e -> cardLayout.show(mainPanel, "ADOPT_CAT")
);

     JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    buttonPanel.setBackground(BG_MAIN);

    buttonPanel.add(backButton);
    buttonPanel.add(selectButton);

        panel.add(lblPetListHeader, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(FONT_PLAIN);
        table.setBackground(WHITE);
        table.setForeground(TEXT_DARK);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(220, 247, 228));
        table.setSelectionForeground(TEXT_DARK);
        table.setShowVerticalLines(false);

        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setFont(FONT_BOLD);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);
    }

    private void loadPets(String type) {
        petTableModel.setRowCount(0);
        selectedPetToAdopt = null;

        boolean dog = type.equalsIgnoreCase("Dog");
        lblPetListHeader.setText(dog ? "Available Dogs" : "Available Cats");

        for (Pet pet : manager.getAvailablePets()) {
            boolean correctType =
                (dog && pet instanceof Dog) ||
                (!dog && pet instanceof Cat);

            if (correctType) {
                petTableModel.addRow(new Object[]{
                    pet.getPetID(),
                    pet.getName(),
                    pet.getAge(),
                    pet.getGender(),
                    pet.getHealthStatus(),
                    pet.calculateAdoptionFee()
                });
            }
        }

        cardLayout.show(mainPanel, "PET_LIST");
    }

    // PAYMENT

    private JPanel createPaymentCard() {
        styleComboBox(cmbPayment);

        JPanel card = new JPanel(new GridLayout(4, 1, 15, 15));
        card.setBackground(WHITE);

        JLabel title = new JLabel(
            "Complete Adoption Payment",
            SwingConstants.CENTER
        );
        title.setFont(FONT_SUBTITLE);

        JButton pay = createBtn("Confirm & Pay", PRIMARY);
        pay.addActionListener(e -> handlePayment());

        card.add(title);
        card.add(new JLabel("Select Payment Method:"));
        card.add(cmbPayment);
        card.add(pay);

        return card;
    }

    private void handlePayment() {
        if (selectedPetToAdopt == null) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a pet first.",
                "No Pet Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            String id = "REC" + String.format(
                "%03d",
                manager.getRecordList().size() + 101
            );

            manager.processAdoption(
                id,
                currentUser,
                selectedPetToAdopt.getPetID()
            );

            AdoptionRecord record =
                manager.getRecordList()
                .get(manager.getRecordList().size() - 1);

            receiptLabels[0].setText("Record ID: " + record.getRecordID());
            receiptLabels[1].setText(
                "Pet: " + record.getPet().getName()
                + " (" + record.getPet().getPetID() + ")"
            );
            receiptLabels[2].setText(
                "Adopter: " + record.getAdopter().getName()
            );
            receiptLabels[3].setText(
                "Fee Paid: LKR " + record.getFeePaid()
            );
            receiptLabels[4].setText(
                "Method: " + cmbPayment.getSelectedItem()
            );
            receiptLabels[5].setText(
                "Date: " + record.getDate()
            );

            cardLayout.show(mainPanel, "RECEIPT");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Payment failed: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // REHOME PET

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

            boolean dog =
                cmbType.getSelectedItem()
                .toString()
                .equalsIgnoreCase("Dog");

            txtBreed.setEnabled(dog);

            if (dog) {
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

        JButton submit = createBtn("Submit Registration", PRIMARY);
        JButton manage = createBtn("Manage Existing Pets", PURPLE);

        submit.addActionListener(e -> handlePetSurrender());

        manage.addActionListener(e -> {
            loadManagePets();
            cardLayout.show(mainPanel, "MANAGE_PETS");
        });

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(WHITE);
        center.add(form, BorderLayout.CENTER);
        center.add(options, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
        buttons.setBackground(WHITE);
        buttons.add(submit);
        buttons.add(manage);

        card.add(title, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        return card;
    }

    private void handlePetSurrender() {
        try {
            String name = txtName.getText().trim();
            String health = txtHealth.getText().trim();
            String id = "P" + String.format(
                "%03d",
                manager.getPetList().size() + 1
            );

            if (name.isEmpty() || health.isEmpty()) {
                throw new IllegalArgumentException();
            }

            int age = Integer.parseInt(txtAge.getText().trim());

            if (age < 0) {
                throw new IllegalArgumentException();
            }

            char gender =
                cmbGender.getSelectedItem()
                .toString()
                .charAt(0);

            if (cmbType.getSelectedItem().equals("Dog")) {
                manager.addPet(new Dog(
                    id, name, age, gender, health,
                    chkVax.isSelected(),
                    txtBreed.getText().trim(),
                    chkLeash.isSelected()
                ));
            } else {
                manager.addPet(new Cat(
                    id, name, age, gender, health,
                    chkVax.isSelected(),
                    chkIndoor.isSelected(),
                    chkLitter.isSelected()
                ));
            }

            JOptionPane.showMessageDialog(
                this,
                "Pet successfully listed for rehoming!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

            clearPetForm();
            cardLayout.show(mainPanel, "HOME");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter valid pet details.",
                "Form Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearPetForm() {
        txtName.setText("");
        txtAge.setText("");
        txtHealth.setText("Healthy");
        txtBreed.setText("");
        chkVax.setSelected(false);
        chkLeash.setSelected(false);
        chkIndoor.setSelected(false);
        chkLitter.setSelected(false);
    }

    // MANAGE PETS

    private JPanel createManagePetsCard() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG_MAIN);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel title = new JLabel("Manage Pets", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        title.setForeground(PRIMARY_DARK);

        managePetTable = new JTable(managePetTableModel);
        managePetTable.setRowHeight(35);
        styleTable(managePetTable);

        JPanel updatePanel = new JPanel(new GridLayout(2, 4, 10, 10));
        updatePanel.setBackground(WHITE);
        updatePanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        updatePanel.add(new JLabel("Update Age:"));
        updatePanel.add(txtUpdateAge);
        updatePanel.add(new JLabel("Update Health:"));
        updatePanel.add(txtUpdateHealth);
        updatePanel.add(new JLabel("Vaccination:"));
        updatePanel.add(chkUpdateVaccinated);

        JButton update = createBtn("Update Selected Pet", PRIMARY);
        JButton delete = createBtn("Delete Selected Pet", PINK);

        updatePanel.add(update);
        updatePanel.add(delete);

        managePetTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedPet();
        });

        update.addActionListener(e -> updateSelectedPet());
        delete.addActionListener(e -> deleteSelectedPet());

        JButton back = createBtn("Back to Home", PRIMARY_DARK);
        back.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setBackground(BG_MAIN);
        bottom.add(updatePanel, BorderLayout.CENTER);
        bottom.add(back, BorderLayout.SOUTH);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(managePetTable), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void loadSelectedPet() {
        int row = managePetTable.getSelectedRow();

        if (row == -1) return;

        String id = managePetTableModel.getValueAt(row, 0).toString();
        selectedPetToManage = manager.findPetByID(id);

        if (selectedPetToManage != null) {
            txtUpdateAge.setText(
                String.valueOf(selectedPetToManage.getAge())
            );
            txtUpdateHealth.setText(
                selectedPetToManage.getHealthStatus()
            );
            chkUpdateVaccinated.setSelected(
                selectedPetToManage.isVaccinated()
            );
        }
    }

    private void updateSelectedPet() {
        if (selectedPetToManage == null) {
            showMessage("Please select a pet first.", "No Pet Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(
                txtUpdateAge.getText().trim()
            );

            String health =
                txtUpdateHealth.getText().trim();

            if (age < 0 || health.isEmpty()) {
                throw new IllegalArgumentException();
            }

            boolean updated = manager.updatePet(
                selectedPetToManage.getPetID(),
                age,
                health,
                chkUpdateVaccinated.isSelected()
            );

            if (updated) {
                showMessage(
                    "Pet details updated successfully.",
                    "Update Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );

                clearUpdateForm();
                loadManagePets();
            }

        } catch (Exception ex) {
            showMessage(
                "Please enter valid pet details.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteSelectedPet() {
        if (selectedPetToManage == null) {
            showMessage(
                "Please select a pet first.",
                "No Pet Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete "
            + selectedPetToManage.getName() + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = manager.deletePet(
                selectedPetToManage.getPetID()
            );

            if (deleted) {
                showMessage(
                    "Pet deleted successfully.",
                    "Delete Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );

                clearUpdateForm();
                loadManagePets();
            }
        }
    }

    private void clearUpdateForm() {
        selectedPetToManage = null;
        txtUpdateAge.setText("");
        txtUpdateHealth.setText("");
        chkUpdateVaccinated.setSelected(false);
    }

    private void loadManagePets() {
        managePetTableModel.setRowCount(0);

        for (Pet pet : manager.getPetList()) {
            String type = pet instanceof Dog ? "Dog" : "Cat";
            String vaccinated = pet.isVaccinated() ? "Yes" : "No";
            String status = pet.isAdopted() ? "Adopted" : "Available";

            managePetTableModel.addRow(new Object[]{
                pet.getPetID(),
                type,
                pet.getName(),
                pet.getAge(),
                pet.getGender(),
                pet.getHealthStatus(),
                vaccinated,
                status
            });
        }
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    // RECEIPT

    private JPanel createReceiptCard() {
        JPanel card = new JPanel(new GridLayout(8, 1, 10, 10));
        card.setBackground(WHITE);

        JLabel title = new JLabel(
            "Adoption Receipt",
            SwingConstants.CENTER
        );

        title.setFont(FONT_SUBTITLE);
        card.add(title);

        for (int i = 0; i < receiptLabels.length; i++) {
            receiptLabels[i] = new JLabel("", SwingConstants.CENTER);
            receiptLabels[i].setFont(FONT_PLAIN);
            card.add(receiptLabels[i]);
        }

        JButton home = createBtn("Back to Home Page", PRIMARY);
        home.addActionListener(e ->
            cardLayout.show(mainPanel, "HOME")
        );

        card.add(home);
        return card;
    }

    // MAIN

    public static void main(String[] args) {
        ShelterManager manager = new ShelterManager();

        manager.addUser(
            new User(
                "U001",
                "Jane Doe",
                "0123456789",
                "Adopter",
                "1234"
            )
        );

        manager.addPet(new Dog(
            "P001", "Buddy", 3, 'M',
            "Healthy", true,
            "Golden Retriever", true
        ));

        manager.addPet(new Dog(
            "P002", "Rex", 4, 'M',
            "Healthy", true,
            "German Shepherd", true
        ));

        manager.addPet(new Dog(
            "P003", "Max", 2, 'M',
            "Healthy", true,
            "Labrador", true
        ));

        manager.addPet(new Dog(
            "P004", "Bella", 5, 'F',
            "Healthy", true,
            "Beagle", true
        ));

        manager.addPet(new Dog(
            "P007", "Rocky", 3, 'M',
            "Healthy", true,
            "Poodle", false
        ));

        manager.addPet(new Cat(
            "P005", "Whiskers", 2, 'F',
            "Healthy", true, true, true
        ));

        manager.addPet(new Cat(
            "P006", "Milo", 1, 'M',
            "Healthy", true, true, true
        ));

        manager.addPet(new Cat(
            "P008", "Luna", 2, 'F',
            "Healthy", true, true, true
        ));

        manager.addPet(new Cat(
            "P009", "Simba", 4, 'M',
            "Healthy", true, true, false
        ));

        manager.addPet(new Cat(
            "P010", "Cleo", 1, 'F',
            "Healthy", true, false, true
        ));

        SwingUtilities.invokeLater(() ->
            new PetAppGUI(manager).setVisible(true)
        );
    }
}