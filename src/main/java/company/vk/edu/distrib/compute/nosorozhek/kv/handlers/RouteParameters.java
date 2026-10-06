package company.vk.edu.distrib.compute.nosorozhek.kv.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public record RouteParameters(Matcher values) {
    public static String requiredId(HttpExchange exchange) {
        String query = exchange.getRequestURI().getRawQuery();
        if (query == null) {
            throw new IllegalArgumentException("Missing or empty id");
        }
        for (String part : query.split("&", -1)) {
            if (!part.startsWith("id=")) {
                continue;
            }
            String id = URLDecoder.decode(part.substring(3), StandardCharsets.UTF_8);
            if (id.isEmpty()) {
                throw new IllegalArgumentException("Missing or empty id");
            }
            return id;
        }
        throw new IllegalArgumentException("Missing or empty id");
    }
}
