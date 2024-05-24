package fr.diginamic.menudb.services;

import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Role;
import fr.diginamic.moviedb.repositories.ActorRepository;

import java.util.Scanner;
import java.util.Set;

public class ActorFilmographyService extends AbstractMenuService {

    private final ActorRepository actorRepository = new ActorRepository();

    @Override
    public void search(Scanner scanner) {

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        Actor actor = null;

        while(actor == null){
            System.out.println("Veuillez saisir un nom complet au format 'prenom nom':");
            String name = scanner.nextLine();
            actor = actorRepository.findOneBy("fullName", name);
            if(actor == null){
                System.err.println("Cet acteur n'existe pas, veuillez saisir le nom complet au format 'prenom nom':");
            }
        }

        Set<Role> roles = actor.getRoles();
        System.out.println("Voici la filmographie de" + actor.getFullName() + ":");
        roles.forEach(role -> System.out.println(role.getMovie().getTitle() + " - " + role.getName()));
    }
}
