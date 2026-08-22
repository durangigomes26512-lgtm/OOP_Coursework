package coursework;

public class Dog extends Pet {

    // Attributes
    private String breed;
    private boolean leashTrained;

    // Parameterized Constructor
    public Dog(String petID, String name, int age,
               String gender, String healthStatus,
               String breed, boolean leashTrained) {

        super(petID, name, age, gender, healthStatus);

        this.breed = breed;
        this.leashTrained = leashTrained;
    }

    // Setters
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setLeashTrained(boolean leashTrained) {
        this.leashTrained = leashTrained;
    }

    // Getters
    public String getBreed() {
        return breed;
    }

    public boolean getLeashTrained() {
        return leashTrained;
    }

    // Overriding care instructions
    @Override
    public String getCareInstructions() {
        return "Requires 2 daily walks. Outdoor exercise space.";
    }

    // Overriding adoption fee
    @Override
    public double calculateAdoptionFee() {
        return 2000.00;
    }

    // Display Dog information
    @Override
    public void displayPetInfo() {

        super.displayPetInfo();

        System.out.println("Breed: " + breed);
        System.out.println("Leash Trained: " + leashTrained);
    }
}