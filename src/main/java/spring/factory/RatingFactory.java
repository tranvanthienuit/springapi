package spring.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import spring.Entity.Rating;
import spring.repository.RatingRepository;

@Service
public class RatingFactory {
    @Autowired
    RatingRepository ratingRepository;

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void delete() {
        ratingRepository.deleteRatingByRating();
    }
}
