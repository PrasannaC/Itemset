package com.project.be.Candidate;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.SortedSetMultimap;
import com.project.be.Frequency.FrequencyCount;
import com.project.be.Utils.FileReader;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by avani on 1/17/17.
 */

// ".*\\b{num}\\b.*"
public class CandidateItems {

    private static final double MIN_SUPPORT_PERCENTAGE = 15.00;
    private static final double MIN_SUPPORT_CALC = (double) (MIN_SUPPORT_PERCENTAGE / 100) * (double) FileReader.getTransactionCount();

    private static ConcurrentHashMap<String, List<String>> transactionItemList;

    static {

        transactionItemList = new ConcurrentHashMap<>();
    }

    public static ConcurrentHashMap<String, List<String>> getTransactionItemList() {
        return transactionItemList;
    }

    public static void push(String key, List<String> value) {
        transactionItemList.putIfAbsent(key, value);
    }


    public static void GenerateAllItemsets() {

        System.out.println("GenerateAllItemsets");

        List<Map.Entry<String, Integer>> lstItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : FrequencyCount.getBackingMap().entrySet()) {
            if (entry.getValue() >= MIN_SUPPORT_CALC) {
                lstItems.add(entry);
            }
        }
        System.out.println(lstItems.size());
        CandidateWorker.init();
        System.out.println("GenerateAllItemsets Thread Create");
        for (List<Map.Entry<String, Integer>> tempList : Lists.partition(lstItems, 20)) {
            CandidateWorker worker = new CandidateWorker(tempList);

            Thread t = new Thread(worker);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(CandidateWorker.getCandidateItems());

    }


    /*public static List<Map.Entry<String, Integer>> getCandidateItemList(int level) {
        List<Map.Entry<String, Integer>> lstItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : FrequencyCount.getBackingMap().entrySet()) {
            if (entry.getValue() >= MIN_SUPPORT_CALC) {
                lstItems.add(entry);
            }
        }

        return lstItems;
    }*/

}
