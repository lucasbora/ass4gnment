package view;

import java.util.*;

public class TextMenu {
    private Map<String, Command> commands = new HashMap<>();

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command c : commands.values()) {
            System.out.printf("%4s : %s%n", c.getKey(), c.getDescription());
        }
    }

    public void show() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = sc.nextLine();
            Command cmd = commands.get(key);
            if (cmd == null) {
                System.out.println("Invalid Option!");
                continue;
            }
            cmd.execute();
        }
    }
}
