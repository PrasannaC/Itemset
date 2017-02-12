package com.project.be.Candidate;

import java.util.List;
import java.util.Map;

/**
 * Created by prasanna on 2/6/17.
 */
public class CandidateWorker implements Runnable {

    private List<Map.Entry<String, Integer>> items;

    public CandidateWorker(List<Map.Entry<String, Integer>> items) {
        this.items = items;
    }

    @Override
    public void run() {

    }
}
