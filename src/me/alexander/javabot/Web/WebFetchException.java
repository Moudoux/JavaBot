package me.alexander.javabot.Web;

public class WebFetchException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = "";

	public WebFetchException(String message) {
		this.message = message;
	}

	public WebFetchException() {
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void printException() {
		System.err
				.print(Thread.currentThread().getName() + " threw new " + this.getClass().getName() + ":\n" + message);
	}

}
