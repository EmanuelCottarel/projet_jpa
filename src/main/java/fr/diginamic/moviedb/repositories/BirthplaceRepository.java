package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class BirthplaceRepository extends AbstractRepository {

    /**
     * Find one Birthplace according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a Birthplace objet
     */
    @Override
    public Birthplace findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }
        EntityManager em = ConnectionDb.getEm();

        Birthplace result = null;
        try {
            String jpql = "SELECT b FROM Birthplace b WHERE b." + field + " LIKE :value";
            TypedQuery<Birthplace> query = em.createQuery(jpql, Birthplace.class);
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
     * Check if the fieldName value match a field of the Birthplace Class to avoid SQL injection.
     * @param fieldName - the searched field
     * @return boolean - true if the field match
     */
    @Override
    public boolean isValidField(String fieldName) {
        for (Field field : Birthplace.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
