package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.Entity.Rating;

import javax.transaction.Transactional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    //    @Query("select new spring.Entity.BookRating(u.book,avg (u.rating)) from Rating u group by u order by avg (u.ratingId)")
//    List<Book> getAllBookRating(Pageable pageable);
    @Modifying
    @Transactional
    @Query("delete from Rating u where u.ratingId > 100")
    void deleteRatingByRating();
}
