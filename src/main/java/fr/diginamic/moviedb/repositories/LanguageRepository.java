package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Country;
import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class LanguageRepository extends AbstractRepository {

    /**
     * Find one fr.diginamic.moviedb.entities.Language according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a fr.diginamic.moviedb.entities.Language objet
     */
    public static Language findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }

        EntityManager em = ConnectionDb.getEm();
        Language result = null;
        try {
            String jpql = "SELECT b FROM Language b WHERE b." + field + " LIKE :value";
            TypedQuery<Language> query = em.createQuery(jpql, Language.class);
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
     * Check if the fieldName value match a field of the Language Class to avoid SQL injection.
     * @param fieldName - the searched field
     * @return boolean - true if the field match
     */
    private static boolean isValidField(String fieldName) {
        for (Field field : Language.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
