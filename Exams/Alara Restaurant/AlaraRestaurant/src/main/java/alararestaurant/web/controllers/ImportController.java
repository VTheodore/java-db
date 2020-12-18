package alararestaurant.web.controllers;

import alararestaurant.service.EmployeeService;
import alararestaurant.service.ItemService;
import alararestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final EmployeeService employeeService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public ImportController(EmployeeService employeeService, ItemService itemService, OrderService orderService) {
        this.employeeService = employeeService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping("/json")
    public ModelAndView importJson() {
        boolean[] areImported = new boolean[]{
                this.employeeService.employeesAreImported(),
                this.itemService.itemsAreImported()
        };

        return super.view("json/import-json", "areImported", areImported);
    }

    @GetMapping("/xml")
    public ModelAndView importXml() {
        boolean[] areImported = new boolean[] {
            this.orderService.ordersAreImported()
        };

        return super.view("xml/import-xml", "areImported", areImported);
    }

    @GetMapping("/employees")
    public ModelAndView importEmployees() throws IOException {
        String employeesJsonFileContent = this.employeeService.readEmployeesJsonFile();

        return super.view("json/import-employees", "employees", employeesJsonFileContent);
    }

    @PostMapping("/employees")
    public ModelAndView importEmployeesConfirm(@RequestParam("employees") String employees) {
        System.out.println(this.employeeService.importEmployees(employees));

        return super.redirect("/import/json");
    }

    @GetMapping("/items")
    public ModelAndView importItems() throws IOException {
        String itemsJsonFileContent = this.itemService.readItemsJsonFile();

        return super.view("json/import-items", "items", itemsJsonFileContent);
    }

    @PostMapping("/items")
    public ModelAndView importItemsConfirm(@RequestParam("items") String items) {
        System.out.println(this.itemService.importItems(items));

        return super.redirect("/import/json");
    }

    @GetMapping("/orders")
    public ModelAndView importOrders() throws IOException {
        String ordersXmlFileContent = this.orderService.readOrdersXmlFile();

        return super.view("xml/import-orders", "orders", ordersXmlFileContent);
    }

    @PostMapping("/orders")
    public ModelAndView importOrdersConfirm() throws JAXBException, FileNotFoundException {
        System.out.println(this.orderService.importOrders());

        return super.redirect("/import/xml");
    }
}
