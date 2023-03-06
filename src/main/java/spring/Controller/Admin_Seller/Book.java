package spring.Controller.Admin_Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.factory.BookFactory;
import spring.factory.CategoryFactory;
import spring.model.BookList;

import java.util.List;

@RestController
public class Book {
    @Autowired
    BookFactory booksService;
    @Autowired
    CategoryFactory categoryFactory;

    @GetMapping(value = {"api/seller/page/{number}", "api/admin/page/{number}"})
    public ResponseEntity<BookList> getAllBook(
            @PathVariable(name = "number", required = false) Integer page) throws Exception {
        BookList bookList = new BookList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<spring.Entity.Book> bookPage = booksService.getAllBookByAdmin(pageable);
        List<spring.Entity.Book> bookPageContent = bookPage.getContent();
        if (bookPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            bookList.setBookList(bookPageContent);
            bookList.setCount(booksService.getAllBook().size());
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }
}
