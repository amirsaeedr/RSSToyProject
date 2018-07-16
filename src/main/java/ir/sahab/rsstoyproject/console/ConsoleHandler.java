package ir.sahab.rsstoyproject.console;

import ir.sahab.rsstoyproject.console.command.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleHandler implements Runnable {
    private Scanner input;
    private Thread thread;
    private final static int FIRST = 0;
    private final static int SECOND = 1;
    private final static int LAST = 2;
    private Map<String, Command> commands;

    public ConsoleHandler() {
        String databasePassword;
        String user;
        input = new Scanner(System.in);
        System.out.println("welcome to RSSReader");
        System.out.print("please enter your sql user name ->");
        user = input.nextLine();
        System.out.print("please enter your sql password ->");
        databasePassword = input.nextLine();
        commands = new HashMap<>();
        commands.put("-h", new HelpCommand(user, databasePassword));
        commands.put("-s",new SearchCommand());
        commands.put("-a",new NewsSiteCommand());
        commands.put("-c",new HistoryCommamd());
        commands.put("-l",new LatestNewsCommand());

    }

    @Override
    public void run() {
        while (true){
            String []args = parseArgs(input.nextLine());
            commands.get(args[0]).execute(args);
        }
    }

    private String [] parseArgs(String input){
        return input.split("\\s+");
    }
    public void start() {
        thread = new Thread(this, "IOThread");
        thread.start();


    }
}
