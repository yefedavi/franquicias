package co.com.pruebatecnica.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
@Data
public class FranchiseResponseJson {
    @Schema(description = "Response code",example = "200")
    @JsonProperty("code")
    String code;
    @Schema(description = "Response message",example = "SUCCESS")
    @JsonProperty("message")
    String message;
}
