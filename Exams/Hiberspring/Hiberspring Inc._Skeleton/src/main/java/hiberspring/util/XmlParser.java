package hiberspring.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

    <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException, FileNotFoundException;
}
