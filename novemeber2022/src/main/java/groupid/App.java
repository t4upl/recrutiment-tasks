package groupid;

import groupid.domain.Route;
import groupid.service.DataLoader;
import groupid.service.MedoidFinder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {


  public static void main(String[] args) {
    System.out.println("START");
    List<Route> routes = loadData();
    routes = normalizeRoutesDirection(routes);
    //TODO add speed analysis to remove error points and cluster ship stops

    Route medoidRoute = findMedoid(routes);
    System.out.println("ANSWER: " + medoidRoute.getCsvLine());
    System.out.println("ANSWER: " + medoidRoute.getGeojson());
    System.out.println("END");
  }

  private static Route findMedoid(List<Route> routes) {
    MedoidFinder medoidFinder = new MedoidFinder();
    return medoidFinder.findMedoid(routes);
  }

  private static List<Route> normalizeRoutesDirection(List<Route> routes) {
    return routes.stream()
      .map(Route::normalize)
      .collect(Collectors.toList());
  }

  private static List<Route> loadData() {
    DataLoader dataLoader = new DataLoader();
    return dataLoader.loadData();
  }
}
