package com.infosys.fs.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User Credit Request Object
 */
@ApiModel(description = "User Credit Request Object")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-02T10:36:32.149+05:30")

public class UserCreditRequest   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  public UserCreditRequest id(String id) {
    this.id = id;
    return this;
  }

  /**
   * User Id
   * @return id
  **/
  @ApiModelProperty(value = "User Id")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserCreditRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * User Name
   * @return name
  **/
  @ApiModelProperty(value = "User Name")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCreditRequest userCreditRequest = (UserCreditRequest) o;
    return Objects.equals(this.id, userCreditRequest.id) &&
        Objects.equals(this.name, userCreditRequest.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCreditRequest {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

