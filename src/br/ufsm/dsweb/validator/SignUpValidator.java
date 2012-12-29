package br.ufsm.dsweb.validator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import br.ufsm.dsweb.db.DBActions;

@Named("signUpValidator")
@FacesValidator("signUpValidator")
@RequestScoped
public class SignUpValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent toValidate, Object value)
			throws ValidatorException {
		String username = (String)value;
		if(DBActions.usernameExists(username)) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Username already taken.");
			message.setSummary("Invalid username.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		}
	}
}
