package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Book;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookList {
    List<Book> bookList;
    int count;
}
