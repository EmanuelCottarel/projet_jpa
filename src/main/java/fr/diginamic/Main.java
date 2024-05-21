package fr.diginamic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Movie;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("data/films.json");

            JsonNode jsonNode = objectMapper.readTree(inputStream);

            if (jsonNode.isArray()){
                for (JsonNode film : jsonNode){

                    Movie movie = objectMapper.readerFor(Movie.class).readValue(film);

                    System.out.println(movie);
//                    System.out.println(film);
//                    System.out.println(film.get("id").asText());
                    break;
                }
            }
//            System.out.println(jsonNode.g);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}