package co.com.pruebatecnica.api;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootConfiguration
@SpringBootTest
class RouterRestTest {

    @InjectMocks
    private RouterRest routeRest;

    @Test
    void routerFunctionTest() {
        Handler handler = mock(Handler.class);
        ProductHandler productHandler = mock(ProductHandler.class);
        RouterFunction<ServerResponse> routerFunction = routeRest.routerFunction(handler,productHandler);
        assertNotNull(routerFunction);
    }
}
