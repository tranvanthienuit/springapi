package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Rating;
import spring.Repository.RatingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;
    public void save(Rating rating){
        ratingRepository.save(rating);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void delete(){
        ratingRepository.deleteRatingByRating();
    }
}
