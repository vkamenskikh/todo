package com.vitalika.todo.validation;

/**
 * @author vitali.kamenskikh
 */
public class BaseValidationException extends Exception {
	private static final long serialVersionUID = 183504877433368L;

	public BaseValidationException(String msg) {
		super(msg);
    }
}
