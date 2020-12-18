package alararestaurant.service;

import javax.xml.bind.JAXBException;

public interface OrderService {

    Boolean ordersAreImported();

    String readOrdersXmlFile();

    String importOrders() throws JAXBException;

    String exportOrdersFinishedByTheBurgerFlippers();
}
