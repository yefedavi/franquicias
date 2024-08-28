package co.com.pruebatecnica.usecase.product;

import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import co.com.pruebatecnica.model.exception.FranchiseException;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductUseCase {
    private final FranchiseGateway franchiseGateway;


    private List<Product> updateList(List<Product> productListOld,String productName,Integer stock){
        List<Product> list = new ArrayList<>(productListOld);
        list.add(new Product(productName, stock));
        return list;
    }

    private List<Product> removeFromList(List<Product> productListOld,String productName){
        List<Product> list = new ArrayList<>(productListOld);
        list.removeIf(product -> product.getName().equals(productName));
        return list;
    }

    private List<Product> changeStockList(List<Product> productListOld,String productName,Integer newStock){
        List<Product> list = new ArrayList<>(productListOld);
        list.forEach(product -> {
            if(product.getName().equals(productName)){
                product.setStock(newStock);
            }
        });
        return list;
    }


    public Mono<Boolean> addToBranchOffice(String franchiseName,String branchOfficeName,String productName,Integer stock){
        return franchiseGateway.findByName(franchiseName).<Franchise>handle((modelDb, sink) -> {
                    if(modelDb.getBranchOfficeList().stream().anyMatch(branchOffice -> branchOffice.getName().equals(branchOfficeName))){
                        List<Product> productList = getProductListBranchOffice(branchOfficeName, modelDb);
                        modelDb.getBranchOfficeList().stream().filter(branchOffice -> branchOffice.getName().equals(branchOfficeName))
                                .findFirst().get().setProductList(updateList(productList,productName,stock));
                        sink.next(modelDb);
                        return;
                    }
                    errorBranchOfficeDoesNotExists(sink);
        }).flatMap(franchiseGateway::save);
    }

    public Mono<Boolean> removeFromBranchOffice(String franchiseName,String branchOfficeName,String productName){
        return franchiseGateway.findByName(franchiseName).<Franchise>handle((modelDb, sink) -> {
                    if(modelDb.getBranchOfficeList().stream().anyMatch(branchOffice -> branchOffice.getName().equals(branchOfficeName))){
                        List<Product> productList = getProductListBranchOffice(branchOfficeName, modelDb);
                        if(productList.stream().noneMatch(product -> product.getName().equals(productName))){
                            sink.error(new FranchiseException(ValidationErrorMessage.PRODUCT_DOES_NOT_EXISTS));
                            return;
                        }
                        modelDb.getBranchOfficeList().stream().filter(branchOffice -> branchOffice.getName().equals(branchOfficeName))
                                .findFirst().get().setProductList(removeFromList(productList,productName));
                        sink.next(modelDb);
                        return;
                    }
            errorBranchOfficeDoesNotExists(sink);
        }).flatMap(franchiseGateway::save);
    }

    private static void errorBranchOfficeDoesNotExists(SynchronousSink<Franchise> sink) {
        sink.error(new FranchiseException(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS));
    }

    public Mono<Boolean> changeStock(String franchiseName,String branchOfficeName,String productName,Integer stock){
        return franchiseGateway.findByName(franchiseName).<Franchise>handle((modelDb, sink) -> {
            if(modelDb.getBranchOfficeList().stream().anyMatch(branchOffice -> branchOffice.getName().equals(branchOfficeName))){
                List<Product> productList = getProductListBranchOffice(branchOfficeName, modelDb);
                if(productList.stream().noneMatch(product -> product.getName().equals(productName))){
                    sink.error(new FranchiseException(ValidationErrorMessage.PRODUCT_DOES_NOT_EXISTS));
                    return;
                }
                modelDb.getBranchOfficeList().stream().filter(branchOffice -> branchOffice.getName().equals(branchOfficeName))
                        .findFirst().get().setProductList(changeStockList(productList,productName,stock));
                sink.next(modelDb);
                return;
            }
            errorBranchOfficeDoesNotExists(sink);
        }).flatMap(franchiseGateway::save);
    }

    private static List<Product> getProductListBranchOffice(String branchOfficeName, Franchise modelDb) {
        return modelDb.getBranchOfficeList().stream().filter(branchOffice -> branchOffice.getName().equals(branchOfficeName))
                .findFirst().get().getProductList();
    }
}
