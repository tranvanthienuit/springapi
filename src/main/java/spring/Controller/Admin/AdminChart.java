package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;
import spring.Service.UserService;

import java.util.Map;

@RestController
public class AdminChart {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    UserService userService;
    @GetMapping(value = "/chart")
    public ResponseEntity<?> chart(){
        Map<Integer,Double> doubleMap =orderssSevice.getBookAndMonth();
        return new ResponseEntity<>(doubleMap, HttpStatus.OK);
    }
}
