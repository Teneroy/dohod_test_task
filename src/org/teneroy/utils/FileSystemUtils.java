package org.teneroy.utils;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FileSystemUtils {

    private static boolean isNetworkDrive(String driveLetter) {
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

    private static void recursiveSearch(File directory, String filter) {
        File[] files = directory.listFiles();

        if(files == null)
            return;

        String filename;
        for (File file: files) {
            if (file.isDirectory())
                recursiveSearch(file, filter);

            filename = file.getName();

            if (filename.contains(filter))
                System.out.println(filename);
        }
    }

    public static void listLocalDrives() {
        Arrays.stream(File.listRoots()).map(x -> x.getPath().substring(0, 1)).filter(Predicate.not(FileSystemUtils::isNetworkDrive)).forEach(System.out::println);
    }

    public static void searchFileByName(String request, String absolutePath) {
        if(absolutePath == null || absolutePath.isEmpty()) {
            System.out.println("Invalid path");
            return;
        }

        if(request == null || request.isEmpty()) {
            System.out.println("Enter a valid search request");
            return;
        }

        File path = new File(absolutePath);

        if(!path.isAbsolute()) {
            System.out.println("Path is not absolute");
        }

        if(!path.exists()) {
            System.out.println("This path has not been found");
        }

        recursiveSearch(path, request);
    }
}
