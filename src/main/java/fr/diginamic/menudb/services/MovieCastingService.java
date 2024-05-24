package fr.diginamic.menudb.services;

import fr.diginamic.moviedb.entities.Movie;
import fr.diginamic.moviedb.entities.Role;
import fr.diginamic.moviedb.repositories.MovieRepository;

import java.util.Scanner;
import java.util.Set;

public class MovieCastingService extends AbstractMenuService {

    private final MovieRepository movieRepository = new MovieRepository();

    @Override
    public void search(Scanner scanner) {

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        Movie movie = null;

        System.out.println("Veuillez saisir le titre du film:");
        while (movie == null) {
            String name = scanner.nextLine();
            movie = movieRepository.findOneBy("title", name);
            if (movie == null) {
                System.err.println("Ce film n'existe pas, veuillez saisir le titre du film:");
            }
        }

        Set<Role> roles = movie.getRoles();
        System.out.println("Voici le casting du film" + movie.getTitle() + ":");
        roles.forEach(role -> System.out.println(" - " + role.getActor().getFullName() + " - " + role.getName()));
    }
}
