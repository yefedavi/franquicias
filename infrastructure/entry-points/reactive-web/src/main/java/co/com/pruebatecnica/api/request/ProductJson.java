package co.com.pruebatecnica.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Nombre franquicia",example = "SUBWAY")
    String franchiseName;
    @NotNull
    @NotEmpty
    @JsonProperty("branchOfficeName")
    @Schema(description = "Nombre sucursal",example = "SUBWAY_CENTRO")
    String branchOfficeName;
    @NotNull
    @NotEmpty
    @JsonProperty("name")
    @Schema(description = "Nombre producto",example = "SUB_NAPILITANO")
    String name;
    @NotNull
    @JsonProperty("stock")
    @Schema(description = "Cantidad en inventario",example = "100")
    Integer stock;
}
