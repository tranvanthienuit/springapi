package spring.Entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Categories {
    //Categories: id, name
    @Id
    @GeneratedValue(generator = "uuid",strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "categoryId", updatable = false, nullable = false)
    private String categoryId;
    @Column(name = "NameCategory")
    private String nameCate;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Book> books;

}