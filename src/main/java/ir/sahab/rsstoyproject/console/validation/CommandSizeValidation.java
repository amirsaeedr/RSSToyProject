package ir.sahab.rsstoyproject.console.validation;

public class CommandSizeValidation implements Validation {
    @Override
    public void validate(String[] args, int value) throws Exception{
        if (args.length!=value)
            throw new Exception("command is not valid");
    }
}
