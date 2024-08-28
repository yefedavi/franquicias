package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class FranchiseAdapter implements FranchiseGateway {

    @Autowired
    private DynamoDBTemplateAdapter dynamoDBTemplateAdapter;

    @Override
    public Mono<Boolean> save(Franchise franchise) {
        System.out.println("llego al adapter "+franchise);
        return dynamoDBTemplateAdapter.save(franchise)
                .map(Objects::nonNull)
                .doOnSubscribe(request -> log.info("request to save dynamodb ".concat(request.toString())))
                .doOnSuccess(response -> log.info("save dynamodb success ".concat(franchise.getName())))
                .doOnError(error -> log.info("save dynamodb error ".concat(error.toString())))
                .onErrorResume(throwable -> Mono.just(false));
    }

    @Override
    public Mono<Franchise> findByName(String name) {
        return dynamoDBTemplateAdapter.getById(name);
    }
}
