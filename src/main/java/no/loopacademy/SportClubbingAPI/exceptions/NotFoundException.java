package no.loopacademy.SportClubbingAPI.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
