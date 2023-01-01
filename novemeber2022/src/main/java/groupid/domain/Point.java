package groupid.domain;

import lombok.Value;

@Value
public class Point {

  double longitude;
  double latitude;

  String getGeoJson() {
    return String.format("[%s, %s]", longitude, latitude);
  }

}
