package softuni.exam.util.impl;

import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    @Override
    public <O> O importFromFile(Class<O> aClass, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return aClass.cast(unmarshaller.unmarshal(new File(filePath)));
    }
}
