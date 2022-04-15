package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Entity.Model.Book;
import spring.Entity.Model.Rating;
import spring.Repository.RatingRepository;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;
//    public void save(Rating rating){
//        ratingRepository.save(rating);
//    }
//    public List<Book> bookRating(){
//
//    }
}
