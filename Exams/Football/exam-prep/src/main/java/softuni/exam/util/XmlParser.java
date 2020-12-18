package softuni.exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O importFromFile(Class<O> aClass, String filePath) throws JAXBException;
}
