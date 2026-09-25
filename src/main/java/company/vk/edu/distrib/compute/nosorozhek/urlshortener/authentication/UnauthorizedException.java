package company.vk.edu.distrib.compute.nosorozhek.urlshortener.authentication;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(Exception e) {
        super(e);
    }
}
