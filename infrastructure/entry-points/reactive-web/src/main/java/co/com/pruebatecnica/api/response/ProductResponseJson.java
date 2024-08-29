package co.com.pruebatecnica.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
@Data
public class ProductResponseJson {
    @JsonProperty("branchOffice")
    String branchOffice;
    @JsonProperty("name")
    String name;
    @JsonProperty("stock")
    Integer stock;
}
