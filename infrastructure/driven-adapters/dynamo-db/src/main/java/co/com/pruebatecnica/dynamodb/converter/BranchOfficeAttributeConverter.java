package co.com.pruebatecnica.dynamodb.converter;

import co.com.pruebatecnica.dynamodb.entity.BranchOfficeEntity;
import co.com.pruebatecnica.dynamodb.entity.ProductEntity;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BranchOfficeAttributeConverter implements AttributeConverter<List<BranchOfficeEntity>> {
    @Override
    public AttributeValue transformFrom(List<BranchOfficeEntity> branchOfficeEntities) {
        List<AttributeValue> branchOfficeList = new ArrayList<>();
        if(branchOfficeEntities!= null && !branchOfficeEntities.isEmpty()){
            branchOfficeEntities.forEach(branchOffice -> {
                List<AttributeValue> productList = new ArrayList<>();
                Map<String, AttributeValue> mapBranchOffice = new LinkedHashMap<>();
                mapBranchOffice.put("name", AttributeValue.fromS(branchOffice.getName()));
                if(branchOffice.getProductList()!= null && !branchOffice.getProductList().isEmpty()){
                    branchOffice.getProductList().forEach(product -> {
                        Map<String, AttributeValue> mapProduct = new LinkedHashMap<>();
                        mapProduct.put("name", AttributeValue.fromS(product.getName()));
                        mapProduct.put("stock", AttributeValue.fromS(product.getStock().toString()));
                        productList.add(AttributeValue.fromM(mapProduct));
                    });
                }
                mapBranchOffice.put("productList", AttributeValue.fromL(productList));
                branchOfficeList.add(AttributeValue.fromM(mapBranchOffice));
            });
        }
        return EnhancedAttributeValue.fromListOfAttributeValues(branchOfficeList).toAttributeValue();
    }

    @Override
    public List<BranchOfficeEntity> transformTo(AttributeValue input) {
        List<AttributeValue> list = input.l();
        List<BranchOfficeEntity> branchOfficeEntityList = new ArrayList<>();
        list.forEach(attributeValue -> {
            List<AttributeValue> productList = attributeValue.m().get("productList").l();
            List<ProductEntity> productEntityList = new ArrayList<>();
            productList.forEach(productValue -> {

                productEntityList.add(new ProductEntity(productValue.m().get("name").s(),Integer.parseInt(productValue.m().get("stock").s())));
            });
            branchOfficeEntityList.add(new BranchOfficeEntity(attributeValue.m().get("name").s(),productEntityList));
        });
        return branchOfficeEntityList;
    }

    @Override
    public EnhancedType<List<BranchOfficeEntity>> type() {
        return EnhancedType.listOf(BranchOfficeEntity.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.M;
    }
}
