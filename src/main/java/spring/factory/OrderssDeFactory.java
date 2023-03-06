package spring.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Book;
import spring.Entity.OrderssDetail;
import spring.model.BookCategory;
import spring.model.BookSelect;
import spring.model.response.MonthPrice;
import spring.repository.BookRepository;
import spring.repository.OrderssDeRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderssDeFactory {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderssDeRepository orderssDeRepository;

    public void removeByOrderssDeId(String orderssDeId) {
        OrderssDetail orderssDetail = orderssDeRepository.findOrderssDetailByOrderssDeId(orderssDeId);
        orderssDeRepository.delete(orderssDetail);
    }

    public void saveOrderssDe(OrderssDetail orderssDetail) {
        orderssDeRepository.save(orderssDetail);
    }

    public Page<OrderssDetail> getAllOrderssDe(Pageable pageable) {
        return orderssDeRepository.findAll(pageable);
    }

    public List<OrderssDetail> findOrderssDe(String orderssId) {
        return orderssDeRepository.findAllByOrderssId(orderssId);
    }

    public List<OrderssDetail> findOrderssDetailsByOrderss(String orderssId) {
        return orderssDeRepository.findOrderssDetailsByOrderss(orderssId);
    }

    public List<Book> getBookFromBorrDe(Pageable pageable) {
        List<BookSelect> objects = orderssDeRepository.getBookFromBorrDe(pageable);
        List<Book> bookList = new ArrayList<>();
        for (BookSelect bookSelect : objects) {
            bookList.add(bookSelect.getBook());
        }
        return bookList;
    }

    public List<OrderssDetail> getAllOrderssDe() {
        return orderssDeRepository.findAll();
    }

    public List<BookCategory> getBookAndCategory() {
        return orderssDeRepository.getBookAndCategory();
    }

    public List<MonthPrice> getPriceAndMonth() {
        return orderssDeRepository.getPriceAndMonth();
    }
}
