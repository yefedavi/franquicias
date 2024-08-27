package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FranchiseAdapter implements FranchiseGateway {

    @Autowired
    private DynamoDBTemplateAdapter dynamoDBTemplateAdapter;
    @Override
    public Mono<Boolean> save(Franchise franchise) {
        return dynamoDBTemplateAdapter.save(franchise).hasElement();
    }
}
