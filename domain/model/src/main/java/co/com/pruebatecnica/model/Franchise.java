package co.com.pruebatecnica.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Franchise {
    private String name;
    private List<BranchOffice> branchOfficeList;
}
