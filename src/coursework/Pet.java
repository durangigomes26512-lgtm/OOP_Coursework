package coursework;

public class Pet {

    // Attributes
    private String petID;
    private String name;
    private int age;
    private String gender;
    private String healthStatus;
    private boolean adoptionStatus;

    // Parameterized Constructor
    public Pet(String petID, String name, int age, String gender, String healthStatus) {
        this.petID = petID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.adoptionStatus = false; // false = Not Adopted
    }

    // Setters
    public void setPetID(String petID) {
        this.petID = petID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setAdoptionStatus(boolean adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    // Getters
    public String getPetID() {
        return petID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public boolean getAdoptionStatus() {
        return adoptionStatus;
    }

    // Method to calculate adoption fee
    public double calculateAdoptionFee() {
        return 0.0;
    }

    // General care instructions
    public String getCareInstructions() {
    	return "Please give the pet food, clean water and take good care of it";
    }

    // Display pet information
    public void displayPetInfo() {
        System.out.println("Pet ID: " + petID);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Health Status: " + healthStatus);

        if (adoptionStatus) {
            System.out.println("Adoption Status: Adopted");
        } else {
            System.out.println("Adoption Status: Not Adopted");
        }
    }
}
   
    	
    
