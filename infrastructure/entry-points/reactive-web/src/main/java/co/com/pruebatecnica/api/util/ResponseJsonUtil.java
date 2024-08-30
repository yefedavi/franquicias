package co.com.pruebatecnica.api.util;

import co.com.pruebatecnica.api.response.FranchiseResponseJson;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseJsonUtil {
    public static FranchiseResponseJson buldResponseJson(String code, String message){
//        return FranchiseResponseJson.builder().code(code).message(message).build();
        return new FranchiseResponseJson(code, message);
    }
}
