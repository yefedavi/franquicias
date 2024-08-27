package co.com.pruebatecnica.model;

import java.util.List;

public record Franchise(String id,String name, List<BranchOffice> branchOfficeList) {
}
