package spring.Controller.Admin_Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.OrderssList;
import spring.Service.BookService;
import spring.Service.OrderssDeSevice;
import spring.Service.OrderssSevice;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/seller/order","/api/admin/order"})
public class Orders {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"page/{number}"})
    public ResponseEntity<OrderssList> getAllOrderss(
            @PathVariable(name = "number", required = false) Integer page) throws Exception {
        OrderssList orderssList = new OrderssList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<spring.Entity.Model.Orderss> OrderssPage = orderssSevice.getAllOrderss(pageable);
        List<spring.Entity.Model.Orderss> orderssPageContent = OrderssPage.getContent();
        if (orderssPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            orderssList.setOrderssList(orderssPageContent);
            orderssList.setCount(orderssSevice.getAllOrderss().size());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<String> removeOrderss(@PathVariable(value = "id", required = false) String OrderssId) throws Exception {
        spring.Entity.Model.Orderss orderss = orderssSevice.findByOrderssId(OrderssId);
        if (orderss != null) {
            orderssSevice.removeByOrderssId(orderss.getOrderssId());
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"/search"})
    public ResponseEntity<List<spring.Entity.Model.Orderss>> findOrderss(@RequestBody Map<String,Object> keysearch) {
        if (keysearch == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<spring.Entity.Model.Orderss> orderssList = orderssSevice.findOrder(keysearch.get("keysearch").toString());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @PostMapping(value = {"/seller/sua-orderss", "/admin/sua-orderss"})
    public ResponseEntity<?> editeStatus(@RequestBody spring.Entity.Model.Orderss orderss) {
        spring.Entity.Model.Orderss orderss1 = orderssSevice.findByOrderssId(orderss.getOrderssId());
        orderss1.setTelephone(orderss.getTelephone());
        orderss1.setAddress(orderss.getAddress());
        orderss1.setStatus(orderss.getStatus());
        orderss1.setPay(orderss.getPay());
        orderssSevice.saveOrderss(orderss1);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
