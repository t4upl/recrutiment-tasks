package net.sympower.cityzen.apx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import org.junit.Test;

public class ApxDataLoaderTest {

  private static final String CORRECT_JSON = "apx-data.json";
  private static final String FIELD_MISSING_JSON = "apx-data-field-missing.json";

  //1573599600000 miliseconds offset as Date
  private static final LocalDate DATE_APPLIED = LocalDate.of(2019, 11, 13);
  private static final Quote QUOTE = getQuote();

  @Test
  public void load_givenJsonFile_shouldCreateQuoteResponse() throws Exception {
    //given
    ApxDataLoader apxDataLoader = new ApxDataLoader();
    apxDataLoader.setUrl(getClass().getResource(CORRECT_JSON));

    //when
    QuoteResponse response = apxDataLoader.load();

    //then
    assertNotNull(response);
    assertNotNull(response.getQuotes());
    assertEquals(3, response.getQuotes().size());
    Quote quote = response.getQuotes().get(0);
    assertEquals(QUOTE, quote);
  }

  @Test(expected = RuntimeException.class)
  public void load_givenJsonFileWithFieldMissing_shouldThrow() throws Exception {
    //given
    ApxDataLoader apxDataLoader = new ApxDataLoader();
    apxDataLoader.setUrl(getClass().getResource(FIELD_MISSING_JSON));

    //when
    apxDataLoader.load();
  }

  private static Quote getQuote() {
    return Quote.builder() //
      .date(DATE_APPLIED) //
      .hour(1) //
      .netVolume(4514.20) //
      .price(34) //
      .build();
  }

}