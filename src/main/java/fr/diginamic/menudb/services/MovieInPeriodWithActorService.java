package fr.diginamic.menudb.services;

import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.moviedb.repositories.ActorRepository;
import fr.diginamic.moviedb.repositories.MovieRepository;

import java.util.List;
import java.util.Scanner;

public class MovieInPeriodWithActorService extends AbstractMenuService {

    private final MovieRepository movieRepository = new MovieRepository();

    private final ActorRepository actorRepository = new ActorRepository();

    @Override
    public void search(Scanner scanner) {

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.println("Veuillez saisir l'année de début:");
        int startYear = scanner.nextInt();
        System.out.println("Veuillez saisir l'année de fin:");
        int endYear = scanner.nextInt();

        Actor actor = findActor(scanner);

        List<Movie> movies = movieRepository.findMoviesBetweenYearsWithActor(startYear, endYear, actor);

        System.out.println("Voici les films sortis entre " + startYear + " et " + endYear +" avec " + actor.getFullName() + ":");
        movies.forEach(movie -> System.out.println(" - " + movie.getTitle() + " - " + movie.getReleaseYear()));
    }

    private Actor findActor(Scanner scanner) {
        System.out.println("Veuillez saisir le nom de l'acteur au format 'Prenom Nom':");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        Actor actor = null;
        while (actor == null) {
            String name = scanner.nextLine();
            actor = actorRepository.findOneBy("fullName", name);
            if (actor == null) {
                System.err.println("Cet acteur n'existe pas, veuillez saisir le nom complet au format 'prenom nom':");
            }
        }
        return actor;
    }
}
