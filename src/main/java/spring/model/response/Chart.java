package spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    private List<MonthBook> MonthBook;
    private List<spring.model.BookCategory> BookCategory;
    private List<MonthUser> MonthUser;
    private List<MonthPrice> MonthPrice;
}
