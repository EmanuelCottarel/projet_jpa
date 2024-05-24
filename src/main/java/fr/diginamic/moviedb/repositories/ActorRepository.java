package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class ActorRepository extends AbstractRepository {

    /**
     * Find one Actor according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a Actor objet
     */
    public Actor findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }

        EntityManager em = ConnectionDb.getEm();
        Actor result = null;
        try {
            String jpql = "SELECT a FROM Actor a WHERE a." + field + " LIKE :value";
            TypedQuery<Actor> query = em.createQuery(jpql, Actor.class);
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
     * Check if the fieldName value match a field of the Actor Class to avoid SQL injection.
     * @param fieldName - the searched field
     * @return boolean - true if the field match
     */
    public boolean isValidField(String fieldName) {
        for (Field field : Actor.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
