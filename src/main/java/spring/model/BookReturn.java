package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Book;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReturn {
    private BookList bookList;
    private List<Book> bookOder;
    private List<Book> bookRating;
}
