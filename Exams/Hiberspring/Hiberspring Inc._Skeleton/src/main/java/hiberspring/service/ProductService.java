package hiberspring.service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
//TODO
public interface ProductService {

    Boolean productsAreImported();

    String readProductsXmlFile() throws IOException;

    String importProducts() throws JAXBException, FileNotFoundException;
}
