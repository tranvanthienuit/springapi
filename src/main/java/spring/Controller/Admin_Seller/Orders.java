package spring.Controller.Admin_Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Orderss;
import spring.factory.BookFactory;
import spring.factory.OrderssDeFactory;
import spring.factory.OrderssFactory;
import spring.model.OrderssList;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/seller/order", "/api/admin/order"})
public class Orders {
    @Autowired
    OrderssFactory orderssFactory;
    @Autowired
    OrderssDeFactory orderssDeFactory;
    @Autowired
    BookFactory bookFactory;

    @GetMapping(value = {"page/{number}"})
    public ResponseEntity<OrderssList> getAllOrderss(
            @PathVariable(name = "number", required = false) Integer page) throws Exception {
        OrderssList orderssList = new OrderssList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<Orderss> OrderssPage = orderssFactory.getAllOrderss(pageable);
        List<Orderss> orderssPageContent = OrderssPage.getContent();
        if (orderssPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            orderssList.setOrderssList(orderssPageContent);
            orderssList.setCount(orderssFactory.getAllOrderss().size());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<String> removeOrderss(@PathVariable(value = "id", required = false) String OrderssId) throws Exception {
        Orderss orderss = orderssFactory.findByOrderssId(OrderssId);
        if (orderss != null) {
            orderssFactory.removeByOrderssId(orderss.getOrderssId());
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"/search"})
    public ResponseEntity<List<Orderss>> findOrderss(@RequestBody Map<String, Object> keysearch) {
        if (keysearch == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<Orderss> orderssList = orderssFactory.findOrder(keysearch.get("keysearch").toString());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @PostMapping(value = {"/seller/sua-orderss", "/admin/sua-orderss"})
    public ResponseEntity<?> editeStatus(@RequestBody Orderss orderss) {
        Orderss orderss1 = orderssFactory.findByOrderssId(orderss.getOrderssId());
        orderss1.setTelephone(orderss.getTelephone());
        orderss1.setAddress(orderss.getAddress());
        orderss1.setStatus(orderss.getStatus());
        orderss1.setPay(orderss.getPay());
        orderssFactory.saveOrderss(orderss1);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
