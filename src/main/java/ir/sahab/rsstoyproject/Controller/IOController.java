package ir.sahab.rsstoyproject.Controller;

import java.util.Scanner;

public class IOController {
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
    private void showHelp(){
        System.out.print(HELP+"\tshows help menu");
        System.out.println(GET+"\t[option] [webSiteName] you can set "+TOP+" as option to get top 10 news of web site ,"
                +HISTORY+" as option to get history of web site and "
                +COUNT +" as option to get today news count for a website ");
        System.out.println(ADD+"\t [webSiteRSSLink] [config] to add new rss link");
        System.out.println(SEARCH+"\t [option] [text] you can set "+TITLE +" as option to search in news title and "+CONTENT +" as option to search in news content");

    }
    public IOController() {
         input = new Scanner(System.in);
    }

    public void read(){
        while (true){
            String command;
            System.out.print("RSSFeedReader->");
            command =input.next();
            if (command.equals(HELP))
                showHelp();
            else if (command.equals(ADD)){
                String RSSLink = input.next();
                System.out.println(ADD+"  "+RSSLink);
            }
            else if (command.equals(GET)){
                String innerCommed = input.next();
                if (innerCommed.equals(TOP)){
                    String RSSName = input.next();
                    System.out.println(GET+" "+TOP +" "+RSSName);
                }
                else if (innerCommed.equals(COUNT)){
                    String RSSName = input.next();
                    System.out.println(GET+" "+COUNT +" "+RSSName);
                }
                else if (innerCommed.equals(HISTORY)){
                    String RSSName = input.next();
                    System.out.println(GET+" "+HISTORY +" "+RSSName);
                }
                else {
                    System.out.println("not valid command");
                    input.nextLine();
                }
            }
            else if(command.equals(SEARCH)) {
                String innerCommed = input.next();
                if (innerCommed.equals(TITLE)) {
                    String text = input.next();
                    System.out.println(SEARCH + " " + text);
                } else if (innerCommed.equals(CONTENT)) {
                    String text = input.next();
                    System.out.println(SEARCH + " " + text);
                } else {
                    System.out.println("not valid command");
                    input.nextLine();
                }
            }
            else {
                System.out.println("not valid command");
                input.nextLine();
            }
        }
    }
}
