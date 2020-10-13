package net.sympower.cityzen.apx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Test;

public class RawQuoteValueTest {

  private static final String TEST_FILE_PATH = "src/test/resources/net/sympower/cityzen/apx/quote-value.json";
  private static final String TLABEL = "Order";
  private static final double VALUE = 1;
  private static final double DELTA = 1e-15;

  @Test
  public void readValue_givenTestFile_shouldCreateRawQuoteValue() throws IOException {
    //given
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    //when
    RawQuoteValue rawQuoteValue = mapper.readValue(Paths.get(TEST_FILE_PATH).toFile(), RawQuoteValue.class);

    //then
    assertNotNull(rawQuoteValue);
    assertEquals(VALUE, rawQuoteValue.getValue(), DELTA);
    assertEquals(TLABEL, rawQuoteValue.getTLabel());
  }
}
