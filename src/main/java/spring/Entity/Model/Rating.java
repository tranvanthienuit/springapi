package spring.Entity.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Rating {
    @Id
    @GeneratedValue()
    @Column(name = "RatingId", updatable = false, nullable = false)
    //    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String ratingId;
    private String userId;
    private String bookId;
    private int rating;
}
