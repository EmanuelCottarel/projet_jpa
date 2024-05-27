package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.moviedb.entities.Type;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class TypeRepository extends AbstractRepository {

    /**
     * Find one fr.diginamic.moviedb.entities.Type according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a fr.diginamic.moviedb.entities.Type objet
     */
    @Override
    public Type findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }

        EntityManager em = ConnectionDb.getEm();
        Type result = null;
        try {
            String jpql = "SELECT b FROM Type b WHERE b." + field + " LIKE :value";
            TypedQuery<Type> query = em.createQuery(jpql, Type.class);
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
     * Check if the fieldName value match a field of the Type Class to avoid SQL injection.
     * @param fieldName - the searched field
     * @return boolean - true if the field match
     */
    @Override
    public boolean isValidField(String fieldName) {
        for (Field field : Type.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
