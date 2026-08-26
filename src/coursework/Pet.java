package coursework;
public abstract class Pet {
    // Attributes
    private String petID;
    private String name;
    private int age;
    private char gender;
    private String healthStatus;
    private boolean isVaccinated;
    private boolean isAdopted;

    // Parameterized Constructor
    public Pet(String petID, String name, int age, char gender, String healthStatus,boolean isVaccinated) {
		this.petID = petID;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.healthStatus = healthStatus;
		this.isAdopted = false; // false = Not Adopted
		this.isVaccinated = isVaccinated; 
    }

    // Setters
	//no setters for petID, name and gender bcz they usually stay the same
	public void setAge(int age) 
	{
		this.age=age;
	}
	public void setHealthStatus(String health) 
	{
		this.healthStatus=health;
	}
	public void setIsAdopted(boolean isadopted)
	{
		this.isAdopted=isadopted;
	}
	public void setIsVaccinated(boolean isvaccinated)
	{
		this.isVaccinated=isvaccinated;
	}
    // Getters
    public String getPetID() 
	{
		return petID;
	}
    public String getName()
	{
		return name;
	}
    public int getAge() {
		return age;
	}
    public char getGender() 
	{
		return gender;
	}
    public String getHealthStatus() 
	{
		return healthStatus;
	}
    public boolean isAdopted() 
	{
		return isAdopted;
	}
    public boolean isVaccinated() {
		return isVaccinated;
	}

    //below classes must be overridden by dog and cat class
    // Method to calculate adoption fee
    public abstract double calculateAdoptionFee();
    // General care instructions
	public abstract String getCareInstructions();
}
