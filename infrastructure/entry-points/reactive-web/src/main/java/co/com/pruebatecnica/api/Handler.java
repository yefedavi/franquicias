package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.mapper.FranchiseOperationsMapper;
import co.com.pruebatecnica.api.request.FranchiseJson;
import co.com.pruebatecnica.usecase.branchoffice.BranchOfficeUseCase;
import co.com.pruebatecnica.usecase.franchise.FranchiseUseCase;
import co.com.pruebatecnica.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final FranchiseUseCase franchiseUseCase;
    private  final BranchOfficeUseCase branchOfficeUseCase;
    private  final ProductUseCase productUseCase;

    public Mono<ServerResponse> addFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranchiseJson.class)
                .doOnSuccess(request -> log.info("Franchise Request ".concat(request.toString())))
                .map(FranchiseOperationsMapper.INSTANCE::franchiseJsonToModel)
                .flatMap(franchiseUseCase::add)
                .flatMap(success -> ServerResponse.accepted().build())
                .doOnSuccess(request -> log.info("Franchise response ".concat(request.toString())));
    }
}
