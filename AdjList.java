import java.io.*;
import java.util.*;

/**
 * This class is an implementation of the graph data structure as an adjacency list. The graph will be
 * used to organize flight flights from the 'FlightDataFile.txt' and perform the appropriate operations
 * required for the program
 *
 * @author Lerich Osay
 * @version 1.0
 */
public class AdjList {

    // non-repeating set of all cities
    private final ArrayList<String> cityNames = new ArrayList<>();

    // adjacency list (linked list of linked lists)
    private LinkedList<City> adjacencyList;

    //  implementation using adjacency list using LinkedList of LinkedLists
    // (City is practically a LinkedList with added attribute of source city of all flights/destinations)
    public AdjList(File flightFile) throws FileNotFoundException {

        // define scanner, delimiter for scanner, and initialize list of all flights
        Scanner scan = new Scanner(flightFile).useDelimiter("[|" + System.getProperty("line.separator") + "]");
        List<Flight> flightList = new LinkedList<>();

        // listing flights from each line of FlightDataFile.txt
        int numOfFlights = Integer.parseInt(scan.nextLine());
        for(int i = 0; i < numOfFlights; i++){
            String start = scan.next();
            String end = scan.next();
            int cost = Integer.parseInt(scan.next());
            int time = Integer.parseInt(scan.next());
            flightList.add(new Flight(start, end, cost, time));
            flightList.add(new Flight(end, start, cost, time));
            if(scan.hasNext())
                scan.nextLine();
        }

        // constructing adjacency list from list of all flights
        adjacencyList = new LinkedList<>();
        for(Flight flight : flightList) {
            boolean cityExists = false;

            int j;
            for (j = 0; j < adjacencyList.size(); j++) {

                // keep track of all existing cities
                if(!cityNames.contains(flight.src))
                    cityNames.add(flight.src);
                if(!cityNames.contains(flight.dest))
                    cityNames.add(flight.dest);

                // add flight to existing source city in the adjacency list
                if (adjacencyList.get(j).src.equals(flight.src)) {
                    adjacencyList.get(j).addFlight(flight);
                    cityExists = true;
                    break;
                }
            }

            // add source city to the adjacency list
            if (!cityExists) {
                City cityToAdd = new City(flight.src);
                cityToAdd.addFlight(flight);
                adjacencyList.add(cityToAdd);
            }
        }
    }

