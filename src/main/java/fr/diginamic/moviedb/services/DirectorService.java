package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.moviedb.entities.Director;
import fr.diginamic.moviedb.entities.Person;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.IOException;

public class DirectorService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final EntityManager em = ConnectionDb.getEm();

    public DirectorService() {
    }

    @Transactional
    public Director create(JsonNode directorNode) throws IOException {

        PersonService personService = new PersonService();
        Person person = personService.create(directorNode);
//        person = em.merge(person);

        Director director = new Director(
                person.getId(),
                person.getFullName(),
                person.getBirthDate(),
                person.getUrlIMDB()

        );

        director.setBirthplace(person.getBirthplace());
        return director;
    }
}
