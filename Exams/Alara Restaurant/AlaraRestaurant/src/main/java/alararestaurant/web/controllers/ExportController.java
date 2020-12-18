package alararestaurant.web.controllers;

import alararestaurant.service.CategoryService;
import alararestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private final OrderService orderService;
    private final CategoryService categoryService;

    @Autowired
    public ExportController(OrderService orderService, CategoryService categoryService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping("/orders-finished-by-the-burger-flippers")
    public ModelAndView exportOrdersFinishedByTheBurgerFlippers() {
        String exportResult = this.orderService.exportOrdersFinishedByTheBurgerFlippers();

        return super.view("export/export-orders-finished-by-the-burger-flippers", "ordersFinishedByTheBurgerFlippers", exportResult);
    }

    @GetMapping("/categories-and-items")
    public ModelAndView exportCategoriesAndItems() {
        String exportResult = this.categoryService.exportCategoriesByCountOfItems();

        return super.view("export/export-categories-by-count-of-items", "categoriesByCountOfItems", exportResult);
    }
}
