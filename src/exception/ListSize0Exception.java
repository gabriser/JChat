package exception;

public class ListSize0Exception extends Exception {

	private static final long serialVersionUID = 1L;

	public ListSize0Exception(String message) {
		super(message);
		
	}

	public ListSize0Exception(Throwable cause) {
		super(cause);
		
	}

	public ListSize0Exception(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ListSize0Exception(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}