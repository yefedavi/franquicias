package co.com.pruebatecnica.model.gateway;

import reactor.core.publisher.Mono;

public interface ProductGateway {
    Mono<Boolean> changeName(String franchiseName,String branchOfficeName,String productName,String newName);
}
