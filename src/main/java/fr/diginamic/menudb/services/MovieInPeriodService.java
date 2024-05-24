package fr.diginamic.menudb.services;

import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.moviedb.entities.Role;
import fr.diginamic.moviedb.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MovieInPeriodService extends AbstractMenuService {

    private final MovieRepository movieRepository = new MovieRepository();

    @Override
    public void search(Scanner scanner) {

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("Veuillez saisir l'année de début:");
        int startYear = scanner.nextInt();
        System.out.println("Veuillez saisir l'année de fin:");
        int endYear = scanner.nextInt();

        List<Movie> movies = movieRepository.findMoviesBetweenYears(startYear, endYear);

        System.out.println("Voici les films sortis entre " + startYear + " et " + endYear + ":");
        movies.forEach(movie -> System.out.println(" - " + movie.getTitle() + " - " + movie.getReleaseYear()));
    }
}
