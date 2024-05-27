package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.moviedb.repositories.LanguageRepository;

import java.io.IOException;

public class LanguageService extends AbstractService {

    private final LanguageRepository languageRepository = new LanguageRepository();

    public LanguageService() {
    }

    @Override
    public Language create(JsonNode languageNode) throws IOException {
        Language language = languageRepository.findOneBy("name", languageNode.asText().toUpperCase());
        if (language == null) {
            language = objectMapper.readerFor(Language.class).readValue(languageNode);
        }
        return language;
    }
}
