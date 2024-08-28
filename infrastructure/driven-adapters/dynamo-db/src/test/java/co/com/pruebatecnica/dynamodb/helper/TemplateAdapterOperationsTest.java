package co.com.pruebatecnica.dynamodb.helper;

import co.com.pruebatecnica.dynamodb.DynamoDBTemplateAdapter;
import co.com.pruebatecnica.dynamodb.FranchiseEntity;
import co.com.pruebatecnica.dynamodb.entity.BranchOfficeEntity;
import co.com.pruebatecnica.dynamodb.entity.ProductEntity;
import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.Product;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

        when(dynamoDbEnhancedAsyncClient.table("franchise", TableSchema.fromBean(FranchiseEntity.class)))
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

        assertNotNull(modelEntity.getName());
        assertNotNull(modelEntity.getBranchOfficeList());
    }

    @Test
    void testSave() {
        Product product = new Product("PRODUCTO_1",5);
        BranchOffice branchOffice = new BranchOffice("SUCURSAL_1",Collections.singletonList(product));
        var model = new Franchise("FRANQUICIA_1",Collections.singletonList(branchOffice));

        when(customerTable.putItem(any(FranchiseEntity.class))).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(any(Franchise.class), eq(FranchiseEntity.class))).thenReturn(modelEntity);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.save(model))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "FRANQUICIA_1";
        Product product = new Product("PRODUCTO_1",5);
        BranchOffice branchOffice = new BranchOffice("SUCURSAL_1",Collections.singletonList(product));
        var model = new Franchise("FRANQUICIA_1",Collections.singletonList(branchOffice));

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(modelEntity, Franchise.class)).thenReturn(model);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.getById("FRANQUICIA_1"))
                .consumeNextWith(franchise -> {
                    assertNotNull(franchise);
                    assertNotNull(franchise.name());
                    assertNotNull(franchise.branchOfficeList());
                })
                .verifyComplete();
    }

    @Test
    void testDelete() {
        Product product = new Product("PRODUCTO_1",5);
        BranchOffice branchOffice = new BranchOffice("SUCURSAL_1",Collections.singletonList(product));
        var model = new Franchise("FRANQUICIA_1",Collections.singletonList(branchOffice));

        when(customerTable.deleteItem(modelEntity))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(any(Franchise.class), eq(FranchiseEntity.class))).thenReturn(modelEntity);
        when(mapper.map(any(FranchiseEntity.class), eq(Franchise.class))).thenReturn(model);


        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.delete(model))
                .consumeNextWith(franchise -> {
                    assertNotNull(franchise);
                    assertNotNull(franchise.name());
                    assertNotNull(franchise.branchOfficeList());
                })
                .verifyComplete();
    }
}