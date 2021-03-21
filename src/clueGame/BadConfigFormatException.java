/* @author Gustavo Alves
 * @author Noah Terry
 * 
 */
package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Wrongly Configured Data Files. Please Double Check.");
	}

	public BadConfigFormatException(String message) {
		super(message);
	}

}
