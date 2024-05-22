package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.moviedb.repositories.BirthplaceRepository;
import fr.diginamic.moviedb.repositories.CountryRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;

public class BirthplaceService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BirthplaceService() {
    }

    @Transactional
    public Birthplace create(JsonNode birthNode) throws IOException {
        Birthplace birthplace = BirthplaceRepository.findOneBy("name", birthNode.get("lieuNaissance").asText());
        if (birthplace == null) {
            birthplace = objectMapper.readerFor(Birthplace.class).readValue(birthNode.get("lieuNaissance"));
        }
        return birthplace;
    }
}
