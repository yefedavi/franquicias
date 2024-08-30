package co.com.pruebatecnica.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({ @RouterOperation(path = "/franchise", beanClass = Handler.class, beanMethod = "addFranchise") })
    public RouterFunction<ServerResponse> routerFunction(Handler handler,ProductHandler productHandler) {
        return route(POST("/api/franchise"), handler::addFranchise)
                .andRoute(PUT("/api/franchise"), handler::changeNameFranchise)
                .andRoute(POST("/api/branchOffice"), handler::addBranchOffice)
                .andRoute(PUT("/api/branchOffice"), handler::changeNameBranchOffice)
                .andRoute(POST("/api/product"), productHandler::addToBranchOffice)
                .andRoute(DELETE("/api/product"), productHandler::removeBranchOffice)
                .andRoute(PUT("/api/product"), productHandler::changeStock)
                .andRoute(PUT("/api/product/name"), productHandler::changeName)
                .andRoute(GET("/api/product/maxStockBranchOffice/{franchiseName}"), productHandler::getStockMaxByBranchOffice);
    }

}
