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

public class ActorRepository extends AbstractRepository<Actor> {

    private final EntityManager em = ConnectionDb.getEm();

    /**
     * Find one Actor according to the field in parameter
     * @param field - the searched field
     * @param value - the searched value
     * @return a Actor objet
     */
    @Override
    public Actor findOneBy(String field, String value) {

        if (!isValidField(field)) {
            throw new IllegalArgumentException("Unrecognized field: " + field);
        }

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
    @Override
    public boolean isValidField(String fieldName) {
        for (Field field : Actor.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Find common actors between two different movies
     * @param movie1
     * @param movie2
     * @return a list of the commons actors
     */
    public List<Actor> findCommonActorsForTwoMovies(Movie movie1, Movie movie2) {

        return em.createQuery("SELECT DISTINCT a FROM Actor a JOIN a.roles r1 JOIN a.roles r2 WHERE r1.movie = :movie1 AND r2.movie = :movie2", Actor.class)
                .setParameter("movie1", movie1)
                .setParameter("movie2", movie2)
                .getResultList();
    }


}
