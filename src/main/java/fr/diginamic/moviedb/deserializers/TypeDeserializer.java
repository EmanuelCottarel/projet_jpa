package fr.diginamic.moviedb.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Language;
import fr.diginamic.moviedb.entities.Type;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<Type> {

    @Override
    public Type deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        return new Type(
                node.asText().trim().toUpperCase());
    }

}
