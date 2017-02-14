package com.project.be.Utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by prasanna on 1/17/17.
 */

public class FileReader {

    private static ConcurrentLinkedQueue<String> transactions = new ConcurrentLinkedQueue<>();
    private static final int chunks = 10;

    public FileReader() {

    }

    public static void addToList(String line){
        transactions.add(line);
    }

    public static ConcurrentLinkedQueue<String> getTransactions() {
        return transactions;
    }

    public static int getTransactionCount(){
        return transactions.size();
    }

    public static void readFile(String fileName) throws IOException {
        int lines = FileUtils.countLines(fileName);
        long[] offsets = new long[chunks];
        File file = new File(fileName);

        // determine line boundaries for number of chunks

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        for (int i = 1; i < chunks; i++) {
            raf.seek(i * file.length() / chunks);

            while (true) {
                int read = raf.read();
                if (read == '\n' || read == -1) {
                    break;
                }
            }

            offsets[i] = raf.getFilePointer();
        }
        raf.close();
        //System.out.println(offsets.length);
        // process each chunk using a thread for each one

        for (int i = 0; i < chunks; i++) {
            long start = offsets[i];
            long end = i < chunks - 1 ? offsets[i + 1] : file.length();
            Thread t = new Thread(new FileWorker(file,start,end));
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
