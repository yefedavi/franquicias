package co.com.pruebatecnica.dynamodb.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BranchOfficeEntity {

    private String name;
    private List<ProductEntity> productList;
}
