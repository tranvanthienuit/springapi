package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Books")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book {
    //Books: id, name, categoryId, author, publishYear, nxb, dayAdded, price, status, description
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "bookId", updatable = false, nullable = false)
    private String bookId;
    @Column(name = "NameBook")
    private String nameBook;
    @Column(name = "Author")
    private String author;
    @Column(name = "PublishYear")
    private String publishYear;
    @Column(name = "PublishCom")
    private String publishCom;
    @Column(name = "DayAdd")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dayAdd;
    @Column(name = "Price")
    private Integer price;
    @Column(name = "Count")
    private Integer count;
    @Column(name = "Description")
    @Lob
    private byte[] description;
    @Column(name = "image")
    @Lob
    private byte[] image;
    @ManyToOne(cascade= {CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name = "CategoryId")
    private Categories category;

    public void setImage(String image) {
        this.image = image.getBytes();
    }

    public String getImage() {
        if (image==null)
            return null;
        return new String(image);
    }
    public void setDescription(String description) {
        this.description = description.getBytes();
    }

    public String getDescription() {
        if (description==null)
            return null;
        return new String(description);
    }
}
