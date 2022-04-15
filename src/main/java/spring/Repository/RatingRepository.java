package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.Entity.BookRating;
import spring.Entity.Model.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,String> {
    @Query("select new spring.Entity.BookRating(u,avg (u.rating))from Rating u group by u.bookId")
    List<BookRating> getAllBookRating();
}
