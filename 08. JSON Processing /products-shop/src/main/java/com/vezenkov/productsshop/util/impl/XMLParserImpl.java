package com.vezenkov.productsshop.util.impl;

import com.vezenkov.productsshop.util.XMLParser;

import javax.xml.bind.*;
import java.io.File;

public class XMLParserImpl implements XMLParser {
    public XMLParserImpl() {
    }

    @Override
    public <O> void exportToXML(O object, String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(object, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <O> O importFromXML(Class<O> aClass, String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return aClass.cast(unmarshaller.unmarshal(file));
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
