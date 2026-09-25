package company.vk.edu.distrib.compute.nosorozhek.urlshortener;

import company.vk.edu.distrib.compute.AbstractHttpServiceFactory;
import company.vk.edu.distrib.compute.Dao;
import company.vk.edu.distrib.compute.urlshortener.UrlShortenerAuthTest;
import company.vk.edu.distrib.compute.urlshortener.UrlShortenerService;
import company.vk.edu.distrib.compute.urlshortener.UrlShortenerTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@UrlShortenerTest
@UrlShortenerAuthTest
public class UrlShortenerServiceFactory extends AbstractHttpServiceFactory<UrlShortenerService> {
    private static final String DATA_DIR_ENV = "SERVICE_DATA_DIR";
    private static final String DEFAULT_DATA_DIR = "data";

    @Override
    protected UrlShortenerService doCreate(int port) throws IOException {
        Path dataDirectory = getDataDirectory();
        Files.createDirectories(dataDirectory);

        Dao<String> urlDao = new PersistentDao(dataDirectory.resolve("urls.data"));
        Dao<String> userDao = new PersistentDao(dataDirectory.resolve("users.data"));

        return new UrlShortenerServiceImpl(urlDao, userDao, port);
    }

    private static Path getDataDirectory() {
        String configuredDirectory = System.getenv(DATA_DIR_ENV);
        if (configuredDirectory == null || configuredDirectory.isBlank()) {
            return Path.of(DEFAULT_DATA_DIR);
        }
        return Path.of(configuredDirectory);
    }
}
