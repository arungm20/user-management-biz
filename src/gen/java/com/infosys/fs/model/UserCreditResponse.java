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
 * User Credit Response Object
 */
@ApiModel(description = "User Credit Response Object")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-02T10:36:32.149+05:30")

public class UserCreditResponse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("score")
  private String score = null;

  public UserCreditResponse id(String id) {
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

  public UserCreditResponse name(String name) {
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

  public UserCreditResponse score(String score) {
    this.score = score;
    return this;
  }

  /**
   * Credit Score of the user
   * @return score
  **/
  @ApiModelProperty(value = "Credit Score of the user")


  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCreditResponse userCreditResponse = (UserCreditResponse) o;
    return Objects.equals(this.id, userCreditResponse.id) &&
        Objects.equals(this.name, userCreditResponse.name) &&
        Objects.equals(this.score, userCreditResponse.score);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, score);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCreditResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
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

