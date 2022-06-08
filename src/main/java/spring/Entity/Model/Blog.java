package spring.Entity.Model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Blog")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "BlogId", updatable = false, nullable = false)
    private String blogId;
    @Column(name = "title")
    private String title;
    @Column(name = "context")
    private String context;
    @Column(name = "content")
    private String content;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "UserId")
    private User user;
}
