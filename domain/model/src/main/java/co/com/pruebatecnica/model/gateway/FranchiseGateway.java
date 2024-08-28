package co.com.pruebatecnica.model.gateway;

import co.com.pruebatecnica.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseGateway {
    Mono<Boolean> save(Franchise franchise);
    Mono<Franchise> findByName(String name);
}
