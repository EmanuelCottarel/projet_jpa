package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;

public class MovieRepository extends AbstractRepository {

    /**
     * Find one Movie according to the field in parameter
     *
     * @param field - the searched field
     * @param value - the searched value
     * @return a Movie entity
     */
    public Movie findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }

        EntityManager em = ConnectionDb.getEm();
        Movie result = null;
        try {
            String jpql = "SELECT m FROM Movie m WHERE m." + field + " LIKE :value";
            TypedQuery<Movie> query = em.createQuery(jpql, Movie.class);
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
     * Check if the fieldName value match a field of the Movie Class to avoid SQL injection.
     *
     * @param fieldName - the searched field
     * @return boolean - true if the field match
     */
    public boolean isValidField(String fieldName) {
        for (Field field : Movie.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
