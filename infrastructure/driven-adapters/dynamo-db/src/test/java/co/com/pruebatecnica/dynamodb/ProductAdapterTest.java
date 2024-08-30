package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
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

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ContextConfiguration(classes = FranchiseAdapterTest.class)
@ExtendWith(SpringExtension.class)
class ProductAdapterTest {
    private FranchiseGateway franchiseGateway;
    private ProductAdapter productAdapter;

    @BeforeEach
    void setUp() {
        franchiseGateway = Mockito.mock(FranchiseGateway.class);
        productAdapter = new ProductAdapter(franchiseGateway);
    }

    public static Franchise createFranchise() {
        Product product = new Product("PRODUCTO_1",5,"SUCURSAL_1");
        Product product2 = new Product("PRODUCTO_2",7,"SUCURSAL_1");
        BranchOffice branchOffice = new BranchOffice("FRANQUICIA_3","SUCURSAL_1", Arrays.asList(product,product2));
        return new Franchise("FRANQUICIA_3", Collections.singletonList(branchOffice));
    }

    @Test
    public void changeNameSuccess(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = productAdapter.changeName("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_1","PRODUCTO_1.1.1");
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();
    }

    @Test
    public void changeNameErrorProductDoesNotExist(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = productAdapter.changeName("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_3","PRODUCTO_1.1.1");
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.PRODUCT_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }

    @Test
    public void changeNameErrorBranchOfficeDoesNotExists(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = productAdapter.changeName("FRANQUICIA_3","SUCURSAL_4","PRODUCTO_1","PRODUCTO_1.1.1");
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }
}
