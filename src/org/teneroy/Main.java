package org.teneroy;

import org.teneroy.utils.FileSystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("You have to choose the particular command");
            return;
        }

        ArrayList<File> files;

        FileSystemService systemService = new FileSystemService();

        switch (args[0]) {
            case "-readDrives" -> systemService.listLocalDrives();
            case "-searchFiles" -> {
                if (args.length < 3) {
                    System.out.println("Incorrect parameters");
                    return;
                }
                if ((files = systemService.searchFileByName(args[1], args[2])) != null)
                    files.forEach(System.out::println);
            }
            case "-changeFiles" -> {
                if (args.length < 3) {
                    System.out.println("Incorrect parameters");
                    return;
                }
                systemService.changeFiles(args[1], args[2]);
            }
            default -> System.out.println("This command is incorrect");
        }


    }
}
