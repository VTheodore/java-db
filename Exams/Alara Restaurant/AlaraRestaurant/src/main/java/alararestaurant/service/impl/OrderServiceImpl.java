package alararestaurant.service.impl;

import alararestaurant.domain.dtos.ItemNameAndQuantityDto;
import alararestaurant.domain.dtos.OrderSeedRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.OrderRepository;
import alararestaurant.service.EmployeeService;
import alararestaurant.service.ItemService;
import alararestaurant.service.OrderService;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

import java.util.HashSet;
import java.util.Set;

import static alararestaurant.util.GlobalConstants.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final EmployeeService employeeService;

    private final ItemService itemService;

    private final XmlParser xmlParser;

    private final ModelMapper modelMapper;

    private final FileUtil fileUtil;

    private final ValidationUtil validationUtil;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, EmployeeService employeeService, ItemService itemService, XmlParser xmlParser, ModelMapper modelMapper, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.orderRepository = orderRepository;
        this.employeeService = employeeService;
        this.itemService = itemService;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() {
        return this.fileUtil.readFile(ORDERS_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        OrderSeedRootDto orderSeedRootDto = this.xmlParser.parseXml(OrderSeedRootDto.class, ORDERS_FILE_PATH);
        StringBuilder ordersOutput = new StringBuilder();

        orderSeedRootDto
                .getOrders()
                .forEach(orderSeedDto -> {
                    if (this.validationUtil.isValid(orderSeedDto)) {
                        Employee employee = this.employeeService.getEmployeeByName(orderSeedDto.getEmployee());

                        if (employee == null) {
                            ordersOutput.append(ERROR_MESSAGE).append(System.lineSeparator());
                            return;
                        }

                        Order order = this.modelMapper.map(orderSeedDto, Order.class);
                        order.setEmployee(employee);

                        Set<OrderItem> orderItems = new HashSet<>();
                        for (ItemNameAndQuantityDto itemDto : orderSeedDto.getItems().getItems()) {
                            Item item = this.itemService.getItemByName(itemDto.getName());
                            if (item == null) {
                                ordersOutput.append(ERROR_MESSAGE).append(System.lineSeparator());
                                return;
                            }

                            OrderItem orderItem = new OrderItem();
                            orderItem.setItem(item);
                            orderItem.setOrder(order);
                            orderItem.setQuantity(itemDto.getQuantity());
                            orderItems.add(orderItem);
                        }

                        order.setOrderItems(orderItems);
                        this.orderRepository.save(order);
                        ordersOutput.append(String.format(ORDER_MESSAGE, order.getCustomer(), order.getDateTime()));
                    } else {
                        ordersOutput.append(ERROR_MESSAGE);
                    }

                    ordersOutput.append(System.lineSeparator());
                });
        return ordersOutput.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        // TODO : Implement me
        return null;
    }
}
