package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Country;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class CountryRepository extends AbstractRepository<Country> {

    private final EntityManager em = ConnectionDb.getEm();

    /**
     * Find one Country according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a Country objet
     */
    @Override
    public Country findOneBy(String field, String value) {

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
    @Override
    public boolean isValidField(String fieldName) {
        for (Field field : Country.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

}
