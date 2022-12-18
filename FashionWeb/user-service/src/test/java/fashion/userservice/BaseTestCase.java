package fashion.userservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class BaseTestCase {

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeMock() throws Exception{
        MockitoAnnotations.openMocks(this).close();
    }
}
