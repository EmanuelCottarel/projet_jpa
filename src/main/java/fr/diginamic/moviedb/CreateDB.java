package fr.diginamic.moviedb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.*;
import fr.diginamic.moviedb.services.*;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CreateDB {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        EntityManager em = null;
        try {
            em = ConnectionDb.getConnection();

            //Services
            ObjectMapper objectMapper = new ObjectMapper();
            CountryService countryService = new CountryService();
            LanguageService languageService = new LanguageService();
            TypeService typeService = new TypeService();
            DirectorService directorService = new DirectorService();
            ActorService actorService = new ActorService();
            RoleService roleService = new RoleService();

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("data/films.json");

            JsonNode jsonNode = objectMapper.readTree(inputStream);

            Map<String, Country> countryCache = new HashMap<>();
            Map<String, Language> languageCache = new HashMap<>();
            Map<String, Type> typeCache = new HashMap<>();
            Map<String, Director> directorCache = new HashMap<>();
            Map<String, Actor> actorCache = new HashMap<>();

            if (jsonNode.isArray()) {
                for (JsonNode film : jsonNode) {
                    em.getTransaction().begin();
                    try {

                        //Create the movie OR get the existant movie with the corresponding ID
                        Movie movie = em.find(Movie.class, film.get("id").asText());
                        if (movie == null) {
                            movie = objectMapper.readerFor(Movie.class).readValue(film);
                        }
                        movie = em.merge(movie);

                        //Create Country associated to the movie
                        if (film.has("pays")) {
                            Country country = null;
                            String countryName = film.get("pays").get("nom").asText();
                            if (countryCache.containsKey(countryName)) {
                                country = countryCache.get(countryName);
                                country = em.merge(country);
                            } else {
                                try {
                                    country = countryService.create(film.get("pays"));
                                    countryCache.put(countryName, country);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            movie.setCountry(country);
                        }

                        //Create Language associated to the movie
                        if (film.has("langue")) {
                            Language language = null;
                            String languageName = film.get("langue").asText();
                            if (languageCache.containsKey(languageName)) {
                                language = languageCache.get(languageName);
                                language = em.merge(language);
                            } else {
                                try {
                                    language = languageService.create(film.get("langue"));
                                    languageCache.put(languageName, language);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            movie.setLanguage(language);
                        }

                        //Create movie Types associated to the movie
                        if (film.has("genres")) {
                            for (JsonNode typeNode : film.get("genres")) {
                                System.out.println(typeNode);
                                Type type = null;
                                String typeName = typeNode.asText();
                                if (typeCache.containsKey(typeName)) {
                                    type = typeCache.get(typeName);
                                    type = em.merge(type);
                                } else {
                                    try {
                                        type = typeService.create(typeNode);
                                        typeCache.put(typeName, type);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                movie.addType(type);
                            }
                        }

                        //Create Directors associated to the movie
                        if (film.has("realisateurs")) {
                            for (JsonNode directorNode : film.get("realisateurs")) {
                                Director director = null;
                                String directorId = directorNode.get("id").asText();
                                if (directorCache.containsKey(directorId)) {
                                    director = directorCache.get(directorId);
                                    director = em.merge(director);
                                } else {
                                    try {
                                        director = directorService.create(directorNode);
                                        directorCache.put(directorId, director);
                                        director = em.merge(director);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!movie.getDirectors().contains(director)) {
                                    movie.addDirector(director);
                                }
                            }
                        }

                        //Create Main casting
                        if (film.has("castingPrincipal")) {
                            for (JsonNode actorNode : film.get("castingPrincipal")) {
                                Actor actor = null;
                                String actorId = actorNode.get("id").asText();
                                if (actorCache.containsKey(actorId)) {
                                    actor = actorCache.get(actorId);
                                    actor = em.merge(actor);
                                } else {
                                    try {
                                        actor = actorService.create(actorNode);
                                        actorCache.put(actorId, actor);
                                        actor = em.merge(actor);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (!movie.getMainActors().contains(actor)) {
                                    movie.addMainActor(actor);
                                }
                            }
                        }

                        //Create Roles
                        if (film.has("roles")) {
                            for (JsonNode roleNode : film.get("roles")) {
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
//                        break;
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.closeConnection();
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);  // Durée en millisecondes
        System.out.println("Temps d'exécution: " + duration + " millisecondes");
    }


}
