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
public class FranchiseNameJson {
    @NotNull
    @NotEmpty
    @JsonProperty("name")
    @Schema(description = "Nombre franquicia",example = "SUBWAY")
    String name;
    @NotNull
    @NotEmpty
    @JsonProperty("newName")
    @Schema(description = "Nombre franquicia nuevo",example = "CUBANO")
    String newName;
}
