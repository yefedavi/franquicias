package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.mapper.FranchiseOperationsMapper;
import co.com.pruebatecnica.api.request.BranchNameOfficeJson;
import co.com.pruebatecnica.api.request.BranchOfficeJson;
import co.com.pruebatecnica.api.request.FranchiseJson;
import co.com.pruebatecnica.api.request.FranchiseNameJson;
import co.com.pruebatecnica.api.util.RequestValidator;
import co.com.pruebatecnica.usecase.branchoffice.BranchOfficeUseCase;
import co.com.pruebatecnica.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.pruebatecnica.api.util.ResponseJsonUtil.buldResponseJson;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final FranchiseUseCase franchiseUseCase;
    private  final BranchOfficeUseCase branchOfficeUseCase;
    private static final String SUCCESS = "SUCCESS";
    private final RequestValidator validator;

    public Mono<ServerResponse> addFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranchiseJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Franchise Request ".concat(request.toString())))
                .map(FranchiseOperationsMapper.INSTANCE::franchiseJsonToModel)
                .flatMap(franchiseUseCase::add)
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Franchise response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> changeNameFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranchiseNameJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Franchise Name Request ".concat(request.toString())))
                .flatMap(requestJson -> franchiseUseCase.changeName(requestJson.getName(),requestJson.getNewName()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Franchise Name response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> addBranchOffice(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchOfficeJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Branch Office Request ".concat(request.toString())))
                .flatMap(requestJson -> branchOfficeUseCase.addToFranchise(requestJson.getFranchiseName(),requestJson.getBranchOfficeName()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Branch Office response ".concat(response.statusCode().toString())));
    }

    public Mono<ServerResponse> changeNameBranchOffice(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchNameOfficeJson.class)
                .flatMap(this.validator::validate)
                .doOnSuccess(request -> log.info("Branch Name Office Request ".concat(request.toString())))
                .flatMap(requestJson -> branchOfficeUseCase.changeName(requestJson.getFranchiseName(),requestJson.getBranchOfficeName(), requestJson.getNewBranchOfficeName()))
                .flatMap(success -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("200", SUCCESS)))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buldResponseJson("400", error.getMessage())))
                .doOnSuccess(response -> log.info("Branch Name response ".concat(response.statusCode().toString())));
    }
}
