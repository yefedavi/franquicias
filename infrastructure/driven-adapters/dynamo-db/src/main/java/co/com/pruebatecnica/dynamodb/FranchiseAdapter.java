package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import co.com.pruebatecnica.model.exception.FranchiseException;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.Objects;

@Service
@Slf4j
public class FranchiseAdapter implements FranchiseGateway {

    private final DynamoDBTemplateAdapter dynamoDBTemplateAdapter;

    public FranchiseAdapter(DynamoDBTemplateAdapter dynamoDBTemplateAdapter) {
        this.dynamoDBTemplateAdapter = dynamoDBTemplateAdapter;
    }

    @Override
    public Mono<Boolean> save(Franchise franchise) {
        return dynamoDBTemplateAdapter.getById(franchise.getName()).switchIfEmpty(Mono.just(new Franchise()))
                .handle(FranchiseAdapter::sinkValidationExists)
                .flatMap(franchise1 -> dynamoDBTemplateAdapter.save(franchise)
                .doOnSubscribe(request -> log.info("request to save dynamodb ".concat(request.toString())))
                .map(Objects::nonNull)
                .doOnSuccess(response -> log.info("save dynamodb success ".concat(franchise.getName())))
                .doOnError(error -> log.info("save dynamodb error ".concat(error.toString())))
                .onErrorResume(throwable -> Mono.error(new FranchiseException(ValidationErrorMessage.DYNAMODB_SAVE_ERROR))));
    }

    private static void sinkValidationExists(Franchise modelDb, SynchronousSink<Franchise> sink) {
        if(modelDb !=null && modelDb.getName() != null){
            sink.error(new FranchiseException(ValidationErrorMessage.FRANCHISE_EXISTS));
        }else {
            sink.next(new Franchise());
        }
    }

    @Override
    public Mono<Boolean> update(Franchise franchise) {
        return  dynamoDBTemplateAdapter.save(franchise)
                .doOnSubscribe(request -> log.info("request to update dynamodb ".concat(request.toString())))
                .map(Objects::nonNull)
                .doOnSuccess(response -> log.info("update dynamodb success ".concat(franchise.getName())))
                .doOnError(error -> log.info("update dynamodb error ".concat(error.toString())))
                .onErrorResume(throwable -> Mono.error(new FranchiseException(ValidationErrorMessage.DYNAMODB_UPDATE_ERROR,throwable.getMessage())));
    }

    @Override
    public Mono<Franchise> findByName(String name) {
        return dynamoDBTemplateAdapter.getById(name)
                .switchIfEmpty(Mono.error(new FranchiseException(ValidationErrorMessage.FRANCHISE_DOES_NOT_EXISTS)));
    }
}
