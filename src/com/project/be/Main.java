package com.project.be;

import com.project.be.Candidate.CandidateItems;
import com.project.be.Frequency.FrequencyCount;
import com.project.be.Utils.FileReader;

import java.util.Map;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final boolean DEBUG = false;

    public static void main(String[] args) throws IOException {

        //Read the file

        FileReader.readFile("/home/prasanna/Documents/T40I10D100K.dat");
        String[] temp = new String[FileReader.getTransactionCount()];
        FrequencyCount frequencyCountObj = new FrequencyCount(FileReader.getTransactions().toArray(temp), 5);

        //Start counting all unique items in the transactions.

        frequencyCountObj.count();

        CandidateItems.GenerateAllItemsets();
        //Candidate Item List
        //Get Level 1 items.


    }

    private static void PrintMapInOrder() {
        Set<Map.Entry<String, Integer>> set = FrequencyCount.getBackingMap().entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(set);
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

}
