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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatingId", updatable = false, nullable = false)
    //    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private Long ratingId;
    @ManyToOne(cascade= {CascadeType.MERGE,CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne(cascade= {CascadeType.MERGE,CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name = "book")
    private Book book;
    @Column(name = "rating")
    private int rating;
}
