# 🐾 Pet Adoption Management System

A Java-based Pet Adoption Management System developed as an Object-Oriented Programming coursework project. The application allows users to register, log in, browse available pets, adopt pets, and manage pet adoption records through a Java Swing graphical user interface.

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
All class attributes are private and accessed through public getters and setters.

### Inheritance
`Dog` and `Cat` inherit common properties and behaviours from the abstract `Pet` class.

### Abstraction
The `Pet` class is abstract and defines abstract methods such as:

- `calculateAdoptionFee()`
- `getCareInstructions()`

### Polymorphism
The system stores pets using `ArrayList<Pet>`, allowing both `Dog` and `Cat` objects to be handled through the `Pet` superclass.

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
