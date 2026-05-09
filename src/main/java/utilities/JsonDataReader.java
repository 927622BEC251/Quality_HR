package utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class JsonDataReader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonDataReader() {
    }

    public static List<Map<String, String>> readArray(String classpathLocation) {
        try (InputStream inputStream = JsonDataReader.class.getClassLoader().getResourceAsStream(classpathLocation)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Test data file not found: " + classpathLocation);
            }
            return OBJECT_MAPPER.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON test data: " + classpathLocation, exception);
        }
    }
}
