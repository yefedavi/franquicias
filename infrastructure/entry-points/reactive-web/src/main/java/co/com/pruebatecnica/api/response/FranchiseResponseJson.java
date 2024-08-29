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
public class FranchiseResponseJson {
    @JsonProperty("code")
    String code;
    @JsonProperty("message")
    String message;
}
