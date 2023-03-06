package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Orderss;
import spring.Entity.User;
import spring.model.response.MonthBook;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderssRepository extends JpaRepository<Orderss, String> {

    @Query("select u from  Orderss u where u.orderssId=:orderssId")
    Orderss findOrderssByOrderssId(@Param("orderssId") String orderssId);

    @Query("select u from Orderss u where u.orderssDate=:date and u.user=:user")
    Orderss findOrderssByOrderssDateAndUserId(Date date, User user);

    @Query("select u from Orderss u where u.fullName like %:keyword% or u.address like %:keyword% or u.status like %:keyword% or u.telephone like %:keyword% or u.pay like %:keyword%")
    List<Orderss> findOrderss(@Param("keyword") String keyword);


    @Query("select u from Orderss u where u.user.userId=:keyword")
    List<Orderss> findOrderssByUserId(@Param("keyword") String keyword);

    @Query("select new spring.Entity.month_book(month(u.orderssDate),sum(u.totalBook)) from Orderss u group by month(u.orderssDate)")
    List<MonthBook> getBookAndMonth();
}
