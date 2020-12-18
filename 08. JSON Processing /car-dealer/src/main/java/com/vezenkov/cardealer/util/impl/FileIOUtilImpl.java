package com.vezenkov.cardealer.util.impl;


import com.vezenkov.cardealer.util.FileIOUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOUtilImpl implements FileIOUtil {

    @Override
    public void writeToFile(String folderDirectory, String fileName, String content) {
        File file = this.createFile(folderDirectory, fileName);

        try {
            if (file != null) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createFile(String folderDirectory, String fileName) {
        try {
            File file = new File(folderDirectory + fileName);

            if (file.createNewFile()) {
                System.out.printf("Created file %s in %s%n", fileName, folderDirectory);
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
