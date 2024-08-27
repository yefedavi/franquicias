package co.com.pruebatecnica.dynamodb.helper;

import co.com.pruebatecnica.dynamodb.DynamoDBTemplateAdapter;
import co.com.pruebatecnica.dynamodb.FranchiseEntity;
import co.com.pruebatecnica.dynamodb.entity.BranchOfficeEntity;
import co.com.pruebatecnica.dynamodb.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<FranchiseEntity> customerTable;

    private FranchiseEntity modelEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(FranchiseEntity.class)))
                .thenReturn(customerTable);

        modelEntity = new FranchiseEntity();
        modelEntity.setName("FRANQUICIA_1");
        BranchOfficeEntity branchOfficeEntity = BranchOfficeEntity.builder().name("SUCURSAL_1")
                .productList(Arrays.asList(ProductEntity.builder().stock(5).name("PRODUCTO_1").build(),
                        ProductEntity.builder().stock(4).name("PRODUCTO_2").build())).build();
        modelEntity.setBranchOfficeList(Collections.singletonList(branchOfficeEntity));
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        FranchiseEntity modelEntityUnderTest = new FranchiseEntity("id", "atr1");

        assertNotNull(modelEntityUnderTest.getId());
        assertNotNull(modelEntityUnderTest.getAtr1());
    }

    @Test
    void testSave() {
        when(customerTable.putItem(modelEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(modelEntity, FranchiseEntity.class)).thenReturn(modelEntity);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.save(modelEntity))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(modelEntity, Object.class)).thenReturn("value");

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.getById("id"))
                .expectNext("value")
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(mapper.map(modelEntity, FranchiseEntity.class)).thenReturn(modelEntity);
        when(mapper.map(modelEntity, Object.class)).thenReturn("value");

        when(customerTable.deleteItem(modelEntity))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.delete(modelEntity))
                .expectNext("value")
                .verifyComplete();
    }
}