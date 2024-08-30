package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.request.ProductJson;
import co.com.pruebatecnica.api.request.ProductNameJson;
import co.com.pruebatecnica.api.request.ProductRemoveJson;
import co.com.pruebatecnica.api.response.ProductResponseJson;
import co.com.pruebatecnica.api.response.ProductRootResponseJson;
import co.com.pruebatecnica.api.util.RequestValidator;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static co.com.pruebatecnica.api.util.ResponseJsonUtil.buldResponseJson;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandler {
    private  final ProductUseCase productUseCase;
    private static final String SUCCESS = "SUCCESS";
    private final RequestValidator validator;

    public Mono<ServerResponse> addToBranchOffice(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Product Request ".concat(request.toString())))
                .flatMap(productJson -> productUseCase.addToBranchOffice(productJson.getFranchiseName(),productJson.getBranchOfficeName(),productJson.getName(),productJson.getStock()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Product response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> removeBranchOffice(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductRemoveJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Remove Product Request ".concat(request.toString())))
                .flatMap(productJson -> productUseCase.removeFromBranchOffice(productJson.getFranchiseName(),productJson.getBranchOfficeName(),productJson.getName()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Product response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> changeStock(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Stock Product Request ".concat(request.toString())))
                .flatMap(productJson -> productUseCase.changeStock(productJson.getFranchiseName(),productJson.getBranchOfficeName(),productJson.getName(),productJson.getStock()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Product response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> getStockMaxByBranchOffice(ServerRequest serverRequest) {
        String franchisteName = serverRequest.pathVariable("franchiseName");
        log.info("Max Product Stock By BranchOffice Request ".concat(franchisteName));
        return productUseCase.getProductsStockMax(franchisteName)
                .collectList()
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buildResponseProductList(success)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Product response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> changeName(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductNameJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Change Name Product Request ".concat(request.toString())))
                .flatMap(productJson -> productUseCase.changeName(productJson.getFranchiseName(),productJson.getBranchOfficeName(),productJson.getName(),productJson.getNewName()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Change Name Product response ".concat(response.statusCode().toString())));
    }

    private ProductRootResponseJson buildResponseProductList(List<Product> productList) {
        List<ProductResponseJson> productResponseJsonList = new ArrayList<>();
        productList.forEach(product -> {
            productResponseJsonList.add(ProductResponseJson.builder().branchOffice(product.getBranchOfficeName()).name(product.getName())
                    .stock(product.getStock()).build());
        });

        return  ProductRootResponseJson.builder().productResponseJsonList(productResponseJsonList).build();
    }

}
