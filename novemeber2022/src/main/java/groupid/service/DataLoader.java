package groupid.service;

import groupid.domain.Point;
import groupid.domain.Port;
import groupid.domain.Route;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {


  private static final String ID = "ID";
  private static final String FROM = "FROM";
  private static final String TO = "TO";
  private static final String POINTS = "POINTS";
  private static final String LONGITUDE = "LONGITUDE";
  private static final String LATITUDE = "LATITUDE";


  private static final Pattern COLUMNS_PATTERN = getColumnsPattern();
  private static final Pattern POINTS_PATTERN = getPointsPattern();
//  private static final int LINE_LIMIT = 100;
  private static final int LINE_LIMIT = Integer.MAX_VALUE;
  private static final int ROUTES_IN_A_FILE = 1500 / 2;


  public List<Route> loadData() {
    InputStream is = getClass().getClassLoader()
      .getResourceAsStream("DEBRV_DEHAM_historical_routes.csv");

    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    int lineCounter = 1;
    String line;
    List<Route> routes = new ArrayList<>(ROUTES_IN_A_FILE);
    try {
      while ((line = br.readLine()) != null && lineCounter < LINE_LIMIT) {
        if (isHeaderOrBlank(line)) {
          lineCounter++;
          continue;
        }
        routes.add(toRoute(lineCounter, line));
        lineCounter++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeResources(is, br);
    }
    return routes;
  }

  private void closeResources(InputStream is, BufferedReader br) {
    try {
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Route toRoute(int lineCounter, String line) {
    Matcher matcher = COLUMNS_PATTERN.matcher(line);
    matcher.find();
    return new Route(lineCounter, matcher.group(ID), toPort(matcher.group(FROM)),
      toPort(matcher.group(TO)), toPoints(matcher.group(POINTS)));
  }

  private Port toPort(String port) {
    return Port.valueOf(port);
  }

  private List<Point> toPoints(String pointsString) {
    pointsString = pointsString.substring(1, pointsString.length() - 1);
    Matcher matcher = POINTS_PATTERN.matcher(pointsString);
    List<Point> points = new ArrayList<>();
    while (matcher.find()) {
      points.add(getPoint(matcher));
    }
    return points;
  }

  private Point getPoint(Matcher matcher) {
    return new Point(toDouble(matcher, LONGITUDE), toDouble(matcher, LATITUDE));
  }

  private double toDouble(Matcher matcher, String groupName) {
    String longitude = matcher.group(groupName);
    return Double.valueOf(longitude);
  }

  private boolean isHeaderOrBlank(String line) {
    return line.contains("leg_duration") || line.trim().isEmpty();
  }

  private static Pattern getColumnsPattern() {
    String patternTemplate = "\"(?<%s>.+?)\",\".+?\",\".+?\",\"(?<%s>.+?)\",\"(?<%s>.+?)\",\".+?\",\".+?\",\"(?<%s>.+?)\"";
    return Pattern.compile(String.format(patternTemplate, ID, FROM, TO, POINTS));
  }

  private static Pattern getPointsPattern() {
    String patternTemplate = "\\[(?<%s>.+?),(?<%s>.+?),.+?\\]";
    return Pattern.compile(String.format(patternTemplate, LONGITUDE, LATITUDE));
  }


}
