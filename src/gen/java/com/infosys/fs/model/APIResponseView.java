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
 * APIResponseView
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-02T10:36:32.149+05:30")

public class APIResponseView   {
  @JsonProperty("statusCode")
  private String statusCode = null;

  @JsonProperty("statusDescription")
  private String statusDescription = null;

  @JsonProperty("severity")
  private String severity = null;

  public APIResponseView statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Indicates the status of the API response. Value of the status code indicates if it was a success or failure example: '2000000'
   * @return statusCode
  **/
  @ApiModelProperty(value = "Indicates the status of the API response. Value of the status code indicates if it was a success or failure example: '2000000'")


  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public APIResponseView statusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
    return this;
  }

  /**
   * Defines the status of the API response. Status Description provides more information on the status
   * @return statusDescription
  **/
  @ApiModelProperty(example = "SUCCESS", value = "Defines the status of the API response. Status Description provides more information on the status")


  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  public APIResponseView severity(String severity) {
    this.severity = severity;
    return this;
  }

  /**
   * Defines the severity of the API response in case of error response.  Possible values can be INFORMATION, WARNING, ERROR, CRITICAL
   * @return severity
  **/
  @ApiModelProperty(example = "INFORMATION", value = "Defines the severity of the API response in case of error response.  Possible values can be INFORMATION, WARNING, ERROR, CRITICAL")


  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    APIResponseView apIResponseView = (APIResponseView) o;
    return Objects.equals(this.statusCode, apIResponseView.statusCode) &&
        Objects.equals(this.statusDescription, apIResponseView.statusDescription) &&
        Objects.equals(this.severity, apIResponseView.severity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusDescription, severity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class APIResponseView {\n");
    
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusDescription: ").append(toIndentedString(statusDescription)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
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

