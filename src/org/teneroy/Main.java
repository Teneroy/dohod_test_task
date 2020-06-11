package org.teneroy;

import org.teneroy.utils.FileSystemUtils;

import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        System.out.println(Arrays.toString(args));

        //FileSystemUtils.listLocalDrives();
        //Objects.requireNonNull(FileSystemUtils.searchFileByName("sf", "C:\\testSearch")).forEach(System.out::println);
        //FileSystemUtils.changeFiles("sf", "C:\\testSearch");
    }
}
