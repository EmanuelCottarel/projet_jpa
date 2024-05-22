package fr.diginamic.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionDb {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final String PERSISTENCE_UNIT_NAME = "default";

    private ConnectionDb() {
    }

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }

    public static EntityManager getEm() {
        if (em == null) {
            em = getEmf().createEntityManager();
        }
        return em;
    }

    public static EntityManager getConnection() {
        if (em == null || !em.isOpen()) {
            em = getEm();
        }
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        return em;
    }

    public static void closeConnection() {
        if (em != null && em.isOpen()) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }

}
