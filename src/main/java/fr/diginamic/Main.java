package fr.diginamic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.moviedb.entities.Director;
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

            if (jsonNode.isArray()) {
                for (JsonNode film : jsonNode) {

                    //Create the movie
                    Movie movie = objectMapper.readerFor(Movie.class).readValue(film);

                    //Create Directors associated to the movie
                    for (JsonNode directorNode : film.get("realisateurs")) {
                        Director director = objectMapper.readerFor(Director.class).readValue(directorNode);
                        System.out.println(director);
                    }


//                    System.out.println(movie);
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