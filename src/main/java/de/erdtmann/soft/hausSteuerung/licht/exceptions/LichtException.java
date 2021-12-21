package de.erdtmann.soft.hausSteuerung.licht.exceptions;

public class LichtException extends Exception {

	private static final long serialVersionUID = 7645909342126646834L;

	public LichtException() {}
	
	public LichtException(String message) {
		super(message);
	}
}
