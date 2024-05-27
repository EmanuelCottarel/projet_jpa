package fr.diginamic.moviedb.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Director;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DirectorDeserializer extends JsonDeserializer<Director> {

    @Override
    public Director deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String stringDate = node.get("naissance").get("dateNaissance").asText().trim();

        LocalDate birthDate = null;
        if (!stringDate.isEmpty()) {
            birthDate = getBirthDate(stringDate, birthDate);
        }

        return new Director(
                node.get("id").asText(),
                node.get("identite").asText(),
                birthDate,
                node.get("url").asText()
        );
    }

    /**
     * Try different formatting depending on the input date format
     * @param stringDate String
     * @param birthDate LocalDate
     * @return LocalDate
     */
    private LocalDate getBirthDate(String stringDate, LocalDate birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH);

        String[] split = stringDate.split(" ");
        if (split.length == 2) {
            stringDate = stringDate.concat(" 2000");
        }
        if (split.length == 1) {
            if (split[0].length() == 4) {
                stringDate = "January 1 ".concat(stringDate);
            }
        }
        try {
            birthDate = LocalDate.parse(stringDate, formatter);
        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage());
        }
        return birthDate;
    }
}
