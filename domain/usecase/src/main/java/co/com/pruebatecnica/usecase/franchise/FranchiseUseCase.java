package co.com.pruebatecnica.usecase.franchise;

import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseGateway franchiseGateway;

    public Mono<Boolean> add(Franchise franchise) {
        return franchiseGateway.save(franchise);
    }

    public Mono<Boolean>changeName(String franchiseName,String newName){
        return franchiseGateway.changeName(franchiseName,newName);
    }

}
