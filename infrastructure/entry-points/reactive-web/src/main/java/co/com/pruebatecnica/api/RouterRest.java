package co.com.pruebatecnica.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler,ProductHandler productHandler) {
        return route(POST("/api/franchise"), handler::addFranchise)
                .andRoute(POST("/api/branchOffice"), handler::addBranchOffice)
                .andRoute(POST("/api/product"), productHandler::addToBranchOffice)
                .andRoute(DELETE("/api/product"), productHandler::removeBranchOffice)
                .andRoute(PUT("/api/product"), productHandler::changeStock)
                .andRoute(GET("/api/product/maxStockBranchOffice/{franchiseName}"), productHandler::getStockMaxByBranchOffice);
    }
}
