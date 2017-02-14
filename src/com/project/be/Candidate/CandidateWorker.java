package com.project.be.Candidate;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Sets.powerSet;

/**
 * Created by prasanna on 2/6/17.
 */
public class CandidateWorker implements Runnable {

    private static ConcurrentHashMap<String, Integer> candidateItems;

    public static void init() {
        candidateItems = new ConcurrentHashMap<>();
    }

    private List<Map.Entry<String, Integer>> items;
    private HashSet<String> itemsSet;

    private void push(String key, Integer value) {
        candidateItems.putIfAbsent(key, value);
    }

    public CandidateWorker(List<Map.Entry<String, Integer>> items) {
        this.items = items;
        itemsSet = new HashSet<>();
        for (Map.Entry<String, Integer> entry : items) {
            itemsSet.add(entry.getKey());
        }
    }

    public static ConcurrentHashMap<String, Integer> getCandidateItems() {
        return candidateItems;
    }

    @Override
    public void run() {
        for (Set<String> set : Sets.powerSet(itemsSet)) {
            String[] temp = set.toArray(new String[set.size()]);
            String key = String.join(" ", temp);

            if (set.isEmpty())
                continue;
            push(key, getTransactionCountForItems(temp));
        }
    }


    private int getTransactionCountForItems(String[] keys) {
        int count = 0;
        for (Map.Entry<String, List<String>> entry : CandidateItems.getTransactionItemList().entrySet()) {
            if (entry.getValue().containsAll(Arrays.asList(keys))) {
                count++;
            }
        }

        return count;
    }
}
