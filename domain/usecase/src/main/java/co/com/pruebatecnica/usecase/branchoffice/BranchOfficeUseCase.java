package co.com.pruebatecnica.usecase.branchoffice;

import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchOfficeUseCase {
    private final FranchiseGateway franchiseGateway;

    public Mono<Boolean> addToFranchise(String franchise,String branchOfficeName){
        return franchiseGateway.findByName(franchise)
                .map(modelDb -> {
                    BranchOffice branchOffice = new BranchOffice(branchOfficeName,null);
                    modelDb.getBranchOfficeList().add(branchOffice);
                    return modelDb;
                }).flatMap(franchiseGateway::save);
    }
}
