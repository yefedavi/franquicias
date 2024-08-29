package co.com.pruebatecnica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchOffice {
    private String franchiseName;
    private String name;
    private List<Product> productList;
}
