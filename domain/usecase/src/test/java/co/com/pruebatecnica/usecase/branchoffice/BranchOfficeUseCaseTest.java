package co.com.pruebatecnica.usecase.branchoffice;

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
class BranchOfficeUseCaseTest {
    @InjectMocks
    private BranchOfficeUseCase branchOfficeUseCase;

    @Mock
    private FranchiseGateway franchiseGateway;

    @Test
    public void addToFranchiseSuccess(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = branchOfficeUseCase.addToFranchise("FRANQUICIA_3","SUCURSAL_55");
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();
    }

    @Test
    public void changeNameSuccess(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mockito.when(franchiseGateway.update(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = branchOfficeUseCase.changeName("FRANQUICIA_3","SUCURSAL_1","SUCURSAL_3");
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();

    }

    @Test
    public void changeNameErrorBranchNotFound(){
        Mockito.when(franchiseGateway.findByName(eq("FRANQUICIA_3"))).thenReturn(Mono.just(createFranchise()));
        Mono<Boolean> response = branchOfficeUseCase.changeName("FRANQUICIA_3","SUCURSAL_2","SUCURSAL_3");
        StepVerifier.create(response)
                .consumeErrorWith(error -> Assertions.assertEquals(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS.getMessage(),error.getMessage()))
                .verify();

    }
}
