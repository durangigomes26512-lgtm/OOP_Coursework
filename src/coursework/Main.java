package coursework;

public class Main {

    public static void main(String[] args) {

        // Create ShelterManager
        ShelterManager manager = new ShelterManager();

        // =========================
        // CREATE USERS
        // =========================

        User adopter = new User(
                "U001",
                "Jane Doe",
                "0123456789",
                "Adopter",
                "1234"
        );

        User rescuer = new User(
                "U002",
                "John Smith",
                "0987654321",
                "Rescuer",
                "5678"
        );

        // Add users to manager
        manager.addUser(adopter);
        manager.addUser(rescuer);


        // =========================
        // CREATE DOGS
        // =========================

        Dog dog1 = new Dog(
                "P001",
                "Buddy",
                3,
                'M',
                "Healthy",
                true,
                "Golden Retriever",
                true
        );

        Dog dog2 = new Dog(
                "P002",
                "Bella",
                2,
                'F',
                "Healthy",
                false,
                "Poodle",
                false
        );


        // =========================
        // CREATE CATS
        // =========================

        Cat cat1 = new Cat(
                "P003",
                "Whiskers",
                2,
                'F',
                "Healthy",
                true,
                true,
                true
        );

        Cat cat2 = new Cat(
                "P004",
                "Milo",
                1,
                'M',
                "Healthy",
                false,
                false,
                true
        );


        // =========================
        // ADD PETS
        // =========================

        manager.addPet(dog1);
        manager.addPet(dog2);
        manager.addPet(cat1);
        manager.addPet(cat2);


        // =========================
        // DISPLAY USER DETAILS
        // =========================

        System.out.println("===== USER DETAILS =====");

        adopter.displayUserInfo();

        System.out.println();

        rescuer.displayUserInfo();


        // =========================
        // DISPLAY PET DETAILS
        // =========================

        System.out.println("\n===== PET DETAILS =====");

        System.out.println("Pet ID: " + dog1.getPetID());
        System.out.println("Name: " + dog1.getName());
        System.out.println("Breed: " + dog1.getBreed());
        System.out.println("Adoption Fee: Rs. " + dog1.calculateAdoptionFee());
        System.out.println("Care: " + dog1.getCareInstructions());

        System.out.println();

        System.out.println("Pet ID: " + cat1.getPetID());
        System.out.println("Name: " + cat1.getName());
        System.out.println("Adoption Fee: Rs. " + cat1.calculateAdoptionFee());
        System.out.println("Care: " + cat1.getCareInstructions());


        // =========================
        // DISPLAY AVAILABLE PETS
        // =========================

        System.out.println("\n===== AVAILABLE PETS =====");

        for (Pet pet : manager.getAvailablePets()) {
            System.out.println(
                    pet.getPetID() + " - " +
                    pet.getName() + " - " +
                    pet.getClass().getSimpleName()
            );
        }


        // =========================
        // AUTHENTICATE USER
        // =========================

        System.out.println("\n===== LOGIN TEST =====");

        User loggedUser = manager.authenticate("U001", "1234");

        if (loggedUser != null) {
            System.out.println("Login successful!");
            System.out.println("Welcome, " + loggedUser.getName());
        } else {
            System.out.println("Invalid username or password.");
        }


        // =========================
        // PROCESS ADOPTION
        // =========================

        System.out.println("\n===== PROCESS ADOPTION =====");

        manager.processAdoption(
                "REC001",
                adopter,
                "P001"
        );


        // =========================
        // DISPLAY AVAILABLE PETS
        // AFTER ADOPTION
        // =========================

        System.out.println("\n===== AVAILABLE PETS AFTER ADOPTION =====");

        for (Pet pet : manager.getAvailablePets()) {
            System.out.println(
                    pet.getPetID() + " - " +
                    pet.getName()
            );
        }


        // =========================
        // DISPLAY ALL RECORDS
        // =========================

        System.out.println("\n===== ALL ADOPTION RECORDS =====");

        manager.displayAllRecords();


        // =========================
        // TEST FIND PET
        // =========================

        System.out.println("\n===== FIND PET =====");

        Pet foundPet = manager.findPetByID("P003");

        if (foundPet != null) {
            System.out.println("Pet found: " + foundPet.getName());
        } else {
            System.out.println("Pet not found.");
        }


        // =========================
        // TEST DELETE PET
        // =========================

        System.out.println("\n===== DELETE PET =====");

        boolean deleted = manager.deletePet("P004");

        if (deleted) {
            System.out.println("Pet P004 deleted successfully.");
        } else {
            System.out.println("Pet not found.");
        }


        // =========================
        // FINAL PET COUNT
        // =========================

        System.out.println("\nTotal pets in shelter: "
                + manager.getPetList().size());
    }
}
