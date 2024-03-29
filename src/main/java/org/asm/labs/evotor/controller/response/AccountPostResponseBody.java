package org.asm.labs.evotor.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "extras"
})
public class AccountPostResponseBody {
    
    @JsonProperty("result")
    private int result;
    
    @JsonProperty("extras")
    private Map<String, Object> extras;
    
    public AccountPostResponseBody(int result) {
        this.result = result;
    }
    
    public AccountPostResponseBody(int result, Map<String, Object> extras) {
        this.result = result;
        this.extras = extras;
    }
    
    @JsonProperty("result")
    public int getResult() {
        return result;
    }
    
    @JsonProperty("extras")
    public Map<String, Object> getExtras() {
        return extras;
    }
    
}
