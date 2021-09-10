import com.fasterxml.jackson.databind.ObjectMapper;
import filters.expressions.Expression;
import org.junit.Test;

import java.io.IOException;

public class MainTest {

    @Test
    public void testJackson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Expression expression = objectMapper.readValue("{ \"fieldPath\":\"sssss\", \"operator\":{ \"operator\":\"IN\"}, \"value\":1}", Expression.class);
        System.out.println("OK");
    }
}
