package spring.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.Entity.Book;
import spring.Entity.BorrowDetail;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface BorrowDeRepository extends JpaRepository<BorrowDetail, String> {
    @Transactional
    @Modifying
    @Query("delete from BorrowDetail u where u.borrowDeId=:idBorrowDe")
    public void removeByBorrowDeId(@Param("idBorrowDe") String idBorrowDe);
    @Transactional
    @Modifying
    @Query("delete from BorrowDetail u where u.borrow=:idBorrow ")
    public void removeByBorrowId(@Param("idBorrow") String idBorrow);
    BorrowDetail findBorrowDetailByBorrowDeId(String idBorrowDe);
    @Query("select u.book,count(u.count) from BorrowDetail u group by u.book")
    public Page<Book> getBookFromBorrDe(Pageable pageable);
    @Query("select u.book,count(u.count) from BorrowDetail u where u.borrow.user.userId=:userId group by u.book")
    public Page<Book> getBookFromBorrDeAndUser(Pageable pageable,@Param("userId")String userId);
}
