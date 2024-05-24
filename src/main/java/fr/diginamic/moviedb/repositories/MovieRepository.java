package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;
import java.util.List;

public class MovieRepository extends AbstractRepository {

    private final EntityManager em = ConnectionDb.getEm();

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
     * Search Movie realeased between two years
     * @param startYear - begin year
     * @param endYear - end year
     * @return List<Movie>
     */
    public List<Movie> findMoviesBetweenYears(int startYear, int endYear) {
        return em.createQuery("SELECT m FROM Movie m WHERE m.releaseYear BETWEEN :startYear AND :endYear ORDER BY m.releaseYear ASC", Movie.class)
                .setParameter("startYear", startYear)
                .setParameter("endYear", endYear)
                .getResultList();
    }

    /**
     * Find the movies where two given actors played in
     * @param actor1
     * @param actor2
     * @return a List of Movie
     */
    public List<Movie> findCommonMoviesForTwoActors(Actor actor1, Actor actor2) {
        return em.createQuery("SELECT m FROM Movie m JOIN m.roles r1 JOIN m.roles r2 WHERE r1.actor = :actor1 AND r2.actor = :actor2", Movie.class)
                .setParameter("actor1", actor1)
                .setParameter("actor2", actor2)
                .getResultList();
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
