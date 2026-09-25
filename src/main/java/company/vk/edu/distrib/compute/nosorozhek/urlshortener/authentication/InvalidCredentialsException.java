package company.vk.edu.distrib.compute.nosorozhek.urlshortener.authentication;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
