package fr.diginamic.moviedb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.moviedb.entities.Country;
import fr.diginamic.moviedb.entities.Director;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.moviedb.repositories.BirthplaceRepository;
import fr.diginamic.moviedb.repositories.CountryRepository;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;

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

                    Movie movie = em.find(Movie.class, film.get("id").asText());

                    if (movie == null) {
                        movie = objectMapper.readerFor(Movie.class).readValue(film);
                    }

                    // Create Directors associated to the movie
                    for (JsonNode directorNode : film.get("realisateurs")) {
                        Director director = em.find(Director.class, directorNode.get("id").asText());
                        if (director == null) {
                            director = objectMapper.readerFor(Director.class).readValue(directorNode);
                            em.persist(director);
                        }
                        if (director.getBirthplace() == null) {
                            Birthplace birthplace = BirthplaceRepository.findOneBy("name", directorNode.get("naissance").get("lieuNaissance").asText());
                            if (birthplace == null) {
                                birthplace = objectMapper.readerFor(Birthplace.class).readValue(directorNode.get("naissance"));
                                em.persist(birthplace);
                            }
                            director.setBirthplace(birthplace);

                        }
                        movie.addDirector(director);
                        System.out.println(director);
                    }

                    //Create Country associated to the movie
                    if (film.has("pays")) {
                        JsonNode countryNode = film.get("pays");
                        Country country = CountryRepository.findOneBy("name", countryNode.get("nom").asText());
                        if (country == null) {
                            country = objectMapper.readerFor(Country.class).readValue(countryNode);
                        }

                        movie.setCountry(country);
                        em.persist(country);

                    }

                    em.persist(movie);

//                    break;
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
