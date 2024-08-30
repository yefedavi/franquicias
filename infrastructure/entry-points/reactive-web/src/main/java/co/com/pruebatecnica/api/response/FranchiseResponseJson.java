package co.com.pruebatecnica.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseResponseJson {
    @Schema(description = "Response code",example = "200")
    @JsonProperty("code")
    String code;
    @Schema(description = "Response message",example = "SUCCESS")
    @JsonProperty("message")
    String message;
}
