# Representative sea route 

This is a recruitment task for a logistics company.

## Task 

Given csv with routes between two seaports find the most representative sea route. There is no strict definition of what means "most representative".

Time taken: 6 hours (2.5h research + 3.5h implementation)
Note: This task would be better suited for data scientist recruitment than java developer. However sometimes you just need to provide PoC.  

![routes](src/main/resources/results/routes.png?raw=true "routes")

## Analysis

Some of the questions, even before looking into data:
1. Averaging routes seems like the most straight-forward idea. However there are some problems with it:
    1. Artifacts - by averaging we can create route that doesn't exist and is not representative
    [Artifact problem](https://ars.els-cdn.com/content/image/1-s2.0-S0031320320305331-gr4.jpg)
    2. Data is inconsistent in time - there are different ships in data set. They move at different speeds. How do we match by which index position to match. Ships may slow down or speed up so two ships who make the same route geographically may do it completely different in time domain.
    3. Data is inconsistent  different point count
    3. Solution: Instead of building a solution from given data let's find most representative sample in set (medoid)   
2. We are presented with real-life data. Real-life data is dirty:
    1. Stops in the ports - duration of trip in data may be laying e.g. first 30 minutes of the trip is ship staying in port
    2. Stops along the way - Ship should have many points at the same location but instead is presented as cluster of datapoints due to driffting or gps inaccuarcy
    3. Teleporting ship - Due to gps inaccuracy ship can do the wormhole jump on a map
    4. Solution (stop in ports): We should ignore few first points until ship 'on the move'
    5. Solution (Stops along the way): If ships speed is below certain threshold in refference to average we should cluster gps points and present them as two points: "stop_start", "stop_end"
    6. Solution (Teleporting ship): Ship speed is being measured at each point. We can calculate average of those speeds and see if ship is possible to make such a big "jump" between the points (with some major tolerance threshold). If not we should remove one of those points.
3. Most representative route - The task is to find most representative route. But what if there is no single representative for entire data set?
    1. During some months they might be a strong current forcing ships to take different way
    2. Some ships may be able to ignore the dangerous waters while smaller vessels may need to do extra way for safety reasons
    3. What if the representative route was different in the past. Maybe Somali pirates started attacking again and forced ships to change routes?
    4. Solution: Use dendrogram to compare ways and see if it doesn't make sense to provide more than one representative route for set
4. Possible solutions:
    1. Averaging - simple for well defined data. Problems: Probably not good choice for dirty data
    2. Measuring similarity distance between routes and picking a route with smallest average distance. Problems: needs measurement
    3. Heat map - devide map into boxes. For each box count how many routes hit the box. For each route calculate how much "heat" is being gathered along the way. Problems: how big to make a box, How to find if route hits the box?  
     

Further notes

This article solves similar problem to the one presented in task:
https://www.sciencedirect.com/science/article/pii/S0031320320305331

## Task (defined)
Since this is an open problem and after talk with other dev from the company I assume that this "representative" route is just used for visualisation for GUI. We care about having a nice visual shape and we don't care about time.

## Solution

I am using Dynamic time warping (DTW). This method is used to count distance between one-dimensional time series. It deals well with situation when one series is at some moments lagging behind and in other hasting in relation to other time series. It can match two similarly shaped time series well. Since the problem deals with two dimensional time series (longitude, latitude) I measure distance for each dimension separately and take the bigger value. It is ad-hoc solution but similar to idea presented in [4.6. Amin](https://www.sciencedirect.com/science/article/pii/S0031320320305331#cesectitle0016)  

1. Read data
2. Make sure that all ships go from the same port to another port, if not reverse ports and point order. It assumed that ships on route A-B and B-A have the same representative, might not be true.
3. Calculate sum of distance between chosen route and all routes in the set. Route with smallest sum is most representative

##  Result

Results as follows
![routes_with_result.png](src/main/resources/results/routes_with_result.png?raw=true "routes_with_result.png")
![result](src/main/resources/results/result.png?raw=true "result")

The most representative route was route line "667" in data set. Geojson result is in "resources\results" folder.

I am satisfied with results. Despite putting dirty data I got pretty good solution. Solution can be improved by cleaning data. Adding operations such as removing of "teleporting points" and removing outliers when calculating distance sum should highly improve the result. Both of those are easy to implement.

## Things that I would do if I got time and got paid
1. Unit tests - code is testable but test need to be written
2. Data cleaning 
3. Two step analysis(maybe) - remove routes with highest distance sum. They only pollute the data and should not be taken into account.

# How to run

Run with java 10

1. mvn clean
2. mvn install
3. Run "main" method in App class