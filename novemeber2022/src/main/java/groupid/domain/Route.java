package groupid.domain;

import static groupid.domain.Port.DEHAM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;


@Value
public class Route {

  int csvLine;
  String id;
  Port from;
  Port to;
  List<Point> points;

  public Route normalize() {
    if (from == DEHAM) {
      return this;
    }
    return new Route(csvLine, id, to, from, getReversedList(points));
  }

  private List<Point> getReversedList(List<Point> points) {
    List<Point> pointsCopy = new ArrayList<>(points);
    Collections.reverse(pointsCopy);
    return pointsCopy;
  }

  public double[] getLongitudes() {
    return points.stream()
      .map(Point::getLongitude)
      .mapToDouble(x-> x)
      .toArray();
  }

  public double[] getLatitudes() {
    return points.stream()
      .map(Point::getLongitude)
      .mapToDouble(x-> x)
      .toArray();
  }

  public String getGeojson(){
    return points.stream()
      .map(Point::getGeoJson)
      .collect(Collectors.joining(",", "[", "]"));
  }


}
