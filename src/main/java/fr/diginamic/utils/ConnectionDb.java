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

    /**
     * @return the existing instance of emf or create a new one if null
     */
    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }

    /**
     * @return the existing instance of em or create a new one if null
     */
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
        return em;
    }

    /**
     * Close the opened connection
     */
    public static void closeConnection() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

}
