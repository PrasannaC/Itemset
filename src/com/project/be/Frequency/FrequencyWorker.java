package com.project.be.Frequency;

import com.project.be.Candidate.CandidateItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by avani on 1/10/17.
 */
public class FrequencyWorker implements Runnable {

    private String[] source;
    private String[] splitSoruce;

    public FrequencyWorker(String[] source) {
        this.source = source;
        //this.splitSoruce =  source.split(" ");
    }


    @Override
    public void run() {

        for (int i = 0; i < source.length; i++) {
            String transaction = source[i];
            String[] arr = transaction.split(" ");
            CandidateItems.push(transaction, Arrays.asList(arr));
            for (String temp : arr) {
                FrequencyCount.push(temp);
            }
        }
    }
}
