package net.sympower.cityzen.apx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class RawQuoteResponseTest {

  private static final String TEST_FILE_PATH = "src/test/resources/net/sympower/cityzen/apx/apx-data.json";

  @Test
  public void readValue_givenTestFile_shouldCreateRawQuote() throws IOException {
    //given
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    //when
    RawQuoteResponse rawQuoteResponse = mapper.readValue(Paths.get(TEST_FILE_PATH).toFile(), RawQuoteResponse.class);

    //then
    assertNotNull(rawQuoteResponse);
    assertNotNull(rawQuoteResponse.getQuotes());
    assertEquals(3, rawQuoteResponse.getQuotes().size());
    List<String> l;
  }


}