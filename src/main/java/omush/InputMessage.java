package omush;

public class InputMessage {
    private static String _clientId;
    private static String _message;

    public InputMessage(String clientId, String message) {
        _clientId = clientId;
        _message = message;
    }

    public String message() {
        return _message;
    }

    public String clientId() {
        return _clientId;
    }
}
