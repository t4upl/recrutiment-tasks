package net.sympower.cityzen.apx;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
class Quote {

  private final LocalDate date;
  private final int hour;
  private final double netVolume;
  private final double price;

}
