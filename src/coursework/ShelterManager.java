package coursework;
import java.util.ArrayList;
public class ShelterManager {
	private ArrayList<Pet> petList;
	private ArrayList<AdoptionRecord> recordList;
	private ArrayList<User> userList;
	
	//constructor
	public ShelterManager() {
		this.petList=new ArrayList<>(); //Initializing the empty list
		this.recordList=new ArrayList<>();
		this.userList=new ArrayList<>();
	}
	
	//getters
	public ArrayList<Pet> getPetList(){return petList;}
	public ArrayList<AdoptionRecord> getRecordList(){return recordList;}
	public ArrayList<User> getUserList(){return userList;}
	
	//methods
	public void addPet(Pet pet) {
		petList.add(pet);
	}
	public boolean deletePet(String petID) {
		//find pet from petid in other method below
		Pet petToDelete=findPetByID(petID);
		if (petToDelete!=null) {
			petList.remove(petToDelete);	
			return true; //pet successfully deleted
		}
		return false; //pet not deleted
	}
	
	public void addUser(User user){
		userList.add(user);
	}
	
	public User findUserByID(String userID) {
		for (User user:userList) {
			if (user.getUserID().equalsIgnoreCase(userID)) {
				return user;
			}
		}
		return null;
	}
	
	public User authenticate(String userID,String password) {
		User user = findUserByID(userID);
		if (user!=null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}
	
	public ArrayList<Pet> getAvailablePets() { //return type -> creates and returns a new list of only unadopted pets 
		ArrayList<Pet> availablePets=new ArrayList<>();
		for (Pet pet: petList) { //iterates through the whole petList array
			if (pet.isAdopted()==false) {
				availablePets.add(pet);
			}
		}
		return availablePets; //returns the array list of available pets
	}
	
	public Pet findPetByID(String petID) {
			for (Pet pet:petList) {
				if (pet.getPetID().equalsIgnoreCase(petID)) {
					return pet; //returns the whole pet object
				}
			}
			return null; //when there is no matching id
	}
	
	public void processAdoption(String recordID,User user,String petID) {
		Pet pet=findPetByID(petID);
		if (pet==null) {
			throw new IllegalArgumentException("Pet with ID "+petID+" does not exist.");
		}
		if (pet.isAdopted()==true) {
			throw new IllegalArgumentException("Pet with ID "+petID+" is alreay adopted.");
		}
		if (user.getUserType().equalsIgnoreCase("Rescuer")) {
			throw new IllegalArgumentException("User "+user.getName()+"is a Rescuer and cannot adopt pets (check another user ID or register as an adopter).");
		}
		AdoptionRecord record = new AdoptionRecord(recordID,pet,user);
		recordList.add(record);
		record.displayRecordDetails();
	}
	
	public void displayAllRecords() { //displays all completed adoption records from record list
		if (recordList.isEmpty()) {
			System.out.println("No adoption records found.");
			return;
		}
		System.out.println("- - - ADOPTION RECORDS - - -");
		for (AdoptionRecord record: recordList) {
			record.displayRecordDetails();
		}
	}
}
