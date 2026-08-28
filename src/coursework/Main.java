package coursework;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        // 1. Set the System Look and Feel
        // (Optional: makes the app look more modern)
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.err.println("Could not set look and feel.");
        }

        // 2. Initialize the backend manager
        ShelterManager shelter = new ShelterManager();

        // 3. Pre-populate with sample Users
        // (ID, Name, Phone, Role, Password)

        // One Adopter for testing login/adoption
        shelter.addUser(
            new User("U001", "Kamal Perera", "0771234567",
                    "Adopter", "pass123")
        );

        // One Rescuer for testing login/rescue
        shelter.addUser(
            new User("U002", "Nimali Silva", "0719876543",
                    "Rescuer", "rescuer456")
        );

        // 4. Pre-populate with sample Pets
        // Dogs: (ID, name, age, gender, health, vaccinated, breed, leashTrained)

        shelter.addPet(
            new Dog("D001", "Buddy", 3, 'M', "Healthy",
                    true, "Golden Retriever", true)
        );

        shelter.addPet(
            new Dog("D002", "Max", 1, 'M', "Excellent",
                    true, "German Shepherd", false)
        );

        shelter.addPet(
            new Dog("D003", "Daisy", 4, 'F', "Healthy",
                    false, "Local Mix", true)
        );

        // Cats: (ID, name, age, gender, health, vaccinated,
        //        indoorOnly, litterTrained)

        shelter.addPet(
            new Cat("C001", "Luna", 2, 'F', "Healthy",
                    true, true, true)
        );

        shelter.addPet(
            new Cat("C002", "Milo", 1, 'M', "Healthy",
                    false, false, true)
        );

        shelter.addPet(
            new Cat("C003", "Kitty", 5, 'F', "Active",
                    true, true, false)
        );

        // 5. Launch the Graphical Interface
        // We use SwingUtilities.invokeLater to ensure thread safety for the GUI
        SwingUtilities.invokeLater(() -> {
            PetAppGUI gui = new PetAppGUI(shelter);
            gui.setVisible(true);
        });

        // 6. Console Output
        // (To confirm the system started correctly)
        System.out.println("==================================");
        System.out.println("   Pet Adoption System is active  ");
        System.out.println("   Pets Loaded: " + shelter.getPetList().size());
        System.out.println("   Users Loaded: " + shelter.getUserList().size());
        System.out.println("==================================");
    }
}

