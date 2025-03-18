package exceptions;

/**
 * Custom exception for handling file management errors.
 */
public class FileManagerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new FileManagerException with the specified detail message and
	 * cause.
	 *
	 * @param message the detail message describing the error
	 * @param cause   the underlying cause of the exception
	 */
	public FileManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}