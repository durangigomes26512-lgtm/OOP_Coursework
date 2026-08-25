package coursework;
public class User {
	private String userID;
	private String name;
	private String phoneNumber;
	private String userType;
	private String password;
	
	//constructor
	public User(String userID,String name,String phoneNumber, String userType, String password) {
		this.userID=userID;
		this.name=name;
		this.phoneNumber=phoneNumber;
		setUserType(userType);
		this.password=password;
	}
	
	//setters & getters
	public void setUserID(String userID) {
		this.userID=userID;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber=phoneNumber;
	}
	public void setUserType(String userType) {
		if (userType.equalsIgnoreCase("Adopter")||userType.equalsIgnoreCase("Rescuer")) {
			this.userType=userType;
		}
		else {
			throw new IllegalArgumentException("Invalid User Type. Must be either 'Adopter' or 'Rescuer'.");
		}
	}
	
	public String getUserID() {return userID;}
	public String getName() {return name;}
	public String getPhoneNumber() {return phoneNumber;}
	public String getUserType() {return userType;}
	public String getPassword() {return password;}
	
	//display output
	public void displayUserInfo() {
		System.out.println("User ID: "+userID);
		System.out.println("Name: "+name);
		System.out.println("Phone Number: "+phoneNumber);
		System.out.println("User Type: "+userType);
	}
}
