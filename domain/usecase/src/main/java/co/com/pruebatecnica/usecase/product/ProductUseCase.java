package co.com.pruebatecnica.usecase.product;

import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    private final FranchiseGateway franchiseGateway;

//    public Mono<Boolean> addToBranchOffice(String franchiseName,String branchOfficeName,String productName,Integer stock){
//        return franchiseGateway.findByName(franchiseName)
//                .map(modelDb -> {
//                    if(modelDb.branchOfficeList().stream().anyMatch(branchOffice -> branchOffice.name().equals(branchOfficeName))){
//                        modelDb.branchOfficeList().stream().filter(branchOffice -> branchOffice.name().equals(branchOfficeName))
//                                .findFirst().get().productList().add(new Product(productName,stock));
//                    }
//                    return modelDb;
//                }).flatMap(franchiseGateway::save);
//    }
//
//    public Mono<Boolean> removeFromBranchOffice(String franchiseName,String branchOfficeName,String productName){
//        return franchiseGateway.findByName(franchiseName)
//                .map(modelDb -> {
//                    if(modelDb.branchOfficeList().stream().anyMatch(branchOffice -> branchOffice.name().equals(branchOfficeName))){
//                        modelDb.branchOfficeList().stream().filter(branchOffice -> branchOffice.name().equals(branchOfficeName))
//                                .findFirst().get().productList().removeIf(product -> product.name().equals(productName));
//                    }
//                    return modelDb;
//                }).flatMap(franchiseGateway::save);
//    }
//
//    public Mono<Boolean> changeStock(String franchiseName,String branchOfficeName,String productName,Integer stock){
//        return franchiseGateway.findByName(franchiseName)
//                .map(modelDb -> {
//                    if(modelDb.branchOfficeList().stream().anyMatch(branchOffice -> branchOffice.name().equals(branchOfficeName))){
//                        modelDb.branchOfficeList().stream().filter(branchOffice -> branchOffice.name().equals(branchOfficeName))
//                                .findFirst().get().productList().removeIf(product -> product.name().equals(productName));
//                        modelDb.branchOfficeList().stream().filter(branchOffice -> branchOffice.name().equals(branchOfficeName))
//                                .findFirst().get().productList().add(new Product(productName,stock));
//                    }
//                    return modelDb;
//                }).flatMap(franchiseGateway::save);
//    }
}
