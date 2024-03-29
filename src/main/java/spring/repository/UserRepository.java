package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.User;
import spring.model.response.MonthUser;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    User findUserByUserId(String userId);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:pass where u.email=:email")
    void setPassword(@Param("pass") String pass, @Param("email") String email);

    @Query("select new spring.Entity.month_user(month(u.dayAdd),count(u.userId)) from User u group by month(u.dayAdd)")
    List<MonthUser> getUserAndMonnth();

    User findByEmail(String mail);

    @Query(value = "select * from users inner join roles on users.role_id = roles.role_id where address like %:keyword% or email like %:keyword% or full_name like %:keyword% or name_user like %:keyword% or sex like %:keyword% or telephone like %:keyword% or name_role like %:keyword% ", nativeQuery = true)
    List<User> findUser(@Param("keyword") String keyword);
}
