package fr.diginamic.moviedb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Director;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.io.InputStream;

public class CreateDB {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = ConnectionDb.getInstance()) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream("data/films.json");

                JsonNode jsonNode = objectMapper.readTree(inputStream);

                if (jsonNode.isArray()) {
                    for (JsonNode film : jsonNode) {

                        //Create the movie
                        Movie movie = objectMapper.readerFor(Movie.class).readValue(film);

                        //Create Directors associated to the movie
                        for (JsonNode directorNode : film.get("realisateurs")) {
                            Director director = em.find(Director.class, directorNode.get("id").asText());
                            if (director == null) {
                                director = objectMapper.readerFor(Director.class).readValue(directorNode);
                            }
                            movie.addDirector(director);
                            System.out.println(director);
                        }

                        System.out.println(movie);


//                    System.out.println(movie);
//                    System.out.println(film);
//                    System.out.println(film.get("id").asText());
                        em.persist(movie);
                        break;
                    }
                }
//            System.out.println(jsonNode.g);

            } catch (IOException e) {
                e.printStackTrace();
            }


            em.getTransaction().commit();

            em.close();

        }
    }
}
