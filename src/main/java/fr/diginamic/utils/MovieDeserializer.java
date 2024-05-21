package fr.diginamic.utils;

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
//        System.out.println(node);
        return new Movie(
                node.get("id").asText(),
                node.get("nom").asText(),
                node.get("anneeSortie").asInt(),
                node.get("rating").asDouble(),
                node.get("plot").asText(),
                node.get("url").asText()
        );
    }
}