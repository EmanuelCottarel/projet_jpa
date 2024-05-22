package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Country;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class CountryRepository extends AbstractRepository {
    // https://chatgpt.com/c/ba63a751-f0e3-4798-8fc3-eeb7d1e5ef21

    /**
     * Find one Country according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a Country objet
     */
    public static Country findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }

        EntityManager em = ConnectionDb.getEm();
        Country result = null;
        try {
            String jpql = "SELECT b FROM Country b WHERE b." + field + " LIKE :value";
            TypedQuery<Country> query = em.createQuery(jpql, Country.class);
            query.setParameter("value", value);
            result = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No result found for field: " + field + " with value: " + value);
        } catch (NonUniqueResultException e) {
            System.out.println("Multiple results found for field: " + field + " with value: " + value);
        }
        return result;

    }

    /**
     * Check if the fieldName value match a field of the Country Class to avoid SQL injection.
     * @param fieldName - the searched field
     * @return boolean - true if the field match
     */
    private static boolean isValidField(String fieldName) {
        for (Field field : Country.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
