package coursework;

public class Cat extends Pet {

    // Attributes
    private boolean indoorOnly;
    private boolean litterTrained;

    // Parameterized Constructor
    public Cat(String petID, String name, int age,
               String gender, String healthStatus,
               boolean indoorOnly, boolean litterTrained) {

        super(petID, name, age, gender, healthStatus);

        this.indoorOnly = indoorOnly;
        this.litterTrained = litterTrained;
    }

    // Setters
    public void setIndoorOnly(boolean  indoorOnly) {
        this.indoorOnly = indoorOnly;
    }

    public void setLitterTrained(boolean litterTrained) {
        this.litterTrained = litterTrained;
    }

    // Getters
    public boolean getIndoorOnly() {
        return indoorOnly;
    }

    public boolean getLitterTrained() {
        return litterTrained;
    }

    // Overriding care instructions
    @Override
    public String getCareInstructions() {

        if (indoorOnly && litterTrained) 
        {
            return "Keep strictly indoors. Clean litter box daily. " + "Provide fresh wet food.";
            
        } 
        else if (indoorOnly)
        {
            return "Keep strictly indoors. Provide fresh food and water.";
        } 
        else if (litterTrained) 
        {
            return "Clean litter box daily. Provide fresh food and water.";
        } 
        else 
        {
            return "Provide fresh food, water and proper care.";
        }
    }

    // Overriding adoption fee
    @Override
    public double calculateAdoptionFee() {
        return 3000.00;
    }

    // Display Cat information
    @Override
    public void displayPetInfo() {

        super.displayPetInfo();

        System.out.println("Indoor Only: " + indoorOnly);
        System.out.println("Litter Trained: " + litterTrained);
    }
}
