package company.vk.edu.distrib.compute.nosorozhek.urlshortener.validation;

public final class InvalidLinkIdException extends IllegalArgumentException {
    public InvalidLinkIdException(String id) {
        super("Invalid link ID: " + id);
    }
}
