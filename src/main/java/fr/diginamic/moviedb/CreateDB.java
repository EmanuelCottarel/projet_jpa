package fr.diginamic.moviedb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.*;
import fr.diginamic.moviedb.services.BirthplaceService;
import fr.diginamic.moviedb.services.CountryService;
import fr.diginamic.moviedb.services.LanguageService;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;

public class CreateDB {

    @Transactional
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

                    //Create the movie OR get the existant movie with the corresponding ID
                    Movie movie = em.find(Movie.class, film.get("id").asText());
                    if (movie == null) {
                        movie = objectMapper.readerFor(Movie.class).readValue(film);
                    }


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

                    // Create Directors associated to the movie
                    for (JsonNode directorNode : film.get("realisateurs")) {
                        createDirector(directorNode, em, objectMapper, movie);
                    }

                    //Create Main casting
//                    if (film.has("castingPrincipal")) {
//                        for (JsonNode actorNode : film.get("castingPrincipal")) {
//                            createActor(actorNode, em, objectMapper, movie);
//                        }
//                    }

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

    private static void createActor(JsonNode actorNode, EntityManager em, ObjectMapper objectMapper, Movie movie) throws IOException {
        // Recherche de l'entité Person correspondante
        Person person = em.find(Person.class, actorNode.get("id").asText());
        if (person != null) {
            // Si une instance de Person existe déjà, utilisez-la pour créer l'Actor


            Actor actor = new Actor(person.getId(), person.getFullName(), person.getBirthDate(), person.getUrlIMDB(), 2.2);
            movie.addMainActor(actor);
            // Ajoutez l'Actor au casting principal du film
        } else {
            // Si l'instance de Person n'existe pas, créez-la
            if (em.find(Actor.class, actorNode.get("id").asText()) == null) {
                Person newPerson = objectMapper.readerFor(Person.class).readValue(actorNode);
                em.persist(newPerson);
                // Utilisez l'instance nouvellement créée de Person pour créer l'Actor
                Actor actor = new Actor(newPerson.getId(), newPerson.getFullName(), newPerson.getBirthDate(), newPerson.getUrlIMDB(), 2.2);
                // Ajoutez l'Actor au casting principal du film
                movie.addMainActor(actor);
            }
        }


        //
//        Actor actor = null;
////        if (em.find(Person.class, actorNode.get("id").asText()) == null && em.find(Person.class, actorNode.get("id").asText()) == null) {
////            actor = em.find(Actor.class, actorNode.get("id").asText());
////            if (actor == null) {
////                actor = objectMapper.readerFor(Actor.class).readValue(actorNode);
////                em.persist(actor);
////            }
////        }
//
//        if (em.find(Person.class, actorNode.get("id").asText()) != null && em.find(Actor.class, actorNode.get("id").asText()) == null) {
//            Person person = em.find(Person.class, actorNode.get("id").asText());
//            em.merge(person);
//            actor = new Actor(person.getId(), person.getFullName(), person.getBirthDate(), person.getUrlIMDB(), 2.2 );
//        }
//
//
//
////        if (em.find(Person.class, actorNode.get("id").asText()) == null) {
////            if (actor.getBirthplace() == null) {
////                Birthplace birthplace = BirthplaceRepository.findOneBy("name", actorNode.get("naissance").get("lieuNaissance").asText());
////                if (birthplace == null) {
////                    birthplace = objectMapper.readerFor(Birthplace.class).readValue(actorNode.get("naissance"));
////                    em.persist(birthplace);
////                }
////                actor.setBirthplace(birthplace);
////
////            }
////        }
//
//        movie.addMainActor(actor);


    }


    private static void createDirector(JsonNode directorNode, EntityManager em, ObjectMapper objectMapper, Movie movie) throws IOException {
        Director director = em.find(Director.class, directorNode.get("id").asText());
        if (director == null) {
            director = objectMapper.readerFor(Director.class).readValue(directorNode);
            em.persist(director);
        }
        if (director.getBirthplace() == null && directorNode.get("naissance").has("lieuNaissance")) {
            BirthplaceService birthplaceService = new BirthplaceService();
            Birthplace birthplace = birthplaceService.create(directorNode.get("naissance"));
            director.setBirthplace(birthplace);

        }
        movie.addDirector(director);
    }
}
