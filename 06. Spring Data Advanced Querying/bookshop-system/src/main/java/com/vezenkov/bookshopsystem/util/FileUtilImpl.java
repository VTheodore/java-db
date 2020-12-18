package com.vezenkov.bookshopsystem.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String[] readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        Set<String> res = new LinkedHashSet<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if (Constants.EMPTY_STRING.equals(line)) continue;
            res.add(line);
        }

        return res.toArray(String[]::new);
    }
}
