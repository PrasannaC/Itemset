package com.project.be.Frequency;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by avani on 1/10/17.
 */

public class FrequencyCount {

    private String[] transactions;
    private int maxDegreeOfParallelism;
    private static ConcurrentMap<String, Integer> backingMap;

    public FrequencyCount() {

    }

    public FrequencyCount(String[] transactions, int maxDegreeOfParallelism) {
        this.transactions = transactions;
        this.maxDegreeOfParallelism = maxDegreeOfParallelism;
    }

    public static ConcurrentMap<String, Integer> getBackingMap() {
        return backingMap;
    }

    public String[][] splitTransactions(final String[] data, final int chunkSize)
    {
        final int length = data.length;
        final String[][] dest = new String[(length + chunkSize - 1)/chunkSize][];
        int destIndex = 0;
        int stopIndex = 0;

        for (int startIndex = 0; startIndex + chunkSize <= length; startIndex += chunkSize)
        {
            stopIndex += chunkSize;
            dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
        }

        if (stopIndex < length)
            dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);
        return dest;
    }


    public void count() {
        int blockSize = transactions.length / maxDegreeOfParallelism;
        String[][] tempArr = splitTransactions(transactions,blockSize);
        for (int i = 0; i < tempArr.length; i++) {

            FrequencyWorker f = new FrequencyWorker(tempArr[i]);
            Thread t = new Thread(f);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void push(String key) {
        if (backingMap == null)
            backingMap = new ConcurrentHashMap<>(10, 8f, 64);

        if (!backingMap.containsKey(key)) {
            backingMap.put(key, 1);
        } else {
            backingMap.put(key, backingMap.get(key) + 1);
           // System.out.println("Incrementing value of " + key + "to " + backingMap.put(key, backingMap.get(key) + 1));
        }
    }
}
