package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.moviedb.entities.Type;
import fr.diginamic.moviedb.repositories.LanguageRepository;
import fr.diginamic.moviedb.repositories.TypeRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;

public class TypeService extends AbstractService {

    private final TypeRepository typeRepository = new TypeRepository();

    public TypeService() {
    }

    @Override
    public Type create(JsonNode languageNode) throws IOException {
        Type type = typeRepository.findOneBy("name", languageNode.asText().toUpperCase());
        if (type == null) {
            type = objectMapper.readerFor(Type.class).readValue(languageNode);
        }
        return type;
    }
}
