package com.artyemmerkylov.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("imageValidator")
public class ImageValidator implements Validator {
    private static final String IMAGE_PATTERN = "^resources/images/products/.+\\.(png|jpg|jpeg)$";

    private Pattern pattern;

    public ImageValidator() {
        pattern = Pattern.compile(IMAGE_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Matcher matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("Image path validation failed.",
                    "Invalid Image path format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
