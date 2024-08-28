package co.com.pruebatecnica.model;

import lombok.Data;

import java.util.List;

@Data
public class Franchise {
    private String name;
    private List<BranchOffice> branchOfficeList;
}
