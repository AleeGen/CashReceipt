package factory;

public class MessageFactory {

    private MessageFactory(){}

    public static final String FILE_NOT_FOUND = "%s: file not found";
    public static final String ERROR_READING = "Error reading file: %s";
    public static final String INVALID_FORMAT = "Invalid data format in the file: %s";
}
