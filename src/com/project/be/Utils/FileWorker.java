package com.project.be.Utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by avani on 1/17/17.
 */
public class FileWorker implements Runnable {
    private final File file;
    private final long start;
    private final long end;

    public FileWorker(File file, long start, long end) {
        this.file = file;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(start);

            while (raf.getFilePointer() < end) {
                String line = raf.readLine();
                if (line == null) {
                    continue;
                }
                FileReader.addToList(line);
                // do what you need per line here
                //System.out.println(line);
            }

            raf.close();
        } catch (IOException e) {
            // deal with exception
        }
    }
}
