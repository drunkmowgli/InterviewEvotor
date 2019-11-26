package org.asm.labs.evotor.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "login",
    "password"
})
public class AccountPostRequestBody {
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("login")
    private String login;
    
    @JsonProperty("password")
    private String password;
    
    @JsonProperty("type")
    public String getType() {
        return type;
    }
    
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }
    
    @JsonProperty("login")
    public String getLogin() {
        return login;
    }
    
    @JsonProperty("login")
    public void setLogin(String login) {
        this.login = login;
    }
    
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }
    
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
