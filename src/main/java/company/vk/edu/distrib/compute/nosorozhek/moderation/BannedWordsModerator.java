package company.vk.edu.distrib.compute.nosorozhek.moderation;

import company.vk.edu.distrib.compute.moderation.LinkCreatedEvent;
import company.vk.edu.distrib.compute.moderation.ModerationResultEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BannedWordsModerator {
    private static final String BANNED_WORDS_PATH = "moderation/banned_words.txt";
    private final Set<String> bannedWords;

    BannedWordsModerator() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(BANNED_WORDS_PATH)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Banned words list not found: " + BANNED_WORDS_PATH);
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                bannedWords = reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toCollection(HashSet::new));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ModerationResultEvent moderate(LinkCreatedEvent event) {
        for (String word : bannedWords) {
            if (event.url().contains(word)) {
                return new ModerationResultEvent(event.id(), false,
                        "Url contains forbidedn word: \"" + word + "\"", Instant.now());
            }
        }
        return new ModerationResultEvent(event.id(), true, null, Instant.now());
    }
}
