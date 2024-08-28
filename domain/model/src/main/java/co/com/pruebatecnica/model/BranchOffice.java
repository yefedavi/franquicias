package co.com.pruebatecnica.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BranchOffice {
    private String name;
    private List<Product> productList;
}
