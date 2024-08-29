package co.com.pruebatecnica.dynamodb.converter;

import co.com.pruebatecnica.dynamodb.entity.BranchOfficeEntity;
import co.com.pruebatecnica.dynamodb.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BranchOfficeAttributeConverterTest {

    BranchOfficeAttributeConverter branchOfficeAttributeConverter = new BranchOfficeAttributeConverter();

    private List<BranchOfficeEntity> buildBranchOfficeEntityList() {
        return List.of(new BranchOfficeEntity("SUCURSAL_1", Arrays.asList(new ProductEntity("PRODUCT_1", 4), new ProductEntity("PRODUCT_2", 5))));
    }

    @Test
    void transformFromTest(){
        List<BranchOfficeEntity> branchOfficeEntityList = buildBranchOfficeEntityList();
        var response = branchOfficeAttributeConverter.transformFrom(branchOfficeEntityList);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCURSAL_1",response.l().get(0).m().get("name").s());
    }

    @Test
    void transformToTest(){
        List<BranchOfficeEntity> branchOfficeEntityList = buildBranchOfficeEntityList();
        var attributeValues = branchOfficeAttributeConverter.transformFrom(branchOfficeEntityList);
        var response = branchOfficeAttributeConverter.transformTo(attributeValues);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("SUCURSAL_1",response.get(0).getName());
    }

    @Test
    void typeTest(){
        Assertions.assertNotNull(branchOfficeAttributeConverter.type());
    }

    @Test
    void attributeValueTypeTest(){
        Assertions.assertNotNull(branchOfficeAttributeConverter.attributeValueType());
    }
}
