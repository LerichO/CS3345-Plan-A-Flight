import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * This program will process flight paths from a given txt file, FlightDataFile.txt, and organize the
 * flights into a graph in the form of an adjacency list. After this initialization, the program will
 * calculate and return the top three paths for each path requested from PathsToCalculateFile.txt to
 * OutputFile.txt.
 *
 * @author Lerich Osay
 * @version 1.0
 */
public class flightPlan {
    /**
     * This main method will begin the execution of the java program, accepting three command line
     * arguments for the desired I/O files.
     *
     * @author Lerich Osay
     * @version 1.0
     * @param args are command line arguments for defining IO files:
     *             args[0] = relative path for FlightDataFile.txt
     *             args[1] = relative path for PathsToCalculate.txt
     *             args[2] = relative path for OutputFile.txt
     */
    public static void main(String[] args) throws IOException {

        // for running debugger in IDE !!!!! COMMENT OUT WHEN RUNNING THROUGH COMMAND !!!!!
//        args = new String[3];
//        args[0] = "src\\FlightDataFile.txt";
//        args[1] = "src\\PathsToCalculateFile.txt";
//        args[2] = "src\\OutputFile.txt";


        //taking in proper IO files, will throw exception if proper file is not found
        File flightDataFile = new File(System.getProperty("user.dir") + "\\" + args[0]);
        File pathsToCalculateFile = new File(System.getProperty("user.dir") + "\\" + args[1]);
        File outputFile = new File(System.getProperty("user.dir") + "\\" + args[2]);

        //adds all flights to graph adjacency list
        AdjList adjacencyList = new AdjList(flightDataFile);

        //executing flight calculations
        adjacencyList.processPaths(pathsToCalculateFile, outputFile);
    }

}
