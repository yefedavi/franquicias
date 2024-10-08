package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(classes = FranchiseAdapterTest.class)
@ExtendWith(SpringExtension.class)
class FranchiseAdapterTest {

    private DynamoDBTemplateAdapter dynamoDBTemplateAdapter;

    private FranchiseAdapter franchiseAdapter;

    @BeforeEach
    void setUp() {
        dynamoDBTemplateAdapter = Mockito.mock(DynamoDBTemplateAdapter.class);
        franchiseAdapter = new FranchiseAdapter(dynamoDBTemplateAdapter);
    }

    private Franchise buildFranchise(){
        Product product = new Product("PRODUCTO_2",5,"SUCURSAL_1");
        BranchOffice branchOffice = new BranchOffice("FRANQUICIA_1","SUCURSAL_1", Collections.singletonList(product));
        var model = new Franchise("FRANQUICIA_1",Collections.singletonList(branchOffice));
        return model;
    }

    @Test
    public void saveSuccess(){
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.empty());
        Mockito.when(dynamoDBTemplateAdapter.save(any())).thenReturn(Mono.just(buildFranchise()));
        StepVerifier.create(franchiseAdapter.save(buildFranchise())).consumeNextWith(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    public void saveError(){
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.empty());
        Mockito.when(dynamoDBTemplateAdapter.save(any())).thenReturn(Mono.error(new RuntimeException("dynamodb error")));
        StepVerifier.create(franchiseAdapter.save(buildFranchise())).consumeErrorWith(error ->
                        Assertions.assertEquals(error.getMessage(), ValidationErrorMessage.DYNAMODB_SAVE_ERROR.getMessage()))
                .verify();
    }

    @Test
    public void saveErrorFranchiseExists(){
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.just(buildFranchise()));
        Mockito.when(dynamoDBTemplateAdapter.save(any())).thenReturn(Mono.just(buildFranchise()));
        StepVerifier.create(franchiseAdapter.save(buildFranchise())).consumeErrorWith(error ->
                        Assertions.assertEquals(error.getMessage(), ValidationErrorMessage.FRANCHISE_EXISTS.getMessage()))
                .verify();
    }

    @Test
    public void updateSuccess(){
        Mockito.when(dynamoDBTemplateAdapter.save(any())).thenReturn(Mono.just(buildFranchise()));
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.just(buildFranchise()));
        StepVerifier.create(franchiseAdapter.update(buildFranchise())).consumeNextWith(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    public void updateError(){
        Mockito.when(dynamoDBTemplateAdapter.save(any())).thenReturn(Mono.error(new RuntimeException("dynamodb error")));
        StepVerifier.create(franchiseAdapter.update(buildFranchise())).consumeErrorWith(error ->
                        Assertions.assertEquals(error.getMessage(), "dynamodb error"))
                .verify();
    }

    @Test
    public void findByNameSuccess(){
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.just(buildFranchise()));
        StepVerifier.create(franchiseAdapter.findByName("FRANQUICIA_1")).consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    public void findByNameErrorFranchiseDoesNotExists(){
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.empty());
        StepVerifier.create(franchiseAdapter.findByName("FRANQUICIA_1")).consumeErrorWith(error ->
                        Assertions.assertEquals(error.getMessage(), ValidationErrorMessage.FRANCHISE_DOES_NOT_EXISTS.getMessage()))
                .verify();
    }

    @Test
    public void changeNameSuccess(){
        Mockito.when(dynamoDBTemplateAdapter.getById(any())).thenReturn(Mono.just(buildFranchise()));
        Mockito.when(dynamoDBTemplateAdapter.delete(any())).thenReturn(Mono.just(buildFranchise()));
        Mockito.when(dynamoDBTemplateAdapter.save(any())).thenReturn(Mono.just(buildFranchise()));
        StepVerifier.create(franchiseAdapter.changeName("FRANQUICIA_1","FRANQUICIA_11")).consumeNextWith(Assertions::assertTrue)
                .verifyComplete();
    }
}
