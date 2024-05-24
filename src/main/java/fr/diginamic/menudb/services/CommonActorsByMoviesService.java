package fr.diginamic.menudb.services;

import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.moviedb.repositories.ActorRepository;
import fr.diginamic.moviedb.repositories.MovieRepository;

import java.util.List;
import java.util.Scanner;

public class CommonActorsByMoviesService extends AbstractMenuService {

    private final MovieRepository movieRepository = new MovieRepository();

    private final ActorRepository actorRepository = new ActorRepository();

    @Override
    public void search(Scanner scanner) {

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        Movie movie1 = findMovie(scanner);
        Movie movie2 = findMovie(scanner);

        List<Actor> actors = actorRepository.findCommonActorsForTwoMovies(movie1, movie2);

        System.out.println("Voici les acteurs communs pour les films " + movie1.getTitle() + " et " + movie2.getTitle() + ":");
        actors.forEach(actor -> System.out.println(" - " + actor.getFullName()));
    }

    private Movie findMovie(Scanner scanner) {
        System.out.println("Veuillez saisir le titre du film:");
        Movie movie = null;
        while (movie == null) {
            String title = scanner.nextLine();
            movie = movieRepository.findOneBy("title", title);
            if (movie == null) {
                System.err.println("Ce film n'existe pas, veuillez saisir le titre du film':");
            }
        }
        return movie;
    }
}
