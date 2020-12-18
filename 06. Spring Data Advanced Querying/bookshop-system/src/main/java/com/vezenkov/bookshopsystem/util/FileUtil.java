package com.vezenkov.bookshopsystem.util;

import java.io.IOException;

public interface FileUtil {
    String[] readFileContent(String filePath) throws IOException;
}
