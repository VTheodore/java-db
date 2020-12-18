package hiberspring.util.impl;

import hiberspring.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class XmlParserImpl implements XmlParser {
    public XmlParserImpl() {
    }

    @Override
    public <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return objectClass.cast(unmarshaller.unmarshal(new File(filePath)));
    }
}
