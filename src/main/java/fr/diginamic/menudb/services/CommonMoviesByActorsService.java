package fr.diginamic.menudb.services;

import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.moviedb.repositories.ActorRepository;
import fr.diginamic.moviedb.repositories.MovieRepository;

import java.util.List;
import java.util.Scanner;

public class CommonMoviesByActorsService extends AbstractMenuService {

    private final MovieRepository movieRepository = new MovieRepository();

    private final ActorRepository actorRepository = new ActorRepository();

    @Override
    public void search(Scanner scanner) {

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        Actor actor1 = findActor(scanner);
        Actor actor2 = findActor(scanner);

        List<Movie> movies = movieRepository.findCommonMoviesForTwoActors(actor1, actor2);

        System.out.println("Voici les films communs pour " + actor1.getFullName() + " et " + actor2.getFullName() + ":");
        movies.forEach(movie -> System.out.println(" - " + movie.getTitle() + " - " + movie.getReleaseYear()));
    }

    private Actor findActor(Scanner scanner) {
        System.out.println("Veuillez saisir le nom de l'acteur au format 'Prenom Nom':");
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