    // method is called from main() method to begin processing paths to calculate
    public void processPaths(File requestedPathsFile, File outputFile) throws IOException{

        // clear output file if it already has data written to it
        outputFile.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile, false));
        out.write("");
        out.close();

        // define scanner and delimiter
        Scanner scan = new Scanner(requestedPathsFile).useDelimiter("[|" + System.getProperty("line.separator") + "]");
        
        // processing requested paths from each line of PathsToCalculate.txt
        int numOfPaths = Integer.parseInt(scan.nextLine());
        for(int i = 0; i < numOfPaths; i++){
            String start = scan.next();
            String end = scan.next();
            String sort = scan.next();

            out = new BufferedWriter(new FileWriter(outputFile, true));

            // declaring paths (DAG) resulting from first shortest-path
            HashMap<String, CityStack> paths;

            // shortest-path based on time
            if(sort.equals("T")) {
                String msg = "Flight " + (i + 1) + ": " + start + ", " + end + " (Time)\n";
                out.write(msg);

                // run algorithm for shortest time
                paths = shortestPathTime(start, new ArrayList<>());
                out.write(outputPath(start, end, paths.get(end), sort) + "\n");
                out.close();
            }

            // shortest-path based on cost
            else if (sort.equals("C")) {
                String msg = "Flight " + (i + 1) + ": " + start + ", " + end + " (Cost)\n";
                out.write(msg);

                // run algorithm for shortest cost
                paths = shortestPathCost(start, new ArrayList<>());
                out.write(outputPath(start, end, paths.get(end), sort) + "\n");
                out.close();
            }

            if(scan.hasNext())
                scan.nextLine();
        }
    }

    // Requested file algorithms:
    // perform search for shortest path (Dijkstras) for each city, returns all shortest paths based on requested sort
    // each city has its own stack representing shortest-path
    // could probably combine these into the same method
    private HashMap<String, CityStack> shortestPathTime(String reqStart, List<Flight> poppedFlights) {

        // construction
        HashMap<String, CityStack> paths = new HashMap<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        for(String city : cityNames){
            paths.put(city, new CityStack("time"));
            visited.put(city, false);
        }
        paths.replace(reqStart, new CityStack(0));

        // meat of shortest-path algorithm
        for(int j = 0; j < cityNames.size(); j++) {

            // minDist method from Khan's Dijkstra's example
            int min = Integer.MAX_VALUE;
            int minInd = -1;
            for (int v = 0; v < cityNames.size(); v++) {
                if (!visited.get(cityNames.get(v)) && paths.get(cityNames.get(v)).getTotalTime() <= min) {
                    min = paths.get(cityNames.get(v)).getTotalTime();
                    minInd = v;
                }
            }

            // processing current city
            visited.replace(cityNames.get(minInd), true);


            for (String currentCity : cityNames) {

                // relax path
                Flight reqFlight = getFlight(cityNames.get(minInd), currentCity);
                if (!visited.get(currentCity)
                        && reqFlight != null
                        && !poppedFlights.contains(reqFlight)
                        && paths.get(cityNames.get(minInd)).getTotalTime() != Integer.MAX_VALUE
                        && paths.get(cityNames.get(minInd)).getTotalTime() + reqFlight.time < paths.get(currentCity).getTotalTime()) {

                    // replace the CityStack path of current city
                    // HashMap with String name of city → CityStack backtracking shortest-path
                    CityStack newStack = new CityStack(paths.get(cityNames.get(minInd)));
                    newStack.push(reqFlight);
                    paths.replace(currentCity, newStack);
                }
            }
        }

        return paths;
    }

    private HashMap<String, CityStack> shortestPathCost(String reqStart, List<Flight> poppedFlights) {

        // construction
        HashMap<String, CityStack> paths = new HashMap<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        for(String city : cityNames){
            paths.put(city, new CityStack("cost"));
            visited.put(city, false);
        }
        paths.replace(reqStart, new CityStack(0));

        // meat of shortest-path algorithm
        for(int j = 0; j < cityNames.size(); j++){

            // minDist method from Khan's Dijkstra's example
            int min = Integer.MAX_VALUE;
            int minInd = -1;
            for(int v = 0; v < cityNames.size(); v++){
                if(!visited.get(cityNames.get(v)) && paths.get(cityNames.get(v)).getTotalCost() <= min){
                    min = paths.get(cityNames.get(v)).getTotalCost();
                    minInd = v;
                }
            }

            // processing current city
            visited.replace(cityNames.get(minInd), true);


            for(String currentCity : cityNames){

                // relax path
                Flight reqFlight = getFlight(cityNames.get(minInd), currentCity);
                if( !visited.get(currentCity)
                        && reqFlight != null
                        && !poppedFlights.contains(reqFlight)
                        && paths.get(cityNames.get(minInd)).getTotalCost() != Integer.MAX_VALUE
                        && paths.get(cityNames.get(minInd)).getTotalCost() + reqFlight.time < paths.get(currentCity).getTotalCost()){

                    // replace the CityStack path of current city
                    // HashMap with String name of city → CityStack backtracking shortest-path
                    CityStack newStack = new CityStack(paths.get(cityNames.get(minInd)));
                    newStack.push(reqFlight);
                    paths.replace(currentCity, newStack);
                }
            }
        }

        return paths;
    }

    //I will say, start and end should not need to be used here but my own structure has forced my hand
    private String outputPath(String start, String end, CityStack path, String sort){

        String msg = "";
        List<Flight> poppedFlights = new ArrayList<>();

        //for loop backtracks twice to return top three shortest paths
        for(int rank = 1; rank <= 3; rank++){

            // try/catch in case there are less than 3 paths
            try{
                CityStack pathStack = new CityStack(path);
                msg += "Path " + rank + ": ";

                // stack into stack, not the best
                // reverse stack to be popped into message text
                Stack<String> msgStack = new Stack<>();
                int pathStackSize = pathStack.size();
                for(int i = 0; i < pathStackSize; i++){
                    msgStack.push(pathStack.pop().src);
                }

                // pop stack of cities from shortest-path into message text
                int msgStackSize = msgStack.size();
                for(int j = 0; j < msgStackSize; j++){
                    msg += msgStack.pop() + " -> ";
                }
                msg += end + ". Time: " + path.getTotalTime() + " Cost: " + path.getTotalCost() + ".00\n";

                /*** BACKTRACKING USING THE STACK IMPLEMENTATION CityStack ***/
                poppedFlights.add(path.pop());
                if (sort.equals("T")){
                    path = shortestPathTime(start, poppedFlights).get(end);
                    if(path.getTotalTime() >= Integer.MAX_VALUE)
                        return msg;
                }
                else if (sort.equals("C")){
                    path = shortestPathCost(start, poppedFlights).get(end);
                    if(path.getTotalCost() >= Integer.MAX_VALUE)
                        return msg;
                }

            }

            //return errror message if path not found, or end list of shortest paths if no 2nd or #rd
            catch(Exception e){
                if(rank == 1)
                    return "No path could be calculated.";
                else
                    return msg;
            }

        }

        return msg;
    }

    // helper method to be used to find if flight exists in graph
    private Flight getFlight(String start, String end){
        for(City city : adjacencyList){
            if(city.src.equals(start) && city.getFlight(end) != null){
                return city.getFlight(end);
            }
        }
        return null;
    }

}
