package co.com.pruebatecnica.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

//@Value
//@Jacksonized
//@Builder(toBuilder = true)
@Data
public class FranchiseJson {
    @JsonProperty("name")
    private String name;
}
