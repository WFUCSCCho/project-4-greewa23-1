/**********************************************************************
 * @file Proj4.java
 * @brief This program implements the Proj4 class. It
 * takes two command line arguments and performs the hash table insert, search,
 * and delete operations using already-sorted, shuffled, and reversed dataset
 * lists as input. It finds the time for the insert, search, and delete operation
 * performances for the different number of inputs.
 * @author Wynne Greene
 * @date: December 5, 2024
 ***********************************************************************/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.FileWriter;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0]; //Filename
        int numLines = Integer.parseInt(args[1]); //Number of lines
        SeparateChainingHashTable<Volcano> hashTable = new SeparateChainingHashTable<>(); //The hashtable of Volcano objects
        ArrayList<Volcano> origList = new ArrayList<Volcano>();   //The list of Volcano objects

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        try {
            // Open the input file
            inputFileNameStream = new FileInputStream(inputFileName);
            inputFileNameScanner = new Scanner(inputFileNameStream);

            // ignore first line
            inputFileNameScanner.nextLine();

            // FINISH ME
            int count = 0; //count keeps track of the line count.
            //Traverse numLines.
            while (count < numLines) {//inputFileNameScanner.hasNext()) {
                //Scan in the line.
                String line = inputFileNameScanner.nextLine();
                String[] parts = line.split(","); // split the string into multiple parts

                Volcano v; //Store the data in v.
                //Condition for exceptions
                if (parts.length != 11) {
                    //data stores the values of each variable.
                    ArrayList<String> data = new ArrayList<>();
                    //s is updated with entries that need to be combined.
                    String s = "";
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].indexOf("\"") != -1) {
                            s += parts[i];
                            i++;
                            //Loop until the last quote is found.
                            while (i < parts.length) {
                                s += "," + parts[i];
                                if (parts[i].indexOf("\"") != -1) {
                                    break;
                                }
                                i++;
                            }
                            data.add(s);
                        } else {
                            data.add(parts[i]);
                        }
                    }
                    //Update v with the data.
                    v = new Volcano(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4),
                            Double.parseDouble(data.get(5)), Double.parseDouble(data.get(6)), Integer.parseInt(data.get(7)), data.get(8), data.get(9), data.get(10));
                } else {
                    //Update v with the data from the array.
                    v = new Volcano(parts[0], parts[1], parts[2], parts[3], parts[4],
                            Double.parseDouble(parts[5]), Double.parseDouble(parts[6]), Integer.parseInt(parts[7]), parts[8], parts[9], parts[10]);
                }
                origList.add(v); // add the data onto the ArrayList
                count++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        //This block guarantees the file is closed.
        finally {
            if (inputFileNameStream != null) {
                //The input file is closed.
                inputFileNameStream.close();
            }
        }

        //Create File Writer to open the output file.
        FileWriter out = null;
        try {
            //Create or open the file to write to it.
            out = new FileWriter("analysis.txt", true);

            //To determine execution time, declare start and time.
            //Find the time by calculating the difference between start and time.

            //Changed between shuffled, sorted, and reversed to see runtimes of the data.
            //Complete the operations on the sorted list.
            Collections.sort(origList);
            //Calculate insert time. Iterate through the list, inserting the elements into the hashtable.
            long startInsert = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                hashTable.insert(origList.get(i));
            }
            long timeInsert = System.nanoTime();
            //Calculate search time. Iterate through the list, searching for the elements in the hashtable.
            long startSearch = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                boolean search = hashTable.contains(origList.get(i));
            }
            long timeSearch = System.nanoTime();
            //Calculate deletion time. Iterate through the list, deleting the elements in the hashtable.
            long startDelete = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                hashTable.remove(origList.get(i));
            }
            long timeDelete = System.nanoTime();

            //Print out the data and write to the output file.
            System.out.println("Sorted: Number of Lines=" + numLines + ", Insert Time(ns)=" + (timeInsert - startInsert)
                    + ", Search Time(ns)=" + (timeSearch - startSearch) + ", Remove Time(ns)=" + (timeDelete - startDelete));
            out.write(numLines + "," + (timeInsert - startInsert)
                    + "," + (timeSearch - startSearch) + "," + (timeDelete - startDelete) + "\n");

            //Do the same operations on the shuffled list.
            Collections.shuffle(origList);

            //Calculate insert time. Iterate through the list, inserting the elements into the hashtable.
            startInsert = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                hashTable.insert(origList.get(i));
            }
            timeInsert = System.nanoTime();

            //Calculate search time. Iterate through the list, searching for the elements in the hashtable.
            startSearch = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                boolean search = hashTable.contains(origList.get(i));
            }
            timeSearch = System.nanoTime();

            //Calculate deletion time. Iterate through the list, deleting the elements in the hashtable.
            startDelete = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                hashTable.remove(origList.get(i));
            }
            timeDelete = System.nanoTime();

            //Print out the data and write to the output file.
            System.out.println("Shuffled: Number of Lines=" + numLines + ", Insert Time(ns)=" + (timeInsert - startInsert)
                    + ", Search Time(ns)=" + (timeSearch - startSearch) + ", Remove Time(ns)=" + (timeDelete - startDelete));
            out.write(numLines + "," + (timeInsert - startInsert)
                    + "," + (timeSearch - startSearch) + "," + (timeDelete - startDelete) + "\n");

            //Do operations on the reversed list.
            Collections.sort(origList, Collections.reverseOrder());

            //Calculate insert time. Iterate through the list, inserting the elements into the hashtable.
            startInsert = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                hashTable.insert(origList.get(i));
            }
            timeInsert = System.nanoTime();

            //Calculate search time. Iterate through the list, searching for the elements in the hashtable.
            startSearch = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                boolean search = hashTable.contains(origList.get(i));
            }
            timeSearch = System.nanoTime();

            //Calculate deletion time. Iterate through the list, deleting the elements in the hashtable.
            startDelete = System.nanoTime();
            for(int i = 0; i<origList.size(); i++) {
                hashTable.remove(origList.get(i));
            }
            timeDelete = System.nanoTime();

            //Print out the data and write to the output file.
            System.out.println("Reversed: Number of Lines=" + numLines + ", Insert Time(ns)=" + (timeInsert - startInsert)
                    + ", Search Time(ns)=" + (timeSearch - startSearch) + ", Remove Time(ns)=" + (timeDelete - startDelete));
            out.write(numLines + "," + (timeInsert - startInsert)
                    + "," + (timeSearch - startSearch) + "," + (timeDelete - startDelete) + "\n");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return;
        } finally {
            out.close();
        }
    }
}
