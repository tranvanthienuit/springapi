package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Entity.Model.Rating;

public interface RatingRepository extends JpaRepository<Rating,String> {
}
