package org.teneroy.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileSystemUtils {

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    public static boolean isNetworkDrive(String driveLetter) {
        List<String> cmd = Arrays.asList("cmd", "/c", "net", "use", driveLetter + ":");

        try {
            Process p = new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start();

            p.getOutputStream().close();

            return p.waitFor() == 0;
        } catch(Exception e) {
            throw new IllegalStateException("Unable to run 'net use' on " + driveLetter, e);
        }

    }

    public static void printFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            System.out.println(st);
        br.close();
    }
}
