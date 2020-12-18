package com.vezenkov.cardealer.util;

import javax.xml.bind.JAXBException;
import java.io.File;

public interface XMLParser {
    <O> void exportToXML(O object, File file) throws JAXBException;

    <O> O importFromXML(Class<O> aClass, String filePath);
}
