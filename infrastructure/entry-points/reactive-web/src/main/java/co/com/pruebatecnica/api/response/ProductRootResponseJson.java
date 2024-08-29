package co.com.pruebatecnica.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder(toBuilder = true)
@Data
public class ProductRootResponseJson {
    @JsonProperty("products")
    List<ProductResponseJson> productResponseJsonList;
}
