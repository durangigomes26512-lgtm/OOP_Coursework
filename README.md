# 🐾 Pet Adoption Management System

A Java-based Pet Adoption Management System developed as an OOP (Object-Oriented Programming) coursework project. The system is designed to simplify the pet 
adoption process by allowing users to register, log in, browse available pets, view pet details, and adopt pets. It also supports rescuers in adding pets to 
the system and maintaining adoption records through a Java Swing graphical user interface.


## 📌 Features

- User registration and login 
- Adopter and Rescuer user roles
- Browse available pets
- View pet details
- Dog and Cat categories
- Pet adoption process
- Adoption fee calculation
- Adoption records and receipts
- Rescue / add pets to the system
- Java Swing graphical user interface

## 🛠️ Technologies Used

- Java
- Java Swing
- Eclipse IDE
- Object-Oriented Programming (OOP)
- ArrayList for in-memory data management

## 🧩 OOP Concepts Used

### Encapsulation

Encapsulation is implemented by declaring the attributes of the classes as private and accessing them through public getter and setter methods. For example, 
the Pet class contains private attributes such as petID, name, age, healthStatus, isVaccinated, and isAdopted. These attributes are accessed using methods
such as getName(), getAge(), setAge(), and setHealthStatus(). The User, Dog, Cat, and AdoptionRecord classes also follow the same approach to protect and 
control access to their data.

### Inheritance

Inheritance is implemented using the Pet class as the parent class, while Dog and Cat are subclasses. Both classes extend the Pet class using the extends 
keyword. Common properties such as pet ID, name, age, gender, health status, and vaccination status are inherited from the Pet class. Dog and Cat also contain 
their own specific attributes, such as breed and leashTrained for dogs and indoorOnly and litterTrained for cats. The super() keyword is used to initialize the 
inherited properties.

### Abstraction

Abstraction is implemented through the abstract Pet class. The Pet class defines common characteristics and behaviours of pets while hiding the specific 
implementation details.It contains two abstract methods, calculateAdoptionFee() and getCareInstructions(). These methods are implemented by the Dog and Cat 
subclasses according to their specific requirements.

- `calculateAdoptionFee()`
- `getCareInstructions()`

### Polymorphism

Polymorphism is implemented through method overriding. Both Dog and Cat override the calculateAdoptionFee() and getCareInstructions() methods from the Pet 
class.Although the methods have the same names, they provide different behaviours depending on the type of pet. For example, the adoption fee for a dog is 
calculated based on vaccination and leash training, while the cat's fee is calculated based on vaccination and litter training.

## 📂 Project Structure

```text
src/
└── coursework/
    ├── Pet.java
    ├── Dog.java
    ├── Cat.java
    ├── User.java
    ├── AdoptionRecord.java
    ├── ShelterManager.java
    └── PetAdoptionGUI.java
