package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.User;

import javax.transaction.Transactional;
import java.util.Base64;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Transactional
    @Modifying
    @Query("delete from User u where u.userId=:idUser")
    void removeUserByUserId(@Param("idUser") String idUser);

    User findByNameUser(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.nameUser=:nameUser where u.userId=:userid")
    void editUserName(@Param("nameUser") String nameUser, @Param("userid") String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:pass where u.userId=:userid")
    void editUserPass(@Param("pass") String pass, @Param("userid") String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.address=:address where u.userId=:userid")
    void editUserAdress(@Param("address") String address, @Param("userid") String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.email=:email where u.userId=:userid")
    void editUserEmail(@Param("email") String email, @Param("userid") String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.telephone=:telephone where u.userId=:userid")
    void editUserTelephone(@Param("telephone") String telephone, @Param("userid") String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.sex=:sex where u.userId=:userid")
    void editUserSex(@Param("sex") String sex, @Param("userid") String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.image=:image where u.userId=:userid")
    void editImage(@Param("image") byte[] image, @Param("userid") String userid);

    User findUserByUserId(String userid);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:pass where u.userId=:userid")
    void setPassword(@Param("pass") String pass, @Param("email") String email);
}
