package fr.diginamic.moviedb;

import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CreateDB {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = ConnectionDb.getInstance()) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            em.getTransaction().commit();

            em.close();

        }
    }
}
