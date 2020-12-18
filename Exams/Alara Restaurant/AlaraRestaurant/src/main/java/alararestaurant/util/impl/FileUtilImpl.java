package alararestaurant.util.impl;

import alararestaurant.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
