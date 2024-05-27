package fr.diginamic.moviedb.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Movie;

import java.io.IOException;

public class MovieDeserializer extends JsonDeserializer<Movie> {


    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String location = "";
        if (node.has("lieuTournage")) {
            JsonNode locationNode = node.get("lieuTournage");
            location = location
                    .concat(locationNode.get("ville").asText())
                    .concat(locationNode.get("ville").asText().isEmpty() ? "" : ", ")
                    .concat(locationNode.get("etatDept").asText())
                    .concat(", ")
                    .concat(locationNode.get("pays").asText());
        }

        return new Movie(
                node.get("id").asText(),
                node.get("nom").asText(),
                node.get("anneeSortie").asInt(),
                node.get("rating").asDouble(),
                node.get("plot").asText(),
                node.get("url").asText(),
                location
        );
    }
}
