package alararestaurant.web.controllers;

import alararestaurant.service.EmployeeService;
import alararestaurant.service.ItemService;
import alararestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    private final EmployeeService employeeService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public HomeController(EmployeeService employeeService, ItemService itemService, OrderService orderService) {
        this.employeeService = employeeService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        boolean[] areImported = new boolean[]{
                this.employeeService.employeesAreImported() && this.itemService.itemsAreImported(), this.orderService.ordersAreImported()
        };

        return super.view("index", "areImported", areImported);
    }
}
