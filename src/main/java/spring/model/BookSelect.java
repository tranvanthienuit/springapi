package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSelect {
    private Book book;
    private Long total;
}
