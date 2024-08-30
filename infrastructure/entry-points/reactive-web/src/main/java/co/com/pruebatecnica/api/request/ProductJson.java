package co.com.pruebatecnica.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
@Data
public class ProductJson {
    @NotNull
    @NotEmpty
    @JsonProperty("franchiseName")
    String franchiseName;
    @NotNull
    @NotEmpty
    @JsonProperty("branchOfficeName")
    String branchOfficeName;
    @NotNull
    @NotEmpty
    @JsonProperty("name")
    String name;
    @NotNull
    @JsonProperty("stock")
    Integer stock;
}
