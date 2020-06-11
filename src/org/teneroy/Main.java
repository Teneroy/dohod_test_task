package org.teneroy;

import org.teneroy.utils.FileSystemUtils;

public class Main {

    public static void main(String[] args) {
        FileSystemUtils.listLocalDrives();
        FileSystemUtils.searchFileByName("sf.txt", "C:\\testSearch");
    }
}
