package co.com.pruebatecnica.api.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseRequestSwg {
    @Schema(description = "Franchise name",example = "FRANCHISE_ONE")
    String name;
}
