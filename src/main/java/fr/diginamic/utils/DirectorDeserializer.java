package fr.diginamic.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Director;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DirectorDeserializer extends JsonDeserializer<Director> {

    @Override
    public Director deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String stringDate = node.get("naissance").get("dateNaissance").asText().trim();
        System.out.println(stringDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);

        LocalDate birthDate = LocalDate.parse(stringDate, formatter);

        return new Director(
                node.get("id").asText(),
                node.get("identite").asText(),
                birthDate,
                node.get("url").asText()
        );
    }
}
