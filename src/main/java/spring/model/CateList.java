package spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Category;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateList {
    List<Category> categoryList;
    int count;
}
