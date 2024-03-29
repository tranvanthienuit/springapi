package spring.Entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatingId", updatable = false)
    //    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private Long ratingId;
    @Column(name = "rating")
    private int rating;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;
}