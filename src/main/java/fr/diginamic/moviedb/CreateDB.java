package fr.diginamic.moviedb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.*;
import fr.diginamic.moviedb.services.*;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.InputStream;

public class CreateDB {

//    @Transactional
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
                    em.getTransaction().begin();
                    try {
                        //TODO Ajouter les try catch sur les create

                        //Create the movie OR get the existant movie with the corresponding ID
                        Movie movie = em.find(Movie.class, film.get("id").asText());
                        if (movie == null) {
                            movie = objectMapper.readerFor(Movie.class).readValue(film);
                        }
                        movie = em.merge(movie);
//                        em.refresh(movie);

                        //Create Country associated to the movie
                        if (film.has("pays")) {
                            CountryService countryService = new CountryService();
                            Country country = countryService.create(film.get("pays"));
                            movie.setCountry(country);
                        }

                        //Create Language associated to the movie
                        if (film.has("langue")) {
                            LanguageService languageService = new LanguageService();
                            Language language = languageService.create(film.get("langue"));
                            movie.setLanguage(language);
                        }
//                        em.merge(movie);

                        //Create movie Types associated to the movie
                        if (film.has("genres")) {
                            for (JsonNode typeNode : film.get("genres")) {
                                TypeService typeService = new TypeService();
                                Type type = typeService.create(typeNode);
                                movie.addType(type);
                            }
                        }
//                        em.merge(movie);
                        System.out.println(movie);

                        //Create Directors associated to the movie
                        if (film.has("realisateurs")) {
                            for (JsonNode directorNode : film.get("realisateurs")) {
                                DirectorService directorService = new DirectorService();
                                Director director = directorService.create(directorNode);
                                director = em.merge(director);
                                if (!movie.getDirectors().contains(director)) {
                                    movie.addDirector(director);
                                }
                            }
                        }

                        //Create Main casting
                        if (film.has("castingPrincipal")) {
                            for (JsonNode actorNode : film.get("castingPrincipal")) {
                                ActorService actorService = new ActorService();
                                Actor actor = actorService.create(actorNode);
                                actor =  em.merge(actor);
                                if (!movie.getMainActors().contains(actor)) {
                                    movie.addMainActor(actor);
                                }
                            }
                        }

                        //Create Roles
                        if (film.has("roles")) {
                            for (JsonNode roleNode : film.get("roles")) {
                                RoleService roleService = new RoleService();
                                Role role = roleService.create(roleNode);
                                role.setMovie(movie);
                                if (!movie.getRoles().contains(role)) {
                                    movie.addRole(role);
                                }
                            }
                        }



                        em.getTransaction().commit();

                    } catch (Exception e) {
                        if (em != null && em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                        }
                        e.printStackTrace();
                    } finally {
                        break;
                    }
                }
            }


        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            ConnectionDb.closeConnection();
        }
    }


}
