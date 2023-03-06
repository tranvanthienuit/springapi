package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Orderss;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderssList {
    List<Orderss> orderssList;
    int count;
}
