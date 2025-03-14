/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.microkernel.entities;

import co.edu.unicauca.microkernel.interfaces.observer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lopez
 */
public class Subject {
      private static List<observer> observers = new ArrayList<>(); // Lista estática

    public void addObserver(observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        System.out.println("Notificando a " + observers.size() + " observadores...");
        for (observer observer : observers) {
            observer.update();
        }
    }
}
