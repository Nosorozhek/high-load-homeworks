package company.vk.edu.distrib.compute.nosorozhek.urlshortener;

import company.vk.edu.distrib.compute.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PersistentDao implements Dao<String> {
    private static final Logger log = LoggerFactory.getLogger(PersistentDao.class);
    private final Map<String, String> entries;
    private final Path filePath;

    private void saveEntries() throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(entries);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> loadEntries() throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Map<String, String>) objectInputStream.readObject();
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new IOException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    PersistentDao(Path filePath) throws IOException {
        this.filePath = filePath;
        entries = loadEntries();
    }

    @Override
    public String get(String key) throws NoSuchElementException, IllegalArgumentException {
        String value = entries.get(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public void upsert(String key, String value) throws IllegalArgumentException {
        entries.put(key, value);
    }

    @Override
    public void delete(String key) throws IllegalArgumentException {
        entries.remove(key);
    }

    @Override
    public void close() throws IOException {
        saveEntries();
    }
}
