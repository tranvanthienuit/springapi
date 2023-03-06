package spring.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Orderss;
import spring.model.response.MonthBook;
import spring.repository.OrderssRepository;

import java.util.List;

@Service
public class OrderssFactory {
    @Autowired
    OrderssRepository orderssRepository;

    public Orderss findByOrderssId(String orderssId) {
        return orderssRepository.findOrderssByOrderssId(orderssId);
    }

    public void removeByOrderssId(String orderssId) {
        Orderss orderss = orderssRepository.findOrderssByOrderssId(orderssId);
        orderssRepository.delete(orderss);
    }


    public void saveOrderss(Orderss orderss) {
        orderssRepository.save(orderss);
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

    public List<MonthBook> getBookAndMonth() {
        return orderssRepository.getBookAndMonth();
    }
}
