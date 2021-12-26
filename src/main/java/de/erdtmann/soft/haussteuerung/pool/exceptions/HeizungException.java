package de.erdtmann.soft.haussteuerung.pool.exceptions;

public class HeizungException extends Exception {

	private static final long serialVersionUID = 4416450094591496657L;
	
	public HeizungException() {}
	
	public HeizungException(String message) {
		super(message);
	}
}
