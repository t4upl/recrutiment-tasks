package net.sympower.cityzen.apx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
class RawQuoteValue {

  @JsonProperty
  private String tLabel;

  @JsonProperty
  private double value;

}
