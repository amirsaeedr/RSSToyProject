package ir.sahab.rsstoyproject.console.command;

public class HelpCommand extends Command {
    public HelpCommand(String user, String databasePassword) {
        super(user, databasePassword);
    }
    @Override
    public void execute(String[] Args) {
        System.out.println("-l [site name]\t for get 10 latest news");
        System.out.println("-c [site name][from day] [to day] \t to get news count of those days ,3rd arg is optional");
        System.out.println("-a [site name][pattern] \t to add new website ,3rd arg is optional");
        System.out.println("-s [type] [text] \t to search in title or content , type could be title or content");
        System.out.println("-h \t for help");
    }
}
