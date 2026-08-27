package coursework;
public class Cat extends Pet {
    // Attributes
    private boolean indoorOnly;
    private boolean litterTrained;
	
    // Parameterized Constructor
    public Cat(String petID, String name, int age,char gender, String healthStatus,boolean isVaccinated,boolean indoorOnly, boolean litterTrained) {
		
        super(petID,name,age,gender,healthStatus,isVaccinated);
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
    public boolean isIndoorOnly() {
		return indoorOnly;
	}
    public boolean isLitterTrained() {
		return litterTrained;
	}

    // Overriding care instructions
    @Override
    public String getCareInstructions() {
		String instructions ="Provide fresh wet food. ";
        if (isIndoorOnly()) {
            instructions+= "Keep strictly indoors. ";
        } 
        if (isLitterTrained()){
           instructions+="Clean litter box daily. ";
        }
        if (!isLitterTrained() && !isIndoorOnly()) {
        	instructions+="Provide proper care. ";
        }
        return instructions;
    }

    // Overriding adoption fee
    @Override
    public double calculateAdoptionFee() {
		double fee=3000;
		if (isVaccinated()) {
			fee+=1800;
		}
		if (isLitterTrained()) {
			fee+=2200;
		}
		return fee;
	}
}
