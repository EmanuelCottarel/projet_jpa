package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.moviedb.repositories.BirthplaceRepository;

import java.io.IOException;

public class BirthplaceService extends AbstractService{

    private final BirthplaceRepository birthplaceRepository = new BirthplaceRepository();

    public BirthplaceService() {
    }

    public Birthplace create(JsonNode birthNode) throws IOException {
        Birthplace birthplace = birthplaceRepository.findOneBy("name", birthNode.get("lieuNaissance").asText());
        if (birthplace == null) {
            birthplace = objectMapper.readerFor(Birthplace.class).readValue(birthNode.get("lieuNaissance"));
        }
        return birthplace;
    }
}
