package fudan.se.lab2.exception;

public class ConferHasBeenRegisteredException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public ConferHasBeenRegisteredException(String full) {
        super("Conference '" + full + "' has been registered");
    }
}
