package co.com.pruebatecnica.usecase.franchise;

import co.com.pruebatecnica.model.Franchise;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FranchiseUseCaseTest {
    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    @Mock
    private FranchiseGateway franchiseGateway;

    @Test
    public void addSuccess(){
        Mockito.when(franchiseGateway.save(any())).thenReturn(Mono.just(Boolean.TRUE));
        Mono<Boolean> response = franchiseUseCase.add(new Franchise("FRANQUICIA_99",null));
        StepVerifier.create(response)
                .consumeNextWith(Assertions::assertTrue).verifyComplete();
    }
}
