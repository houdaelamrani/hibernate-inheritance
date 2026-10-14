package com.example;

import com.example.model.joined.Developpeur;
import com.example.model.joined.Employe;
import com.example.model.joined.Manager;
import com.example.model.singletable.Moto;
import com.example.model.singletable.Vehicule;
import com.example.model.singletable.Voiture;
import com.example.model.tableperclass.Electronique;
import com.example.model.tableperclass.Livre;
import com.example.model.tableperclass.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println(" TP JPA & HIBERNATE : GESTION DES STRATEGIES D'HERITAGE");
        System.out.println(" Etudiante : Houda El Amrani | Environnement : Windows 11 & H2 In-Memory");
        System.out.println("===============================================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        System.out.println("\n[1] Persistance des entités - SINGLE_TABLE (Vehicule, Voiture, Moto)...");
        Voiture voiture = new Voiture("Toyota", 4);
        Moto moto = new Moto("Harley", true);
        em.persist(voiture);
        em.persist(moto);

        System.out.println("[2] Persistance des entités - JOINED (Employe, Developpeur, Manager)...");
        Developpeur dev = new Developpeur("Alice", "Java");
        Manager manager = new Manager("Bob", 3);
        em.persist(dev);
        em.persist(manager);

        System.out.println("[3] Persistance des entités - TABLE_PER_CLASS (Produit, Livre, Electronique)...");
        Livre livre = new Livre("Clean Code", "Robert C. Martin");
        Electronique electronique = new Electronique("Smartphone", 24);
        em.persist(livre);
        em.persist(electronique);

        em.getTransaction().commit();

        System.out.println("\n===============================================================================");
        System.out.println(" RESULTATS DES REQUETES POLYMORPHES JPQL");
        System.out.println("===============================================================================");

        // Lecture des véhicules (SINGLE_TABLE)
        System.out.println("\n--- Liste des véhicules ---");
        em.createQuery("SELECT v FROM Vehicule v", Vehicule.class)
                .getResultList().forEach(v -> System.out.println(v.getClass().getSimpleName() + ": " + v.getMarque()));

        // Lecture des employés (JOINED)
        System.out.println("\n--- Liste des employés ---");
        em.createQuery("SELECT e FROM Employe e", Employe.class)
                .getResultList().forEach(e -> System.out.println(e.getClass().getSimpleName() + ": " + e.getNom()));

        // Lecture des produits (TABLE_PER_CLASS)
        System.out.println("\n--- Liste des produits ---");
        em.createQuery("SELECT p FROM Produit p", Produit.class)
                .getResultList().forEach(p -> System.out.println(p.getClass().getSimpleName() + ": " + p.getNom()));

        System.out.println("\n===============================================================================");
        System.out.println(" TP TERMINE AVEC SUCCES !");
        System.out.println(" Generation des captures d'ecran Windows en cours...");
        System.out.println("===============================================================================");

        com.example.util.CaptureResultatWindows.main(new String[0]);

        em.close();
        emf.close();
    }
}
