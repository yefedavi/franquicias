package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.request.*;
import co.com.pruebatecnica.api.util.RequestValidator;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.exception.FieldValidationException;
import co.com.pruebatecnica.usecase.branchoffice.BranchOfficeUseCase;
import co.com.pruebatecnica.usecase.franchise.FranchiseUseCase;
import co.com.pruebatecnica.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Validator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RouterRest.class, ProductHandler.class,Handler.class})
@WebFluxTest
class HandlerTest {

    @InjectMocks
    private ProductHandler productHandler;

    @Autowired
    private ApplicationContext context;

    @MockBean
    private ProductUseCase productUseCase;

    @MockBean
    private FranchiseUseCase franchiseUseCase;

    @MockBean
    private BranchOfficeUseCase branchOfficeUseCase;

    @MockBean
    private RequestValidator requestValidator;

    @MockBean
    private Validator validator;

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private Handler handler;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build();
    }

    private FranchiseJson buildRequestAddFranchise(){
        return FranchiseJson.builder().name("SUBWAY").build();
    }

    private FranchiseJson buildRequestAddFranchiseFieldEmpty(){
        return FranchiseJson.builder().name("SUBWAY").build();
    }

    private FranchiseNameJson buildRequestChangeNameFranchise(){
        return FranchiseNameJson.builder().name("SUBWAY").newName("SUBWAY_SA").build();
    }

    private BranchOfficeJson buildRequestAddBranchOffice(){
        return BranchOfficeJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").build();
    }

    private BranchNameOfficeJson buildRequestNameBranchOffice(){
        return BranchNameOfficeJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURSAL_1").newBranchOfficeName("SUCURSAL_1.1.1").build();
    }

    private ProductJson buildRequestAddProduct(){
        return ProductJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").name("SUBWAY_HAWAIANO").stock(6).build();
    }

    private ProductJson buildRequestAddProduct2(){
        return ProductJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_2").name("SUBWAY_italiano").stock(21).build();
    }

    private ProductNameJson buildRequestChangeProductName(){
        return ProductNameJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_2").name("SUBWAY_italiano").newName("SUBWAY_ITALIANISIMO").build();
    }

    private ProductJson buildRequestDeleteProduct(){
        return ProductJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").name("SUBWAY_HAWAIANO").build();
    }


    @Test
    void testAddFranchiseSuccess() {
        when(franchiseUseCase.add(any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(FranchiseJson.builder().name("SUBWAY").build()));
        webTestClient.post()
                .uri("/api/franchise")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddFranchise()), FranchiseJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testAddFranchiseErrorFieldValidation() {
        when(requestValidator.validate(any()))
                .thenReturn(Mono.error(new FieldValidationException("name no puede estar vacio")));
        webTestClient.post()
                .uri("/api/franchise")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddFranchiseFieldEmpty()), FranchiseJson.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void testChangeNameFranchiseSuccess() {
        when(franchiseUseCase.changeName(any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(FranchiseNameJson.builder().name("SUBWAY").newName("SUBWAY_SA").build()));
        webTestClient.method(HttpMethod.PUT)
                .uri("/api/franchise")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestChangeNameFranchise()), FranchiseNameJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testAddBranchOfficeSuccess() {
        when(branchOfficeUseCase.addToFranchise(any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(BranchOfficeJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").build()));
        webTestClient.post()
                .uri("/api/branchOffice")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddBranchOffice()), BranchOfficeJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testChangeNameBranchOfficeSuccess() {
        when(branchOfficeUseCase.changeName(any(), any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(BranchNameOfficeJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").newBranchOfficeName("SUCURSAL_1").build()));
        webTestClient.method(HttpMethod.PUT)
                .uri("/api/branchOffice")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestNameBranchOffice()), BranchNameOfficeJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testProductAddToBranchOfficeSuccess() {
        when(productUseCase.addToBranchOffice(any(),any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(ProductJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").name("SUBWAY_HAWAIANO").stock(6).build()));
        webTestClient.post()
                .uri("/api/product")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddProduct()), ProductJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testProductRemoveToBranchOfficeSuccess() {
        when(productUseCase.removeFromBranchOffice(any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(ProductJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").name("SUBWAY_HAWAIANO").build()));
        webTestClient.method(HttpMethod.DELETE)
                .uri("/api/product")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestDeleteProduct()), ProductJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testProductChangeStockSuccess() {
        when(productUseCase.changeStock(any(),any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(ProductJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").name("SUBWAY_HAWAIANO").stock(17).build()));
        webTestClient.method(HttpMethod.PUT)
                .uri("/api/product")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddProduct2()), ProductJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testProductGetStockMaxByBranchOfficeStockSuccess() {
        when(productUseCase.getProductsStockMax(any()))
                .thenReturn(Flux.just(new Product("SUBWAY_VEGETARIANO",50,"SUCURAL_2"),new Product("SUBWAY_HAWAIANO",67,"SUCURAL_1")));
        webTestClient.method(HttpMethod.GET)
                .uri("/api/product/maxStockBranchOffice/SUBWAY")
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"products\":[{\"branchOffice\":\"SUCURAL_2\",\"name\":\"SUBWAY_VEGETARIANO\",\"stock\":50},{\"branchOffice\":\"SUCURAL_1\",\"name\":\"SUBWAY_HAWAIANO\",\"stock\":67}]}");
    }

    @Test
    void testProductChangeNameSuccess() {
        when(productUseCase.changeName(any(),any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        when(requestValidator.validate(any()))
                .thenReturn(Mono.just(ProductNameJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURSAL_1").name("SUBWAY_italiano").newName("SUBWAY_ITALIANISIMO").build()));
        webTestClient.method(HttpMethod.PUT)
                .uri("/api/product/name")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestChangeProductName()), ProductNameJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
