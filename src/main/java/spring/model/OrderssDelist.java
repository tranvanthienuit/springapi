package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.OrderssDetail;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderssDelist {
    List<OrderssDetail> OrderssDelists;
    int count;
}
