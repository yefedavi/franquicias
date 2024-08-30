package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.request.*;
import co.com.pruebatecnica.api.response.FranchiseResponseJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({ @RouterOperation(path = "/api/franchise", beanClass = Handler.class, beanMethod = "addFranchise",produces = "application/json",
    method = RequestMethod.POST, operation = @Operation(operationId = "addFranchise",
            requestBody = @RequestBody(content = {@Content(schema = @Schema(implementation = FranchiseJson.class))}),
            responses = { @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = FranchiseResponseJson.class))),
            @ApiResponse(responseCode = "400", description = "Franchise exists")})),
            @RouterOperation(path = "/api/franchise", beanClass = Handler.class, beanMethod = "changeNameFranchise",produces = "application/json",
                    method = RequestMethod.PUT, operation = @Operation(operationId = "changeNameFranchise",
                    requestBody = @RequestBody(content = {@Content(schema = @Schema(implementation = FranchiseNameJson.class))}),
                    responses = { @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = FranchiseResponseJson.class))),
                            @ApiResponse(responseCode = "400", description = "Franchise does not exists")})),
            @RouterOperation(path = "/api/branchOffice", beanClass = Handler.class, beanMethod = "addBranchOffice",produces = "application/json",
                    method = RequestMethod.POST, operation = @Operation(operationId = "addBranchOffice",
                    requestBody = @RequestBody(content = {@Content(schema = @Schema(implementation = BranchOfficeJson.class))}),
                    responses = { @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = FranchiseResponseJson.class))),
                            @ApiResponse(responseCode = "400", description = "Franchise does not exists,Branch office does not exists")})),
            @RouterOperation(path = "/api/branchOffice", beanClass = Handler.class, beanMethod = "changeNameFranchise",produces = "application/json",
                    method = RequestMethod.PUT, operation = @Operation(operationId = "changeNameFranchise",
                    requestBody = @RequestBody(content = {@Content(schema = @Schema(implementation = BranchNameOfficeJson.class))}),
                    responses = { @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = FranchiseResponseJson.class))),
                            @ApiResponse(responseCode = "400", description = "Franchise does not exists,Branch office does not exists")})),
            @RouterOperation(path = "/api/product", beanClass = ProductHandler.class, beanMethod = "addToBranchOffice",produces = "application/json",
                    method = RequestMethod.POST, operation = @Operation(operationId = "addToBranchOffice",
                    requestBody = @RequestBody(content = {@Content(schema = @Schema(implementation = ProductJson.class))}),
                    responses = { @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = FranchiseResponseJson.class))),
                            @ApiResponse(responseCode = "400", description = "Franchise does not exists,Branch office does not exists")}))
    })
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
