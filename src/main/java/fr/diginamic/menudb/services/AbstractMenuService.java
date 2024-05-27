package fr.diginamic.menudb.services;

import java.util.Scanner;

public abstract class AbstractMenuService {

    /**
     * Interrogate the user in the Scanner and request the corresponding repository to get the information to display.
     * @param scanner Scanner
     */
    public abstract void search(Scanner scanner);
}
