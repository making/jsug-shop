package jsug.domain.service.order;

public class EmptyCartOrderException extends RuntimeException {
    public EmptyCartOrderException(String message) {
        super(message);
    }
}
