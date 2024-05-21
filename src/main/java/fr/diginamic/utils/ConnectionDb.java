package fr.diginamic.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionDb {

    private static EntityManagerFactory emf;

    private static final String PERSISTENCE_UNIT_NAME = "default";

    private ConnectionDb() {
    }

    public static EntityManagerFactory getInstance() {
        if (emf == null) {
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }

}
