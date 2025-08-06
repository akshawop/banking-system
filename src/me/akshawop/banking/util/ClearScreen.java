package me.akshawop.banking.util;

import java.io.IOException;

/**
 * The helper class to clear the terminal screen.
 */
public class ClearScreen {
    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

        } catch (IOException | InterruptedException ex) {
            System.err.println("Error while clearing screen: " + ex);
        }
    }
}
