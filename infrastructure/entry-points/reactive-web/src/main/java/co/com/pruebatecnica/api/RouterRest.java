package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.swagger.FranchiseRequestSwg;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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
                .andRoute(POST("/api/branchOffice"), handler::addBranchOffice)
                .andRoute(POST("/api/product"), productHandler::addToBranchOffice)
                .andRoute(DELETE("/api/product"), productHandler::removeBranchOffice)
                .andRoute(PUT("/api/product"), productHandler::changeStock)
                .andRoute(PUT("/api/product/name"), productHandler::changeName)
                .andRoute(GET("/api/product/maxStockBranchOffice/{franchiseName}"), productHandler::getStockMaxByBranchOffice);
    }

}
