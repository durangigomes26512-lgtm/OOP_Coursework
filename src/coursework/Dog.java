package coursework;
public class Dog extends Pet {
    // Attributes
    private String breed;
    private boolean leashTrained;

    // Parameterized Constructor
    public Dog(String petID, String name, int age,char gender, String healthStatus,boolean isVaccinated, String breed, boolean leashTrained) {
        super(petID, name, age, gender, healthStatus,isVaccinated);
        this.breed = breed;
        this.leashTrained = leashTrained;
    }

    // Setters
    public void setBreed(String breed)
	{
		this.breed = breed;
	}
    public void setLeashTrained(boolean leashTrained) {
		this.leashTrained = leashTrained;
	}
    // Getters
    public String getBreed() {
		return breed;
	}
    public boolean isLeashTrained() {
		return leashTrained;
	}

    // Overriding care instructions
    @Override
    public String getCareInstructions() {
		String instructions = "Daily walks required. Leash Trained: ";
		if (isLeashTrained()) 
		{
		 instructions+="Yes";
		}
		else 
		{
			instructions+="No";
		}
		return instructions;	
    }

    // Overriding adoption fee
    @Override
    public double calculateAdoptionFee() {
		/*base fee to adopt = 5000, if pet is vaccinated / leash trained
		 additional fee of 3000*/
		double fee=5000;
		if (isVaccinated()) 
		{
			fee+=3000;
		}
		if (isLeashTrained())
		{
			fee+=4000;
		}
		return fee;
    }
}
