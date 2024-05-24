package fr.diginamic.menudb;

import fr.diginamic.menudb.services.ActorFilmographyService;

import java.util.Scanner;

public class SearchMenu {

    public static void main(String[] args) {
        System.out.println("Bienvenue dans le menu de l'application IMDB, veuillez faire un choix:");
        Scanner scanner = new Scanner(System.in);
        do {
            int choice = showMenu(scanner);
            handleChoice(scanner, choice);
        } while (scanner.hasNextLine());
    }

    /**
     * Display the application menu in the console
     *
     * @param scanner Scanner
     */
    private static int showMenu(Scanner scanner) {

        System.out.println("1 - Afficher la filmographie d'un acteur " +
                "\n2 - Afficher le casting d'un film " +
                "\n3 - Afficher les films sorties entre deux années " +
                "\n4 - Afficher les films communs à 2 acteurs/actrices" +
                "\n5 - Afficher les acteurs communs à 2 films" +
                "\n6 - Afficher les films sortis entre 2 annees et qui ont un acteur/actrice donné au casting" +
                "\n7 - Quitter l'application");

        return scanner.nextInt();
    }

    private static void handleChoice(Scanner scanner, int choice) {
        switch (choice) {
            case 1:
                ActorFilmographyService actorFilmographyService = new ActorFilmographyService();
                actorFilmographyService.search(scanner);
                showMenu(scanner);
                break;

        }
    }
}
