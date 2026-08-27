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

     
        SwingUtilities.invokeLater(() -> {

            PetAdoptionGUI gui =
                    new PetAdoptionGUI(manager);

            gui.setVisible(true);
        });
    }
}
