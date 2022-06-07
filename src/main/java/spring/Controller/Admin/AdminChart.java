package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.Chart;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;
import spring.Service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminChart {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    UserService userService;
    @GetMapping(value = "/admin/chart")
    public ResponseEntity<?> chart(){
        Map<Integer,Integer> bookAndMonth = new HashMap<>();
        if (orderssSevice.getBookAndMonth() == null || orderssSevice.getBookAndMonth().isEmpty()){
            bookAndMonth.clear();
        }

        Map<Integer,String> bookAndCategory = new HashMap<>();
        if (orderssDeSevice.getBookAndCategory() == null || orderssDeSevice.getBookAndCategory().isEmpty()){
            bookAndCategory.clear();
        }
        Map<Integer,Double> priceAndMonth = new HashMap<>();
        if (orderssDeSevice.getPriceAndMonth() == null || orderssDeSevice.getPriceAndMonth().isEmpty()){
            priceAndMonth.clear();
        }
        Map<Integer,Integer> userAndMonth = new HashMap<>();
        if (userService.getUserAndMonnth() == null || userService.getUserAndMonnth().isEmpty()){
            userAndMonth.clear();
        }
        Chart chart = new Chart(bookAndMonth,bookAndCategory,userAndMonth,priceAndMonth);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }
}
