package net.sympower.cityzen.apx;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
class RawQuoteResponse {

  @JsonProperty("quote")
  private List<RawQuote> quotes;

}
