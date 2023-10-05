package com.demo.accounts.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
* ApiErrorResponse
*/
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-09-12T14:12:51.963959300+05:30[Asia/Calcutta]")

public class ApiErrorResponse  implements Serializable {
    private static final long serialVersionUID = 1L;

        @JsonProperty("message")
            private String message;

        @JsonProperty("statusCode")
            private Integer statusCode;

        @JsonProperty("path")
            private String path;

    public ApiErrorResponse message(String message) {
        this.message = message;
    return this;
    }

    /**
        * Get message
    * @return message
    */
   // @ApiModelProperty(value = "")
    

  public String getMessage() {
    return message;
    }

    public void setMessage(String message) {
    this.message = message;
    }

    public ApiErrorResponse statusCode(Integer statusCode) {
        this.statusCode = statusCode;
    return this;
    }

    /**
        * Get statusCode
    * @return statusCode
    */
  //  @ApiModelProperty(value = "")
    

  public Integer getStatusCode() {
    return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
    }

    public ApiErrorResponse path(String path) {
        this.path = path;
    return this;
    }

    /**
        * Get path
    * @return path
    */
   // @ApiModelProperty(value = "")
    

  public String getPath() {
    return path;
    }

    public void setPath(String path) {
    this.path = path;
    }


@Override
public boolean equals(Object o) {
if (this == o) {
return true;
}
if (o == null || getClass() != o.getClass()) {
return false;
}
    ApiErrorResponse apiErrorResponse = (ApiErrorResponse) o;
    return Objects.equals(this.message, apiErrorResponse.message) &&
    Objects.equals(this.statusCode, apiErrorResponse.statusCode) &&
    Objects.equals(this.path, apiErrorResponse.path);
}

@Override
public int hashCode() {
return Objects.hash(message, statusCode, path);
}

@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("class ApiErrorResponse {\n");

sb.append("    message: ").append(toIndentedString(message)).append("\n");
sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
sb.append("    path: ").append(toIndentedString(path)).append("\n");
sb.append("}");
return sb.toString();
}

/**
* Convert the given object to string with each line indented by 4 spaces
* (except the first line).
*/
private String toIndentedString(Object o) {
if (o == null) {
return "null";
}
return o.toString().replace("\n", "\n    ");
}
}
