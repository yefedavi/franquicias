package co.com.pruebatecnica.dynamodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    private String name;
    private Integer stock;
}
