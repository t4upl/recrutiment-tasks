package net.sympower.cityzen.apx;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApxDataLoader {

  @Value("${apxDataLoader.url}")
  private String urlStr;

  @Setter(AccessLevel.PACKAGE)
  private URL url;

  public void init() throws MalformedURLException {
    if (this.url == null) {
      this.url = new URL(urlStr);
    }
  }

  QuoteResponse load() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    RawQuoteResponse rawQuoteResponse = mapper.readValue(url, RawQuoteResponse.class);
    return new QuoteResponse(rawQuoteResponse);
  }
  
}
