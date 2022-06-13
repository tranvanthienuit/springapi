package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Orderss;
import spring.Entity.Model.User;
import spring.Entity.month_book;
import spring.Repository.OrderssRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderssSevice {
    @Autowired
    OrderssRepository orderssRepository;

    public Orderss findOrderssByOrderssId(String idOrderss) {
        return orderssRepository.findOrderssByOrderssId(idOrderss);
    }

    public void removeOrderssByOrderssId(String idOrderss) {
        Orderss orderss = orderssRepository.findOrderssByOrderssId(idOrderss);
        orderssRepository.delete(orderss);
    }


    public void saveOrderss(Orderss orderss) {
        orderssRepository.save(orderss);
    }

    public Orderss findOrderssByOrderssDateAndUserId(Date date, User user) {
        return orderssRepository.findOrderssByOrderssDateAndUserId(date, user);
    }

    public Page<Orderss> getAllOrderss(Pageable pageable) {
        return orderssRepository.findAll(pageable);
    }

    public List<Orderss> findOrder(String keysearch) {
        if (orderssRepository.findOrderssByUserId(keysearch).size() != 0)
            return orderssRepository.findOrderssByUserId(keysearch);
        return orderssRepository.findOrderss(keysearch);
    }

    public List<Orderss> getAllOrderss() {
        return orderssRepository.findAll();
    }

    public List<month_book> getBookAndMonth() {
        return orderssRepository.getBookAndMonth();
    }
}
