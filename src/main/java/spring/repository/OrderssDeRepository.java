package spring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.OrderssDetail;
import spring.model.BookCategory;
import spring.model.BookSelect;
import spring.model.response.MonthPrice;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface OrderssDeRepository extends JpaRepository<OrderssDetail, String> {

    @Transactional
    @Modifying
    @Query("delete from OrderssDetail u where u.orderss.orderssId=:orderssId ")
    void removeByOrderssId(@Param("orderssId") String orderssId);

    @Query("select u from OrderssDetail u where u.orderssDeId=:orderssDeId")
    OrderssDetail findOrderssDetailByOrderssDeId(String orderssDeId);

    @Query("select u from OrderssDetail u where u.orderss.orderssId=:orderssId")
    List<OrderssDetail> findAllByOrderssId(@Param("orderssId") String orderssId);

    @Query("select new spring.Entity.BookSelect(u.book,sum(u.count)) from OrderssDetail u group by u.book.bookId order by sum(u.count) desc ")
    List<BookSelect> getBookFromBorrDe(Pageable pageable);

    @Query("select new spring.Entity.BookSelect(u.book,sum(u.count)) from OrderssDetail u where u.orderss.user.userId=:userId group by u.book.bookId order by sum (u.count) desc ")
    List<BookSelect> getBookFromBorrDeAndUser(Pageable pageable, @Param("userId") String userId);

    @Query("select u from OrderssDetail u where u.orderss.orderssId=:OrderssId")
    List<OrderssDetail> findOrderssDetailsByOrderss(String OrderssId);

    @Query("select new spring.Entity.book_category(sum(u.count),u.book.category.nameCate) from OrderssDetail u group by u.book.category.nameCate")
    List<BookCategory> getBookAndCategory();

    @Query("select new spring.Entity.month_price(month(u.orderss.orderssDate),sum(u.total)) from OrderssDetail u group by month(u.orderss.orderssDate)")
    List<MonthPrice> getPriceAndMonth();
}
