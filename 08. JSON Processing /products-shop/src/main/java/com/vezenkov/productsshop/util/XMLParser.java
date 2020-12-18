package com.vezenkov.productsshop.util;

import javax.xml.bind.JAXBException;

public interface XMLParser {
    <O> void exportToXML(O object, String filePath) throws JAXBException;

    <O> O importFromXML(Class<O> aClass, String filePath);
}
