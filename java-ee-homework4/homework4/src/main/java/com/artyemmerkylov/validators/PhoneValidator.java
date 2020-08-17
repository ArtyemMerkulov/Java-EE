package com.artyemmerkylov.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {
    private static final String PHONE_PATTERN = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" +
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    private Pattern pattern;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Matcher matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("Phone validation failed.",
                    "Invalid Phone format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
