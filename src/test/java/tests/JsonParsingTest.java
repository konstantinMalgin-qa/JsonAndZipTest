package tests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.model.Client;

import java.io.InputStream;

public class JsonParsingTest {

    private final ClassLoader cl = JsonParsingTest.class.getClassLoader();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Проверяем файл client.json")
    @Test
    void parseJsonWithJackson() throws Exception {
        try (InputStream is = cl.getResourceAsStream("client.json")) {
            Assertions.assertNotNull(is, "Файл client.json не найден в resources");

            Client client = objectMapper.readValue(is, Client.class);

            Assertions.assertEquals("Jack", client.getName());
            Assertions.assertEquals("jack@example.com", client.getEmail());
            Assertions.assertEquals(2, client.orders.size());

            Assertions.assertEquals("Corn", client.orders.get(0).product);
            Assertions.assertEquals(14, client.orders.get(0).price);
        }
    }
}
