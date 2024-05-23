package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.moviedb.entities.Person;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.IOException;

public class PersonService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final EntityManager em = ConnectionDb.getEm();

    public PersonService() {
    }

    @Transactional
    public Person create(JsonNode personNode) throws IOException {
        Person person = em.find(Person.class, personNode.get("id").asText().toUpperCase());
        if (person == null) {
            person = objectMapper.readerFor(Person.class).readValue(personNode);
        }

        if (person.getBirthplace() == null && personNode.get("naissance").has("lieuNaissance")) {
            BirthplaceService birthplaceService = new BirthplaceService();
            Birthplace birthplace = birthplaceService.create(personNode.get("naissance"));
            em.persist(birthplace);
            person.setBirthplace(birthplace);
        }
        return person;
    }
}
