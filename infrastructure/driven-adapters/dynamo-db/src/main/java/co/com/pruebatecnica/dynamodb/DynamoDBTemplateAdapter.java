package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.dynamodb.helper.TemplateAdapterOperations;
import co.com.pruebatecnica.model.Franchise;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Franchise, String, FranchiseEntity> {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Franchise.class), "franchise");
    }
}
