package groupid.service;

import groupid.domain.Route;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Value;
import smile.math.distance.DynamicTimeWarping;

public class MedoidFinder {

  public Route findMedoid(List<Route> routes) {
    Map<DistancePair, Double> distanceMap = new HashMap<>();
    return routes.stream()
      .peek(this::printProcessing)
      .map(route -> getRouteWithDistanceSum(route, routes, distanceMap))
      .sorted(Comparator.comparing(RouteWithDistanceSum::getDistanceSum))
      .map(RouteWithDistanceSum::getRoute)
      .findFirst().orElseThrow(() -> new RuntimeException("No routes"));
  }

  private void printProcessing(Route route) {
    int csvLine = route.getCsvLine();
    if (csvLine % 10 == 0) {
      System.out.println("Processing " + csvLine);
    }
  }

  private RouteWithDistanceSum getRouteWithDistanceSum(Route route, List<Route> routes,
    Map<DistancePair, Double> distanceMap) {
    return new RouteWithDistanceSum(route, getDistanceSum(route, routes, distanceMap));

  }

  private double getDistanceSum(Route route, List<Route> routes,
    Map<DistancePair, Double> distanceMap) {
    double latitudeDistance = getDistanceForCoordinate(route, routes, Coordinate.LATITUDE,
      distanceMap);
    double longitudeDistance = getDistanceForCoordinate(route, routes, Coordinate.LONGITUDE,
      distanceMap);

    return Math.max(latitudeDistance, longitudeDistance);
  }

  private double getDistanceForCoordinate(Route route, List<Route> routes,
    Coordinate coordinate, Map<DistancePair, Double> distanceMap) {
    return routes.stream()
      .map(someRoute -> countDistance(route, someRoute, coordinate, distanceMap))
      .mapToDouble(distance -> distance)
      .sum();
  }

  private Double countDistance(Route route1, Route route2, Coordinate coordinate,
    Map<DistancePair, Double> distanceMap) {
    DistancePair distancePair = getDistancePair(route1, route2, coordinate);
    return distanceMap.computeIfAbsent(distancePair, key ->
      getDistanceForCoordinate(route1, route2, coordinate));
  }

  private DistancePair getDistancePair(Route route1, Route route2, Coordinate coordinate) {
    return new DistancePair(route1.getCsvLine(), route2.getCsvLine(), coordinate);
  }

  private Double getDistanceForCoordinate(Route route1, Route route2, Coordinate coordinate) {
    if (coordinate == Coordinate.LATITUDE) {
      return DynamicTimeWarping.d(route1.getLatitudes(), route2.getLatitudes());
    }

    return DynamicTimeWarping.d(route1.getLongitudes(), route2.getLongitudes());
  }


  @Value
  private class RouteWithDistanceSum {

    Route route;
    //TODO instead of passing distance sum we should pass order distance list and then cut off
    // bottom 5% and top 5% in order to remove outliers
    double distanceSum;
  }


  @Value
  class DistancePair {

    private final int csvLine1;
    private final int csvLine2;
    private final Coordinate coordinate;

    DistancePair(int csvLine1, int csvLine2,
      Coordinate coordinate) {
      if (csvLine1 > csvLine2) {
        int biggerValue = csvLine1;
        csvLine1 = csvLine2;
        csvLine2 = biggerValue;
      }
      this.coordinate = coordinate;
      this.csvLine1 = csvLine1;
      this.csvLine2 = csvLine2;
    }
  }

  enum Coordinate {
    LATITUDE, LONGITUDE
  }

}
