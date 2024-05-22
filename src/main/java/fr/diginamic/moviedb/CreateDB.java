package fr.diginamic.moviedb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Director;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.InputStream;

public class CreateDB {
    public static void main(String[] args) {
        EntityManager em = null;
        try {
            em = ConnectionDb.getConnection();
            ObjectMapper objectMapper = new ObjectMapper();

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("data/films.json");

            JsonNode jsonNode = objectMapper.readTree(inputStream);

            if (jsonNode.isArray()) {
                for (JsonNode film : jsonNode) {
                    // Create the movie
                    Movie movie = objectMapper.readerFor(Movie.class).readValue(film);

                    // Create Directors associated to the movie
                    for (JsonNode directorNode : film.get("realisateurs")) {
                        Director director = em.find(Director.class, directorNode.get("id").asText());
                        if (director == null) {
                            director = objectMapper.readerFor(Director.class).readValue(directorNode);
                            em.persist(director);
                        }
                        movie.addDirector(director);
                    }

                    em.persist(movie);
                    break;
                }
            }


        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            ConnectionDb.closeConnection();
        }
    }
}
