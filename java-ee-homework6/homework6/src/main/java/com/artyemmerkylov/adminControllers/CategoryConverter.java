package com.artyemmerkylov.adminControllers;

import com.artyemmerkylov.models.Category;
import com.artyemmerkylov.models.CategoryRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class CategoryConverter implements Converter<Category> {

    @Inject
    private CategoryRepository categoryRepository;

    @Override
    public Category getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return categoryRepository.findById(Integer.parseInt(value))
                .orElseThrow(() -> new ConverterException(new FacesMessage(String.format("%s is not correct id", value))));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Category value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
