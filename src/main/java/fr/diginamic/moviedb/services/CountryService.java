package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Country;
import fr.diginamic.moviedb.repositories.CountryRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;

public class CountryService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CountryService() {
    }

    @Transactional
    public Country create(JsonNode countryNode) throws IOException {
        Country country = CountryRepository.findOneBy("name", countryNode.get("nom").asText());
        if (country == null) {
            country = objectMapper.readerFor(Country.class).readValue(countryNode);
        }
        return country;
    }
}
