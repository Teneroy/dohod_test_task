package org.teneroy;

import java.io.File;
import java.util.ArrayList;

public interface FileSystemManipulator {
    void changeFiles(String request, String absolutePath);

    void listLocalDrives();

    ArrayList<File> searchFileByName(String request, String absolutePath);
}
