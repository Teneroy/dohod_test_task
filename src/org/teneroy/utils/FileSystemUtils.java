package org.teneroy.utils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class FileSystemUtils {

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

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

    private static ArrayList<File> recursiveSearch(File directory, String filter, ArrayList<File> result) {
        File[] files = directory.listFiles();

        if(files == null)
            return null;

        if(result == null)
            result = new ArrayList<>();

        for (File file: files) {

            if (file.isDirectory()) {
                recursiveSearch(file, filter, result);
                continue;
            }

            if (file.getName().contains(filter)) {
                result.add(file);
            }
        }

        return result;
    }

    private static void printFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            System.out.println(st);
        br.close();
    }

    private static void writeToFile(File file) throws IOException {
        BufferedWriter writer;
        BufferedReader reader;
        String randomString = "Произвольная строка";
        String copiedFileName = file.getParent() + "\\" + "copy_" + file.getName();
        System.out.println(copiedFileName);

        File fileCopied = new File(copiedFileName);
        if(!fileCopied.createNewFile()) {
            System.out.println("This file cannot be rewritten");
            return;
        }

        reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        writer = new BufferedWriter(new FileWriter(copiedFileName, true));

        writer.append(randomString);
        writer.newLine();

        String line;
        while ((line = reader.readLine()) != null) {
            writer.append(line);
            writer.newLine();
        }

        writer.close();
        reader.close();

        if(!file.delete() || !fileCopied.renameTo(file)) {
            System.out.println("File " + file.getName() + " cannot be rewritten");
        }
    }

    public static ArrayList<File> searchFileByName(String request, String absolutePath) {
        if(absolutePath == null || absolutePath.isEmpty()) {
            System.out.println("Invalid path");
            return null;
        }

        if(request == null || request.isEmpty()) {
            System.out.println("Enter a valid search request");
            return null;
        }

        File path = new File(absolutePath);

        if(!path.isAbsolute()) {
            System.out.println("Path is not absolute");
            return null;
        }

        if(!path.exists()) {
            System.out.println("This path has not been found");
            return null;
        }

        return recursiveSearch(path, request, null);
    }

    public static void listLocalDrives() {
        Arrays.stream(File.listRoots()).map(x -> x.getPath().substring(0, 1)).filter(Predicate.not(FileSystemUtils::isNetworkDrive)).forEach(System.out::println);
    }

    public static void changeFiles(String request, String absolutePath) {
        ArrayList<File> files = searchFileByName(request, absolutePath);

        if(files == null) {
            System.out.println("Files has not been found");
            return;
        }

        files.forEach(file -> {
            if(getFileExtension(file).equals("txt"))
                return;

            System.out.println(file.getName());

            try {
                printFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        files.forEach(file -> {
            if(getFileExtension(file).equals("txt"))
                return;

            try {
                writeToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
