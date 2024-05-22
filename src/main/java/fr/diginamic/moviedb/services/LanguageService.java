package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.moviedb.repositories.LanguageRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;

public class LanguageService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public LanguageService() {
    }

    @Transactional
    public Language create(JsonNode languageNode) throws IOException {
        Language language = LanguageRepository.findOneBy("name", languageNode.asText().toUpperCase());
        if (language == null) {
            language = objectMapper.readerFor(Language.class).readValue(languageNode);
        }
        return language;
    }
}
