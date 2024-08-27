package co.com.pruebatecnica.dynamodb;

import co.com.pruebatecnica.dynamodb.converter.BranchOfficeAttributeConverter;
import co.com.pruebatecnica.dynamodb.entity.BranchOfficeEntity;
import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
@Data
public class FranchiseEntity {

    @Getter(onMethod_ = {@DynamoDbPartitionKey,@DynamoDbAttribute("name")})
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute("branchOfficeList"),@DynamoDbConvertedBy(BranchOfficeAttributeConverter.class)}))
    private List<BranchOfficeEntity> branchOfficeList;
}
