package co.com.pruebatecnica.usecase.product;

import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static co.com.pruebatecnica.usecase.data.FranchiseMock.createFranchise;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {
    @InjectMocks
    private ProductUseCase productUseCase;

    @Mock
    private FranchiseGateway franchiseGateway;

    @Test
    public void addToBranchOfficeSuccess(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = productUseCase.addToBranchOffice("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_66",20);
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();
    }

    @Test
    public void addToBranchOfficeDoesNotExist(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mono<Boolean> response = productUseCase.addToBranchOffice("FRANQUICIA_3","SUCURSAL_3","PRODUCTO_66",20);
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }

    @Test
    public void removeFromBranchOfficeSuccess(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = productUseCase.removeFromBranchOffice("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_1");
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();
    }

    @Test
    public void removeFromBranchOfficeErrorBranchOfficeDoesNotExist(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mono<Boolean> response = productUseCase.removeFromBranchOffice("FRANQUICIA_3","SUCURSAL_3","PRODUCTO_1");
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }


    @Test
    public void removeFromBranchOfficeErrorProductDoesNotExist(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mono<Boolean> response = productUseCase.removeFromBranchOffice("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_333");
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.PRODUCT_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }

    @Test
    public void changeStockSuccess(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = productUseCase.changeStock("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_1",50);
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();
    }

    @Test
    public void changeStockErrorBranchDoesNotExists(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mono<Boolean> response = productUseCase.changeStock("FRANQUICIA_3","SUCURSAL_44","PRODUCTO_1",50);
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }

    @Test
    public void changeStockErrorProductDoesNotExists(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mono<Boolean> response = productUseCase.changeStock("FRANQUICIA_3","SUCURSAL_1","PRODUCTO_77",45);
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.PRODUCT_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();
    }
}
