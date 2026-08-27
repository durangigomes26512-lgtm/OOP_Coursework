package main;

import gui.PetAppGUI;
import model.Cat;
import model.Dog;
import service.ShelterManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        ShelterManager manager =
                new ShelterManager();

        // Sample pets

        manager.addPet(
                new Dog(
                        1,
                        "Max",
                        3,
                        "Male",
                        "Healthy",
                        "Labrador",
                        true
                )
        );

        manager.addPet(
                new Dog(
                        2,
                        "Charlie",
                        4,
                        "Male",
                        "Healthy",
                        "Golden Retriever",
                        true
                )
        );

        manager.addPet(
                new Cat(
                        3,
                        "Luna",
                        2,
                        "Female",
                        "Healthy",
                        true,
                        true
                )
        );

        manager.addPet(
                new Cat(
                        4,
                        "Milo",
                        1,
                        "Male",
                        "Healthy",
                        true,
                        true
                )
        );

        SwingUtilities.invokeLater(() -> {

            PetAdoptionGUI gui =
                    new PetAdoptionGUI(manager);

            gui.setVisible(true);
        });
    }
}
