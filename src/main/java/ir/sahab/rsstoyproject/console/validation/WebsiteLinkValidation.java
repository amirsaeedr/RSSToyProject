package ir.sahab.rsstoyproject.console.validation;

import java.util.regex.Pattern;

public class WebsiteLinkValidation implements Validation {
    @Override
    public void validate(String[] args, int value) throws Exception {
        Pattern pattern;
        pattern = Pattern.compile("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$");
        if (!pattern.matcher(args[value]).matches()) {
            throw new Exception("website name is not valid");
        }
    }
}
