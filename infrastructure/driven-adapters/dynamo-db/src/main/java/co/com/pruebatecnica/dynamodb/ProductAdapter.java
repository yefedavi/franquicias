package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import co.com.pruebatecnica.model.exception.FranchiseException;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import co.com.pruebatecnica.model.gateway.ProductGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductAdapter implements ProductGateway {

    private final FranchiseGateway franchiseGateway;

    public ProductAdapter(FranchiseGateway franchiseGateway) {
        this.franchiseGateway = franchiseGateway;
    }
    @Override
    public Mono<Boolean> changeName(String franchiseName,String branchOfficeName,String productName,String newName) {
        return franchiseGateway.findByName(franchiseName).flatMap(franchise -> {
            if(franchise.getBranchOfficeList().stream().anyMatch(branchOffice -> branchOffice.getName().equals(branchOfficeName))){
                List<Product> productList = getProductListBranchOffice(branchOfficeName, franchise);
                if(productList.stream().anyMatch(productSaved -> productSaved.getName().equals(productName))){
                    franchise.getBranchOfficeList().stream().filter(branchOffice -> branchOffice.getName().equals(branchOfficeName))
                            .findFirst().get().setProductList(updateNameList(productList,productName,newName));
                    return Mono.just(franchise);
                }else {
                    return Mono.error(new FranchiseException(ValidationErrorMessage.PRODUCT_DOES_NOT_EXISTS));
                }
            }else{
                return Mono.error(new FranchiseException(ValidationErrorMessage.BRANCH_OFFICE_DOES_NOT_EXISTS));
            }
        }).flatMap(franchiseGateway::update).map(Objects::nonNull);
    }

    private static List<Product> getProductListBranchOffice(String branchOfficeName, Franchise modelDb) {
        return modelDb.getBranchOfficeList().stream().filter(branchOffice -> branchOffice.getName().equals(branchOfficeName))
                .findFirst().get().getProductList();
    }

    private List<Product> updateNameList(List<Product> productListOld,String productName,String newName){
        List<Product> list = new ArrayList<>(productListOld);
        list.forEach(product -> {
            if(product.getName().equals(productName)){
                product.setName(newName);
            }
        });
        return list;
    }
}
