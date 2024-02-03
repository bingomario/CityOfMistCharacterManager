package ui;

import model.*;
import model.Character;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


import java.util.List;
import java.util.ArrayList;

//Provides a console ui user interface
public class ConsoleUI {
    private List<Party> partyList;
    private Scanner input;

    //EFFECTS: Creates an empty partyList, initializes scanner, begins console display
    public ConsoleUI() {
        partyList = new ArrayList<>();
        input = new Scanner(System.in);
        runConsole();
    }

    //EFFECTS: Prints a welcome message and calls the first input loop
    private void runConsole() {
        System.out.println("Welcome to the City of Mist Character Manager!");
        mainMenu();
    }

    //EFFECTS: Overhead loop, allowing users to exit application
    //add parties to the list, or select a party
    private void mainMenu() {
        boolean run = true;
        String command = null;
        while (run) {
            if (partyList.size() == 1) {
                System.out.println("There is currently 1 party");
            } else {
                System.out.printf("There are currently %d parties%n", partyList.size());
            }
            printMenuOptions();
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                System.out.println("Goodbye");
                run = false;
            } else {
                mainCommand(command);
            }
        }
    }

    //EFFECTS: Prints all main menu options to console
    private void printMenuOptions() {
        System.out.println("Would you like to:");
        System.out.println("\t'a' : add a new party");
        System.out.println("\t'r' : remove an empty party");
        System.out.println("\t'p' : list all parties");
        System.out.println("\t'c' : choose a party");
        System.out.println("\t's' : save parties");
        System.out.println("\t'l' : load parties from file");
        System.out.println("\t'q' : quit the application");
    }

    //EFFECTS:Interprets a command in the main loop
    private void mainCommand(String command) {
        if (command.equals("a")) {
            addParty();
        } else if (command.equals("r")) {
            removeParty();
        } else if (command.equals("p")) {
            listParties();
        } else if (command.equals("c")) {
            selectParty();
        } else if (command.equals("s")) {
            saveParties("./data/parties.json");
        } else if (command.equals("l")) {
            loadParties("./data/parties.json");
        } else {
            System.out.printf("Unknown Command : %s%n", command);
        }
    }

    //EFFECTS: Saves list of parties to given file
    private void saveParties(String file) {
        JsonWriter writer = new JsonWriter(file);
        try {
            writer.open();
            writer.writeToFile(partyList);
            writer.close();
            System.out.println("Save complete!");
        } catch (FileNotFoundException e) {
            System.out.println("File Error, failed to save parties.");
        }
    }

    //MODIFIES: this
    //EFFECTS: Loads parties from given file
    private void loadParties(String file) {
        JsonReader reader = new JsonReader(file);
        try {
            partyList = reader.readFile();
            System.out.println("File load complete!");
        } catch (IOException e) {
            System.out.println("File Read Error, failed to load file.");
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds party of users creation to list of parties
    private void addParty() {
        System.out.println("Please enter the new party's name: ");
        input.nextLine();
        String name = input.nextLine();
        this.partyList.add(new Party(name));
    }

    //MODIFIES: this
    //EFFECTS: Removes an empty party of users choice from list of Parties
    private void removeParty() {
        listParties();
        System.out.println("Enter the number of the party you wish to remove: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < partyList.size()) {
            if (partyList.get(index).getNumMembers() == 0) {
                partyList.remove(index);
            } else {
                System.out.println("Party must have no members to be removed");
            }

        } else {
            System.out.println("Invalid selection");
        }
    }

    //EFFECTS: Lists all parties
    private void listParties() {
        int i = 1;
        for (Party party : partyList) {
            System.out.printf("%d - %s%n", i, party.getName());
            i = i + 1;
        }
    }

    //EFFECTS: Selects a party of the user's choice
    private void selectParty() {
        listParties();
        System.out.println("Enter the number of the party you wish to select: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < partyList.size()) {
            partyMenu(partyList.get(index));
        } else {
            System.out.println("Invalid selection");
        }
    }

    //EFFECTS: Party level loop,
    //commands include adding & removing characters, listing all characters, selecting a character,
    //or heading back to main level
    private void partyMenu(Party party) {
        boolean run = true;
        String command = null;

        while (run) {
            System.out.printf("Welcome to Party %s%n", party.getName());
            System.out.println("Would you like to:");
            System.out.println("\t'a' : add a new character");
            System.out.println("\t'r' : remove a character");
            System.out.println("\t'l' : list all characters");
            System.out.println("\t's' : select a character");
            System.out.println("\t'q' : return to main menu");
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                run = false;
            } else {
                partyCommand(command, party);
            }
        }
    }

    //EFFECTS:Interprets a command in the party loop
    private void partyCommand(String command, Party party) {
        if (command.equals("a")) {
            addCharacter(party);
        } else if (command.equals("r")) {
            removeCharacter(party);
        } else if (command.equals("l")) {
            listCharacters(party);
        } else if (command.equals("s")) {
            selectCharacter(party);
        } else {
            System.out.printf("Unknown Command: %s%n", command);
        }
    }

    //MODIFIES: party
    //EFFECTS: Adds character of users creation to party
    private void addCharacter(Party party) {
        System.out.println("Please enter the new Character's name: ");
        input.nextLine();
        String name = input.nextLine();
        party.addMember(new Character(name));
    }

    //MODIFIES: party
    //EFFECTS: Removes a character of users choice from the party
    private void removeCharacter(Party party) {
        listCharacters(party);
        System.out.println("Enter the number of the character you wish to remove: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < party.getNumMembers()) {
            party.removeMember(index);
        } else {
            System.out.println("Invalid selection");
        }
    }

    //EFFECTS: Lists all characters in the party
    private void listCharacters(Party party) {
        int i = 1;
        List<Character> characters = party.getMembers();
        for (Character character : characters) {
            System.out.printf("%d - %s%n", i, character.getName());
            i = i + 1;
        }
    }

    //EFFECTS: Selects a character of the user's choice from the party
    private void selectCharacter(Party party) {
        listCharacters(party);
        System.out.println("Enter the number of the character you wish to select: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < party.getNumMembers()) {
            characterMenu(party.getMembers().get(index));
        } else {
            System.out.println("Invalid selection");
        }
    }

    //EFFECTS: Character Level Loop
    //commands include accessing Themes, Statuses, and Story Tags,
    //as well as listing all data and adding build up
    private void characterMenu(Character character) {
        boolean run = true;
        String command = null;

        while (run) {
            System.out.println(character.getName());
            System.out.println("Would you like to:");
            System.out.println("\t'o' : see a character overview");
            System.out.println("\t't' : access character themes");
            System.out.println("\t's' : access character statues");
            System.out.println("\t'l' : access character story tags");
            System.out.println("\t'b' : add build up to character");
            System.out.println("\t'q' : return to party menu");
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                run = false;
            } else {
                characterCommand(command, character);
            }
        }
    }

    //EFFECTS: Interprets a command in the Character loop
    private void characterCommand(String command, Character character) {
        if (command.equals("o")) {
            characterOverview(character);
        } else if (command.equals("t")) {
            listThemes(character);
            themesOptions(character);
        } else if (command.equals("s")) {
            for (Status status : character.getStatuses()) {
                System.out.printf("Status: %-20s Tier: %d%n", status.getName(), status.getTier());
            }
            statusOptions(character);
        } else if (command.equals("l")) {
            for (String tag : character.getStoryTags()) {
                System.out.printf("Tag: %s%n", tag);
            }
            tagOptions(character.getStoryTags());
        } else if (command.equals("b")) {
            addBuildUp(character);
        } else {
            System.out.printf("Unknown Command: %s%n", command);
        }
    }

    //EFFECTS: Prints all information regarding a character, their themes,
    //their statuses, and their story tags
    private void characterOverview(Character character) {
        System.out.printf("Name : %s%n", character.getName());
        int i = 1;
        for (Theme theme : character.getThemes()) {
            System.out.printf("Theme %d:%n", i);
            themeOverview(theme);
            i = i + 1;
        }
        System.out.println("Statuses:");
        for (Status status : character.getStatuses()) {
            System.out.printf("Status: %-20s Tier: %d%n", status.getName(), status.getTier());
        }
        System.out.println("Story Tags:");
        for (String tag : character.getStoryTags()) {
            System.out.printf("Tag: %s%n", tag);
        }
        System.out.printf("Build Up: %d/5%n", character.getBuildUp());
        System.out.printf("Evolutions: %d%n", character.getNumEvolutions());
        System.out.println();
    }

    //EFFECTS: Prints all information regarding a theme
    private void themeOverview(Theme theme) {
        System.out.printf("Name: %s", theme.getName());
        System.out.printf("\tCore: %s%n", theme.getCoreStatement());
        System.out.printf("Power Tags: ");
        for (String tag : theme.getTags()) {
            System.out.printf("%s, ", tag);
        }
        System.out.println();
        System.out.printf("Weaknesses: ");
        for (String tag : theme.getWeaknesses()) {
            System.out.printf("%s, ", tag);
        }
        System.out.println();
        System.out.printf("Attention: %d/3%n", theme.getAttention());
        System.out.printf("Loss: %d/3%n", theme.getLoss());
        System.out.printf("Improvements: %d%n", theme.getNumImprovements());
    }

    //EFFECTS: Lists the names of all the themes of a given character
    private void listThemes(Character character) {
        int i = 1;
        for (Theme theme : character.getThemes()) {
            System.out.printf("%d: %s%n", i, theme.getName());
            i = i + 1;
        }
    }

    //EFFECTS: Sub-menu of character to add, remove, or select a theme
    private void themesOptions(Character character) {
        String command = null;
        System.out.println("Would you like to:");
        System.out.println("\t'a' : add a theme");
        System.out.println("\t'r' : remove a theme");
        System.out.println("\t's' : select a theme");
        command = input.next().toLowerCase();
        if (command.equals("a")) {
            addTheme(character);
        } else if (command.equals("r")) {
            deleteTheme(character);
        } else if (command.equals("s")) {
            selectTheme(character);
        }
    }

    //MODIFIES: character
    //EFFECTS: Creates a theme from user input then adds it to given character
    private void addTheme(Character character) {
        System.out.println("Enter l for a logos theme and m for a mythos theme: ");
        String themeType = input.next().toLowerCase();
        if (!(themeType.equals("l") || themeType.equals("m"))) {
            System.out.printf("Invalid theme type: %s%n", themeType);
            return;
        }
        System.out.println("Enter the theme's name: ");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Enter the theme's core statement: ");
        //input.nextLine();
        String core = input.nextLine();
        if (themeType.equals("l")) {
            character.addTheme(new Logos(name, core));
        } else if (themeType.equals("m")) {
            character.addTheme(new Mythos(name, core));
        }
    }

    //MODIFIES: character
    //EFFECTS: Removes a theme of the users choice from character
    private void deleteTheme(Character character) {
        listThemes(character);
        List<Theme> themes = character.getThemes();
        System.out.println("Enter the number of the theme you wish to remove: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < themes.size()) {
            themes.remove(index);
        } else {
            System.out.println("Invalid Selection");
        }
    }

    //EFFECTS: Selects a theme of the users choice from the character
    private void selectTheme(Character character) {
        listThemes(character);
        List<Theme> themes = character.getThemes();
        System.out.println("Enter the number of the theme you wish to select: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < themes.size()) {
            themeMenu(themes.get(index));
        } else {
            System.out.println("Invalid Selection");
        }
    }

    //EFFECTS: Sub-menu of character to add and remove statuses
    private void statusOptions(Character character) {
        String command = null;
        System.out.println("Would you like to:");
        System.out.println("\t'a' : add a status");
        System.out.println("\t'r' : remove a status");
        command = input.next().toLowerCase();
        if (command.equals("a")) {
            addStatus(character);
        } else if (command.equals("r")) {
            deleteStatus(character);
        }
    }

    //MODIFIES: character
    //EFFECTS: Adds a status of users creation to character
    private void addStatus(Character character) {
        System.out.println("Enter the status' name:");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Enter the status' tier:");
        int tier = input.nextInt();
        character.addStatus(new Status(name, tier));
    }

    //MODIFIES: character
    //EFFECTS: Removes a status of users choice from character
    private void deleteStatus(Character character) {
        int i = 1;
        List<Status> statuses = character.getStatuses();
        for (Status status : statuses) {
            System.out.printf("%d: Status: %-20s Tier: %d%n", i, status.getName(), status.getTier());
            i = i + 1;
        }
        System.out.println("Enter the number of the status you wish to remove: ");
        int index = input.nextInt() - 1;
        if (0 <= index && index < statuses.size()) {
            statuses.remove(index);
        } else {
            System.out.println("Invalid Selection");
        }
    }

    //MODIFIES: tags
    //EFFECTS: Sub-menu to add and remove tags to/from given list.
    //Allows user to choose whether they are adding or removing a tag,
    //then allows them to create the tag they wish to add or select which they wish to remove.
    private void tagOptions(List<String> tags) {
        String command = null;
        System.out.println("Would you like to:");
        System.out.println("\t'a' : add a tag");
        System.out.println("\t'r' : remove a tag");
        command = input.next().toLowerCase();
        if (command.equals("a")) {
            System.out.println("Enter the tag you would like to add: ");
            input.nextLine();
            String tag = input.nextLine();
            tags.add(tag);
        } else if (command.equals("r")) {
            int i = 1;
            for (String tag : tags) {
                System.out.printf("%d: Tag: %s%n", i, tag);
                i = i + 1;
            }
            System.out.println("Enter the number of the tag you would like to remove: ");
            int index = input.nextInt() - 1;
            if (index >= 0 && index <= tags.size()) {
                tags.remove(index);
            }
        }
    }

    //MODIFIES: character
    //Adds an amount of build up of users choice to character
    private void addBuildUp(Character character) {
        System.out.printf("Current Build Up: %-20d", character.getBuildUp());
        System.out.printf("Current Improvements: %d%n", character.getNumEvolutions());
        System.out.println("Enter the amount of build up you wish too add: ");
        int toAdd = input.nextInt();
        character.addBuildUp(toAdd);
        System.out.printf("New Build Up: %-20d", character.getBuildUp());
        System.out.printf("New Improvements: %d%n", character.getNumEvolutions());
    }

    //EFFECTS: Theme level menu, with options for accessing power and weakness tags,
    //changing the core statement, and marking attention or loss
    private void themeMenu(Theme theme) {
        boolean run = true;
        String command = null;

        while (run) {
            themeOverview(theme);
            System.out.println("Would you like to:");
            System.out.println("\t'p': access power tags");
            System.out.println("\t'w': access weakness tags");
            System.out.println("\t'c': change core statement (mystery/identity)");
            System.out.println("\t'a': mark attention");
            System.out.println("\t'l': mark loss (fade/crack)");
            System.out.println("\t'q': return to character menu");
            command = input.next();
            if (command.equals("q")) {
                run = false;
            } else {
                themeCommands(command, theme);
            }
        }
    }

    //EFFECTS: Interprets command from Theme menu
    private void themeCommands(String command, Theme theme) {
        if (command.equals("p")) {
            tagOptions(theme.getTags());
        } else if (command.equals("w")) {
            tagOptions(theme.getWeaknesses());
        } else if (command.equals("c")) {
            changeCore(theme);
        } else if (command.equals("a")) {
            markAttention(theme);
        } else if (command.equals("l")) {
            markLoss(theme);
        }
    }

    //MODIFIES: theme
    //EFFECTS: Changes core statement to a message of users creation
    private void changeCore(Theme theme) {
        System.out.println("Enter new core statement: ");
        input.nextLine();
        String core = input.nextLine();
        theme.setCoreStatement(core);
    }

    //MODIFIES: theme
    //EFFECTS: Marks one attention on theme
    private void markAttention(Theme theme) {
        boolean newImprovement = theme.markAttention();
        System.out.printf("New Attention: %d%n", theme.getAttention());
        if (newImprovement) {
            System.out.printf("New Improvements: %d%n", theme.getNumImprovements());
        }
    }

    //MODIFIES: theme
    //EFFECTS: Marks one loss on theme
    private void markLoss(Theme theme) {
        theme.markLoss();
        System.out.printf("New Loss: %d%n", theme.getLoss());
    }
}
