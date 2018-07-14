package ir.sahab.rsstoyproject.Controller;

import ir.sahab.rsstoyproject.model.ConfigManager;
import ir.sahab.rsstoyproject.model.NewsManager;

import java.util.Scanner;

public class IOController implements Runnable{
    private final static String HELP = "help";
    private final static String GET = "get";
    private final static String ADD = "add";
    private final static String SEARCH = "search";
    private final static String TITLE = "title";
    private final static String CONTENT = "content";
    private final static String TOP ="top";
    private final static String HISTORY = "history";
    private final static String COUNT = "count";
    private Scanner input;
    private Thread thread;
    private final static int FIRST = 0;
    private final static int SECOND = 1;
    private final static int LAST = 2;
    private ConfigManager configManger;
    private NewsManager newsManager;
    private void showHelp(){
        System.out.println(HELP+"\tshows help menu");
        System.out.println(GET+"\t[option] [webSiteName] you can set "+TOP+" as option to get top 10 news of web site ,"
                +HISTORY+" as option to get history of web site and "
                +COUNT +" as option to get today news count for a website ");
        System.out.println(ADD+"\t [webSiteRSSLink] [config] to add new rss link");
        System.out.println(SEARCH+"\t [option] [text] you can set "+TITLE +" as option to search in news title and "+CONTENT +" as option to search in news content");

    }
    public IOController() {
        String databasePassword ;
        String user ;
        input = new Scanner(System.in);
        System.out.println("welcome to RSSReader");
        System.out.print("please enter your sql user name ->");
        user = input.nextLine();
        System.out.print("please enter your sql password ->");
        databasePassword = input.nextLine();
        newsManager = NewsManager.getInstance(user, databasePassword);
        configManger = ConfigManager.getInstance(user,databasePassword);
    }
    private void read(){
        String query;
        String commandParts[];
        while (true){
            String command;
            System.out.print("RSSFeedReader->");
            command =input.nextLine();
            commandParts = command.split(" ");
            if (commandParts[FIRST].equals(HELP))
                showHelp();
            else if (commandParts[FIRST].equals(ADD)&&commandParts.length==3){
                configManger.addConfig(commandParts[SECOND],commandParts[LAST]);
                System.out.println("Config added");
            }
            else if (commandParts[FIRST].equals(GET)&&commandParts.length==3){
                if (commandParts[SECOND].equals(TOP)){
                    query = "select distinct * from News where site=\""+commandParts[LAST]+"\" limit 10;";
                    newsManager.get(query);
                }
                else if (commandParts[SECOND].equals(COUNT)){
                    System.out.println(GET+" "+COUNT +" "+commandParts[LAST]);
                }
                else if (commandParts[SECOND].equals(HISTORY)){
                    System.out.println(GET+" "+HISTORY +" "+commandParts[LAST]);
                }
                else {
                    System.out.println("not valid command");
                }
            }
            else if(commandParts[FIRST].equals(SEARCH)&&commandParts.length==3) {
                if (commandParts[SECOND].equals(TITLE)) {
                    System.out.println(SEARCH + " " + commandParts[LAST]);
                } else if (commandParts[SECOND].equals(CONTENT)) {
                    System.out.println(SEARCH + " " + commandParts[LAST]);
                } else {
                    System.out.println("not valid command");
                }
            }
            else {
                System.out.println("not valid command");
            }
        }
    }

    @Override
    public void run() {
        read();
    }

    public void start() {
        thread= new Thread(this,"IOThread");
        thread.start();
    }
}
