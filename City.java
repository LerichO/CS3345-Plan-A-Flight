import java.util.LinkedList;

/**
 * This class is will serve as a representation of each list within the adjacency list. It will be an
 * enhancement of the Java LinkedList data structure which will allow for the reference of the source city
 * for this respective list of destination cities as an attribute of City type objects.
 *
 * @author Lerich Osay
 * @version 1.0
 */
public class City {
    String src;
    LinkedList<Flight> dests;

    // construct City object from attributes of City
    public City(String src, LinkedList<Flight> dests){
        this.src = src;
        this.dests = dests;
    }

    // construct empty City object
    public City(String src){
        this(src, new LinkedList<>());
    }

    public void addFlight(Flight flight){
        dests.add(flight);
    }

    public Flight getFlight(String reqDest){
        for(Flight dest : dests){
            if(dest.dest.equals(reqDest)){
                return dest;
            }
        }
        return null;
    }
}


/**
 * This class is an implementation of a weighted graph edge. Within the context of this program, this
 * class will serve the purpose of representing the individual flights between each city with the
 * cost and duration of each individual flight.
 *
 * @author Lerich Osay
 * @version 1.0
 */
class Flight {
    String src, dest;
    int cost, time;
    public Flight(String src, String dest, int cost, int time){
        this.src = src;     //u
        this.dest = dest;   //v
        this.cost = cost;   //cost weight
        this.time = time; //time duration weight
    }
    public Flight(){
        this("", "", 0, 0);
    }
}
