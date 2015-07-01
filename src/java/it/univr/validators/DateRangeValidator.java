package it.univr.validators;

import it.univr.messages.MessagesHandler;

import java.sql.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validatore per le date di ritiro delle vendite. Una data di ritiro è valida
 * se è successiva alla data dell'ordine ({@code Ordine.data_ordine}) oppure se
 * non è stato inserito nulla nel campo del form, in questo caso il valore nel
 * database rimarrà a {@code NULL}.
 */

@FacesValidator("it.univr.validators.DateRangeValidator")
public class DateRangeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        // inputText dove è stata inserita la data
        UIInput pickUpDate =
                (UIInput) component.getAttributes().get("pickUpDate");

        if (pickUpDate == null)
            return;

        String pickUpDateStr = (String) pickUpDate.getSubmittedValue();

        if (pickUpDateStr == null || "".equals(pickUpDateStr))
            return;

        Date dateMin = (Date) component.getAttributes().get("dateMin");

        if (dateMin.toString().compareTo(pickUpDateStr) > 0) {

            pickUpDate.setValid(false);

            MessagesHandler.getInstance().buildMessage("invalidDate",
                    FacesMessage.SEVERITY_WARN);
        }
    }

}
