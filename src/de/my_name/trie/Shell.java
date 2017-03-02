package de.my_name.trie;

import de.my_name.trie.util.Trie;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;

/**
 * Created by my_name on 24/10/2016.
 */
public final class Shell {

    private static boolean run = true;
    private static Trie current;

    private static final String REGEX = "\\s+";
    private static final String TABULATOR = "        ";
    private static final String PROMPT = "trie> ";
    private static final String ERROR_MESSAGE = "Error! ";

    private Shell() {
        throw new UnsupportedOperationException();
    }

    private static void execute(String input) {
        Scanner scanner = new Scanner(input);
        scanner.useDelimiter(REGEX);
        String command;
        if (scanner.hasNext()) {
            command = scanner.next();
        } else {
            //Returns if nothing is entered in the shell.
            scanner.close();
            return;
        }
        char first = command.charAt(0);
        first = Character.toLowerCase(first);
        switch (first) {
            case 'n':
                current = new Trie();
                break;
            case 'a':
                add(current, scanner);
                break;
            case 'c':
                change(current, scanner);
                break;
            case 'd':
                delete(current, scanner);
                break;
            case 'p':
                getPoints(current, scanner);
                break;
            case 't':
                trie(current);
                break;
            case 'h':
                help();
                break;
            case 'q':
                run = false;
                break;
            default:
                error("'" + command + "' is an unkown "
                        + "command. Enter 'help' for Help.");

        }
        scanner.close();
    }

    private static void add(Trie current, Scanner scanner) {
        String name = readName(scanner);
        Integer points = readInt(scanner);
        if (name == null || points == null) {
            return;
        }
        boolean added = current.add(name, points);
        if (!added) {
            error("'" + name + "' is already in the trie.");
        }
    }

    private static void change(Trie current, Scanner scanner) {
        String name = readName(scanner);
        Integer points = readInt(scanner);
        if (name == null || points == null) {
            return;
        }
        boolean changed = current.change(name, points);
        if (!changed) {
            error("'" + name + "' is not already in the trie.");
        }
    }

    private static void delete(Trie current, Scanner scanner) {
        String name = readName(scanner);
        if (name == null) {
            return;
        }
        boolean deleted = current.remove(name);
        if (!deleted) {
            error("'" + name + "' is not in the trie.");
        }
    }

    private static void getPoints(Trie current, Scanner scanner) {
        String name = readName(scanner);
        if (name == null) {
            return;
        }
        Integer points = current.getPoints(name);
        if (points != null) {
            System.out.println(points.toString());
        } else {
            error("'" + name + "' is not in the trie.");
        }
    }

    private static void trie(Trie current) {
            System.out.println(current.toString());
    }

    private static void help() {
        System.out.println("Help menu for this trie shell:\n\n");
        System.out.println("Commands:\n");
        System.out.println("new\n" + TABULATOR + "Creates a new Trie and "
                + "discards the old one.\n");
        System.out.println("add <name> <points>\n" + TABULATOR + "Adds the "
                + "<points> of the student <name> to the Trie.\n");
        System.out.println("change <name> <points>\n" + TABULATOR + "Changes "
                + "the preexisting points of a student <name> to <points>.\n");
        System.out.println("delete <name>\n" + TABULATOR + "Deletes the "
                + "student <name> from the Trie.\n");
        System.out.println("points <name>\n" + TABULATOR + "Returns the "
                + "points of the student <name>.\n");
        System.out.println("help\n" + TABULATOR + "Shows this command prompt"
                + ".\n");
        System.out.println("trie\n" + TABULATOR + "Returns a string "
                + "representation of the Trie\n\n");
        System.out.println("Arguments:\n");
        System.out.println("<name>\n" + TABULATOR + "Must be a name written "
                + "in the lower case English alphabet.\n");
        System.out.println("<points>\n" + TABULATOR + "Must be a non-negative"
                + " integer.");
    }

    private static String readName(Scanner scanner) {
        String name;
        if (scanner.hasNext()) {
            name = scanner.next();
        } else {
            error("No command!");
            return null;
        }
        if (!nameHasCorrectForm(name)) {
            error("Name does not have the correct Form.");
            return null;
        }
        return name;
    }

    private static Integer readInt(Scanner scanner) {
        int points;
        if (scanner.hasNextInt()) {
            points = scanner.nextInt();
        } else {
            error("No number!");
            return null;
        }
        if (!pointsHaveCorrectForm(points)) {
            error("Number does not have the correct Form.");
            return null;
        }
        return points;
    }

    private static boolean nameHasCorrectForm(String name) {
        char[] charedKey = name.toCharArray();
        boolean correct = true;
        for (char ch : charedKey) {
            if (Character.getType(ch) != Character.LOWERCASE_LETTER) {
                correct = false;
            }
        }
        return correct;
    }

    private static boolean pointsHaveCorrectForm(int points) {
        return (points >= 0);
    }

    private static void error(String message) {
        System.out.println(ERROR_MESSAGE + message);
    }
    /**
     * The main method which is run a soon the programm is run.
     *
     * @param args The shell input Parameters args are recieved here.
     * @throws IOException is thrown if the buffered reader throws one.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        current = new Trie();
        while (run) {
            System.out.print(PROMPT);
            String input = reader.readLine();
            if (input == null) {
                break;
            }
            execute(input);
        }
    }
}
