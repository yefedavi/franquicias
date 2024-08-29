package co.com.pruebatecnica.dynamodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeEntity {

    private String name;
    private List<ProductEntity> productList;
}
