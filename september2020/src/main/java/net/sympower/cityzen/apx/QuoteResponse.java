package net.sympower.cityzen.apx;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.Getter;

@Getter
class QuoteResponse {

  private final List<Quote> quotes;

  private static final String NET_VOLUME = "Net Volume";
  private static final String PRICE = "Price";
  private static final String HOUR = "Hour";

  QuoteResponse(RawQuoteResponse rawQuoteResponse) {
    this.quotes = initQuotes(rawQuoteResponse);
  }

  private List<Quote> initQuotes(RawQuoteResponse rawQuoteResponse) {
    List<RawQuote> rawQuotes = rawQuoteResponse.getQuotes();
    return rawQuotes.stream() //
      .map(this::toQuote) //
      .collect(toList());
  }

  private Quote toQuote(RawQuote rawQuote) {
    return Quote.builder() //
      .date(rawQuote.getDateApplied()) //
      .hour(getHour(rawQuote)) //
      .netVolume(getNetVolume(rawQuote)) //
      .price(getPrice(rawQuote)) //
      .build();
  }

  private RawQuoteValue getRawQuoteValue(RawQuote rawQuote, String tLabel) {
    return rawQuote.getRawQuoteValues().stream() //
      .filter(rawQuoteValueElem -> rawQuoteValueElem.getTLabel().equals(tLabel)) //
      .findAny() //
      .orElseThrow(() -> new RuntimeException(format("Conversion from RawQuote to Quote failed. " //
          + "RawQuote %s does not contain fied: %s", rawQuote, tLabel)));
  }

  private int getHour(RawQuote rawQuote) {
    return (int)getRawQuoteValue(rawQuote, HOUR).getValue();
  }

  private double getNetVolume(RawQuote rawQuote) {
    return getRawQuoteValue(rawQuote, NET_VOLUME).getValue();
  }

  private double getPrice(RawQuote rawQuote) {
    return getRawQuoteValue(rawQuote, PRICE).getValue();
  }


}
