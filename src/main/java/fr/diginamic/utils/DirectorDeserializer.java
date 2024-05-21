package fr.diginamic.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fr.diginamic.moviedb.entities.Director;

import java.io.IOException;

public class DirectorDeserializer extends JsonDeserializer<Director> {

    @Override
    public Director deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return null;
    }
}
