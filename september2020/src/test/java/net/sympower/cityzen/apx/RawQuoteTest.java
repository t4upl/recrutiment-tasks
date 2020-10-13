package net.sympower.cityzen.apx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.junit.Test;

public class RawQuoteTest {

  private static final String TEST_FILE_PATH = "src/test/resources/net/sympower/cityzen/apx/quote.json";

  @Test
  public void readValue_givenTestFile_shouldCreateRawQuote() throws IOException {
    //given
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    //when
    RawQuote rawQuote = mapper.readValue(Paths.get(TEST_FILE_PATH).toFile(), RawQuote.class);

    //then
    assertNotNull(rawQuote.getRawQuoteValues());
    assertEquals(4, rawQuote.getRawQuoteValues().size());

    assertEquals(LocalDate.of(2019, 11, 13), rawQuote.getDateApplied());
  }

}