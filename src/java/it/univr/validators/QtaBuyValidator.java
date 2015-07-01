package it.univr.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validatore per le quantità di prodotti inserite nel form degli acquisti. Una
 * quantità è considerata valida se compresa tra {@code 0} e la quantità massima
 * disponibile di quel particolare prodotto.
 */

@FacesValidator("it.univr.validators.QtaBuyValidator")
public class QtaBuyValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		double qtaMax = (double) component.getAttributes().get("qtaMax");

		new DoubleRangeValidator(qtaMax, 0).validate(context, component, value);
	}

}
