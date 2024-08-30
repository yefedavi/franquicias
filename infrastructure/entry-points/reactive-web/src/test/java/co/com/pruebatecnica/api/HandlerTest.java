package co.com.pruebatecnica.api;

import co.com.pruebatecnica.api.request.BranchOfficeJson;
import co.com.pruebatecnica.api.request.FranchiseJson;
import co.com.pruebatecnica.api.request.ProductJson;
import co.com.pruebatecnica.api.request.ProductNameJson;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.usecase.branchoffice.BranchOfficeUseCase;
import co.com.pruebatecnica.usecase.franchise.FranchiseUseCase;
import co.com.pruebatecnica.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

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

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build();
    }

    private FranchiseJson buildRequestAddFranchise(){
        return FranchiseJson.builder().name("SUBWAY").build();
    }

    private BranchOfficeJson buildRequestAddBranchOffice(){
        return BranchOfficeJson.builder().franchiseName("SUBWAY").branchOfficeName("SUCURAL_1").build();
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
        Mockito.when(franchiseUseCase.add(any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        webTestClient.post()
                .uri("/api/franchise")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddFranchise()), FranchiseJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testAddBranchOfficeSuccess() {
        Mockito.when(branchOfficeUseCase.addToFranchise(any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        webTestClient.post()
                .uri("/api/branchOffice")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddBranchOffice()), BranchOfficeJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testAddToBranchOfficeSuccess() {
        Mockito.when(productUseCase.addToBranchOffice(any(),any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        webTestClient.post()
                .uri("/api/product")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddProduct()), ProductJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testRemoveToBranchOfficeSuccess() {
        Mockito.when(productUseCase.removeFromBranchOffice(any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        webTestClient.method(HttpMethod.DELETE)
                .uri("/api/product")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestDeleteProduct()), ProductJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testChangeStockSuccess() {
        Mockito.when(productUseCase.changeStock(any(),any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        webTestClient.method(HttpMethod.PUT)
                .uri("/api/product")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestAddProduct2()), ProductJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testGetStockMaxByBranchOfficeStockSuccess() {
        Mockito.when(productUseCase.getProductsStockMax(any()))
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
    void testChangeNameSuccess() {
        Mockito.when(productUseCase.changeName(any(),any(),any(),any()))
                .thenReturn(Mono.just(Boolean.TRUE));
        webTestClient.method(HttpMethod.PUT)
                .uri("/api/product/name")
                .header("Content-Type", "application/json")
                .body(Mono.just(buildRequestChangeProductName()), ProductNameJson.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
