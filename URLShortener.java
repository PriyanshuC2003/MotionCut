import java.util.*;

public class URLShortener {
    private static final String BASE_URL = "http://short.ly/";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private final Map<String, String> shortToLong = new HashMap<>();
    private final Map<String, String> longToShort = new HashMap<>();
    private final Random random = new Random();

    // Shorten a URL
    public String shortenURL(String longURL) {
        if (longToShort.containsKey(longURL)) {
            return BASE_URL + longToShort.get(longURL);
        }
        
        String shortKey;
        do {
            shortKey = generateShortKey();
        } while (shortToLong.containsKey(shortKey));

        shortToLong.put(shortKey, longURL);
        longToShort.put(longURL, shortKey);

        return BASE_URL + shortKey;
    }

    // Expand a short URL
    public String expandURL(String shortURL) {
        String key = shortURL.replace(BASE_URL, "");
        return shortToLong.getOrDefault(key, "Invalid Short URL");
    }

    // Generate a unique short key
    private String generateShortKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            key.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return key.toString();
    }
}
