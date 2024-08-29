package co.com.pruebatecnica.usecase.branchoffice;

import co.com.pruebatecnica.model.BranchOffice;
import co.com.pruebatecnica.model.Franchise;
import co.com.pruebatecnica.model.enums.ValidationErrorMessage;
import co.com.pruebatecnica.model.exception.FranchiseException;
import co.com.pruebatecnica.model.gateway.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class BranchOfficeUseCase {
    private final FranchiseGateway franchiseGateway;

    private List<BranchOffice> updateList(List<BranchOffice> branchOfficeListOLd, String branchOfficeName){
        List<BranchOffice> list = null;
        if(branchOfficeListOLd != null && !branchOfficeListOLd.isEmpty()){
            list = new ArrayList<>(branchOfficeListOLd);
        }else{
            list = new ArrayList<>();
        }
        list.add(new BranchOffice(null,branchOfficeName,null));
        return list;
    }

    public Mono<Boolean> addToFranchise(String franchise,String branchOfficeName){
        return franchiseGateway.findByName(franchise)
                .handle((Franchise franchiseDb,SynchronousSink<Franchise> sink) -> sinkValidationExists(franchiseDb, sink,branchOfficeName))
                .map(modelDb -> {
                    modelDb.setBranchOfficeList(updateList(modelDb.getBranchOfficeList(),branchOfficeName));
                    return modelDb;
                }).flatMap(franchiseGateway::update)
                .onErrorResume(Predicate.not(FranchiseException.class::isInstance), error -> Mono.error(new FranchiseException(ValidationErrorMessage.DYNAMODB_SAVE_ERROR)));
    }

    private static void sinkValidationExists(Franchise modelDb, SynchronousSink<Franchise> sink,String branchOfficeName) {
        if(modelDb.getBranchOfficeList() != null && modelDb.getBranchOfficeList().stream().anyMatch(branchOffice -> branchOffice.getName().equals(branchOfficeName))){
            sink.error(new FranchiseException(ValidationErrorMessage.BRANCH_OFFICE_EXISTS));
        }else {
            sink.next(modelDb);
        }
    }
}
