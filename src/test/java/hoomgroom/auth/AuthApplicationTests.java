package hoomgroom.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true, "The application context should have loaded.");
    }

    @Test
    void mainMethodRunsSuccessfully() {
        boolean didRunSuccessfully = true;
        try {
            AuthApplication.main(new String[]{});
        } catch (Exception e) {
            didRunSuccessfully = false;
        }
        assertTrue(didRunSuccessfully, "The main method should run without throwing any exceptions.");
    }
}
