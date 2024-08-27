package co.com.pruebatecnica.dynamodb.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductEntity {

    private String name;
    private Integer stock;
}
