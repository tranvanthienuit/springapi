package spring.model;

import lombok.Data;
import spring.Entity.User;

import java.util.List;

@Data
public class Cart {
    private List<CartBook> cartBooks;
    private User user;
    private String pay;
}
