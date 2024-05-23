package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.moviedb.entities.Director;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;

import java.io.IOException;

public class DirectorService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final EntityManager em = ConnectionDb.getEm();

    public DirectorService() {
    }

    public Director create(JsonNode directorNode) throws IOException {
        Director director = em.find(Director.class, directorNode.get("id").asText());
        if (director == null) {
            director = objectMapper.readerFor(Director.class).readValue(directorNode);

            if (director.getBirthplace() == null && directorNode.get("naissance").has("lieuNaissance")) {
                BirthplaceService birthplaceService = new BirthplaceService();
                Birthplace birthplace = birthplaceService.create(directorNode.get("naissance"));
                em.persist(birthplace);
                director.setBirthplace(birthplace);
            }
            return director;

        }

        return director;
    }
}
