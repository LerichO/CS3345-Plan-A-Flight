import java.util.LinkedList;

/**
 * This class is an implementation of the stack data structure. The CityStack will be used for storing
 * the shortest path from a given source to the destination that this stack is an attribute of. It will
 * then be used during backtracking to then trace the next two shortest routes. This will most likely be
 * implemented and used primarily in the shortest path algorithm itself.
 *
 * @author Lerich Osay
 * @version 1.0
 */
public class CityStack {
    private LinkedList<Flight> list;

    private int totalCost;
    private int totalTime;

    // construct new CityStack object from exisint CityStack object
    public CityStack(CityStack cityStack){
        this.totalCost = cityStack.getTotalCost();
        this.totalTime = cityStack.getTotalTime();
        this.list = cityStack.toList();
    }

    // use for start city in shortest-path algorithm
    public CityStack(int weight){

        this.totalCost = weight;
        this.totalTime = weight;
        this.list = new LinkedList<>();
    }

    // construct stack during construction of Dijkstra's
    public CityStack(String sort){
        if (sort.equals("cost"))
            this.totalCost = Integer.MAX_VALUE;
        else if (sort.equals("time"))
            this.totalTime = Integer.MAX_VALUE;

        this.list = new LinkedList<>();

    }

    public int size(){
        return list.size();
    }

    public int getTotalCost(){

        // reimplement this method for using stack in shortest path algorithm
//        for(int i = 0; i < list.size() - 1; i++){
//            totalCost += list.get(i).costToDest(list.get(i + 1).src);
//        }
        return totalCost;
    }

    public int getTotalTime(){

        // reimplement this method for using stack in shortest path algorithm
//        for(int i = 0; i < list.size() - 1; i++){
//            totalTime += list.get(i).timeToDest(list.get(i + 1).src);
//        }
        return totalTime;
    }

    // push flight / destination city to stack and update weights
    public void push(Flight cityToBePushed){
        totalTime += cityToBePushed.time;
        totalCost += cityToBePushed.cost;
        list.add(cityToBePushed);
    }

    // pop flight / destination city from stack and update weights
    public Flight pop(){
        Flight cityToBePopped = list.getLast();
        totalTime -= cityToBePopped.time;;
        totalCost -= cityToBePopped.cost;
        list.removeLast();
        return cityToBePopped;
    }

    // return destination city without popping
    public String peek(){
        return list.getLast().dest;
    }

    // pop stack to given city, will be used for backtracking algorithm
    public boolean popToCity(String start){
        if(contains(start)) {
            for(Flight flight : list){
                this.pop();
                if(flight.src.equals(start))
                    break;
            }

            return true;
        }
        else{
            return false;
        }
    }

    private boolean contains(String city){
        for(Flight flight : list){
            if(flight.src.equals(city))
                return true;
        }
        return false;
    }

    public LinkedList<Flight> toList(){
        return new LinkedList<>(list);
    }

}
