package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;

import java.io.IOException;

public abstract class AbstractService {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected EntityManager em = ConnectionDb.getEm();

    /**
     * Create a new Object
     * @param node - the jsonNode containing the data required to create a new Object
     * @return a new Object
     * @throws IOException - if deserialization fails
     */
    public abstract Object create(JsonNode node) throws IOException;


}
