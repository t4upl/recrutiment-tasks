package net.sympower.cityzen.apx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class RawQuote {

  private static final String ZONE_ID = "Europe/Brussels";

  private LocalDate dateApplied;

  @JsonProperty("values")
  private List<RawQuoteValue> rawQuoteValues;

  @JsonSetter("date_applied")
  public void setDateApplied(long dateApplied) {
    this.dateApplied = Instant.ofEpochMilli(dateApplied).atZone(ZoneId.of(ZONE_ID))
      .toLocalDate();
  }

}
