package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.factory.OrderssDeFactory;
import spring.factory.OrderssFactory;
import spring.factory.UserFactory;
import spring.model.BookCategory;
import spring.model.response.Chart;
import spring.model.response.MonthBook;
import spring.model.response.MonthPrice;
import spring.model.response.MonthUser;

import java.util.List;

@RestController
public class AdminChart {
    @Autowired
    OrderssFactory orderssFactory;
    @Autowired
    OrderssDeFactory orderssDeFactory;
    @Autowired
    UserFactory userFactory;

    @GetMapping(value = "/api/admin/chart")
    public ResponseEntity<?> chart() {
        List<MonthBook> bookAndMonth = orderssFactory.getBookAndMonth();
        if (orderssFactory.getBookAndMonth() == null || orderssFactory.getBookAndMonth().isEmpty()) {
            bookAndMonth.clear();
        }
        List<BookCategory> bookAndCategory = orderssDeFactory.getBookAndCategory();
        if (bookAndCategory == null || bookAndCategory.isEmpty()) {
            bookAndCategory.clear();
        }
        List<MonthPrice> priceAndMonth = orderssDeFactory.getPriceAndMonth();
        if (priceAndMonth == null || priceAndMonth.isEmpty()) {
            priceAndMonth.clear();
        }
        List<MonthUser> userAndMonth = userFactory.getUserAndMonnth();
        if (userAndMonth == null || userAndMonth.isEmpty()) {
            userAndMonth.clear();
        }
        Chart chart = new Chart(bookAndMonth, bookAndCategory, userAndMonth, priceAndMonth);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }
}
