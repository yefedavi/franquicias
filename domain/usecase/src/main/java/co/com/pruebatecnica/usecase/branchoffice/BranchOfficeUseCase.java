package co.com.pruebatecnica.usecase.branchoffice;

import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.Product;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BranchOfficeUseCase {
    private final FranchiseGateway franchiseGateway;

    private List<BranchOffice> updateList(List<BranchOffice> branchOfficeListOLd, String branchOfficeName){
        List<BranchOffice> list = new ArrayList<>(branchOfficeListOLd);
        list.add(new BranchOffice(branchOfficeName,null));
        return list;
    }

    public Mono<Boolean> addToFranchise(String franchise,String branchOfficeName){
        return franchiseGateway.findByName(franchise)
                .map(modelDb -> {
                    modelDb.setBranchOfficeList(updateList(modelDb.getBranchOfficeList(),branchOfficeName));
                    return modelDb;
                }).flatMap(franchiseGateway::save);
    }
}
